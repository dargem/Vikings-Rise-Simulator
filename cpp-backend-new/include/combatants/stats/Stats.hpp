#ifndef STATS_HPP
#define STATS_HPP

#include <array>
#include <cstddef>
#include "combatants/stats/StatRequest.hpp"


struct Stats
{
public:
    // Returns copy of the stat corresponding to the request
    [[nodiscard]] double getStat(StatRequest request) const;

    // Helper methods for conciseness
    [[nodiscard]] double getAttack() const;
    [[nodiscard]] double getDefense() const;
    [[nodiscard]] double getHealth() const;

    // Adds the amount of the double to the stat corresponding with the request
    void changeStat(StatRequest request, double change);

    // Merge another stat object into the called object
    // Sums together all stats effectively
    void mergeStats(const Stats& other);


private:
    std::array<double, static_cast<size_t>(StatRequest::COUNT)> data{};
};

#endif