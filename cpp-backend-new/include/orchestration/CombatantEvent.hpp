#ifndef COMBATANT_EVENT_HPP
#define COMBATANT_EVENT_HPP

#include <cstdint>

enum class CombatantEvent : std::uint8_t
{
    START,
    PRIMARY_COMMANDER_ACTIVE_DEALT,
    SECONDARY_COMMANDER_ACTIVE_DEALT,
    ACTIVE_DEALT,
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