#ifndef COMBAT_PUBLISHER_HPP
#define COMBAT_PUBLISHER_HPP

#include <vector>
#include <map>
#include "CombatantEvents.hpp"
#include "../skills/Skill.hpp"

// forward declaration for combatant to avoid circular dependency
class Combatant;

class CombatPublisher
{
    public:
        CombatPublisher();
        bool subToEvent(const Skill& skill, CombatantEvent combat_event);
        bool unsubToEvent(const Skill& skill, CombatantEvent combat_event);
        void publishEvent(CombatantEvent event, Combatant& friendly_combatant, Combatant& enemy_combatant) const;
    private:
        std::map<CombatantEvent, std::vector<const Skill*>> combat_event_subscribers;
};

#endif