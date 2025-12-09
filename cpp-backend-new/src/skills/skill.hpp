#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillTypes.hpp"
#include <vector>
#include "../orchestration/CombatEvents.hpp"
#include "../combatants/Combatant.hpp"

class Skill
{
    public:
        void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const;
        friend bool operator==(const Skill& skill_1, const Skill& skill_2);
        std::vector<CombatEvent> getTriggerEvents() const;
    private:
        SkillType skill_type;
};

#endif