#include "TimedEffect.hpp"

TimedEffect::TimedEffect(short duration, double magnitude)
    : timer{Timer(duration)},
    magnitude{magnitude}
{}

std::optional<double> TimedEffect::tickAndGetMagnitude()
{
    return timer.checkAndDeincrementDuration() 
        ? std::optional<double>(magnitude) 
        : std::nullopt;
}