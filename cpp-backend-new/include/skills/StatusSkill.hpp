#ifndef STATUS_SKILL_HPP
#define STATUS_SKILL_HPP

#include "skills/SkillType.hpp"
#include <vector>
#include "orchestration/CombatantEvent.hpp"
#include "effects/TimedEffect.hpp"
#include "effects/EffectType.hpp"
#include "skills/Skill.hpp"
#include "combatants/Combatant.hpp"

template<SkillTarget Target> 
class StatusSkill: public Skill
{
public:
    StatusSkill(
        TimedEffect status_effect, 
        SkillType skill_type, 
        Condition skill_condition, 
        CombatantEvent skill_dependent, 
        double chance,
        EffectType effect_type, 
        bool is_removable
    );

    void onDependent(
        Combatant& self, 
        Combatant& target, 
        NumberGenerator& number_generator
    ) const override;
    
    bool operator==(const Skill& other) const override;
private:
    const TimedEffect status_effect;
    const bool is_removable;
    const EffectType effect_type;
};

template<SkillTarget Target>
StatusSkill<Target>::StatusSkill(
    const TimedEffect status_effect, 
    const SkillType skill_type, 
    const Condition skill_condition, 
    const CombatantEvent skill_dependent, 
    const double chance,
    const EffectType effect_type, 
    const bool is_removable
)
    : Skill(skill_type, skill_condition, skill_dependent, chance),
    status_effect(status_effect),
    effect_type { effect_type },
    is_removable { is_removable }
{}

template<SkillTarget Target>
void StatusSkill<Target>::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    if (Skill::checkCondition(self, target, number_generator))
    {
        const double scalars = 1;
        TimedEffect sent_effect(status_effect, scalars);

        if constexpr (Target == SkillTarget::FRIENDLY)
        {
            self.addStatusEffect(sent_effect, effect_type);
        }
        else
        {
            target.addStatusEffect(sent_effect, effect_type);
        }
    }
}

template<SkillTarget Target>
bool StatusSkill<Target>::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const StatusSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}

#endif