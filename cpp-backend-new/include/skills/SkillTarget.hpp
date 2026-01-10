#ifndef SKILL_TARGET_HPP
#define SKILL_TARGET_HPP


#include <cstdint>

// DO NOT ADD MORE OR POSSIBLE ISSUES IN STATUS SKILL WITH LOADING
enum class SkillTarget : std::uint8_t
{
    FRIENDLY,
    ENEMY
};

#endif