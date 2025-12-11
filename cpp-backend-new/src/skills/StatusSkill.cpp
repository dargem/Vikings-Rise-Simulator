#include "StatusSkill.hpp"
#include "../combatants/Combatant.hpp"

StatusSkill::StatusSkill(const TimedEffect status_effect, const SkillType skill_type, const EffectType effect_type, const SkillCondition skill_condition)
    : Skill(skill_type, effect_type, skill_condition), 
    status_effect(status_effect)
{}

void StatusSkill::onDependent(Combatant& combatant_friendly, Combatant& combatant_enemy) const
{
    if (Skill::checkCondition(combatant_friendly, combatant_enemy))
    {
        switch (Skill::getSkillTarget())
        {
        case SkillTarget::FRIENDLY:
            combatant_friendly.addStatusEffect();
        case SkillTarget::ENEMY:
            combatant_enemy.addStatusEffect();
        };
    }
}

std::vector<CombatantEvent> StatusSkill::getTriggerEvents() const
{
    return {};
}

bool StatusSkill::operator==(const Skill& other) const
{
    const StatusSkill* other_skill = dynamic_cast<const StatusSkill*>(&other);
    if (other_skill)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}
