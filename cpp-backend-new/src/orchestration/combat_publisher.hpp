#include <vector>
#include <map>
#include "combat_events.hpp"
#include "../skills/skill.hpp"

class CombatPublisher
{
    public:
        bool subToEvent(Skill& skill, CombatEvent combat_event);
        bool unsubToEvent(Skill& skill, CombatEvent combat_event);
        void publishEvent(CombatEvent event);
    private:
        std::map<CombatEvent, std::vector<Skill&>> combat_event_subscribers;
};