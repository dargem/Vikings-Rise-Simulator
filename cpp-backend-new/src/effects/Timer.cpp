#include "Timer.hpp"

bool Timer::checkAndDeincrementDuration()
{
    return --duration >= 0;
}

Timer::Timer(short duration) 
    : duration{duration}
{}