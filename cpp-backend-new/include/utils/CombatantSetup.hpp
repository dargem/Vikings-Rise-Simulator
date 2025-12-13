#ifndef COMBATANT_SETUP_HPP
#define COMBATANT_SETUP_HPP

#include "utils/SkillName.hpp"
#include "utils/CommanderName.hpp"
#include "utils/MountSlot1Names.hpp"
#include "utils/MountSlot2Names.hpp"

struct CombatantSetup
{
    static constexpr int NUM_COMMANDERS = 2;
    static constexpr int NUM_SKILLS = 4;
    static constexpr int NUM_SLOT_1_MOUNT_SKILLS = 2;
    static constexpr int NUM_SLOT_2_MOUNT_SKILLS = 2;
    
    CommanderName commanders[NUM_COMMANDERS];
    SkillName skills[NUM_SKILLS];
    MountSlot1Names slot_1_skills[NUM_SLOT_1_MOUNT_SKILLS];
    MountSlot2Names slot_2_skills[NUM_SLOT_2_MOUNT_SKILLS];

};

#endif