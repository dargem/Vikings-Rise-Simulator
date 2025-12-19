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
        bool subToEvent(const Skill& skill, CombatantEvent combat_event);
        bool unsubToEvent(const Skill& skill, CombatantEvent combat_event);
        void publishEvent(CombatantEvent event, Combatant& self, Combatant& target, NumberGenerator& number_generator) const;
        
        // Takes ownership of the skill
        void addSkill(std::unique_ptr<Skill> skill);
    private:
        std::vector<std::unique_ptr<Skill>> owned_skills;
        std::map<CombatantEvent, std::vector<const Skill*>> combat_event_subscribers;
};

#endif