#include "TimedEffect.hpp"

TimedEffect::TimedEffect(short duration, double magnitude)
    : timer { Timer(duration) },
    magnitude { magnitude }
{}

TimedEffect::TimedEffect(TimedEffect& copied_effect, double scalars) 
    : timer { copied_effect.cloneTimer() },
    magnitude { copied_effect.getMagnitude() * scalars }
{}

double TimedEffect::getMagnitude() const 
{
    return magnitude;
}

Timer TimedEffect::cloneTimer() const 
{
    // use the default copy constructor
    // effectively a deep copy seeing it just contains primitives
    return Timer(timer);
}

std::optional<double> TimedEffect::tickAndGetMagnitude()
{
    return timer.checkAndDeincrementDuration() 
        ? std::optional<double>(magnitude) 
        : std::nullopt;
}