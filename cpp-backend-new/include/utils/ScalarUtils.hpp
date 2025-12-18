#ifndef SCALARS_HPP
#define SCALARS_HPP

#include <cmath>
#include "combatants/UnitType.hpp"

namespace ScalarUtils
{
    constexpr double TROOP_ADVANTAGE_DAMAGE_MULTIPLYER {1.05};
    constexpr double TROOP_ADVANTAGE_RECEIVED_MULTIPLYER {0.95};
    constexpr double TROOP_DEFAULT_MULTIPLYER {1};
    
    // 100+k gradient
    constexpr double GRADIENT_100K_RISE { 0.001 };
    constexpr double GRADIENT_100K_CONSTANT { 432.2219717 };

    // messy estimate real value is probably 1/275 I assume?
    constexpr double GRADIENT_10K_100K_RISE {0.003634298861};
    constexpr double GRADIENT_10K_100K_CONSTANT {105.1350742};

    constexpr double LINEAR_FACTOR_0K_10K { 0.778 };
    constexpr double POWER_FACTOR_0K_10K { 0.487 };

    bool troopTypeAdvantage(UnitType friendly, UnitType target);

    bool troopTypeDisadvantage(UnitType friendly, UnitType target);

    constexpr double scaleDamage(double magnitude, double attack)
    {
        // fairly trivial but gets inlined so should help clarity w/o cost
        return magnitude * attack;
    }

    constexpr double troopScalar(int troops)
    {
        // troop scalar 
        if (troops >= 100000)
        {
            return ((troops - 100000) * GRADIENT_100K_RISE) + GRADIENT_100K_CONSTANT;
        }
        
        if (troops > 10000)
        {
            return ((troops - 10000) * GRADIENT_10K_100K_RISE) + GRADIENT_10K_100K_CONSTANT;
        }
        
        return LINEAR_FACTOR_0K_10K * std::pow(troops, POWER_FACTOR_0K_10K);
    }
}

#endif