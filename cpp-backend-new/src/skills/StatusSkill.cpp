#include "skills/StatusSkill.hpp"
#include "combatants/Combatant.hpp"

StatusSkill::StatusSkill(const TimedEffect status_effect, SkillType skill_type, EffectType effect_type, const SkillCondition skill_condition, CombatantEvent skill_dependent, SkillTarget skill_target)
    : Skill(skill_type, effect_type, skill_condition, skill_dependent, skill_target),
    status_effect(status_effect)
{}

void StatusSkill::onDependent(Combatant& combatant_friendly, Combatant& combatant_enemy) const
{
    if (Skill::checkCondition(combatant_friendly, combatant_enemy))
    {
        const double scalars = 1;
        TimedEffect sent_effect(status_effect, scalars);
        switch (Skill::getSkillTarget())
        {
        case SkillTarget::FRIENDLY:
            combatant_friendly.addStatusEffect(sent_effect, Skill::getEffectType());
            break;
        case SkillTarget::ENEMY:
            combatant_enemy.addStatusEffect(sent_effect, Skill::getEffectType());
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
