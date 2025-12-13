#include "utils/SkillParser.hpp"
#include <fstream>
#include <iostream>

SkillParser::SkillParser(const std::string& json_file_path) 
    : file_path(json_file_path) 
{}

bool SkillParser::loadJson() {
    try {
        std::ifstream file(file_path);
        if (!file.is_open()) {
            std::cerr << "Failed to open file: " << file_path << std::endl;
            return false;
        }
        
        file >> skill_data;
        return true;
    } catch (const json::exception& e) {
        std::cerr << "JSON parsing error: " << e.what() << std::endl;
        return false;
    }
}

std::vector<std::unique_ptr<Skill>> SkillParser::getCommanderSkills(const std::string& commander_name) const {
    std::vector<std::unique_ptr<Skill>> skills;
    
    if (!skill_data.contains("Commanders") || !skill_data["Commanders"].contains(commander_name)) {
        return skills;
    }
    
    const json& commander = skill_data["Commanders"][commander_name];
    
    // Parse all skill categories (AwakenedActive, Secondary, etc.)
    for (auto& [category, skill_array] : commander.items()) {
        if (skill_array.is_array()) {
            for (const auto& skill_json : skill_array) {
                auto skill = jsonToSkill(skill_json);
                if (skill) {
                    skills.push_back(std::move(skill));
                }
            }
        }
    }
    
    return skills;
}

std::unique_ptr<Skill> SkillParser::getSkillById(const std::string& skill_id) const {
    // Search in Commanders
    if (skill_data.contains("Commanders")) {
        for (auto& [commander_name, commander_data] : skill_data["Commanders"].items()) {
            for (auto& [category, skill_array] : commander_data.items()) {
                if (skill_array.is_array()) {
                    for (const auto& skill_json : skill_array) {
                        if (skill_json.contains("id") && skill_json["id"] == skill_id) {
                            return jsonToSkill(skill_json);
                        }
                    }
                }
            }
        }
    }
    
    // Search in Skills section
    if (skill_data.contains("Skills")) {
        for (auto& [skill_name, skill_json] : skill_data["Skills"].items()) {
            if (skill_json.contains("id") && skill_json["id"] == skill_id) {
                return jsonToSkill(skill_json);
            }
        }
    }
    
    return nullptr;
}

std::vector<std::unique_ptr<Skill>> SkillParser::getGenericSkills() const {
    std::vector<std::unique_ptr<Skill>> skills;
    
    if (!skill_data.contains("Skills")) {
        return skills;
    }
    
    for (auto& [skill_name, skill_json] : skill_data["Skills"].items()) {
        auto skill = jsonToSkill(skill_json);
        if (skill) {
            skills.push_back(std::move(skill));
        }
    }
    
    return skills;
}

std::unique_ptr<Skill> SkillParser::jsonToSkill(const json& skill_json) const {
    if (!skill_json.contains("effects") || !skill_json["effects"].is_array()) {
        std::cerr << "Skill missing effects array" << std::endl;
        return nullptr;
    }
    
    // Get the skill type from skillType or metaType
    SkillType skill_type = SkillType::COMMAND; // Default
    if (skill_json.contains("skillType") && skill_json["skillType"].contains("category")) {
        skill_type = stringToSkillType(skill_json["skillType"]["category"].get<std::string>());
    } else if (skill_json.contains("metaType") && skill_json["metaType"].contains("category")) {
        skill_type = stringToSkillType(skill_json["metaType"]["category"].get<std::string>());
    }
    
    // Build skill condition from trigger requirements
    SkillCondition condition = buildSkillCondition(skill_json);
    
    // Determine skill target
    SkillTarget target = determineSkillTarget(skill_json);
    
    // Process effects - create appropriate skill type based on first effect
    const json& effects = skill_json["effects"];
    if (effects.empty()) {
        std::cerr << "Skill has no effects" << std::endl;
        return nullptr;
    }
    
    const json& first_effect = effects[0];
    if (!first_effect.contains("type") || !first_effect.contains("magnitude")) {
        std::cerr << "Effect missing type or magnitude" << std::endl;
        return nullptr;
    }
    
    std::string effect_type_str = first_effect["type"].get<std::string>();
    EffectType effect_type = stringToEffectType(effect_type_str);
    double magnitude = first_effect["magnitude"].get<double>();
    
    // Determine if this is a status effect (heal, poison, burn, shield, etc.) or direct damage
    if (effect_type_str == "directDamage" || effect_type_str == "damage") {
        // Create DamageSkill
        return std::make_unique<DamageSkill>(
            magnitude,
            skill_type,
            effect_type,
            condition,
            target
        );
    } else {
        // Create StatusSkill (heal, poison, burn, shield, etc.)
        // Need duration for TimedEffect
        int duration = 1; // Default duration
        if (first_effect.contains("skillDuration")) {
            duration = first_effect["skillDuration"].get<int>();
        }
        
        TimedEffect timed_effect(duration, magnitude);
        
        return std::make_unique<StatusSkill>(
            timed_effect,
            skill_type,
            effect_type,
            condition,
            target
        );
    }
}

