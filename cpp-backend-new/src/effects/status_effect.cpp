#include "status_effect.hpp"

StatusEffect::StatusEffect(short duration, double magnitude, EffectType effect_type)
    : Timer(duration),
    magnitude{magnitude},
    effect_type{effect_type}
{}

double StatusEffect::getMagnitude()
{
    return magnitude;
}

EffectType StatusEffect::getEffectType()
{
    return effect_type;
}