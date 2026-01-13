#include "effects/TimedStatChange.hpp"

TimedStatChange::TimedStatChange(
    const double duration,
    const StatRequest stat_request,
    double magnitude
) 
    : timer { duration }, 
    changed_stat { stat_request }, 
    magnitude { magnitude }
{}

bool TimedStatChange::tickAndChangeStat(Stats& stats)
{
    // Skip stat change if expired
    if (timer.checkAndDeincrementDuration())
    {
        stats.changeStat(changed_stat, magnitude);
        return true;
    }
    return false;
}