#include "skills/Skill.hpp"

Skill::Skill(
    SkillType skill_type, 
    Condition skill_condition, 
    CombatantEvent skill_dependent, 
    double chance
)
    : skill_type { skill_type },
    skill_condition { skill_condition },
    skill_dependent { skill_dependent },
    chance { chance },
    always_triggers { chance == 1.0 }
{}

SkillType Skill::getSkillType() const 
{
    return skill_type;
}

// checks if the skill should trigger
// checks if skill condition is met
// checks if probability condition is met
bool Skill::checkCondition(
    const Combatant& friendly, 
    const Combatant& target, 
    NumberGenerator& number_generator
) const
{
    return(
        skill_condition.isMet(friendly, target) && 
        (always_triggers || number_generator.getRandomDouble() < chance)
    );
}

CombatantEvent Skill::getSkillDependent() const
{
    return skill_dependent;
}