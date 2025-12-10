#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillTypes.hpp"
#include <vector>
#include "../orchestration/CombatantEvents.hpp"
#include "ISkill.hpp"
// forward declaration for combatant to prevent a circular dependency
class Combatant;

class StatusSkill: public ISkill
{
    public:
        void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const override;
        bool operator==(const ISkill& other) const override;
        std::vector<CombatantEvent> getTriggerEvents() const;
    private:
        SkillType skill_type;
};

#endif