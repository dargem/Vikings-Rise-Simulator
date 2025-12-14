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
                "Active": [
                    {
                        "skillType": { "category": "command" },
                        "trigger": { "triggerRequirement": ["poison"] },
                        "effects": [
                            {
                                "type": "directDamage",
                                "magnitude": 100.0
                            }
                        ]
                    }
                ]
            }
        }
    }
    )"_json;

    auto skills = parser.loadSkills(skillJson, CommanderName::Sigrid);
    
    ASSERT_EQ(skills.size(), 1);
    
    auto* damageSkill = dynamic_cast<DamageSkill*>(skills[0].get());
    ASSERT_NE(damageSkill, nullptr);
}

TEST(SkillParserTest, LoadSkillsThrowsOnInvalidSkill) {
    SkillParser parser;
    
    json skillJson = R"(
    {
        "Commanders": {
            "Sigrid": {
                "Active": [
                    {
                        "skillType": { "category": "command" }
                    }
                ]
            }
        }
    }
    )"_json;

    EXPECT_THROW(parser.loadSkills(skillJson, CommanderName::Sigrid), std::runtime_error);
}

TEST(SkillParserTest, LoadSkillsIgnoresUnknownCommander) {
    SkillParser parser;
    json skillJson = R"({"Commanders": {}})"_json;
    
    // Assuming there is a CommanderName::Ivar or similar, but let's use a cast if we don't know others
    // Or just check Sigrid when not present
    
    auto skills = parser.loadSkills(skillJson, CommanderName::Sigrid);
    EXPECT_TRUE(skills.empty());
}
