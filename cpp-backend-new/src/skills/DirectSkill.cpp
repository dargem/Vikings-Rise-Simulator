#include "skills/DirectSkill.hpp"

DirectSkill::DirectSkill(
    SkillType skill_type,
    Condition skill_condition, 
    CombatantEvent skill_dependent, 
    SkillTarget skill_target,
    double chance,
    DirectEffectType effect,
    double damage_magnitude
) 
    : Skill(skill_type, skill_condition, skill_dependent, chance),
    effect { effect },
    damage_magnitude { damage_magnitude },
    skill_target { skill_target }
{}

void DirectSkill::onDependent(Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    switch (effect) 
    {
    case DirectEffectType::DAMAGE:
        // just damage the target, no skills self damage currently avoids branching
        // can add in later if there is self damage added

        // holder damage enemy
        return;
    case DirectEffectType::HEAL:
        // just heals the friendly, no skills heal the target currently avoids branching
        // can change later

        // holder heal friendly
        return;
    }
}

bool DirectSkill::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const DirectSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}