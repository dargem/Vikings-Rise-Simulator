#include "Skill.hpp"

Skill::Skill(SkillType skill_type, EffectType effect_type, SkillCondition skill_condition, SkillTarget target)
    : skill_type { skill_type },
    effect_type { effect_type },
    skill_condition { skill_condition },
    target { target }
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

SkillTarget Skill::getSkillTarget() const
{
    return target;
}