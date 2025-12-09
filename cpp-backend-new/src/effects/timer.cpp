#include "timer.hpp"

bool Timer::checkAndDeincrementDuration()
{
    --duration;

    return duration >= 0;
}

Timer::Timer(short duration) 
    : duration{duration}
{}