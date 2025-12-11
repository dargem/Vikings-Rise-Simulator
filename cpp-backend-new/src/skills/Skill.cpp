#include "Skill.hpp"

Skill::Skill(const SkillType skill_type, const EffectType effect_type, const SkillCondition skill_condition)
    : skill_type { skill_type },
    effect_type { effect_type },
    skill_condition { skill_condition }
{}

SkillType Skill::getSkillType() const 
{
    return skill_type;
}

EffectType Skill::getEffectType() const
{
    return effect_type;
}

bool Skill::checkCondition(const Combatant& friendly, const Combatant& target) const
{
    return skill_condition.isMet(friendly, target);
}