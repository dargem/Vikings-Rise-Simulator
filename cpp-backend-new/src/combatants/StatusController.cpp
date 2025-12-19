#include "combatants/StatusController.hpp"
#include "effects/EffectType.hpp"

StatusController::StatusController()
{
    for (int i = 0; i < static_cast<int>(EffectType::COUNT); ++i) 
    {
        if (static_cast<EffectType>(i) == EffectType::NONE)
        {
            continue;
        }
        
        debuff_collection[static_cast<EffectType>(i)] = std::vector<TimedEffect>();
    }
}

void StatusController::addStatusEffect(const TimedEffect effect, EffectType type)
{
    // get the timed effects for a specific debuff type
    std::vector<TimedEffect>& timed_effects = debuff_collection[type];
    // needs to check its not already on there also, do later
    timed_effects.push_back(effect);
}

void StatusController::removeRandomBuffEffect(int num_removals)
{
    // filler 4 now
}
void StatusController::removeAllDebuffEffects()
{
    // filler 4 now
}

bool StatusController::checkEffectActive(EffectType type) const
{
    const std::vector<TimedEffect>& timed_effects = debuff_collection.at(type);
    return !timed_effects.empty();
}