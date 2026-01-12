#ifndef LOADABLE_HPP
#define LOADABLE_HPP

#include <vector>
#include <memory>
#include "skills/Skill.hpp"
#include "combatants/stats/Stats.hpp"

// a loadable is a collection of data 
// that exists to be loaded by a combatant
struct Loadable
{
    std::vector<std::unique_ptr<Skill>> skills;
    std::vector<std::unique_ptr<Stats>> stats;
};

#endif