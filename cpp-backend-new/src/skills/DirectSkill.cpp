#include "skills/DirectSkill.hpp"

DirectSkill::DirectSkill(
    SkillType skill_type,
    Condition skill_condition, 
    CombatantEvent skill_dependent, 
    SkillTarget skill_target,
    double chance,
    DirectEffectType effect,
    double damage_magnitude
) 
    : Skill(skill_type, skill_condition, skill_dependent, skill_target, chance),
    effect { effect },
    damage_magnitude { damage_magnitude }
{}

void DirectSkill::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{}

bool DirectSkill::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const DirectSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}
