#ifndef SKILL_CONDITION_HPP
#define SKILL_CONDITION_HPP

#include <optional>
#include "skills/ConditionType.hpp"
#include "effects/EffectType.hpp"

class Combatant; // Forward declaration

struct SkillCondition
{
public:
    SkillCondition(ConditionType condition_type, EffectType trigger_requirement);
    bool isMet(const Combatant& self, const Combatant& target) const;
private:
    ConditionType condition_type;
    EffectType trigger_requirement;
};

#endif