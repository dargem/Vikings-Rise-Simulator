#include "StatusSkill.hpp"

void StatusSkill::onDependent(Combatant& combatant_friendly, Combatant& combatant_enemy) const
{

}

std::vector<CombatEvent> StatusSkill::getTriggerEvents() const
{
    return {};
}

bool StatusSkill::operator==(const ISkill& other) const
{
    const StatusSkill* other_skill = dynamic_cast<const StatusSkill*>(&other);
    if (other_skill)
    {
        return skill_type == other_skill->skill_type;
    }
    return false;
}
