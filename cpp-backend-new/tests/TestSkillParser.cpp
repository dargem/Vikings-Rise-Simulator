#include <gtest/gtest.h>
#include "utils/SkillParser.hpp"
#include "skills/DamageSkill.hpp"
#include "skills/StatusSkill.hpp"
#include <nlohmann/json.hpp>

using json = nlohmann::json;

TEST(SkillParserTest, LoadSkillsForSigrid) {
    SkillParser parser;
    
    json skillJson = R"(
    {
        "Commanders": {
            "Sigrid": {
                "AwakenedActive": [
                    {
                        "skillType": { "category": "command" },
                        "trigger": { 
                            "triggerRequirement": "poison",
                            "conditionType": "HAS_EFFECT_SELF",
                            "dependentRequirement": "BASIC_DEALT"
                        },
                        "direct_damage_skills": [
                            {
                                "magnitude": 100.0,
                                "target": "enemy"
                            }
                        ],
                        "status_skills": [
                            { 
                                "type": "heal", 
                                "magnitude": 50,
                                "skillDuration": 2,
                                "removable": false,
                                "chance": 1.0,
                                "target": "friendly"
                            }
                        ]
                    }
                ]
            }
        }
    }
    )"_json;

    bool isPrimary { true };
    auto skills = parser.loadSkills(skillJson, CommanderName::Sigrid, isPrimary);
    
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

    bool isPrimary { true };
    EXPECT_THROW(parser.loadSkills(skillJson, CommanderName::Sigrid, isPrimary), std::runtime_error);
}

TEST(SkillParserTest, LoadSkillsThrowsOnUnknownCommander) {
    SkillParser parser;
    json skillJson = R"({"Commanders": {}})"_json;
    
    bool isPrimary { true };
    EXPECT_THROW(parser.loadSkills(skillJson, CommanderName::Sigrid, isPrimary), std::runtime_error);
}