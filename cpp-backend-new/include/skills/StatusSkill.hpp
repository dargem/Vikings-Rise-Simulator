#ifndef STATUS_SKILL_HPP
#define STATUS_SKILL_HPP

#include "skills/SkillType.hpp"
#include <vector>
#include "orchestration/CombatantEvent.hpp"
#include "effects/TimedEffect.hpp"
#include "effects/EffectType.hpp"
#include "skills/Skill.hpp"
// forward declaration for combatant to prevent a circular dependency
class Combatant;

class StatusSkill: public Skill
{
public:
    StatusSkill(
        const TimedEffect status_effect, 
        const SkillType skill_type, 
        const EffectType effect_type, 
        const SkillCondition skill_condition, 
        const CombatantEvent skill_dependent, 
        const SkillTarget skill_target, 
        const bool is_removable
    );

    void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const override;
    bool operator==(const Skill& other) const override;
    std::vector<CombatantEvent> getTriggerEvents() const;
private:
    const TimedEffect status_effect;
    const bool is_removable;
    const EffectType effect_type;
};

#endif