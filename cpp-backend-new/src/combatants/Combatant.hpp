#ifndef COMBATANT_HPP
#define COMBATANT_HPP

#include "../effects/TimedEffect.hpp"
#include "../effects/EffectType.hpp"
#include "../orchestration/CombatPublisher.hpp"
#include "../orchestration/CombatantEvent.hpp"
#include "StatusController.hpp"
#include "../skills/Skill.hpp"
#include "Stats.hpp"
#include <vector>

class Combatant
{
public:
    Combatant(int troops, Stats stats);

    bool checkEffectActive(const EffectType effect_type) const;
    void addSkill(const Skill& skill, const CombatantEvent combat_event);
    void addStatusEffect(const TimedEffect timed_effect, const EffectType type);

    void setTroops(int troops);
    int getTroops() const;
    void setStats(Stats stats);
    double getAttack();
    double getDefense();
    double getHealth();

private:
    int troops;
    Stats stats;
    CombatPublisher combat_publisher;
    StatusController status_controller;

    std::vector<TimedEffect> status_effects;
    std::vector<TimedEffect> buff_effects;
    std::vector<const Skill*> skills;
};  

#endif