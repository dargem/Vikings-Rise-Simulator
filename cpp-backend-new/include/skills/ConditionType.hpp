#ifndef CONDITION_TYPE_HPP
#define CONDITION_TYPE_HPP

#include <cstdint>

enum class ConditionType : std::uint8_t
{
    NONE,
    HAS_EFFECT_SELF,
    HAS_EFFECT_TARGET,
    TROOP_COUNT_GREATER_THAN_TARGET
};

#endif