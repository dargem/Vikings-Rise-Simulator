#include "orchestration/CombatPublisher.hpp"
#include <vector>
#include <algorithm>
#include <ranges>

CombatPublisher::CombatPublisher() 
{
    for (int i = 0; i < static_cast<int>(CombatantEvent::COUNT); ++i) 
    {
        combat_event_subscribers[static_cast<CombatantEvent>(i)] = std::vector<const SkillGrouping&>();
    }
}

bool CombatPublisher::subToEvent(const SkillGrouping& new_skill_grouping) 
{
    CombatantEvent event = new_skill_grouping.getDependentEvent();
    std::vector<const SkillGrouping&>& current_groupings = combat_event_subscribers[event];

    for (const SkillGrouping& skill_grouping : current_groupings)
    {
        // equivalence logic, return false if is in already
    }
    
    current_groupings.push_back(new_skill_grouping);

    return true;
}

bool CombatPublisher::unsubToEvent(const SkillGrouping& remove_skill_grouping)
{
    CombatantEvent event = remove_skill_grouping.getDependentEvent();
    std::vector<const SkillGrouping&>& current_groupings = combat_event_subscribers[event];
    
    for (const SkillGrouping& skill_grouping : current_groupings)
    {
        // equivalent logic, erase and return true if already in
    }

    // not inside loop
    return false;
}

void CombatPublisher::publishEvent(const CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const
{
    const std::vector<const SkillGrouping&>& skills = combat_event_subscribers.at(event);
    for (const Skill& skill : skills)
    {
        skill.onDependent(self, target, number_generator);
    }
}

void CombatPublisher::addSkillGrouping(std::unique_ptr<SkillGrouping> skill_grouping) 
{
    owned_skill_groupings.push_back(std::move(skill_grouping));
}
