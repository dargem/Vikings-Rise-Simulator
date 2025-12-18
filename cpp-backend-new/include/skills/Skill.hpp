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
    Skill(
        SkillType skill_type, 
        SkillCondition skill_condition, 
        CombatantEvent skill_dependent, 
        SkillTarget target,
        double chance
    );
    virtual ~Skill() = default;
    virtual void onDependent(Combatant& self, Combatant& target) const = 0;
    virtual bool operator==(const Skill& other) const = 0;

    [[nodiscard]] bool checkCondition(const Combatant& friendly, const Combatant& target) const;

    [[nodiscard]] SkillType getSkillType() const;
    [[nodiscard]] SkillTarget getSkillTarget() const;
private:
    SkillType skill_type;
    const double chance;
    // a flag used alongside chance for when chance == 1, number generation isn't needed
    // number generation however is not optimized out by the compiler as the internal state
    // of the generator is changed with each call 
    const bool always_triggers;

    // e.g. a skill dependent on a basic attack will that needs a conditional bleed effect type to proc
    const SkillCondition skill_condition;
    // e.g. a skill is dependent on a basic attack for it to actually trigger
    CombatantEvent skill_dependent;
    SkillTarget target;
};

#endif