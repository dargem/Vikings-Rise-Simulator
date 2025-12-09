#include "../effects/status_effect.hpp"
#include <vector>

class Combatant
{
    public:
        void addStatusEffect(StatusEffect status_effect);
        void addBuffEffect(StatusEffect buff_effect);
    private:
        std::vector<StatusEffect> status_effects;
        std::vector<StatusEffect> buff_effects;
};