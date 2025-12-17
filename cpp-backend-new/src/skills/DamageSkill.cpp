#include "skills/DamageSkill.hpp"

DamageSkill::DamageSkill(
    const double damage_magnitude, 
    const SkillType skill_type,
    const SkillCondition skill_condition, 
    const CombatantEvent skill_dependent, 
    const SkillTarget skill_target
) 
    : Skill(skill_type, skill_condition, skill_dependent, skill_target),
    damage_magnitude { damage_magnitude }
{}

void DamageSkill::onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const
{}

bool DamageSkill::operator==(const Skill& other) const
{
    const DamageSkill* other_skill = dynamic_cast<const DamageSkill*>(&other);
    if (other_skill)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}
