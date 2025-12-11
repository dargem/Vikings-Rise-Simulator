#ifndef COMBATANT_HPP
#define COMBATANT_HPP

#include "../effects/TimedEffect.hpp"
#include "../effects/EffectType.hpp"
#include "../orchestration/CombatPublisher.hpp"
#include "../orchestration/CombatantEvents.hpp"
#include "StatusController.hpp"
#include "../skills/Skill.hpp"
#include "Stats.hpp"
#include <vector>

class Combatant
{
public:
    Combatant(int troops, Stats stats);

    bool checkEffectActive(EffectType effect_type);
    void addSkill(Skill& skill, CombatantEvent combat_event);
    void addStatusEffect(TimedEffect timed_effect, EffectType type);

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
    StatusController status_manager;

    std::vector<TimedEffect> status_effects;
    std::vector<TimedEffect> buff_effects;
    std::vector<const Skill*> skills;
};  

#endif