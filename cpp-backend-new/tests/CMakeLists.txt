add_executable(unit_tests 
    test_timed_effect.cpp
    ../src/effects/timed_effect.cpp
)

target_link_libraries(unit_tests GTest::GTest GTest::Main)

add_test(NAME TimedEffectTest COMMAND unit_tests)
