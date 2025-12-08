#include "effect_types.hpp"

class TimedEffect
{
    public:
        TimedEffect(short duration, EffectType effect_type);
        // member functions
        bool checkAndDeincrementDuration();
        EffectType getEffectType();
    protected:
        private:
            short duration;
            EffectType effect_type;
};

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