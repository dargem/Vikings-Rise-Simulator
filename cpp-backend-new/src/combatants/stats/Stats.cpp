#include "combatants/stats/Stats.hpp"

// type cast the request to an integral value then return double
double Stats::getStat(const StatRequest request) const
{
    return data[(static_cast<size_t>(request))];
}

double Stats::getAttack() const
{
    return data[static_cast<size_t>(StatRequest::Attack)];
}

double Stats::getDefense() const
{
    return data[static_cast<size_t>(StatRequest::Defense)];
}

double Stats::getHealth() const
{
    return data[static_cast<size_t>(StatRequest::Health)];
}

// type cast the request to integral than add change
void Stats::changeStat(const StatRequest request, const double change)
{
    data[static_cast<size_t>(request)] += change;
}

void Stats::mergeStats(const Stats& other)
{
    for (size_t i{}; i < data.size(); ++i)
    {
       data[i] += other.data[i]; 
    }
}
