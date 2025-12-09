#include <gtest/gtest.h>
#include "../src/effects/timed_effect.hpp"
#include "../src/effects/effect_types.hpp"

TEST(TimedEffectTest, ConstructorInitializesProperly)
{
    constexpr short TESTED_DURATION{3};
    constexpr double TESTED_MAGNITUDE{360.451};
    constexpr EffectType TESTED_EFFECT_TYPE{EffectType::BURN};

    TimedEffect effect(TESTED_DURATION, TESTED_MAGNITUDE, TESTED_EFFECT_TYPE);
    EXPECT_EQ(effect.getMagnitude(), TESTED_MAGNITUDE);
    EXPECT_EQ(effect.getEffectType(), TESTED_EFFECT_TYPE);
}