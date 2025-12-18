#include "skills/StatusSkill.hpp"
#include "combatants/Combatant.hpp"

StatusSkill::StatusSkill(
    const TimedEffect status_effect, 
    const SkillType skill_type, 
    const SkillCondition skill_condition, 
    const CombatantEvent skill_dependent, 
    const SkillTarget skill_target,
    double chance,
    const EffectType effect_type, 
    const bool is_removable
)
    : Skill(skill_type, skill_condition, skill_dependent, skill_target, chance),
    status_effect(status_effect),
    effect_type { effect_type },
    is_removable { is_removable }
{}

void StatusSkill::onDependent(Combatant& self, Combatant& target) const
{
    if (Skill::checkCondition(self, target))
    {
        const double scalars = 1;
        TimedEffect sent_effect(status_effect, scalars);
        switch (Skill::getSkillTarget())
        {
        case SkillTarget::FRIENDLY:
            self.addStatusEffect(sent_effect, effect_type);
            break;
        case SkillTarget::ENEMY:
            target.addStatusEffect(sent_effect, effect_type);
            break;
        };
    }
}

std::vector<CombatantEvent> StatusSkill::getTriggerEvents()
{
    return {};
}

bool StatusSkill::operator==(const Skill& other) const
{
    const auto* other_skill = dynamic_cast<const StatusSkill*>(&other);
    if (other_skill != nullptr)
    {
        return getSkillType() == other_skill->getSkillType();
    }
    return false;
}
