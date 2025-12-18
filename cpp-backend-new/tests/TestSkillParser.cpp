#include <gtest/gtest.h>
#include "utils/SkillParser.hpp"
#include "skills/DamageSkill.hpp"
#include "skills/StatusSkill.hpp"
#include <nlohmann/json.hpp>

using json = nlohmann::json;

TEST(SkillParserTest, LoadSkillsForFakeSigrid) {
    SkillParser parser;
    
    json skillJson = R"(
    {
        "Commanders": {
            "Sigrid": {
                "AwakenedActive": [
                    {
                        "skillType": { "category": "COMMAND" },
                        "trigger": { 
                            "triggerRequirement": "POISON",
                            "conditionType": "HAS_EFFECT_SELF",
                            "dependentRequirement": "BASIC_DEALT"
                        },
                        "direct_damage_skills": [
                            {
                                "magnitude": 100.0,
                                "target": "ENEMY",
                                "chance": 0.5
                            }
                        ],
                        "status_skills": [
                            { 
                                "type": "HEAL", 
                                "magnitude": 50,
                                "skillDuration": 2,
                                "removable": false,
                                "chance": 1.0,
                                "target": "FRIENDLY"
                            }
                        ]
                    }
                ]
            }
        }
    }
    )"_json;

    bool is_primary { true };
    auto skills = parser.loadSkills(skillJson, CommanderName::Sigrid, is_primary);
    
    ASSERT_EQ(skills.size(), 2);
    
    auto* damageSkill = skills[0].get();
    ASSERT_NE(damageSkill, nullptr);
}

TEST(SkillParserTest, LoadSkillsThrowsOnInvalidSkill) {
    SkillParser parser;
    
    json skillJson = R"(
    {
        "Commanders": {
            "Sigrid": {
                "AwakenedActive": [
                    {
                        "skillType": { "category": "command" }
                    }
                ]
            }
        }
    }
    )"_json;

    bool is_primary { true };
    EXPECT_THROW(static_cast<void>(parser.loadSkills(skillJson, CommanderName::Sigrid, is_primary)), std::runtime_error);
}

TEST(SkillParserTest, LoadSkillsThrowsOnUnknownCommander) {
    SkillParser parser;
    json skillJson = R"({"Commanders": {}})"_json;
    
    bool is_primary { true };
    EXPECT_THROW(static_cast<void>(parser.loadSkills(skillJson, CommanderName::Sigrid, is_primary)), std::runtime_error);
}