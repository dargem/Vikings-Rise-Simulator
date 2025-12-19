#ifndef SKILL_CONDITION_HPP
#define SKILL_CONDITION_HPP

#include "skills/ConditionType.hpp"
#include "effects/EffectType.hpp"

class Combatant; // Forward declaration

struct Condition
{
public:
    Condition(ConditionType condition_type, EffectType trigger_requirement);
    [[nodiscard]] bool isMet(const Combatant& self, const Combatant& target) const;
private:
    ConditionType condition_type;
    EffectType trigger_requirement;
};

#endif