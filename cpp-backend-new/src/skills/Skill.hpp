#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillType.hpp"
#include "../effects/EffectType.hpp"
#include "SkillCondition.hpp"
#include "SkillTarget.hpp"

// forward declaration to avoid circular dependency
class Combatant;

class Skill
{
public:
    Skill(SkillType skill_type, EffectType effect_type, const SkillCondition skill_condition, SkillTarget target);
    virtual ~Skill() {}
    virtual void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const = 0;
    virtual bool operator==(const Skill& other) const = 0;

    bool checkCondition(const Combatant& friendly, const Combatant& target) const;

    SkillType getSkillType() const;
    EffectType getEffectType() const;
    SkillTarget getSkillTarget() const;
private:
    SkillType skill_type;
    EffectType effect_type;
    SkillCondition skill_condition;
    SkillTarget target;
};

#endif