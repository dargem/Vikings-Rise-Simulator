#ifndef STATS_HPP
#define STATS_HPP

#include <array>
#include <cstddef>
#include "combatants/stats/StatRequest.hpp"


struct Stats
{
public:
    double& getStat(StatRequest request);
private:
    std::array<double, static_cast<size_t>(StatRequest::COUNT)> _data;
};

#endif