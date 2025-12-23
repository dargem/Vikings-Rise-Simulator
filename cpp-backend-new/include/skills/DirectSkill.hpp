#ifndef DIRECT_SKILL_HPP
#define DIRECT_SKILL_HPP

#include "skills/Skill.hpp"
#include "effects/TimedEffect.hpp"
#include "orchestration/CombatantEvent.hpp"
#include "effects/DirectEffectType.hpp"

class DirectSkill: public Skill
{
public:
    DirectSkill(
        SkillType skill_type, 
        Condition skill_condition, 
        CombatantEvent skill_dependent, 
        SkillTarget skill_target,
        double chance,
        DirectEffectType effect,
        double damage_magnitude
    );

    void onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const override;
    bool operator==(const Skill& other) const override;
private:
    const DirectEffectType effect;
    const double damage_magnitude; 
};

#endif