#include <gtest/gtest.h>
#include "utils/SkillParser.hpp"
#include "skills/DirectSkill.hpp"
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
                        "dependentRequirement": "COMMANDER_ACTIVE_DEALT",
                        "category": "AWAKEN",
                        
                        "direct_skills": [
                            {
                                "conditionType": "NONE",
                                "triggerRequirement": "NONE",
                                "type": "DAMAGE",
                                "magnitude": 1700,
                                "chance": 1,
                                "target": "ENEMY"
                            },
                            {
                                "conditionType": "HAS_EFFECT_TARGET",
                                "triggerRequirement": "SILENCE",
                                "type": "DAMAGE",
                                "magnitude": 1200,
                                "chance": 1,
                                "target": "ENEMY"
                            }
                        ],
                        "status_skills": [
                            {
                                "type": "BLEED",
                                "conditionType": "HAS_EFFECT_TARGET",
                                "triggerRequirement": "SLOW",
                                "magnitude": 300,
                                "chance": 1,
                                "skillDuration": 2,
                                "removable": true,
                                "target": "ENEMY"
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
    
    ASSERT_EQ(skills.size(), 3);
    
    auto* direct_skill = skills[0].get();
    ASSERT_NE(direct_skill, nullptr);
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