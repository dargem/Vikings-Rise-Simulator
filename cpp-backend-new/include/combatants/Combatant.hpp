#ifndef COMBATANT_HPP
#define COMBATANT_HPP

#include "effects/TimedEffect.hpp"
#include "effects/EffectType.hpp"
#include "orchestration/CombatPublisher.hpp"
#include "orchestration/CombatantEvent.hpp"
#include "combatants/StatusController.hpp"
#include "skills/Skill.hpp"
#include "combatants/stats/Stats.hpp"
#include <vector>

class Combatant
{
public:
    Combatant(int troops, Stats stats);

    [[nodiscard]] bool checkEffectActive(EffectType effect_type) const;
    void addSkill(const Skill* skill);
    void addStatusEffect(TimedEffect timed_effect, EffectType type);

    void setTroops(int troops);
    [[nodiscard]] int getTroops() const;
    void setStats(Stats stats);
    [[nodiscard]] double getAttack() const;
    [[nodiscard]] double getDefense() const;
    [[nodiscard]] double getHealth() const;
private:
    int troops;
    Stats stats;
    CombatPublisher combat_publisher;
    StatusController status_controller;

    std::vector<TimedEffect> status_effects;
    std::vector<TimedEffect> buff_effects;
};  

#endif