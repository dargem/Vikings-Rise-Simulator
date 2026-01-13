#ifndef TIMED_STAT_CHANGE
#define TIMED_STAT_CHANGE

#include "combatants/stats/StatRequest.hpp"
#include "effects/Timer.hpp"
#include "combatants/stats/Stats.hpp"

class TimedStatChange 
{
public:
    // Construct a stat change, 
    // using the duration, type and magnitude
    TimedStatChange(
        double duration, 
        StatRequest stat_request, 
        double magnitude
    );
    // Modify the sent stats array with the change
    // Doesn't modify the stats array if its expired
    // Returns false if the timer has expired
    // Caller should delete expired stat changes
    [[nodiscard]] bool tickAndChangeStat(Stats& stats);
private:
    Timer timer;
    const StatRequest changed_stat;
    const double magnitude;
};

#endif