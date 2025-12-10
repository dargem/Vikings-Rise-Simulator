#ifndef STATUS_MANAGER_HPP
#define STATUS_MANAGER_HPP

#include "../effects/TimedEffect.hpp"
#include "../effects/EffectType.hpp"
#include <vector>
#include <map>

class StatusManager
{
public:
    StatusManager();
    void addStatusEffect(TimedEffect status_effect);
    void removeRandomBuffEffect(int num_removals);
    void removeAllDebuffEffects();
    bool checkEffectActive(EffectType status_effect) const;

private:
    std::map<EffectType, std::vector<TimedEffect>> debuff_collection;
    std::vector<TimedEffect> status_effect;
};

#endif