#include "timed_effect.hpp"

TimedEffect::TimedEffect(short duration, double magnitude, EffectType effect_type)
    : Timer(duration),
    magnitude{magnitude},
    effect_type{effect_type}
{}

double TimedEffect::getMagnitude()
{
    return magnitude;
}

EffectType TimedEffect::getEffectType()
{
    return effect_type;
}