#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "effects/Timer.hpp"
#include "effects/EffectType.hpp"
#include <optional>

// used for storing damage effects
class TimedEffect
{
public:
    TimedEffect(double duration, double effectMagnitude);

    // A special copy constructor to make damaging effects from the skill's stored one
    TimedEffect(const TimedEffect& copied_effect, double scalars);
    // If false the status effect has expired
    [[nodiscard]] std::optional<double> tickAndGetMagnitude();
    [[nodiscard]] Timer cloneTimer() const; 
private:
    [[nodiscard]] double getMagnitude() const;
    const double magnitude;
    Timer timer;
};

#endif