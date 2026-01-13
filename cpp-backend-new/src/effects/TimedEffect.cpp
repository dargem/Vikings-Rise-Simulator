#include "effects/TimedEffect.hpp"

TimedEffect::TimedEffect(double duration, double effectMagnitude)
    : timer { duration },
    magnitude { effectMagnitude }
{}

TimedEffect::TimedEffect(const TimedEffect& copied_effect, double scalars) 
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
    return {timer};
}

std::optional<double> TimedEffect::tickAndGetMagnitude()
{
    return timer.checkAndDeincrementDuration() 
        ? std::optional<double>(magnitude) 
        : std::nullopt;
}