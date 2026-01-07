#ifndef SKILL_HPP
#define SKILL_HPP

#include "skills/SkillType.hpp"
#include "effects/EffectType.hpp"
#include "skills/SkillCondition.hpp"
#include "skills/SkillTarget.hpp"
#include "orchestration/CombatantEvent.hpp"
#include "utils/NumberGenerator.hpp"
#include <memory>

// forward declaration to avoid circular dependency
class Combatant;

class Skill
{
public:
    Skill(
        SkillType skill_type, 
        Condition skill_condition, 
        CombatantEvent skill_dependent, 
        double chance
    );

    Skill(
        
    );

    virtual ~Skill() = default;

    virtual void onDependent(
        Combatant& self, 
        Combatant& target, 
        NumberGenerator& number_generator
    ) const = 0;
    
    virtual bool operator==(const Skill& other) const = 0;

    // Checks if a skill should trigger
    // Checks RNG + Trigger condition
    [[nodiscard]] 
    bool checkCondition(
        const Combatant& friendly, 
        const Combatant& target, 
        NumberGenerator& number_generator
    ) const;

    [[nodiscard]] SkillType getSkillType() const;
    [[nodiscard]] CombatantEvent getSkillDependent() const;
private:
    const SkillType skill_type;
    const double chance;
    // a flag used alongside chance for when chance == 1, number generation isn't needed
    // number generation however is not optimized out by the compiler as the internal state
    // of the generator is changed with each call 
    const bool always_triggers;

    // e.g. a skill dependent on a basic attack will that needs a conditional bleed effect type to proc
    const Condition skill_condition;
    // e.g. a skill is dependent on a basic attack for it to actually trigger
    const CombatantEvent skill_dependent;
};

#endif