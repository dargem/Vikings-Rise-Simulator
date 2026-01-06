#include "orchestration/CombatPublisher.hpp"
#include <vector>
#include <algorithm>
#include <ranges>

CombatPublisher::CombatPublisher() 
{
    for (int i = 0; i < static_cast<int>(CombatantEvent::COUNT); ++i) 
    {
        combat_event_subscribers[static_cast<CombatantEvent>(i)] = std::vector<const Skill*>();
    }
}

bool CombatPublisher::subToEvent(const Skill* skill) 
{
    CombatantEvent event = skill->getSkillDependent();
    std::vector<const Skill*>& current_skills = combat_event_subscribers[event];

    for (const auto skill : current_skills)
    {
        // equivalence logic, return false if is in already
    }
    
    current_skills.push_back(skill);

    return true;
}

bool CombatPublisher::unsubToEvent(const Skill* skill)
{
    CombatantEvent event = skill->getSkillDependent();
    std::vector<const Skill*>& current_skills = combat_event_subscribers[event];
    
    for (const Skill* skill : current_skills)
    {
        // equivalent logic, erase and return true if already in
    }

    // not inside loop
    return false;
}

void CombatPublisher::publishEvent(const CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    const std::vector<const Skill*>& skills = combat_event_subscribers.at(event);
    for (const Skill* skill : skills)
    {
        skill->onDependent(self, target, number_generator);
    }
}

void CombatPublisher::addSkill(const Skill* skill) 
{
    subToEvent(skill);
}