add_executable(unit_tests 
    test_status_effect.cpp
    test_timer.cpp
    ../src/effects/status_effect.cpp
    ../src/effects/timer.cpp
)

target_link_libraries(unit_tests GTest::GTest GTest::Main)

add_test(NAME StatusEffectTest COMMAND unit_tests)
add_test(NAME TimerTest COMMAND unit_tests)
