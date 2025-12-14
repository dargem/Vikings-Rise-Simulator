#include "orchestration/SetupPublisherLoader.hpp"
#include <fstream>
#include <iostream>
#include <stdexcept>
#include <string>

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
        if (!file.is_open()) {
            std::cerr << "Failed to open file: " << skills_path << std::endl;
            return false;
        }
        
        file >> skill_data;
        return true;
    } 
    catch (const json::exception& e) 
    {
        std::cerr << "JSON parsing error: " << e.what() << std::endl;
        return false;
    }
}

void SetupPublisherLoader::loadPublisher(CombatPublisher& combat_publisher, const CombatantSetup& combatant_setup) const
{

}

