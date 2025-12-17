#include "skills/Skill.hpp"

Skill::Skill(
    SkillType skill_type, 
    const SkillCondition skill_condition, 
    CombatantEvent skill_dependent, 
    SkillTarget target
)
    : skill_type { skill_type },
    skill_condition { skill_condition },
    skill_dependent { skill_dependent },
    target { target }
{}

SkillType Skill::getSkillType() const 
{
    return skill_type;
}

bool Skill::checkCondition(const Combatant& friendly, const Combatant& target) const
{
    return skill_condition.isMet(friendly, target);
}

SkillTarget Skill::getSkillTarget() const
{
    return target;
}