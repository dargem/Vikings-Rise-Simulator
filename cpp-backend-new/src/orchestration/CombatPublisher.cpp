#include "CombatPublisher.hpp"
#include <vector>

CombatPublisher::CombatPublisher() 
{
    for (int i = 0; i < static_cast<int>(CombatEvent::COUNT); ++i) 
    {
        combat_event_subscribers[static_cast<CombatEvent>(i)] = std::vector<Skill>();
    }
}

bool CombatPublisher::subToEvent(const Skill skill, const CombatEvent event) 
{
    std::vector<Skill>& skills = combat_event_subscribers[event];

    if (std::find(skills.begin(), skills.end(), skill) != skills.end())
    {
        // skill already inside the vector
        return false;
    }

    skills.push_back(skill);
    return true;
}

bool CombatPublisher::unsubToEvent(const Skill skill, const CombatEvent event)
{
    std::vector<Skill>& skills = combat_event_subscribers[event];
    
    if (std::find(skills.begin(), skills.end(), skill) == skills.end())
    {
        // skill not inside the vector
        return false;
    }

    std::erase(skills, skill);
}

void CombatPublisher::publishEvent(const CombatEvent event, Combatant& friendly_combatant, Combatant& enemy_combatant) const
{
    const std::vector<Skill>& skills = combat_event_subscribers.at(event);
    for (const Skill& skill : skills)
    {
        skill.onDependent(friendly_combatant, enemy_combatant);
    }
}
