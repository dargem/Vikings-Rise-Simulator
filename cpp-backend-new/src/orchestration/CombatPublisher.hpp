#ifndef COMBAT_PUBLISHER_HPP
#define COMBAT_PUBLISHER_HPP

#include <vector>
#include <map>
#include "CombatEvents.hpp"
#include "../skills/Skill.hpp"

// forward declaration for combatant to avoid circular dependency
class Combatant;

class CombatPublisher
{
    public:
        CombatPublisher();
        bool subToEvent(Skill skill, CombatEvent combat_event);
        bool unsubToEvent(Skill skill, CombatEvent combat_event);
        void publishEvent(CombatEvent event, Combatant& friendly_combatant, Combatant& enemy_combatant) const;
    private:
        std::map<CombatEvent, std::vector<Skill>> combat_event_subscribers;
};

#endif