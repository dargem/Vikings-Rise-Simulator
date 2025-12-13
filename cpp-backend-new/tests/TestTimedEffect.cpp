#include <gtest/gtest.h>
#include "effects/TimedEffect.hpp"
#include "effects/EffectType.hpp"

TEST(TimedEffectTest, ConstructorInitializesProperly)
{
    constexpr short TESTED_DURATION{3};
    constexpr double TESTED_MAGNITUDE{360.451};

    TimedEffect effect(TESTED_DURATION, TESTED_MAGNITUDE);
    EXPECT_EQ(effect.tickAndGetMagnitude(), TESTED_MAGNITUDE);
}

TEST(TimedEffectTest, CorrectDamageOverTime)
{
    /// ints representable in IEEE 754 so no floating point errors
    constexpr short TESTED_DURATION{3};
    constexpr double TESTED_MAGNITUDE{400};
    constexpr double EXPECTED_TOTAL_MAGNITUDE{TESTED_DURATION * TESTED_MAGNITUDE};

    TimedEffect effect(TESTED_DURATION, TESTED_MAGNITUDE);
    
    double accumulated_magnitude{};
    while (auto magnitude{effect.tickAndGetMagnitude()})
    {
        accumulated_magnitude += *magnitude;
    };

    EXPECT_EQ(accumulated_magnitude, EXPECTED_TOTAL_MAGNITUDE);
}