SkillCondition SkillParser::buildSkillCondition(const json& skill_json) const {
    // TODO: Parse trigger requirements and build proper SkillCondition
    // For now, return a default condition
    // Need to map triggerRequirement array to ConditionType and EffectType
    
    ConditionType condition_type = ConditionType::HAS_EFFECT_SELF; // Default
    EffectType effect_type = EffectType::POISON; // Default
    
    if (skill_json.contains("trigger") && skill_json["trigger"].contains("triggerRequirement")) {
        const json& requirements = skill_json["trigger"]["triggerRequirement"];
        if (requirements.is_array() && !requirements.empty()) {
            // TODO: Map string requirements to ConditionType and EffectType
            // Example: "poison" -> ConditionType::HAS_EFFECT_SELF, EffectType::POISON
            // Example: "basicAttack" -> ConditionType related to attack
            std::string first_req = requirements[0].get<std::string>();
            
            // Basic mapping (needs expansion based on your requirements)
            if (first_req == "poison") {
                condition_type = ConditionType::HAS_EFFECT_SELF;
                effect_type = EffectType::POISON;
            }
            // TODO: Add more condition mappings
        }
    }
    
    return SkillCondition(condition_type, effect_type);
}

SkillTarget SkillParser::determineSkillTarget(const json& skill_json) const {
    // TODO: Determine from JSON if skill targets FRIENDLY or ENEMY
    // This might be in a "target" field or inferred from effect type
    // For now, default to FRIENDLY for heals, ENEMY for damage
    
    if (skill_json.contains("effects") && skill_json["effects"].is_array() && !skill_json["effects"].empty()) {
        std::string effect_type = skill_json["effects"][0]["type"].get<std::string>();
        if (effect_type == "heal" || effect_type == "shield" || effect_type == "buff") {
            return SkillTarget::FRIENDLY;
        } else if (effect_type == "damage" || effect_type == "directDamage" || effect_type == "poison" || effect_type == "burn") {
            return SkillTarget::ENEMY;
        }
    }
    
    // TODO: Add explicit target field parsing if it exists in JSON
    return SkillTarget::ENEMY; // Default
}

SkillType SkillParser::stringToSkillType(const std::string& type_str) const {
    if (type_str == "command") return SkillType::COMMAND;
    if (type_str == "passive") return SkillType::PASSIVE;
    if (type_str == "cooperation") return SkillType::COOPERATION;
    if (type_str == "counterattack") return SkillType::COUNTERATTACK;
    // TODO: Add more SkillType mappings as needed
    return SkillType::COMMAND; // Default
}

EffectType SkillParser::stringToEffectType(const std::string& effect_str) const {
    if (effect_str == "poison") return EffectType::POISON;
    if (effect_str == "heal" || effect_str == "healing") return EffectType::HEALING;
    if (effect_str == "burn") return EffectType::BURN;
    if (effect_str == "bleed") return EffectType::BLEED;
    if (effect_str == "absorption") return EffectType::ABSORPTION;
    if (effect_str == "retribution") return EffectType::RETRIBUTION;
    // TODO: Add specific damage effect type or use appropriate existing type
    if (effect_str == "damage" || effect_str == "directDamage") return EffectType::BURN; // Using BURN as placeholder for damage
    // TODO: Add more EffectType mappings based on your EffectType enum
    return EffectType::POISON; // Default
}

SkillTarget SkillParser::stringToSkillTarget(const std::string& target_str) const {
    if (target_str == "friendly" || target_str == "FRIENDLY") return SkillTarget::FRIENDLY;
    if (target_str == "enemy" || target_str == "ENEMY") return SkillTarget::ENEMY;
    // TODO: Add more SkillTarget mappings if needed
    return SkillTarget::ENEMY; // Default
}

ConditionType SkillParser::stringToConditionType(const std::string& condition_str) const {
    if (condition_str == "HAS_EFFECT_SELF") return ConditionType::HAS_EFFECT_SELF;
    if (condition_str == "HAS_EFFECT_TARGET") return ConditionType::HAS_EFFECT_TARGET;
    if (condition_str == "TROOP_COUNT_GREATER_THAN_TARGET") return ConditionType::TROOP_COUNT_GREATER_THAN_TARGET;
    // TODO: Add more ConditionType mappings as needed
    return ConditionType::HAS_EFFECT_SELF; // Default
}
