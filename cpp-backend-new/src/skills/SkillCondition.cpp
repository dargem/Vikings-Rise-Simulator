#include "SkillCondition.hpp"

bool SkillCondition::isMet(const Combatant self, const Combatant target) const
{
    switch(condition_type)
    {
    case ConditionType::HAS_EFFECT_SELF:
        return self.checkEffectActive(effect_type);
    case ConditionType::HAS_EFFECT_TARGET:
        return target.checkEffectActive(effect_type);
    case ConditionType::TROOP_COUNT_GREATER_THAN_TARGET:
        return self.getTroops() > target.getTroops();
    }

    return true;
}