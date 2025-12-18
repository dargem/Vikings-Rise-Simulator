#include "skills/SkillCondition.hpp"
#include "combatants/Combatant.hpp"

SkillCondition::SkillCondition(ConditionType condition_type, EffectType trigger_requirement)
    : condition_type {condition_type},
    trigger_requirement { trigger_requirement }
{}

bool SkillCondition::isMet(const Combatant& self, const Combatant& target) const
{   
    switch(condition_type)
    {
    case ConditionType::NONE:
        return true;
    case ConditionType::HAS_EFFECT_SELF:
        return self.checkEffectActive(trigger_requirement);
    case ConditionType::HAS_EFFECT_TARGET:
        return target.checkEffectActive(trigger_requirement);
    case ConditionType::TROOP_COUNT_GREATER_THAN_TARGET:
        return self.getTroops() > target.getTroops();
    }

    return true;
}