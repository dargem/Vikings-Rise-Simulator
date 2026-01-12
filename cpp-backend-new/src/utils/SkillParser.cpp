#include "utils/SkillParser.hpp"
#include <iostream>
#include <unordered_map>

std::vector<std::unique_ptr<Skill>> SkillParser::loadSkills(const json& skill_data, CommanderName commander_name, const bool is_primary) const 
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

    for (const auto& [category, skill_array] : commander.items()) 
    {
        if (skill_array.is_array()) 
        {
            for (const auto& skill_json : skill_array) 
            {
                auto json_skills = jsonToSkill(skill_json, is_primary);
                for (auto& skill : json_skills) 
                {
                    skills.push_back(std::move(skill));
                }
            }
        }
    }
    
    return skills;
}

static std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, SkillName skill_name) 
{
    // TODO: Implement mapping for SkillName enum to string ID
    return {};
}

static std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, MountSlot1Names mount_slot_1_names) 
{
    // TODO: Implement mapping
    return {};
}

static std::vector<std::unique_ptr<Skill>> loadSkills(const json& skill_data, MountSlot2Names mount_slot_2_names) 
{
    // TODO: Implement mapping
    return {};
}

std::vector<std::unique_ptr<Skill>> SkillParser::jsonToSkill(const json& skill_json, const bool is_primary_commander) 
{
    if (!skill_json.contains("category")) 
    {
        throw std::runtime_error("Skill JSON missing 'category'");
    }

    std::vector<std::unique_ptr<Skill>> skills;
    
    // Get the skill type from skillType or metaType
    SkillType skill_type = stringToSkillType(skill_json["category"].get<std::string>());
    
    // Build skill condition from trigger requirements
    CombatantEvent dependent = stringToCombatantEvent(
        skill_json["dependentRequirement"].get<std::string>(), 
        is_primary_commander
    );

    if (!skill_json.contains("status_skills") || !skill_json.contains("direct_skills") ||  !skill_json.contains("passive_effects"))
    {
        throw std::runtime_error("Commander talent contains no skills");
    }
    
    // Process status effects, if it has them
    if (skill_json.contains("status_skills"))
    {
        const json& status_skills = skill_json["status_skills"];
        if (!status_skills.is_array()) 
        {
            throw std::runtime_error("status_skills must be an array");
        }
        if (status_skills.empty()) 
        {
            throw std::runtime_error("Skill has empty status_skills");
        }

        for (const json& skill : status_skills)
        {
            Condition condition = buildCondition(skill);
            SkillTarget target = determineSkillTarget(skill);

            if (
                !skill.contains("skillDuration") ||
                !skill.contains("magnitude") || 
                !skill.contains("type") || 
                !skill.contains("removable") ||
                !skill.contains("chance")
            )
            {
                throw std::runtime_error("Status skill missing required fields");
            }

            const double chance = skill["chance"].get<double>();
            const TimedEffect timed_effect(skill["skillDuration"].get<double>(), skill["magnitude"].get<double>());
            EffectType effect_type = stringToEffectType(skill["type"].get<std::string>());
            const bool is_removable = skill["removable"].get<bool>();

            if (target == SkillTarget::FRIENDLY)
            {
                skills.push_back(std::make_unique<StatusSkill<SkillTarget::FRIENDLY>>(
                    timed_effect, 
                    skill_type, 
                    condition, 
                    dependent, 
                    chance, 
                    effect_type, 
                    is_removable
                ));
            }
            else if (target == SkillTarget::ENEMY)
            {
                skills.push_back(std::make_unique<StatusSkill<SkillTarget::ENEMY>>(
                    timed_effect, 
                    skill_type, 
                    condition, 
                    dependent, 
                    chance, 
                    effect_type, 
                    is_removable
                ));
            }
            else
            {
                throw std::runtime_error("target not valid, must be a friendly or an enemy");
            }
        }   
    }

    // Process damage effects, if it has them
    if (skill_json.contains("direct_skills"))
    {
        const json& damage_skills = skill_json["direct_skills"];
        if (!damage_skills.is_array()) 
        {
            throw std::runtime_error("direct_skills must be an array");
        }

        if (damage_skills.empty()) 
        {
            throw std::runtime_error("Skill has empty direct_skills");
        }

        for (const json& skill : damage_skills)
        {
            Condition condition = buildCondition(skill);

            SkillTarget target = determineSkillTarget(skill);

            if (!skill.contains("magnitude") || !skill.contains("chance") || !skill.contains("type")) 
            {
                throw std::runtime_error("Damage skill missing required fields");
            }

            DirectEffectType effect = stringToDirectEffectType(skill["type"].get<std::string>());
            const double chance = skill["chance"].get<double>();
            const double magnitude = skill["magnitude"].get<double>();

            skills.push_back(std::make_unique<DirectSkill>(skill_type, condition, dependent, target, chance, effect, magnitude));
        }
    }

    // Process passive effects, if it has them
    // e.g. +30% BA damage

    if (skill_json.contains("passive_effects"))
    {
        const json& passive_effects = skill_json["passive_effects"];
    }
    
    return skills;
}

Condition SkillParser::buildCondition(const json& skill_json)
{
    if (skill_json.contains("conditionType") && skill_json.contains("triggerRequirement"))
    {
        const EffectType effect_requirement = SkillParser::stringToEffectType(skill_json["triggerRequirement"].get<std::string>());
        const ConditionType condition_type = SkillParser::stringToConditionType(skill_json["conditionType"].get<std::string>());

        return {condition_type, effect_requirement};
    }
    
    throw std::runtime_error("Skill JSON missing 'trigger' with 'triggerRequirement' and 'conditionType'");
}

