#include "utils/CombatantSetup.hpp"
#include "orchestration/CombatPublisher.hpp"
#include "utils/SkillParser.hpp"
#include <string_view>

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
    json skill_data;
};