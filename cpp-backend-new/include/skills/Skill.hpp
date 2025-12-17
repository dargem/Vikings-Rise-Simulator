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
        SkillTarget target
    );
    virtual ~Skill() = default;
    virtual void onDependent(Combatant& self, Combatant& target) const = 0;
    virtual bool operator==(const Skill& other) const = 0;

    [[nodiscard]] bool checkCondition(const Combatant& friendly, const Combatant& target) const;

    [[nodiscard]] SkillType getSkillType() const;
    [[nodiscard]] SkillTarget getSkillTarget() const;
private:
    SkillType skill_type;
    // e.g. a skill dependent on a basic attack will that needs a conditional bleed effect type to proc
    SkillCondition skill_condition;
    // e.g. a skill is dependent on a basic attack for it to actually trigger
    CombatantEvent skill_dependent;
    SkillTarget target;
};

#endif