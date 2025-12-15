#include "utils/SkillParser.hpp"
#include <iostream>

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, CommanderName commander_name) const 
{
    std::string name_str;
    switch (commander_name) 
    {
        case CommanderName::Sigrid: name_str = "Sigrid"; break;
        default: throw std::runtime_error("No matching commander string");
    }

    std::vector<std::unique_ptr<Skill>> skills;
    
    if (!skill_data.contains("Commanders") || !skill_data["Commanders"].contains(name_str)) {
        throw std::runtime_error("JSON does not contain commander " + name_str);
    }
    
    const json& commander = skill_data["Commanders"][name_str];
    
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

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, SkillName skill_name) const {
    // TODO: Implement mapping for SkillName enum to string ID
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, MountSlot1Names mount_slot_1_names) const {
    // TODO: Implement mapping
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, MountSlot2Names mount_slot_2_names) const {
    // TODO: Implement mapping
    return {};
}

std::unique_ptr<Skill> SkillParser::jsonToSkill(const json& skill_json) const {
    if (!skill_json.contains("effects") || !skill_json["effects"].is_array()) {
        throw std::runtime_error("Skill missing effects array");
    }
    
    // Get the skill type from skillType or metaType
    SkillType skill_type;
    if (skill_json.contains("skillType") && skill_json["skillType"].contains("category")) {
        skill_type = stringToSkillType(skill_json["skillType"]["category"].get<std::string>());
    } else if (skill_json.contains("metaType") && skill_json["metaType"].contains("category")) {
        skill_type = stringToSkillType(skill_json["metaType"]["category"].get<std::string>());
    } else {
        throw std::runtime_error("Skill JSON missing 'skillType' or 'metaType' with 'category'");
    }
    
    // Build skill condition from trigger requirements
    SkillCondition condition = buildSkillCondition(skill_json);
    
    // Determine skill target
    SkillTarget target = determineSkillTarget(skill_json);
    
    // Process effects - create appropriate skill type based on first effect
    const json& effects = skill_json["effects"];
    if (effects.empty()) {
        throw std::runtime_error("Skill has no effects");
    }
    
    const json& first_effect = effects[0];
    if (!first_effect.contains("type") || !first_effect.contains("magnitude")) {
        throw std::runtime_error("Effect missing type or magnitude");
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
        if (!first_effect.contains("skillDuration")) {
            throw std::runtime_error("Status effect missing skillDuration");
        }
        int duration = first_effect["skillDuration"].get<int>();
        
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
    if (skill_json.contains("trigger") && skill_json["trigger"].contains("triggerRequirement")) {
        const json& requirements = skill_json["trigger"]["triggerRequirement"];
        if (requirements.is_array() && !requirements.empty()) {
            std::string first_req = requirements[0].get<std::string>();
            
            if (first_req == "poison") {
                return SkillCondition(ConditionType::HAS_EFFECT_SELF, EffectType::POISON);
            }
            
            throw std::runtime_error("Unknown trigger requirement: " + first_req);
        }
    }
    
    throw std::runtime_error("Skill JSON missing 'trigger' with 'triggerRequirement'");
}

SkillTarget SkillParser::determineSkillTarget(const json& skill_json) const {
    if (skill_json.contains("effects") && skill_json["effects"].is_array() && !skill_json["effects"].empty()) {
        std::string effect_type = skill_json["effects"][0]["type"].get<std::string>();
        if (effect_type == "heal" || effect_type == "shield" || effect_type == "buff") {
            return SkillTarget::FRIENDLY;
        } else if (effect_type == "damage" || effect_type == "directDamage" || effect_type == "poison" || effect_type == "burn") {
            return SkillTarget::ENEMY;
        }
    }
    
    throw std::runtime_error("Unable to determine skill target from effects");
}

SkillType SkillParser::stringToSkillType(const std::string& type_str) const {
    if (type_str == "command") return SkillType::COMMAND;
    if (type_str == "passive") return SkillType::PASSIVE;
    if (type_str == "cooperation") return SkillType::COOPERATION;
    if (type_str == "counterattack") return SkillType::COUNTERATTACK;
    throw std::runtime_error("Unknown SkillType: " + type_str);
}

EffectType SkillParser::stringToEffectType(const std::string& effect_str) const {
    if (effect_str == "poison") return EffectType::POISON;
    if (effect_str == "heal" || effect_str == "healing") return EffectType::HEALING;
    if (effect_str == "burn") return EffectType::BURN;
    if (effect_str == "bleed") return EffectType::BLEED;
    if (effect_str == "absorption") return EffectType::ABSORPTION;
    if (effect_str == "retribution") return EffectType::RETRIBUTION;
    if (effect_str == "damage" || effect_str == "directDamage") return EffectType::BURN; // Using BURN as placeholder for damage
    throw std::runtime_error("Unknown EffectType: " + effect_str);
}

SkillTarget SkillParser::stringToSkillTarget(const std::string& target_str) const 
{
    if (target_str == "friendly" || target_str == "FRIENDLY") return SkillTarget::FRIENDLY;
    if (target_str == "enemy" || target_str == "ENEMY") return SkillTarget::ENEMY;
    throw std::runtime_error("Unknown SkillTarget: " + target_str);
}

ConditionType SkillParser::stringToConditionType(const std::string& condition_str) const {
    if (condition_str == "HAS_EFFECT_SELF") return ConditionType::HAS_EFFECT_SELF;
    if (condition_str == "HAS_EFFECT_TARGET") return ConditionType::HAS_EFFECT_TARGET;
    if (condition_str == "TROOP_COUNT_GREATER_THAN_TARGET") return ConditionType::TROOP_COUNT_GREATER_THAN_TARGET;
    throw std::runtime_error("Unknown ConditionType: " + condition_str);
}
