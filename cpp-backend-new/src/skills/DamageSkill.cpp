#include "skills/DamageSkill.hpp"

DamageSkill::DamageSkill(const double damage_magnitude, const SkillType skill_type, const EffectType effect_type,
                         SkillCondition skill_condition, SkillTarget skill_target) 
    : Skill(skill_type, effect_type, skill_condition, skill_target),
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
