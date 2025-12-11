#ifndef STATUS_MANAGER_HPP
#define STATUS_MANAGER_HPP

#include "effects/TimedEffect.hpp"
#include "effects/EffectType.hpp"

#include <vector>
#include <map>

class StatusController
{
public:
    StatusController();
    void addStatusEffect(const TimedEffect effect, EffectType type);
    void removeRandomBuffEffect(int num_removals);
    void removeAllDebuffEffects();
    bool checkEffectActive(EffectType type) const;
private:
    // Collection of active debuffs grouped by type
    std::map<EffectType, std::vector<TimedEffect>> debuff_collection;
    // List of general status effects
    std::vector<TimedEffect> status_effects;
};

#endif