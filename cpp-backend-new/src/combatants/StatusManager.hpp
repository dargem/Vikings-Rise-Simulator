#ifndef STATUS_MANAGER_HPP
#define STATUS_MANAGER_HPP

#include "../effects/StatusEffect.hpp"
#include "../effects/EffectTypes.hpp"
#include <vector>
#include <map>

class StatusManager
{
public:
    void addDebuffStatusEffect(StatusEffect status_effect);
    void removeRandomStatusEffects(int num_removals);
    void removeAllEffects();
    bool checkEffectActive(EffectType status_effect) const;

private:
    std::map<EffectType, std::vector<StatusEffect>> debuff_collection;
    std::vector<StatusEffect> status_effect;
};

#endif