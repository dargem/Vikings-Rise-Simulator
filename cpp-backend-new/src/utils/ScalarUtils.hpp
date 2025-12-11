#ifndef SCALARS_HPP
#define SCALARS_HPP

#include "combatants/UnitType.hpp"

namespace ScalarUtils
{
    constexpr double TROOP_ADVANTAGE_DAMAGE_MULTIPLYER {1.05};
    constexpr double TROOP_ADVANTAGE_RECEIVED_MULTIPLYER {0.95};
    constexpr double TROOP_DEFAULT_MULTIPLYER {1};

    bool troopTypeAdvantage(UnitType friendly, UnitType target);

    bool troopTypeDisadvantage(UnitType friendly, UnitType target);

    constexpr double scaleDamage(double magnitude, double attack)
    {
        // fairly trivial but gets inlined so should help clarity wo cost
        return magnitude * attack;
    }

    constexpr double troopScalar(int troops)
    {
        // troop scalar 
        if (troops >= 100000)
        {
            return (troops - 100000) * 0.001 + 432.2219717;
        }
    }
    

}

#endif