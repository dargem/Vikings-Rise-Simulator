#include "timer.hpp"
#include "effect_types.hpp"

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