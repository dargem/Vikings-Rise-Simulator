#ifndef STATUS_EFFECT_HPP
#define STATUS_EFFECT_HPP

#include "Timer.hpp"
#include "EffectType.hpp"

class StatusEffect: public Timer
{
    public:
        StatusEffect(short duration, double magnitude, EffectType effect_type);
        double getMagnitude();
        EffectType getEffectType();
    private:
        double magnitude;
        EffectType effect_type;
};

#endif