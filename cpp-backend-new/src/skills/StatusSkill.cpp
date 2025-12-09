#include "StatusSkill.hpp"

void StatusSkill::onDependent(Combatant& combatant_friendly, Combatant& combatant_enemy) const
{

}

std::vector<CombatEvent> StatusSkill::getTriggerEvents() const
{
    return {};
}

bool operator==(const StatusSkill& other) 
{
    return false;
}
