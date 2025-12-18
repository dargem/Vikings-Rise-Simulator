#ifndef DAMAGE_SKILL_HPP
#define DAMAGE_SKILL_HPP

#include "skills/Skill.hpp"
#include "effects/TimedEffect.hpp"
#include "orchestration/CombatantEvent.hpp"

class DamageSkill: public Skill
{
public:
    DamageSkill(
        SkillType skill_type, 
        SkillCondition skill_condition, 
        CombatantEvent skill_dependent, 
        SkillTarget skill_target,
        double chance,
        double damage_magnitude
    );

    void onDependent(Combatant& self, Combatant& target) const override;
    bool operator==(const Skill& other) const override;
private:
    const double damage_magnitude; 
};

#endif