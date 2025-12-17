#include "utils/SkillParser.hpp"
#include <iostream>

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, CommanderName commander_name, const bool isPrimary) const 
{
    std::string name_str;
    switch (commander_name) 
    {
        case CommanderName::Sigrid: name_str = "Sigrid"; break;
        default: throw std::runtime_error("No matching commander string");
    }

    std::vector<std::unique_ptr<Skill>> skills;
    
    if (!skill_data.contains("Commanders") || !skill_data["Commanders"].contains(name_str)) 
    {   
        throw std::runtime_error("JSON does not contain commander " + name_str);
    }
    
    const json& commander = skill_data["Commanders"][name_str];
    
    // Parse all skill categories (AwakenedActive, Secondary, etc.)
    if (!commander.contains("AwakenedActive"))
    {
        throw std::runtime_error("Commander does not have an active: " + name_str);
    }

    for (auto& [category, skill_array] : commander.items()) 
    {
        if (skill_array.is_array()) 
        {
            for (const auto& skill_json : skill_array) 
            {
                auto json_skills = jsonToSkill(skill_json);
                for (auto& skill : json_skills) 
                {
                    skills.push_back(std::move(skill));
                }
            }
        }
    }
    
    return skills;
}

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, SkillName skill_name) const 
{
    // TODO: Implement mapping for SkillName enum to string ID
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, MountSlot1Names mount_slot_1_names) const 
{
    // TODO: Implement mapping
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, MountSlot2Names mount_slot_2_names) const 
{
    // TODO: Implement mapping
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::jsonToSkill(const json& skill_json) const 
{
    if (!skill_json.contains("skillType") || !skill_json["skillType"].contains("category")) 
    {
        throw std::runtime_error("Skill JSON missing 'skillType' or 'skillType' with 'category'");
    }

    std::vector<std::unique_ptr<Skill>> skills;
    
    // Get the skill type from skillType or metaType
    SkillType skill_type = stringToSkillType(skill_json["skillType"]["category"].get<std::string>());
    
    // Build skill condition from trigger requirements
    SkillCondition condition = buildSkillCondition(skill_json);
    CombatantEvent dependent = stringToCombatantEvent(skill_json["trigger"]["dependentRequirement"].get<std::string>());

    // Process status effects, if it has them
    if (skill_json.contains("status_skills"))
    {
        const json& status_skills = skill_json["status_skills"];
        if (!status_skills.is_array()) throw std::runtime_error("status_skills must be an array");
        if (status_skills.empty()) throw std::runtime_error("Skill has empty status_skills");

        for (const json& skill : status_skills)
        {
            SkillTarget target = determineSkillTarget(skill_json);

            if (!skill.contains("skillDuration") || !skill.contains("magnitude") || !skill.contains("type")) {
                 throw std::runtime_error("Status skill missing required fields");
            }

            const TimedEffect timed_effect(skill["skillDuration"].get<double>(), skill["magnitude"].get<double>());
            EffectType effect_type = stringToEffectType(skill["type"].get<std::string>());

            skills.push_back(std::make_unique<StatusSkill>(timed_effect, skill_type, effect_type, condition, dependent, target));
        }
    }

    // Process damage effects, if it has them
    if (skill_json.contains("direct_damage_skills"))
    {
        const json& damage_skills = skill_json["direct_damage_skills"];
        if (!damage_skills.is_array()) throw std::runtime_error("direct_damage_skills must be an array");
        if (damage_skills.empty()) throw std::runtime_error("Skill has empty direct_damage_skills");

        for (const json& skill : damage_skills)
        {
            SkillTarget target = determineSkillTarget(skill_json);

            if (!skill.contains("magnitude") || !skill.contains("type")) {
                throw std::runtime_error("Damage skill missing required fields");
            }

            double magnitude = skill["magnitude"].get<double>();
            EffectType effect_type = stringToEffectType(skill["type"].get<std::string>());

            skills.push_back(std::make_unique<DamageSkill>(magnitude, skill_type, effect_type, condition, dependent, target));
        }
    }
    
    return skills;
}

SkillCondition SkillParser::buildSkillCondition(const json& skill_json) const 
{
    if (skill_json.contains("trigger") && skill_json["trigger"].contains("triggerRequirement") && skill_json["trigger"].contains("conditionType"))
    {
        const ConditionType condition_type = stringToConditionType(skill_json["trigger"]["conditionType"].get<std::string>());
        const EffectType effect_requirement = stringToEffectType(skill_json["trigger"]["triggerRequirement"].get<std::string>());

        return SkillCondition(condition_type, effect_requirement);
    }
    
    throw std::runtime_error("Skill JSON missing 'trigger' with 'triggerRequirement'");
}

SkillTarget SkillParser::determineSkillTarget(const json& skill_json) const 
{
    if (skill_json.contains("target"))
    {
        std::string target = skill_json["target"];
        if (target == "enemy") return SkillTarget::ENEMY;
        if (target == "friendly") return SkillTarget::FRIENDLY;
    }
    
    throw std::runtime_error("Unable to determine skill target from effects");
}

SkillType SkillParser::stringToSkillType(const std::string& type_str) const 
{
    if (type_str == "command") return SkillType::COMMAND;
    if (type_str == "passive") return SkillType::PASSIVE;
    if (type_str == "cooperation") return SkillType::COOPERATION;
    if (type_str == "counterattack") return SkillType::COUNTERATTACK;
    throw std::runtime_error("Unknown SkillType: " + type_str);
}

EffectType SkillParser::stringToEffectType(const std::string& effect_str) const 
{
    if (effect_str == "poison") return EffectType::POISON;
    if (effect_str == "heal") return EffectType::HEALING;
    if (effect_str == "burn") return EffectType::BURN;
    if (effect_str == "bleed") return EffectType::BLEED;
    if (effect_str == "absorption") return EffectType::ABSORPTION;
    if (effect_str == "retribution") return EffectType::RETRIBUTION;
    throw std::runtime_error("Unknown EffectType: " + effect_str);
}

SkillTarget SkillParser::stringToSkillTarget(const std::string& target_str) const 
{
    if (target_str == "friendly" || target_str == "FRIENDLY") return SkillTarget::FRIENDLY;
    if (target_str == "enemy" || target_str == "ENEMY") return SkillTarget::ENEMY;
    throw std::runtime_error("Unknown SkillTarget: " + target_str);
}

ConditionType SkillParser::stringToConditionType(const std::string& condition_str) const 
{
    if (condition_str == "HAS_EFFECT_SELF") return ConditionType::HAS_EFFECT_SELF;
    if (condition_str == "HAS_EFFECT_TARGET") return ConditionType::HAS_EFFECT_TARGET;
    if (condition_str == "TROOP_COUNT_GREATER_THAN_TARGET") return ConditionType::TROOP_COUNT_GREATER_THAN_TARGET;
    throw std::runtime_error("Unknown ConditionType: " + condition_str);
}

CombatantEvent SkillParser::stringToCombatantEvent(const std::string& combatant_event_str) const
{
    if (combatant_event_str == "START") return CombatantEvent::START;
    // active primary skipped, both should be handled elsewhere to differentiate primary/secondary
    // active secondary skipped
    if (combatant_event_str == "ACTIVE_RECEIVED") return CombatantEvent::ACTIVE_RECEIVED;
    if (combatant_event_str == "BASIC_DEALT") return CombatantEvent::BASIC_DEALT;
    if (combatant_event_str == "BASIC_RECEIVED") return CombatantEvent::BASIC_RECEIVED;
    if (combatant_event_str == "CONTINUOUS_DAMAGE_RECEIVED") return CombatantEvent::CONTINUOUS_DAMAGE_RECEIVED;
    if (combatant_event_str == "COUNTER_ATTACK_DEALT") return CombatantEvent::COUNTER_ATTACK_DEALT;
    if (combatant_event_str == "SHIELD_GRANTED") return CombatantEvent::SHIELD_GRANTED;
    if (combatant_event_str == "ROUND_MOD_6") return CombatantEvent::ROUND_MOD_6;
    if (combatant_event_str == "ROUND_MOD_9") return CombatantEvent::ROUND_MOD_9;

    throw std::runtime_error("Unknown CombatantEvent: " + combatant_event_str);
}