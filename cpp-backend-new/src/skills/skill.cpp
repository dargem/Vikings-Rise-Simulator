#include "Skill.hpp"

void Skill::onDependent(Combatant& combatant_friendly, Combatant& combatant_enemy) const
{

}

std::vector<CombatEvent> Skill::getTriggerEvents() const
{
    return {};
}

bool operator==(const Skill& skill_1, const Skill& skill_2) 
{
    return false;
}
