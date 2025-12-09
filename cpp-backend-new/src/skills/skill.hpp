#ifndef SKILL_HPP
#define SKILL_HPP

#include "skill_types.hpp"
#include "../orchestration/combat_events.hpp"
#include <vector>

class Skill
{
    public:
        std::vector<CombatEvent> getTriggerEvents();

    private:
        SkillType skill_type;
          
};

#endif