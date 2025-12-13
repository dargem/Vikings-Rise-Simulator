#include "utils/CommanderName.hpp"
#include "orchestration/CombatPublisher.hpp"
#include <vector>

/**
 * @brief loads a combat publisher with skills
 */
class SetupPublisherLoader
{
public:
    void load_publisher(CombatPublisher combat_publisher, std::vector<CommanderName> commanders);
private:

};