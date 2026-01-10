#include "skills/StatusSkill.hpp"
#include "combatants/Combatant.hpp"

StatusSkill::StatusSkill(
    const TimedEffect status_effect, 
    const SkillType skill_type, 
    const Condition skill_condition, 
    const CombatantEvent skill_dependent, 
    const SkillTarget skill_target,
    double chance,
    const EffectType effect_type, 
    const bool is_removable
)
    : Skill(skill_type, skill_condition, skill_dependent, chance),
    status_effect(status_effect),
    effect_type { effect_type },
    is_removable { is_removable },
    skill_target { skill_target },
    apply_func { skill_target == SkillTarget::FRIENDLY ? &applyToSelf : &applyToTarget }
{}

void StatusSkill::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    if (Skill::checkCondition(self, target, number_generator))
    {
        const double scalars = 1;
        TimedEffect sent_effect(status_effect, scalars);

        apply_func(self, target, sent_effect, effect_type);
    }
}

bool StatusSkill::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const StatusSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}

static void applyToSelf(Combatant& self, Combatant& target, const TimedEffect& effect, EffectType type) 
{
    self.addStatusEffect(effect, type);
}

static void applyToTarget(Combatant& self, Combatant& target, const TimedEffect& effect, EffectType type) 
{
    target.addStatusEffect(effect, type);
}