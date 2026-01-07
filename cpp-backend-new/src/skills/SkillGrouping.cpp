#include "skills/SkillGrouping.hpp"

SkillGrouping::SkillGrouping(
    SkillType skill_type, 
    Condition skill_condition, 
    CombatantEvent dependent, 
    double chance
)
    : Skill(skill_type, skill_condition, dependent, chance)
{}

void SkillGrouping::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    // early return if shouldn't trigger
    if (!Skill::checkCondition(self, target, number_generator))
    {
        return;
    }

    for (const auto& skill: skills)
    {
        skill->onDependent(self, target, number_generator);
    }
}

void SkillGrouping::addSkill(std::unique_ptr<Skill> skill)
{
    skills.push_back(std::move(skill));
}
