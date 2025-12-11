#ifndef SKILL_CONDITION_HPP
#define SKILL_CONDITION_HPP

#include <optional>
#include "ConditionType.hpp"
#include "../effects/EffectType.hpp"
#include "../combatants/Combatant.hpp"

struct SkillCondition
{
public:
    bool isMet(Combatant self, Combatant target);
private:
    ConditionType condition_type;
    std::optional<EffectType> effect_condition;
};

#endif