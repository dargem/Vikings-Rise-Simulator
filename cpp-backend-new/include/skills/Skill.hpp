#ifndef SKILL_HPP
#define SKILL_HPP

#include "skills/SkillType.hpp"
#include "effects/EffectType.hpp"
#include "skills/SkillCondition.hpp"
#include "skills/SkillTarget.hpp"
#include "orchestration/CombatantEvent.hpp"

// forward declaration to avoid circular dependency
class Combatant;

class Skill
{
public:
    Skill(SkillType skill_type, EffectType effect_type, const SkillCondition skill_condition, CombatantEvent skill_dependent, SkillTarget target);
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
    // e.g. a skill dependent on a basic attack will that needs a conditional bleed effect type to proc
    SkillCondition skill_condition;
    // e.g. a skill is dependent on a basic attack for it to actually trigger
    CombatantEvent skill_dependent;
    SkillTarget target;
};

#endif