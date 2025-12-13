#include "utils/CombatantSetup.hpp"
#include "orchestration/CombatPublisher.hpp"
#include "utils/SkillParser.hpp"
#include <string>

/**
 * @brief loads a combat publisher with skills
 */
class SetupPublisherLoader
{
public:
    /**
     * @brief Loads a combat_publisher with 
     * 
     * @param combat_publisher 
     * @param commanders 
     */
    void load_publisher(CombatPublisher& combat_publisher, const CombatantSetup& combatant_setup) const;
private:
    static constexpr char* skills_path { "cpp-backend-new/resources/skills.json" };
};