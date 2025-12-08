#include <gtest/gtest.h>
#include "../src/effects/timed_effect.hpp"
#include "../src/effects/effect_types.hpp"

// Test that the constructor correctly sets the effect type
TEST(TimedEffectTest, ConstructorInitializesCorrectly) {
    TimedEffect effect(3, EffectType::BURN);
    EXPECT_EQ(effect.getEffectType(), EffectType::BURN);
}

// Test the duration logic
TEST(TimedEffectTest, DurationDecrementsCorrectly) {
    // Initialize with duration of 2 ticks
    TimedEffect effect(2, EffectType::POISON);
    
    // 1st tick: duration becomes 1. 1 >= 0 is TRUE.
    EXPECT_TRUE(effect.checkAndDeincrementDuration());
    
    // 2nd tick: duration becomes 0. 0 >= 0 is TRUE.
    EXPECT_TRUE(effect.checkAndDeincrementDuration());
    
    // 3rd tick: duration becomes -1. -1 >= 0 is FALSE.
    EXPECT_FALSE(effect.checkAndDeincrementDuration());
}
