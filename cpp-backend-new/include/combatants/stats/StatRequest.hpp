#ifndef STAT_REQUEST_HPP
#define STAT_REQUEST_HPP

#include <cstdint>

enum class StatRequest : uint8_t
{
    Attack = 0,
    Defense,
    Health,
    DamageDealt,
    AbsorptionDealt,
    HealingDealt,
    BurnDealt,
    PoisonDealt,
    BleedDealt,
    BasicDealt,
    CounterDealt,
    COUNT
};

#endif