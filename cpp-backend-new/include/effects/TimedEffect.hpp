#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "effects/Timer.hpp"
#include "effects/EffectType.hpp"
#include <optional>

class TimedEffect
{
    public:
        TimedEffect(const short duration, const double magnitude);

        // A special copy constructor to make damaging effects from the skill's stored one
        TimedEffect(const TimedEffect& copied_effect, const double scalars);
        std::optional<double> tickAndGetMagnitude();
        double getMagnitude() const;
        Timer cloneTimer() const; 
    private:
        const double magnitude;
        Timer timer;
};

#endif