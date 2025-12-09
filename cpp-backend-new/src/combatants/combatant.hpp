#ifndef COMBATANT_HPP
#define COMBATANT_HPP

#include "../effects/StatusEffect.hpp"
#include "../orchestration/CombatPublisher.hpp"
#include "../skills/Skill.hpp"
#include "Stats.hpp"
#include <vector>

class Combatant
{
public:
    Combatant(int troops, Stats stats);

    void addSkill(Skill skill);
    void addStatusEffect(StatusEffect status_effect);
    void addBuffEffect(StatusEffect buff_effect);

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
    
    std::vector<StatusEffect> status_effects;
    std::vector<StatusEffect> buff_effects;
};

#endif