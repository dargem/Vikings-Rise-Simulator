#ifndef SKILL_PARSER_HPP
#define SKILL_PARSER_HPP

#include <string>
#include <vector>
#include <optional>
#include <memory>
#include <nlohmann/json.hpp>
#include "../src/skills/Skill.hpp"
#include "../src/skills/StatusSkill.hpp"
#include "../src/skills/DamageSkill.hpp"
#include "../src/skills/SkillType.hpp"
#include "../src/skills/SkillTarget.hpp"
#include "../src/skills/ConditionType.hpp"
#include "../src/effects/EffectType.hpp"
#include "../src/effects/TimedEffect.hpp"

using json = nlohmann::json;

class SkillParser {
public:
    SkillParser(const std::string& json_file_path);
    
    // Search for all skills belonging to a commander and convert to Skill objects
    std::vector<std::unique_ptr<Skill>> getCommanderSkills(const std::string& commander_name) const;
    
    // Search for a specific skill by ID and convert to Skill object
    std::unique_ptr<Skill> getSkillById(const std::string& skill_id) const;
    
    // Gets skills from the "Skills" section
    std::vector<std::unique_ptr<Skill>> getGenericSkill(const std::string& generic_skill) const;
    
    // Load and parse the JSON file
    bool loadJson();
    
private:
    std::string file_path;
    json skill_data;
    
    // Convert JSON skill to actual Skill object (StatusSkill, DamageSkill, etc.)
    std::unique_ptr<Skill> jsonToSkill(const json& skill_json) const;
    
    // Helper functions to convert strings to enums
    SkillType stringToSkillType(std::string_view& type_str) const;
    EffectType stringToEffectType(std::string_view& effect_str) const;
    SkillTarget stringToSkillTarget(std::string_view& target_str) const;
    ConditionType stringToConditionType(std::string_view& condition_str) const;
    SkillTarget stringToTargetType(std::string_view& target_str) const;
    
    // Build SkillCondition from trigger requirements
    SkillCondition buildSkillCondition(const json& trigger_json) const;
    
    // Determine skill target from JSON (FRIENDLY, ENEMY, etc.)
    SkillTarget determineSkillTarget(const json& skill_json) const;
};

#endif
