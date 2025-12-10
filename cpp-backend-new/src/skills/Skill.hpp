#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillType.hpp"
#include "../effects/EffectType.hpp"

// forward declaration to avoid circular dependency
class Combatant;

class Skill
{
public:
    Skill(const SkillType skill_type, const EffectType effect_type);
    virtual ~Skill() {}

    virtual void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const = 0;
    virtual bool operator==(const Skill& other) const = 0;
    SkillType getSkillType() const { return skill_type; }
private:
    const SkillType skill_type;
    const EffectType effect_type;
};

#endif