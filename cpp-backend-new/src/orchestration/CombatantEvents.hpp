#ifndef COMBATANT_EVENT_HPP
#define COMBATANT_EVENT_HPP

enum class CombatantEvent
{
    START,
    ACTIVE_PRIMARY,
    ACTIVE_SECONDARY,
    ACTIVE_RECEIVED,
    BASIC_ATTACK,
    BASIC_RECEIVED,
    CONTINUOUS_DAMAGE,
    COUNTER_ATTACK,
    SHIELD_GRANTED,
    ROUND_MOD_6,
    ROUND_MOD_9,
    COUNT
    // hacky way to do mod rounds
    // can change later
};

#endif