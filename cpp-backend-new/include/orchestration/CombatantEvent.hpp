#ifndef COMBATANT_EVENT_HPP
#define COMBATANT_EVENT_HPP

enum class CombatantEvent
{
    START,
    ACTIVE_PRIMARY_SENT,
    ACTIVE_SECONDARY_SENT,
    ACTIVE_RECEIVED,
    BASIC_DEALT,
    BASIC_RECEIVED,
    CONTINUOUS_DAMAGE_RECEIVED,
    COUNTER_ATTACK_DEALT,
    SHIELD_GRANTED,
    ROUND_MOD_6,
    ROUND_MOD_9,
    COUNT
    // hacky way to do mod rounds
    // can change later
};

#endif