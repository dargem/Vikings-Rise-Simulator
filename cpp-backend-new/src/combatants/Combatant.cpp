#include "Combatant.hpp"

/**
 * @brief Construct a new Combatant:: Combatant object
 * 
 * @param troops how many troops to load it with
 * @param stats the stats of a troop
 * @param combat_publisher reference to publisher combat events to listens
 */
Combatant::Combatant(int troops, Stats stats) 
    : troops{troops},
    stats{stats}
{}

/**
 * @brief adds a status effect to the combatant
 * 
 * @param status_effect value passed status effect
 */
void Combatant::addStatusEffect(StatusEffect status_effect) 
{
    status_effects.push_back(status_effect);
}

/**
 * @brief adds a buff effect to the combatant
 * 
 * @param buff_effect value passed buff effect
 */
void Combatant::addBuffEffect(StatusEffect buff_effect) 
{
    buff_effects.push_back(buff_effect);
}

void Combatant::addSkill(ISkill& skill, CombatantEvent combat_event)
{
    combat_publisher.subToEvent(skill, combat_event);
}

void Combatant::setTroops(int troops) 
{
    this->troops = troops;
}

int Combatant::getTroops()
{
    return troops;
}

void Combatant::setStats(Stats stats)
{
    this->stats = stats;
}

double Combatant::getAttack()
{
    return stats.attack;
}

double Combatant::getDefense()
{
    return stats.defense;
}

double Combatant::getHealth()
{
    return stats.health;
}
