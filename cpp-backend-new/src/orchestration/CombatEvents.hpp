#ifndef COMBAT_EVENT_HPP
#define COMBAT_EVENT_HPP

enum class CombatEvent
{
    START,
    ACTIVE,
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