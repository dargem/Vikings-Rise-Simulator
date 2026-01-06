#ifndef COMBAT_PUBLISHER_HPP
#define COMBAT_PUBLISHER_HPP

#include <vector>
#include <map>
#include <memory>
#include "orchestration/CombatantEvent.hpp"
#include "skills/Skill.hpp"
#include "utils/NumberGenerator.hpp"
// forward declaration for combatant to avoid circular dependency
class Combatant;

class CombatPublisher
{
public:
    CombatPublisher();
    bool subToEvent(const Skill* skill);
    bool unsubToEvent(const Skill* skill);
    void publishEvent(CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const;
    
    // Subscribe a skill reference (does not take ownership)
    void addSkill(const Skill* skill);
private:
    std::map<CombatantEvent, std::vector<const Skill*>> combat_event_subscribers;
};

#endif