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

using json = nlohmann::json;

class SkillParser {
public:
    SkillParser(const std::string& json_file_path);
    
    // Search for all skills belonging to a commander and convert to Skill objects
    std::vector<std::unique_ptr<Skill>> getCommanderSkills(const std::string& commander_name) const;
    
    // Search for a specific skill by ID and convert to Skill object
    std::unique_ptr<Skill> getSkillById(const std::string& skill_id) const;
    
    // Get all skills from the "Skills" section
    std::vector<std::unique_ptr<Skill>> getGenericSkills() const;
    
    // Load and parse the JSON file
    bool loadJson();
    
private:
    std::string file_path;
    json skill_data;
    
    // Convert JSON skill to actual Skill object (StatusSkill, DamageSkill, etc.)
    std::unique_ptr<Skill> jsonToSkill(const json& skill_json) const;
    
    // Helper functions to convert strings to enums
    SkillType stringToSkillType(const std::string& type_str) const;
    EffectType stringToEffectType(const std::string& effect_str) const;
    SkillTarget stringToSkillTarget(const std::string& target_str) const;
    ConditionType stringToConditionType(const std::string& condition_str) const;
    
    // Build SkillCondition from trigger requirements
    SkillCondition buildSkillCondition(const json& trigger_json) const;
    
    // Determine skill target from JSON (FRIENDLY, ENEMY, etc.)
    SkillTarget determineSkillTarget(const json& skill_json) const;
};

#endif
