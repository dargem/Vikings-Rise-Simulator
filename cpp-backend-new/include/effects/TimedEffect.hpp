#ifndef TIMED_EFFECT_HPP
#define TIMED_EFFECT_HPP

#include "effects/Timer.hpp"
#include "effects/EffectType.hpp"
#include <optional>

class TimedEffect
{
public:
    TimedEffect(double duration, double effectMagnitude);

    // A special copy constructor to make damaging effects from the skill's stored one
    TimedEffect(const TimedEffect& copied_effect, double scalars);
    [[nodiscard]] std::optional<double> tickAndGetMagnitude();
    [[nodiscard]] double getMagnitude() const;
    [[nodiscard]] Timer cloneTimer() const; 
private:
    const double magnitude;
    Timer timer;
};

#endif