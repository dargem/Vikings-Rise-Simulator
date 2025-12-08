#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "effect_types.hpp"

class TimedEffect
{
    public:
        TimedEffect(short duration, EffectType effect_type);
        // member functions
        bool checkAndDeincrementDuration();
        EffectType getEffectType();
    
    private:
        short duration;
        EffectType effect_type;
};

#endif