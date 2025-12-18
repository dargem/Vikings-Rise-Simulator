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
        TimedEffect status_effect, 
        SkillType skill_type, 
        SkillCondition skill_condition, 
        CombatantEvent skill_dependent, 
        SkillTarget skill_target, 
        double chance,
        EffectType effect_type, 
        bool is_removable
    );

    void onDependent(Combatant& self, Combatant& target) const override;
    bool operator==(const Skill& other) const override;
    [[nodiscard]] static std::vector<CombatantEvent> getTriggerEvents();
private:
    const TimedEffect status_effect;
    const bool is_removable;
    const EffectType effect_type;
};

#endif