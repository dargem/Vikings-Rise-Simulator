#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "Timer.hpp"
#include "EffectType.hpp"
#include <optional>

class TimedEffect
{
    public:
        TimedEffect(short duration, double magnitude);
        std::optional<double> tickAndGetMagnitude();
    private:
        double magnitude;
        Timer timer;
};

#endif