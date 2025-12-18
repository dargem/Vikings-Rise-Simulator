#include "skills/Skill.hpp"

Skill::Skill(
    SkillType skill_type, 
    SkillCondition skill_condition, 
    CombatantEvent skill_dependent, 
    SkillTarget target,
    double chance
)
    : skill_type { skill_type },
    skill_condition { skill_condition },
    skill_dependent { skill_dependent },
    target { target },
    chance { chance },
    always_triggers { chance == 1.0 }
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