#ifndef STATUS_SKILL_HPP
#define STATUS_SKILL_HPP

#include "SkillType.hpp"
#include <vector>
#include "../orchestration/CombatantEvent.hpp"
#include "../effects/TimedEffect.hpp"
#include "../effects/EffectType.hpp"
#include "Skill.hpp"
// forward declaration for combatant to prevent a circular dependency
class Combatant;

class StatusSkill: public Skill
{
    public:
        StatusSkill(const TimedEffect status_effect, const SkillType skill_type, const EffectType effect_type);
        void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const override;
        bool operator==(const Skill& other) const override;
        std::vector<CombatantEvent> getTriggerEvents() const;
    private:
        const TimedEffect status_effect;
};

#endif