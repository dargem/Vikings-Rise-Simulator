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
void Combatant::addStatusEffect(TimedEffect timed_effect, EffectType type)
{
    status_controller.addStatusEffect(timed_effect, type);
}

bool Combatant::checkEffectActive(const EffectType effect_type) const
{
    return status_controller.checkEffectActive(effect_type);
}

void Combatant::addSkill(const Skill& skill, const CombatantEvent combat_event)
{
    combat_publisher.subToEvent(skill, combat_event);
}

void Combatant::setTroops(int troops) 
{
    this->troops = troops;
}

int Combatant::getTroops() const
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
