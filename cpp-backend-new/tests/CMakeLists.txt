add_executable(unit_tests 
    TestTimedEffect.cpp
    TestTimer.cpp
    ../src/effects/TimedEffect.cpp
    ../src/effects/Timer.cpp
)

target_link_libraries(unit_tests GTest::GTest GTest::Main)

add_test(NAME TimedEffectTest COMMAND unit_tests)
add_test(NAME TimerTest COMMAND unit_tests)
