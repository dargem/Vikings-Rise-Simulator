#ifndef COMBATANT_HPP
#define COMBATANT_HPP

#include "../effects/TimedEffect.hpp"
#include "../orchestration/CombatPublisher.hpp"
#include "../orchestration/CombatantEvents.hpp"
#include "../skills/Skill.hpp"
#include "Stats.hpp"
#include <vector>

class Combatant
{
public:
    Combatant(int troops, Stats stats);

    void addSkill(Skill& skill, CombatantEvent combat_event);
    void addStatusEffect(TimedEffect status_effect);
    void addBuffEffect(TimedEffect buff_effect);

    void setTroops(int troops);
    int getTroops();
    void setStats(Stats stats);
    double getAttack();
    double getDefense();
    double getHealth();

private:
    int troops;
    Stats stats;
    CombatPublisher combat_publisher;
    
    std::vector<TimedEffect> status_effects;
    std::vector<TimedEffect> buff_effects;
    std::vector<const Skill*> skills;
};

#endif