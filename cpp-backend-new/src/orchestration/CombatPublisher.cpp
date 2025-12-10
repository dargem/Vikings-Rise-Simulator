#include "CombatPublisher.hpp"
#include <vector>
#include <algorithm>

CombatPublisher::CombatPublisher() 
{
    for (int i = 0; i < static_cast<int>(CombatantEvent::COUNT); ++i) 
    {
        combat_event_subscribers[static_cast<CombatantEvent>(i)] = std::vector<const Skill*>();
    }
}

bool CombatPublisher::subToEvent(const Skill& skill, const CombatantEvent event) 
{
    std::vector<const Skill*>& skills = combat_event_subscribers[event];

    if (std::find(skills.begin(), skills.end(), &skill) != skills.end())
    {
        // skill already inside the vector
        return false;
    }
    
    skills.push_back(&skill);
    return true;
}

bool CombatPublisher::unsubToEvent(const Skill& skill, const CombatantEvent event)
{
    std::vector<const Skill*>& skills = combat_event_subscribers[event];
    
    auto it = std::find(skills.begin(), skills.end(), &skill);
    if (it == skills.end())
    {
        // skill not inside the vector
        return false;
    }

    skills.erase(it);
    return true;
}

void CombatPublisher::publishEvent(const CombatantEvent event, Combatant& friendly_combatant, Combatant& enemy_combatant) const
{
    const std::vector<const Skill*>& skills = combat_event_subscribers.at(event);
    for (const Skill* skill : skills)
    {
        skill->onDependent(friendly_combatant, enemy_combatant);
    }
}
