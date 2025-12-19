#ifndef EFFECT_TYPE_HPP
#define EFFECT_TYPE_HPP

#include <cstdint>

enum class EffectType : std::uint8_t 
{ 
    START,
    // NONE is just a sentinel value 
    NONE,
    BURN, 
    BLEED, 
    POISON, 
    ABSORPTION, 
    HEAL,
    RETRIBUTION,
    SLOW,
    SILENCE,
    COUNT
};

#endif