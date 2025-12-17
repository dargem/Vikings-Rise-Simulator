#ifndef EFFECT_TYPE_HPP
#define EFFECT_TYPE_HPP

#include <cstdint>

enum class EffectType : std::uint8_t 
{ 
    START,
    BURN, 
    BLEED, 
    POISON, 
    ABSORPTION, 
    HEALING,
    RETRIBUTION,
    COUNT
};

#endif