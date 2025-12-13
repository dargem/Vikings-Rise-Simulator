#include <gtest/gtest.h>
#include "effects/Timer.hpp"
#include "effects/EffectType.hpp"

// Test the duration logic
TEST(TimerTest, DurationDecrementsCorrectly) {
    // Initialize with duration of 2 ticks
    Timer effect(2);
    
    // 1st tick: duration becomes 1. 1 >= 0 is TRUE.
    EXPECT_TRUE(effect.checkAndDeincrementDuration());
    
    // 2nd tick: duration becomes 0. 0 >= 0 is TRUE.
    EXPECT_TRUE(effect.checkAndDeincrementDuration());
    
    // 3rd tick: duration becomes -1. -1 >= 0 is FALSE.
    EXPECT_FALSE(effect.checkAndDeincrementDuration());
}
