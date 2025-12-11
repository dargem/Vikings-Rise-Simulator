#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "Timer.hpp"
#include "EffectType.hpp"
#include <optional>

class TimedEffect
{
    public:
        TimedEffect(short duration, double magnitude);

        // A special copy constructor to make damaging effects from the skill's stored one
        TimedEffect(TimedEffect& copied_effect, double scalars);
        std::optional<double> tickAndGetMagnitude();
        double getMagnitude() const;
        Timer cloneTimer() const; 
    private:
        const double magnitude;
        Timer timer;
};

#endif