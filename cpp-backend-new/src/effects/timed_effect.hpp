#include "timer.hpp"
#include "effect_types.hpp"

class TimedEffect: public Timer
{
    public:
        TimedEffect(short duration, double magnitude, EffectType effect_type);
        double getMagnitude();
        EffectType getEffectType();
    private:
        double magnitude;
        EffectType effect_type;
};