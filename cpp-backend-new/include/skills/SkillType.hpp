#ifndef SKILL_TYPES_HPP
#define SKILL_TYPES_HPP

#include <cstdint>

enum class SkillType : std::uint8_t
{
    PASSIVE,
    COOPERATION,
    COUNTERATTACK,
    COMMAND
};

#endif