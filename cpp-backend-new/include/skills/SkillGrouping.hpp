#ifndef SKILL_GROUPING_HPP
#define SKILL_GROUPING_HPP

#include <vector>
#include "skills/Skill.hpp"
#include "orchestration/CombatantEvent.hpp"
#include "utils/NumberGenerator.hpp"
#include <memory>

class SkillGrouping : Skill
{
public:
    SkillGrouping(
        SkillType skill_type, 
        Condition skill_condition, 
        CombatantEvent dependent, 
        double chance
    );

    SkillGrouping(
        CombatantEvent dependent,
        double chance
    );

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
    ) const override;

    /**
     * @brief Add a skill to the groupings internal vector
     * 
     * @param skill skill to add
     */
    void addSkill(std::unique_ptr<Skill> skill);
private:
    std::vector<std::unique_ptr<Skill>> skills;
};

#endif