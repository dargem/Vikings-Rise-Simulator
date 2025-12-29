#ifndef COMBATANT_SETUP_HPP
#define COMBATANT_SETUP_HPP

#include <array>
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
    
    std::array<CommanderName, NUM_COMMANDERS> commanders;
    std::array<SkillName, NUM_SKILLS> num_skills;
    std::array<MountSlot1Names, NUM_SLOT_1_MOUNT_SKILLS> slot_1_skills;
    std::array<MountSlot2Names, NUM_SLOT_2_MOUNT_SKILLS> slot_2_skills;
};

#endif