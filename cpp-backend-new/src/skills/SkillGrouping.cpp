#include "skills/SkillGrouping.hpp"

SkillGrouping::SkillGrouping(CombatantEvent dependent, double chance)
    : dependent { dependent },
    chance { chance },
    always_triggers { chance == 1.0 }
{}

void SkillGrouping::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    // early return if shouldn't trigger
    if (!checkShouldTrigger(number_generator))
    {
        return;
    }

    for (const auto& skill: skills)
    {
        skill->onDependent(self, target, number_generator);
    }
}

inline bool SkillGrouping::checkShouldTrigger(NumberGenerator& number_generator) const
{
    return always_triggers || number_generator.getRandomDouble() < chance;
}