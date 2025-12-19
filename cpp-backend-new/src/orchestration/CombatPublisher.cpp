#include "orchestration/CombatPublisher.hpp"
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

    if (std::ranges::find(skills, &skill) != skills.end())
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
    
    auto iterator = std::ranges::find(skills, &skill);
    if (iterator == skills.end())
    {
        // skill not inside the vector
        return false;
    }

    skills.erase(iterator);
    return true;
}

void CombatPublisher::publishEvent(const CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    const std::vector<const Skill*>& skills = combat_event_subscribers.at(event);
    for (const Skill* skill : skills)
    {
        skill->onDependent(self, target, number_generator);
    }
}

void CombatPublisher::addSkill(std::unique_ptr<Skill> skill) 
{
    owned_skills.push_back(std::move(skill));
}
