package test;
import java.util.List;
import java.util.HashMap;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.Map;
import java.util.Set;
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
    public static Combatant dummy = new Combatant(100, 100, 100, 200000, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");


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
            "Divine Shield"

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

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File skillsFile = new File("demo/src/main/java/test/SkillDatabase.json");
            skills = objectMapper.readValue(
                skillsFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Skill.class)
            );
            //skills reads the skill json making a list
            File lookupFile = new File("demo/src/main/java/test/NameAssosciatedSkillDatabase.json");
            List<Map<String, String>> skillDataList = objectMapper.readValue(
                lookupFile, new TypeReference<List<Map<String, String>>>() {}
            );
            //skillDataList of maps is made, 1 map = 1 json entry
            skillLookup = new HashMap<>();
            for (Map<String, String> skillEntry : skillDataList) { //iterating through the list of hashmaps
                String nameKey = skillEntry.get("name").toLowerCase(); //namekey is the name of commander/or header skill
                skillLookup.put(nameKey, skillEntry); //puts in namekey with the whole entry, keep information on if awakened type
            }
            //skillLookup is a map, string key for map value
            //gets a map where name of a commander can be inputted for its entry, also want it to note if awakened associated though
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with the lookup, wrong file location?");
            System.exit(0);
            //on error just makes an empty list
        }
    }
}