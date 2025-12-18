#ifndef SKILL_PARSER_HPP
#define SKILL_PARSER_HPP

#include <string>
#include <vector>
#include <optional>
#include <memory>
#include <nlohmann/json.hpp>
#include "skills/Skill.hpp"
#include "skills/StatusSkill.hpp"
#include "skills/DamageSkill.hpp"
#include "skills/SkillType.hpp"
#include "skills/SkillTarget.hpp"
#include "skills/ConditionType.hpp"
#include "effects/EffectType.hpp"
#include "effects/TimedEffect.hpp"
#include "utils/CommanderName.hpp"
#include "utils/SkillName.hpp"
#include "utils/MountSlot1Names.hpp"
#include "utils/MountSlot2Names.hpp"
#include "orchestration/CombatantEvent.hpp"

using json = nlohmann::json;

class SkillParser 
{
public:
    
    // turns a skill name into a vector of actual skill objects
    [[nodiscard]] std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, CommanderName commander_name, bool isPrimary) const;
    [[nodiscard]] std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, SkillName skill_name) const;
    [[nodiscard]] std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, MountSlot1Names mount_slot_1_names) const;
    [[nodiscard]] std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, MountSlot2Names mount_slot_2_names) const;
    
private:    

    // Helper functions to convert strings to enums
    [[nodiscard]] static SkillType stringToSkillType(const std::string& type_str);
    [[nodiscard]] static EffectType stringToEffectType(const std::string& effect_str);
    [[nodiscard]] static SkillTarget stringToSkillTarget(const std::string& target_str);
    [[nodiscard]] static ConditionType stringToConditionType(const std::string& condition_str);
    [[nodiscard]] static CombatantEvent stringToCombatantEvent(const std::string& combatant_event_str, bool is_primary_commander);

    // Build SkillCondition from trigger requirements
    [[nodiscard]] static SkillCondition buildSkillCondition(const json& skill_json);
    
    // Determine skill target from JSON (FRIENDLY, ENEMY, etc.)
    [[nodiscard]] static SkillTarget determineSkillTarget(const json& skill_json);

    [[nodiscard]] static std::vector<std::unique_ptr<Skill>> jsonToSkill(const json& skill_json, bool is_primary_commander);
};

#endif
