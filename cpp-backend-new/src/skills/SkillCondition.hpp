#ifndef SKILL_CONDITION_HPP
#define SKILL_CONDITION_HPP

#include <optional>
#include "ConditionType.hpp"
#include "../effects/EffectType.hpp"
#include "../combatants/Combatant.hpp"

struct SkillCondition
{
public:
    bool isMet(const Combatant self, const Combatant target) const;
private:
    const ConditionType condition_type;
    const EffectType effect_type;
};

#endif