SkillTarget SkillParser::determineSkillTarget(const json& skill_json) 
{
    static const std::unordered_map<std::string, SkillTarget> skillTargetMap = {
        {"ENEMY", SkillTarget::ENEMY},
        {"FRIENDLY", SkillTarget::FRIENDLY}
    };

    if (skill_json.contains("target")) 
    {
        std::string target = skill_json["target"].get<std::string>();
        
        auto iterator = skillTargetMap.find(target);
        if (iterator != skillTargetMap.end())
        {
            return iterator->second;
        }
        throw std::runtime_error("Unable to determine skill target from effects: " + target);
    }
    
    throw std::runtime_error("skill_json does not contain target");
}

SkillType SkillParser::stringToSkillType(const std::string& type_str) 
{
    static const std::unordered_map<std::string, SkillType> skillTypeMap = {
        {"COMMAND", SkillType::COMMAND},
        {"PASSIVE", SkillType::PASSIVE},
        {"COOPERATION", SkillType::COOPERATION},
        {"COUNTERATTACK", SkillType::COUNTERATTACK},
        {"AWAKEN", SkillType::AWAKEN}
    };
    auto iterator = skillTypeMap.find(type_str);
    if (iterator != skillTypeMap.end()) 
    {
        return iterator->second;
    }
    throw std::runtime_error("Unknown SkillType: " + type_str);
}

EffectType SkillParser::stringToEffectType(const std::string& effect_str) 
{
    static const std::unordered_map<std::string, EffectType> effectTypeMap = {
        {"POISON", EffectType::POISON},
        {"HEAL", EffectType::HEAL},
        {"BURN", EffectType::BURN},
        {"BLEED", EffectType::BLEED},
        {"ABSORPTION", EffectType::ABSORPTION},
        {"RETRIBUTION", EffectType::RETRIBUTION},
        {"SLOW", EffectType::SLOW},
        {"SILENCE", EffectType::SILENCE},
        {"NONE", EffectType::NONE}
    };
    auto iterator = effectTypeMap.find(effect_str);
    if (iterator != effectTypeMap.end()) 
    {
        return iterator->second;
    }
    throw std::runtime_error("Unknown EffectType: " + effect_str);
}

SkillTarget SkillParser::stringToSkillTarget(const std::string& target_str) 
{
    static const std::unordered_map<std::string, SkillTarget> skillTargetMap = {
        {"FRIENDLY", SkillTarget::FRIENDLY},
        {"ENEMY", SkillTarget::ENEMY}
    };
    auto iterator = skillTargetMap.find(target_str);
    if (iterator != skillTargetMap.end()) {
        return iterator->second;
    }
    throw std::runtime_error("Unknown SkillTarget: " + target_str);
}

ConditionType SkillParser::stringToConditionType(const std::string& condition_str) 
{
    static const std::unordered_map<std::string, ConditionType> conditionTypeMap = {
        {"NONE", ConditionType::NONE},
        {"HAS_EFFECT_SELF", ConditionType::HAS_EFFECT_SELF},
        {"HAS_EFFECT_TARGET", ConditionType::HAS_EFFECT_TARGET},
        {"TROOP_COUNT_GREATER_THAN_TARGET", ConditionType::TROOP_COUNT_GREATER_THAN_TARGET}
    };
    auto iterator = conditionTypeMap.find(condition_str);
    if (iterator != conditionTypeMap.end()) {
        return iterator->second;
    }
    throw std::runtime_error("Unknown ConditionType: " + condition_str);
}

CombatantEvent SkillParser::stringToCombatantEvent(const std::string& combatant_event_str, const bool is_primary_commander)
{
    static const std::unordered_map<std::string, CombatantEvent> combatantEventMap = {
        {"START", CombatantEvent::START},
        {"ACTIVE_DEALT", CombatantEvent::ACTIVE_DEALT},
        {"ACTIVE_RECEIVED", CombatantEvent::ACTIVE_RECEIVED},
        {"BASIC_DEALT", CombatantEvent::BASIC_DEALT},
        {"BASIC_RECEIVED", CombatantEvent::BASIC_RECEIVED},
        {"CONTINUOUS_DAMAGE_RECEIVED", CombatantEvent::CONTINUOUS_DAMAGE_RECEIVED},
        {"COUNTER_ATTACK_DEALT", CombatantEvent::COUNTER_ATTACK_DEALT},
        {"SHIELD_GRANTED", CombatantEvent::SHIELD_GRANTED},
        {"ROUND_MOD_6", CombatantEvent::ROUND_MOD_6},
        {"ROUND_MOD_9", CombatantEvent::ROUND_MOD_9}
    };

    auto iterator = combatantEventMap.find(combatant_event_str);
    if (iterator != combatantEventMap.end()) 
    {
        return iterator->second;
    }

    if (combatant_event_str == "COMMANDER_ACTIVE_DEALT") 
    {
        return is_primary_commander 
            ? CombatantEvent::PRIMARY_COMMANDER_ACTIVE_DEALT 
            : CombatantEvent::SECONDARY_COMMANDER_ACTIVE_DEALT;
    }

    throw std::runtime_error("Unknown CombatantEvent: " + combatant_event_str);
}

DirectEffectType SkillParser::stringToDirectEffectType(const std::string& effect_type_str)
{
    static const std::map<std::string, DirectEffectType> directEffectMap = {
        {"DAMAGE", DirectEffectType::DAMAGE},
        {"HEAL", DirectEffectType::HEAL}
    };

    auto iterator = directEffectMap.find(effect_type_str);
    if (iterator != directEffectMap.end())
    {
        return iterator->second;
    }

    throw std::runtime_error("Direct effect cannot be " + effect_type_str);
}