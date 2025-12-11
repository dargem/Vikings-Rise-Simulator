#include "SkillCondition.hpp"

bool SkillCondition::isMet(Combatant self, Combatant target)
{
    switch(condition_type)
    {
    case ConditionType::HAS_EFFECT_SELF:
        break;
    case ConditionType::HAS_EFFECT_TARGET:
        break;
    case ConditionType::TROOP_COUNT_GREATER_THAN_TARGET:
        break;
    }
    return true;
}