add_executable(unit_tests 
    TestTimedEffect.cpp
    TestTimer.cpp
    TestSkillParser.cpp
    ../src/effects/TimedEffect.cpp
    ../src/effects/Timer.cpp
    ../src/utils/SkillParser.cpp
    ../src/utils/NumberGenerator.cpp
    ../src/skills/Skill.cpp
    ../src/skills/DirectSkill.cpp
    ../src/skills/SkillCondition.cpp
    ../src/combatants/Combatant.cpp
    ../src/combatants/StatusController.cpp
    ../src/orchestration/CombatPublisher.cpp
)

target_link_libraries(unit_tests PRIVATE GTest::GTest GTest::Main nlohmann_json::nlohmann_json)

add_test(NAME TimedEffectTest COMMAND unit_tests)
add_test(NAME TimerTest COMMAND unit_tests)
add_test(NAME SkillParserTest COMMAND unit_tests)
