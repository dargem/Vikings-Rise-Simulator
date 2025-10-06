package test;
import java.util.List;
import java.util.HashMap;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashSet;

//for storing parsed json
public class SkillDatabase {
    public static List<Skill> skills;
    public static Map<String, Map<String, String>> skillLookup;
    public static HashSet<String> skillNamesSet;
    public static HashSet<String> firstSlotMountSkillNamesSet;
    public static HashSet<String> secondSlotMountSkillNamesSet;
    public static HashSet<String> commanderNamesSet;
    public static HashSet<String> baseTypeSet;
    public static HashSet<String> localKeptSet;
    public static HashSet<String> damageEffectSet;
    public static HashSet<String> debuffEffectSet; // non damaging debuffs lie slow
    public static HashSet<String> immunityEffectSet; // stuff div shield and rally immunity grants immunity to
    public static Combatant dummy = new Combatant(100, 100, 100, 
                                                  200000, false, -1,
                                                  "N/A", "N/A", 
                                                  "N/A", "N/A", 
                                                  "N/A", "N/A", 
                                                  "N/A", "N/A", 
                                                  "N/A", "N/A");


    static {
        skillNamesSet = new HashSet<>(List.of(
            "First Strike",
            "Poison Arrow",
            "Bone Corroding Arrow",
            "Fiery Rage",
            "Enrage",
            "Silencer",
            "Odin's Asylum",
            "Rage Leech",
            "Berserk Killing Machine",
            "Silent Invasion",
            "Einherjar's Oath",
            "Disarmament",
            "Devastating Charge",
            "Green Chant",
            "Furious Hack and Slash",
            "Divine Blessing",
            "Battle Role Shift",
            "Divine Shield",
            "Blood Eagle Punishment"

        ));

        firstSlotMountSkillNamesSet = new HashSet<>(List.of(
            "Bloodthirst Gaze",
            "Soul of Fury",
            "Bloodwing Assault",
            "Strangled Death",
            "Stinging Tongue",
            "Sturdy Bone Armour",
            "Pain N Fury",
            "Impenetrable Scales",
            "Beastly Rage"
        ));

        secondSlotMountSkillNamesSet = new HashSet<>(List.of(
            "Divine Awe",
            "Agonizing Frost",
            "Icicle Armor",
            "Fatal Chomp",
            "Blessed Dew",
            "Venom Rip",
            "Wolfus Gnaw"
        ));

        commanderNamesSet = new HashSet<>(List.of(
            "Alf",
            "Sephina",
            "Vali",
            "Gunnar",
            "Heidrun",
            "Charlton",
            "Hilda",
            "Greta",
            "Sigrid"

        ));

        baseTypeSet = new HashSet<>(List.of(
            "evasion",
            "retribution",
            "rage",
            "attackBoost",
            "defenseBoost",
            "healthBoost",
            "nullificationIncrease",
            "dealtIncrease",
            "commandDealtIncrease",
            "cooperationDealtIncrease",
            "passiveDealtIncrease",
            "counterattackDealtIncrease",
            "burnDealtIncrease",
            "bleedDealtIncrease",
            "poisonDealtIncrease",
            "lacerateDealtIncrease",
            "basicAttackDamage",
            "activeDealtIncrease",
            "immunityControl"
        ));

        localKeptSet = new HashSet<>(List.of(
            "directDamage",
            "absorption",
            "heal",
            "purify",
            "debuffClear",
            "buffClear"
        ));

        damageEffectSet = new HashSet<>(List.of(
            "burnDamage",
            "bleedDamage",
            "poisonDamage",
            "lacerateDamage"
        ));

        debuffEffectSet = new HashSet<>(List.of(
            "slow",
            "silence"
        ));

        immunityEffectSet = new HashSet<>(List.of(
            "silence",
            "brokenBlade",
            "disarm"
        ));

        ObjectMapper objectMapper = new ObjectMapper();

        try (
            InputStream skillStream = SkillDatabase.class.getResourceAsStream("/test/SkillDatabase.json");
            InputStream nameMapStream = SkillDatabase.class.getResourceAsStream("/test/NameAssosciatedSkillDatabase.json");
        ) {
            // Validate resources
            if (skillStream == null) {
                throw new FileNotFoundException("SkillDatabase.json not found in resources");
            }
            if (nameMapStream == null) {
                throw new FileNotFoundException("NameAssosciatedSkillDatabase.json not found in resources");
            }

            // Load skills
            skills = objectMapper.readValue(
                skillStream,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Skill.class)
            );

            // Load name-to-skill mapping
            List<Map<String, String>> skillDataList = objectMapper.readValue(
                nameMapStream,
                new TypeReference<List<Map<String, String>>>() {}
            );

            // Create lookup map
            skillLookup = new HashMap<>();
            for (Map<String, String> entry : skillDataList) {
                String nameKey = entry.get("name").toLowerCase();
                skillLookup.put(nameKey, entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading skill database or lookup — check file paths and packaging.");
            System.exit(1);
        }

    }
}