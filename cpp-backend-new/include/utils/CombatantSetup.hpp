#ifndef COMBATANT_SETUP_HPP
#define COMBATANT_SETUP_HPP

#include "SkillName.hpp"
#include "CommanderName.hpp"

struct CombatantSetup
{
    static constexpr int NUM_COMMANDERS = 2;
    static constexpr int NUM_SKILLS = 4;
    static constexpr int NUM_SLOT_1_MOUNT_SKILLS = 2;
    static constexpr int NUM_SLOT_2_MOUNT_SKILLS = 2;
    
    CommanderName commanders[NUM_COMMANDERS];
    SkillName skills[NUM_SKILLS];
};

#endif