#include "orchestration/SetupPublisherLoader.hpp"
#include <fstream>
#include <iostream>
#include <stdexcept>
#include <string>
#include <nlohmann/json.hpp>

SetupPublisherLoader::SetupPublisherLoader() 
{
    // make it throw later
    loadJson();
}

bool SetupPublisherLoader::loadJson() 
{
    try 
    {
        std::ifstream file(std::string(skills_path).c_str());
        if (!file.is_open()) 
        {
            std::cerr << "Failed to open file: " << skills_path << "\n";
            return false;
        }
        

        file >> skill_data;
        return true;
    } 
    catch (const json::exception& e) 
    {
        std::cerr << "JSON parsing error: " << e.what() << "\n";
        return false;
    }
}

void SetupPublisherLoader::loadPublisher(CombatPublisher& combat_publisher, const CombatantSetup& combatant_setup)
{
    SkillParser parser;

    // Load skills for each commander
    for (int i = 0; i < CombatantSetup::NUM_COMMANDERS; ++i) {

        const bool is_primary { i == 0 };

        auto skills = parser.loadSkills(skill_data, combatant_setup.commanders[i], is_primary);
        for (auto& skill : skills) 
        {
            // Store ownership in this loader, provide reference to publisher
            combat_publisher.addSkill(skill.get());
            owned_skills.push_back(std::move(skill));
        }
    }
    
    // TODO: Load other skills (skills array, mount skills) when implemented in parser
}

