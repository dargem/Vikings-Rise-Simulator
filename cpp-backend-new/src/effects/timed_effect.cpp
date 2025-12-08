#include "timed_effect.hpp"

bool TimedEffect::checkAndDeincrementDuration()
{
    --duration;

    return duration >= 0;
}

EffectType TimedEffect::getEffectType()
{
    return effect_type;
}

TimedEffect::TimedEffect(short duration, EffectType effect_type) 
    : duration{duration}, 
    effect_type{effect_type} 
{}