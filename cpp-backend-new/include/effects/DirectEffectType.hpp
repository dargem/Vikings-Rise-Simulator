#ifndef DIRECT_EFFECT_TYPE_HPP
#define DIRECT_EFFECT_TYPE_HPP

#include <cstdint>
/**
 * @brief For direct effects
 * For example there are skills that do same round heals
 * E.g. green chant so same round hitters aren't limited to damage
 */
enum class DirectEffectType: std::uint8_t
{
    DAMAGE,
    HEAL
};

#endif