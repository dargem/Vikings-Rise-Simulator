#ifndef SETUP_PUBLISHER_LOADER_HPP
#define SETUP_PUBLISHER_LOADER_HPP

#include "utils/CombatantSetup.hpp"
#include "orchestration/CombatPublisher.hpp"
#include "utils/SkillParser.hpp"
#include <string_view>
#include <nlohmann/json.hpp>

#ifndef RESOURCE_DIR
#define RESOURCE_DIR "resources"
#endif

using json = nlohmann::json;

/**
 * @brief loads a combat publisher with skills
 */
class SetupPublisherLoader
{
public:
    SetupPublisherLoader();

    bool loadJson();

    /**
     * @brief Loads a combat_publisher with 
     * 
     * @param combat_publisher 
     * @param commanders 
     */
    void loadPublisher(CombatPublisher& combat_publisher, const CombatantSetup& combatant_setup) const;
private:
    
    static constexpr std::string_view skills_path = RESOURCE_DIR "/skills.json";
    json commander_data;
    json skill_data;
    json mount_slot_1_data;
    json mount_slot_2_data;
    /*

    std::vector<Skill> loadSkills(const json& skill_data, CommanderName commander_name, CombatPublisher& combat_publisher) const;
    std::vector<Skill> loadSkills(const json& skill_data, SkillName skill_name, CombatPublisher& combat_publisher) const;
    std::vector<Skill> loadSkills(const json& skill_data, MountSlot1Names mount_slot_1_names, CombatPublisher& combat_publisher) const;
    std::vector<Skill> loadSkills(const json& skill_data, MountSlot2Names mount_slot_2_names, CombatPublisher& combat_publisher) const;

    // Helper functions to convert strings to enums
    SkillType stringToSkillType(const std::string& type_str) const;
    EffectType stringToEffectType(const std::string& effect_str) const;
    SkillTarget stringToSkillTarget(const std::string& target_str) const;
    ConditionType stringToConditionType(const std::string& condition_str) const;
    
    // Build SkillCondition from trigger requirements
    SkillCondition buildSkillCondition(const json& trigger_json) const;
    
    // Determine skill target from JSON (FRIENDLY, ENEMY, etc.)
    SkillTarget determineSkillTarget(const json& skill_json) const;
    */
};

#endif