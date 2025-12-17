#ifndef DAMAGE_SKILL_HPP
#define DAMAGE_SKILL_HPP

#include "skills/Skill.hpp"
#include "effects/TimedEffect.hpp"
#include "orchestration/CombatantEvent.hpp"

class DamageSkill: public Skill
{
public:
    DamageSkill(const double damage_magnitude, const SkillType skill_type, const EffectType effect_type, SkillCondition skill_condition, CombatantEvent skill_dependent, SkillTarget skill_target);
    void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const override;
    bool operator==(const Skill& other) const override;
private:
    const double damage_magnitude; 
};

#endif