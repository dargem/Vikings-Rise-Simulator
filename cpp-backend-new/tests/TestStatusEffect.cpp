#include <gtest/gtest.h>
#include "../src/effects/StatusEffect.hpp"
#include "../src/effects/EffectTypes.hpp"

TEST(StatusEffectTest, ConstructorInitializesProperly)
{
    constexpr short TESTED_DURATION{3};
    constexpr double TESTED_MAGNITUDE{360.451};
    constexpr EffectType TESTED_EFFECT_TYPE{EffectType::BURN};

    StatusEffect effect(TESTED_DURATION, TESTED_MAGNITUDE, TESTED_EFFECT_TYPE);
    EXPECT_EQ(effect.getMagnitude(), TESTED_MAGNITUDE);
    EXPECT_EQ(effect.getEffectType(), TESTED_EFFECT_TYPE);
}