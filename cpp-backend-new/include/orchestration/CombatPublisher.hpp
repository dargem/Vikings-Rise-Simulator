#ifndef COMBAT_PUBLISHER_HPP
#define COMBAT_PUBLISHER_HPP

#include <vector>
#include <map>
#include <memory>
#include "orchestration/CombatantEvent.hpp"
#include "skills/SkillGrouping.hpp"
#include "utils/NumberGenerator.hpp"
// forward declaration for combatant to avoid circular dependency
class Combatant;

class CombatPublisher
{
public:
    CombatPublisher();
    bool subToEvent(const SkillGrouping& skill_grouping);
    bool unsubToEvent(const SkillGrouping& skill_grouping);
    void publishEvent(CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const;
    
    // Takes ownership of the skill
    void addSkillGrouping(std::unique_ptr<SkillGrouping> skill_grouping);
private:
    std::vector<std::unique_ptr<const SkillGrouping>> owned_skill_groupings;
    std::map<CombatantEvent, std::vector<const SkillGrouping&>> combat_event_subscribers;
};

#endif