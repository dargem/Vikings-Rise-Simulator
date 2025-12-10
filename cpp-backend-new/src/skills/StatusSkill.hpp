#ifndef SKILL_HPP
#define SKILL_HPP

#include "SkillTypes.hpp"
#include <vector>
#include "../orchestration/CombatantEvents.hpp"
#include "../effects/StatusEffect.hpp"
#include "ISkill.hpp"
// forward declaration for combatant to prevent a circular dependency
class Combatant;

class StatusSkill: public ISkill
{
    public:
        StatusSkill(const StatusEffect status_effect, const SkillType skill_type);
        void onDependent(Combatant& friendly_combatant, Combatant& enemy_combatant) const override;
        bool operator==(const ISkill& other) const override;
        std::vector<CombatantEvent> getTriggerEvents() const;
    private:
        const SkillType skill_type;
        const StatusEffect status_effect;
};

#endif