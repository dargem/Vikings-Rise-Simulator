add_executable(unit_tests 
    TestStatusEffect.cpp
    TestTimer.cpp
    ../src/effects/StatusEffect.cpp
    ../src/effects/Timer.cpp
)

target_link_libraries(unit_tests GTest::GTest GTest::Main)

add_test(NAME StatusEffectTest COMMAND unit_tests)
add_test(NAME TimerTest COMMAND unit_tests)
