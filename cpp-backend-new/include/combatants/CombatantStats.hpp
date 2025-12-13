#ifndef COMBATANT_STATS_HPP
#define COMBATANT_STATS_HPP

struct CombatantStats
{
    double attack_tech;
    double defense_tech;
    double health_tech;

    double damage_taken_reduction;
    double generic_damage_increase;

    double rallied_troop_damage_increase;

    double basic_attack_damage_increase;
    double counter_attack_damage_increase;

    double bleed_damage_increase;
    double poison_damage_increase;

    double cooperation_skill_damage_increase;
    double counterattack_skill_damage_increase;

    double archer_targeting_damage_increase;
    double infantry_targeting_damage_increase;
    double pike_targeting_damage_increase;
};

#endif