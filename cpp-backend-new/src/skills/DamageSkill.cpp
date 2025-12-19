#include "skills/DamageSkill.hpp"

DamageSkill::DamageSkill(
    SkillType skill_type,
    Condition skill_condition, 
    CombatantEvent skill_dependent, 
    SkillTarget skill_target,
    double chance,
    double damage_magnitude
) 
    : Skill(skill_type, skill_condition, skill_dependent, skill_target, chance),
    damage_magnitude { damage_magnitude }
{}

void DamageSkill::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{}

bool DamageSkill::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const DamageSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}
