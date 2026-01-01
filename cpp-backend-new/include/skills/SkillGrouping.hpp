#ifndef SKILL_GROUPING_HPP
#define SKILL_GROUPING_HPP

#include <vector>
#include "skills/Skill.hpp"
#include "orchestration/CombatantEvent.hpp"
#include "utils/NumberGenerator.hpp"
#include <memory>

class SkillGrouping
{
public:
    SkillGrouping(CombatantEvent dependent, double chance);

    /**
     * @brief Called when a dependent is triggered
     * 
     * @param self your combatant
     * @param target the targets combatant
     * @param number_generator used for number generation
     */
    void onDependent(
        Combatant& self, 
        Combatant& target, 
        NumberGenerator& number_generator
    ) const;

    /**
     * @brief Add a skill to the groupings internal vector
     * 
     * @param skill skill to add
     */
    void addSkill(std::unique_ptr<Skill> skill);

    [[nodiscard]] CombatantEvent getDependentEvent() const;
private:
    std::vector<std::unique_ptr<Skill>> skills;

    const CombatantEvent dependent;
    const bool always_triggers;
    const double chance;

    [[nodiscard]] 
    bool checkShouldTrigger(NumberGenerator& number_generator) const;
};

#endif