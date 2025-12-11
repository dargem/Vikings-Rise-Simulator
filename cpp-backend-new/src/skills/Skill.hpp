#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillType.hpp"
#include "../effects/EffectType.hpp"
#include "SkillCondition.hpp"

// forward declaration to avoid circular dependency
class Combatant;

class Skill
{
public:
    Skill(const SkillType skill_type, const EffectType effect_type, const SkillCondition skill_condition);
    virtual ~Skill() {}
    virtual void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const = 0;
    virtual bool operator==(const Skill& other) const = 0;

    bool checkCondition(const Combatant& friendly, const Combatant& target) const;

    SkillType getSkillType() const;
    EffectType getEffectType() const;
private:
    const SkillType skill_type;
    const EffectType effect_type;
    const SkillCondition skill_condition;
};

#endif