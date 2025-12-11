#include "ScalarUtils.hpp"

namespace ScalarUtils
{
    bool troopTypeAdvantage(UnitType friendly, UnitType target)
    {
        switch (friendly)
        {
        case UnitType::PIKE:
            return target == UnitType::INFANTRY;

        case UnitType::INFANTRY:
            return target == UnitType::ARCHER;

        case UnitType::ARCHER:
            return target == UnitType::PIKE;
        }
    }

    bool troopTypeDisadvantage(UnitType friendly, UnitType target)
    {
        return troopTypeDisadvantage(target, friendly);
    }
}