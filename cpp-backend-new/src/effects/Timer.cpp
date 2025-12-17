#include "effects/Timer.hpp"

bool Timer::checkAndDeincrementDuration()
{
    return --duration >= 0;
}

Timer::Timer(double duration) 
    : duration{duration}
{}