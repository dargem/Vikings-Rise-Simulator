package test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Combatant {
    private HashMap<String, String> setupNames = new HashMap<>();
    Set<String> triggeredSet = new HashSet<>();
    //sent out 
    CombatantInfo combatantInfo;
    CombatantInfo enemyCombatant;
    private List<Skill> allSkills = new ArrayList<>();
    HashMap<String, Boolean> uptimeDic = new HashMap<>();
    // friendly vars
    private TotalCounter totalCounter = new TotalCounter();
    private List<StatusEffect> buffEffects = new ArrayList<>();
    private double basicAttackDamage;
    private double counterAttackDamage;
    private double dealtIncrease;
    private double healDealtIncrease;
    private double absorptionDealtIncrease;
    private double commandDealtIncrease;
    private double cooperationDealtIncrease;
    private double passiveDealtIncrease;
    private double counterattackDealtIncrease;
    private double burnDamageIncrease;
    private double poisonDamageIncrease;
    private double bleedDamageIncrease;
    private double lacerateDamageIncrease;
    public double activeDealtIncrease;
    private int numberEnemyAttackers;
    private int combatantId;
    private int initialTroopCount; // for resetting combatant info when needed
    private Random random = new Random();
    private int activeCount = 0;
    private int secondaryCount = 0;

    private CombatantInfoHolder combatantInfoHolder;

    // Constructor
    public Combatant(double attack, double defense, double health, int troopCount, boolean isRally,
                     String commander1Name, String commander2Name, 
                     String skill1Name, String skill2Name, String skill3Name, String skill4Name, 
                     String mountFirstSlot1Name, String mountFirstSlot2Name, 
                     String mountSecondSlot1Name, String mountSecondSlot2Name) {
        combatantInfoHolder = new CombatantInfoHolder(troopCount, attack, defense, health, isRally);
        combatantInfo = combatantInfoHolder.generateNewCombatantInfo();

        initialTroopCount = troopCount;
        setupNames.put("commander1", commander1Name);
        setupNames.put("commander2", commander2Name);
        setupNames.put("skill1", skill1Name);
        setupNames.put("skill2", skill2Name);
        setupNames.put("skill3", skill3Name);
        setupNames.put("skill4", skill4Name);
        setupNames.put("mountFirstSlot1", mountFirstSlot1Name);
        setupNames.put("mountFirstSlot2", mountFirstSlot2Name);
        setupNames.put("mountSecondSlot1", mountSecondSlot1Name);
        setupNames.put("mountSecondSlot2", mountSecondSlot2Name);
        createSkillsList();
        internalReset();
    }

    public void setCombatantId(int combatantId) { this.combatantId = combatantId; }

    private void internalReset() {
        
        combatantInfo.setTroopCount(initialTroopCount);
        combatantInfo.resetRound();
        uptimeDic.put("heal",false);
        uptimeDic.put("absorption",false);
        uptimeDic.put("heal",false);
        uptimeDic.put("lessUnits",false);
        uptimeDic.put("moreUnits",false);
        uptimeDic.put("silence",false);
        totalCounter.reset();
        //roundEndReset(); // resets all values
    }

    public void reset() {
        combatantInfo = combatantInfoHolder.generateNewCombatantInfo();
        startPhase(SkillDatabase.dummy.getCombatantInfo()); // lazy way to keep base effects for r1
        clearTempBuffs();
        internalReset();
    }
    
    private void createSkillsList() {
        allSkills.clear();
        Map<String, Map<String, String>> skillLookup = SkillDatabase.skillLookup;

        List<String> intermediaryBreakingStage = new ArrayList<>();
        boolean com1 = false;
        boolean com2 = false;
        String commander1active = "";
        String commander2active = "";

        // Resolve setupNames into base skill components
        for (Map.Entry<String, String> entry : setupNames.entrySet()) {
            String targetName = entry.getValue();
            if (targetName.equalsIgnoreCase("N/A")) continue;

            Map<String, String> skillEntry = skillLookup.get(targetName.toLowerCase());
            boolean match = false;

            if (skillEntry != null) {
                for (Map.Entry<String, String> field : skillEntry.entrySet()) {
                    String key = field.getKey();
                    String value = field.getValue();

                    if (key.equals("name")) {
                        com1 = false;
                        com2 = false;
                        if (value.equalsIgnoreCase(setupNames.get("commander1"))) { com1 = true; }
                        else if (value.equalsIgnoreCase(setupNames.get("commander2"))) { com2 = true; }
                        //System.out.println(setupNames.get("commander2"));
                    }

                    if (!key.equals("name") && !"N/A".equalsIgnoreCase(value)) {
                        intermediaryBreakingStage.add(value);
                        match = true;
                    }

                    // Assign awakened skill only if it's the correct commander and com1/com2 are true
                    if (key.equals("awakenedSkill")) {
                        if (com1) {
                            //System.out.println(value);
                            commander1active = value;
                        }
                        if (com2) {
                            //System.out.println(value);
                            commander2active = value;
                        }
                    }
                }
            }

            if (!match) {
                System.out.println("No match found for " + targetName);
                System.out.println("check for error");
                System.exit(0);
            }
        }

        // Break down skills into further components and filters
        for (String targetName : intermediaryBreakingStage) {
            Map<String, String> skillEntry = skillLookup.get(targetName.toLowerCase());
            boolean com1check = false; // Reset for each skill in intermediaryBreakingStage
            boolean com2check = false; // Reset for each skill in intermediaryBreakingStage
            boolean match = false;

            if (skillEntry != null) {
                for (Map.Entry<String, String> field : skillEntry.entrySet()) {
                    String key = field.getKey();
                    String value = field.getValue();

                    if (key.equalsIgnoreCase("name")) { // Check for "name" key
                        com1check = false;
                        com2check = false;
                        if (value.equalsIgnoreCase(commander1active)) {
                            com1check = true;
                        }
                        if (value.equalsIgnoreCase(commander2active)) {
                            com2check = true;
                        }
                    }

                    if (!key.equals("name") && !"N/A".equalsIgnoreCase(value)) {
                        Skill skill = Skill.loadFromJsonByName(value);
                        if (com1check) {
                            //System.out.println(skill.getName());
                            skill.setDependent("activeMain");
                        }
                        if (com2check) {
                            //System.out.println(skill.getName());
                            skill.setDependent("activeSecondary");
                        }
                        allSkills.add(skill);
                        match = true;
                        //System.out.println("Added skill: " + skill.getName() + " with dependent: " + skill.getDependent());
                    }
                }
            }
            if (!match) {
                Skill skill = Skill.loadFromJsonByName(targetName);
                allSkills.add(skill);
            }
        }
    }

    //setters and getters
    public void setCombatantInfo(CombatantInfo combatantInfo) { this.combatantInfo = combatantInfo; }
    public double getFactorPerSecond(){ 
        
        double rounds = (double)combatantInfo.getRound() - 1;
        System.out.println("Absorption Factor Total per Second: " + totalCounter.getAbsorptionFactorTotal() / rounds);
        System.out.println("Accumulated Factor Total per Second: " + totalCounter.getAccumulatedFactorTotal() / rounds);
        System.out.println("Counter Attack Damage Total per Second: " + totalCounter.getCounterAttackDamageTotal() / rounds);
        System.out.println("Damage Factor Total per Second: " + totalCounter.getDamageFactorTotal() / rounds);
        System.out.println("Heal Factor Total per Second: " + totalCounter.getHealFactorTotal() / rounds);
        System.out.println("Reduction Factor Total per Second: " + totalCounter.getReductionFactorTotal() / rounds);
        System.out.println("Status Factor Total per Second: " + totalCounter.getStatusFactorTotal() / rounds);
        
        return totalCounter.getAccumulatedFactorTotal() / ((double)combatantInfo.getRound()-1);
    }
    public List<Skill> getAllSkills() { return allSkills; }
    public int getRound() { return combatantInfo.getRound(); }
    public CombatantInfo getCombatantInfo() { return combatantInfo; }
    public void setNumberEnemyAttackers(int numberEnemyAttackers) { this.numberEnemyAttackers = numberEnemyAttackers; }
    public CombatantInfo getEnemyCombatant() { return enemyCombatant; }
    public double getInitialTroopCount() { return initialTroopCount; }
    public void setTroopCount() { combatantInfo.setTroopCount(initialTroopCount); }
    // methods
    /*
    private double computeDamage() {

    }
    */

    // runs buff effects for your troops, then exchange phase
    public void roundInitialisation() {
        uptimeDic.clear();
        numberEnemyAttackers = 0; // for counterattack scaling logic


        runBuffEffects();
        // if rage reaches 1000, should do active triggers immediately
        combatantInfo.tickRage();
        //if (combatantId == 0) {System.out.println(combatantInfo.getRage());}
    }

    //Start Phase ticks rounds, adds actives and triggered set
    public void startPhase(CombatantInfo enemyCombatant) {
        this.enemyCombatant = enemyCombatant;
        // find uptimes and the bases for triggered set
        for (String damageEffectUptime : SkillDatabase.damageEffectSet) {
            uptimeDic.put(damageEffectUptime, enemyCombatant.isEffectActive(damageEffectUptime));
            if (enemyCombatant.isEffectActive(damageEffectUptime)) { triggeredSet.add("continuousDamage"); }
            //if (combatantId == 0) { System.out.println(damageEffectUptime + " " + enemyCombatant.isEffectActive(damageEffectUptime)); }
        }
        //System.out.println(enemyCombatant.isEffectActive("bleedDamage"));
        for (String debuffEffectUptime : SkillDatabase.debuffEffectSet) {
            uptimeDic.put(debuffEffectUptime, enemyCombatant.isEffectActive(debuffEffectUptime));
        }
        //System.out.println(combatantInfo.getRound());

        uptimeDic.put("flanked",false); // add a check later for this logic only does 1v1's for now
        uptimeDic.put(">50%", combatantInfo.getTroopCount() > initialTroopCount/2);
        uptimeDic.put("<50%", combatantInfo.getTroopCount() < initialTroopCount/2);
        uptimeDic.put("absorption", combatantInfo.isAbsorptionActive());
        uptimeDic.put("retribution", combatantInfo.checkRetribution());
        uptimeDic.put("evasion",combatantInfo.checkEvasion());
        //System.out.println(combatantInfo.getRound());
        //if (combatantId==0) { System.out.println(combatantInfo.checkEvasion()); }
        uptimeDic.put("moreUnits",combatantInfo.getTroopCount()>enemyCombatant.getTroopCount());
        uptimeDic.put("lessUnits",combatantInfo.getTroopCount()<enemyCombatant.getTroopCount());
        if (combatantInfo.getTroopCount() == enemyCombatant.getTroopCount()) {
            if (Math.random() < 0.5) { uptimeDic.put("moreUnits",true); }
            else { uptimeDic.put("lessUnits",true); } // troop sizes equal in rally sims so make it random to account for reinforce variation
        }

        //if (Math.random()<0.5 && combatantId == 0) { uptimeDic.put("burnDamage",true); }
        //System.out.println(combatantId + " Rage " + combatantInfo.getRage() + " " + combatantInfo.getRound());

        if (combatantInfo.getMainActive()) { triggeredSet.add("activeMain");triggeredSet.add("active"); activeCount++; }
        if (combatantInfo.getSecondaryActive()) { triggeredSet.add("activeSecondary");triggeredSet.add("active"); secondaryCount++; }
        if (enemyCombatant.getMainActive() || enemyCombatant.getSecondaryActive()) { triggeredSet.add("activeReceived"); }
        //if (combatantId == 1) { System.out.println(combatantInfo.isEffectActive("silence"));}
        //if (combatantId == 1) { System.out.println("primary " + activeCount); }
        //if (combatantId == 1) { System.out.println("secondary " + secondaryCount); }
        //if (combatantId == 1) { System.out.println(combatantInfo.getActiveCounter()); }

        double enemyEvasion = enemyCombatant.getEvasion(); // evasion prevents damage but not triggers
        if (Math.random() > enemyEvasion) {
            totalCounter.addDamageFactor((1+basicAttackDamage+enemyCombatant.getDamageReceivedIncrease()-enemyCombatant.getNullification())*200);
            enemyCombatant.addDamageTaken(Scaler.scale((1+basicAttackDamage+enemyCombatant.getDamageReceivedIncrease()-enemyCombatant.getNullification())*200,combatantInfo.getAttack(),combatantInfo.getTroopCount()));
        }
        if (combatantInfo.getBasicAttackCheck()) { triggeredSet.add("basicAttack"); }
        if (combatantInfo.getCounterAttackCheck()) { triggeredSet.add("counterAttack"); }
        if (enemyCombatant.getBasicAttackCheck()) { triggeredSet.add("basicReceived"); }
        if (Math.random()<0.5) { triggeredSet.add("chance1"); }
        else { triggeredSet.add("chance2"); }

        for (Skill skill : allSkills){
            if (skill.shouldTrigger(combatantInfo.getRound(), uptimeDic, triggeredSet)) {

                //scuffed but shouldn't error since its read in order
                triggeredSet.add(skill.getName()); // reuse triggered set to send to the target

                // break early if evasion, still needs triggered set to be added to though and it doesn't break not self buffs
                if (enemyEvasion != 0) {
                    if (Math.random() < enemyEvasion && !SkillDatabase.baseTypeSet.contains(skill.getEffectType())) { continue; }
                }

                if (SkillDatabase.localKeptSet.contains(skill.getEffectType())) {
                    switch (skill.getEffectType()) {
                        case "directDamage" -> countDamageFactor(skill);
                        case "absorption" -> countAbsorptionFactor(skill);
                        case "heal" -> countHealFactor(skill);
                        case "purify" -> combatantInfo.purify();
                        case "debuffClear" -> combatantInfo.debuffClear((int)skill.getMagnitude());
                        case "buffClear" -> enemyCombatant.addBuffClear();
                    }
                }
                else if (SkillDatabase.baseTypeSet.contains(skill.getEffectType())) {
                    addBuffEffect(new StatusEffect(skill.getName(), skill.getEffectType(), skill.getDuration(), skill.getMagnitude(),skill.getRemovable()));
                }
                else {
                    StatusEffect debuff = new StatusEffect(skill.getName(), skill.getEffectType(), skill.getDuration(), skill.getMagnitude(),skill.getRemovable());
                    if (SkillDatabase.damageEffectSet.contains(debuff.getType())) {
                        // this is a damage debuff
                        double holder = 0;
                        holder += enemyCombatant.getDamageReceivedIncrease();
                        switch (debuff.getType()) {
                            case "burnDamage" -> holder += burnDamageIncrease;
                            case "poisonDamage" -> holder += poisonDamageIncrease;
                            case "bleedDamage" -> holder += bleedDamageIncrease;
                            case "lacerateDamage" -> holder += lacerateDamageIncrease;
                        }
                        if (skill.getDependent().equalsIgnoreCase("activeMain") || skill.getDependent().equalsIgnoreCase("secondaryMain")) { holder += activeDealtIncrease; } 
                        // active dealt increases do help status damage from active skills, generic dealt increases don't though for some reason
                        debuff.setMagnitude(Scaler.scale(skill.getMagnitude()*(1+holder),combatantInfo.getAttack(),combatantInfo.getTroopCount()));
                        totalCounter.addStatusFactor(skill.getMagnitude()*(1+holder)*debuff.getDuration()); //adds status factors, done all at once since status will still do damage after changing target
                        enemyCombatant.addDamageDebuffEffect(combatantId,debuff);
                        continue;
                    }
                    enemyCombatant.addDebuffEffect(combatantId,debuff);
                    //System.out.println(debuff.getName()); // add better system for error cases
                }
            }
        }
        combatantInfo.addDamageTakenPostDefense(enemyCombatant.getRetributionDamage()); // check if damage received increases help retribution
    }

    public void counterattackPhase(CombatantInfo enemyCombatant) {
        this.enemyCombatant = enemyCombatant;
        double damage = 1 + counterAttackDamage + enemyCombatant.getDamageReceivedIncrease() - enemyCombatant.getNullification() - enemyCombatant.getCounterAttackDamageReduction();
        damage *= 200;
        //if (combatantId == 0) {System.out.println(damage);}
        double enemyEvasion = enemyCombatant.getEvasion();
        if (enemyEvasion != 0) {
            if (Math.random() < enemyEvasion) { return; }
        }
        if (damage > 0) {
            totalCounter.addDamageFactor(damage);
            enemyCombatant.addDamageTaken(Scaler.scale(damage, combatantInfo.getAttack(), combatantInfo.getTroopCount()));
        }
        //System.out.println(enemyCombatant.getRetributionDamage());
        combatantInfo.addDamageTakenPostDefense(enemyCombatant.getRetributionDamage());
    }

    public void endPhase() {
        endRoundReset();
        //removes effects
        for (StatusEffect buffEffect : buffEffects) {
            if (buffEffect.justAdded()) {
                triggeredSet.add("shieldGranted");
                break;
            }
        }

        for (int i = 0; i < combatantInfo.getBuffClear(); i++) {
            List<StatusEffect> removableList = new ArrayList<>();
            for (StatusEffect buff : buffEffects) {
                if (buff.getRemovable()) { removableList.add(buff); }
            }
            if (!removableList.isEmpty()) {
                StatusEffect toRemove = removableList.get(random.nextInt(removableList.size()));
                buffEffects.remove(toRemove);
                //System.out.println(toRemove.getName());
            }

            if (!buffEffects.isEmpty()) {
                buffEffects.remove(random.nextInt(buffEffects.size()));
                break;
            }
        }

        List<StatusEffect> expired = new ArrayList<>();
        for (StatusEffect buffEffect : buffEffects) {
            buffEffect.tick(); // decrease duration
            if (buffEffect.isExpired()) {
                expired.add(buffEffect);
            }
        }
        buffEffects.removeAll(expired);
        combatantInfo.tickRound();
        //for (StatusEffect buff : buffEffects) { System.out.print(buff.getName() + " "); }
        //System.out.println(" ");

    }

    private void clearTempBuffs() {
        buffEffects.removeIf(buff -> !buff.getEngrained());
    }

    private void countAbsorptionFactor(Skill skill) {
        StatusEffect shield = new StatusEffect(skill.getName(), skill.getEffectType(), skill.getDuration(), 
            Scaler.scale((skill.getMagnitude()*(1+absorptionDealtIncrease)),combatantInfo.getAttack()/enemyCombatant.getDefense(),combatantInfo.getTroopCount()),skill.getRemovable());
        totalCounter.addAbsorptionFactor(skill.getMagnitude()*(1+absorptionDealtIncrease));
        combatantInfo.addAbsorption(shield);
    }

    private void countHealFactor(Skill skill) {
        StatusEffect heal = new StatusEffect(skill.getName(), skill.getEffectType(), skill.getDuration(), skill.getMagnitude()*(1+healDealtIncrease), skill.getRemovable());
        totalCounter.addHealFactor(skill.getMagnitude()*(1+healDealtIncrease));
        buffEffects.add(heal);
    }

    private void endRoundReset() {
        triggeredSet.clear();
        basicAttackDamage=0;
        counterAttackDamage=0;
        dealtIncrease=0;
        healDealtIncrease=0;
        absorptionDealtIncrease=0;
        commandDealtIncrease=0;
        cooperationDealtIncrease=0;
        passiveDealtIncrease=0;
        counterattackDealtIncrease=0;
        burnDamageIncrease=0;
        poisonDamageIncrease=0;
        bleedDamageIncrease=0;
        lacerateDamageIncrease=0;
        activeDealtIncrease=0;
        numberEnemyAttackers=0;
    }

    private void addBuffEffect(StatusEffect statusEffect) {
        buffEffects.removeIf(effect -> effect.getName().equals(statusEffect.getName()));
        buffEffects.add(statusEffect); // should probably replace with a hashmap for better lookup do later
    }

    private void runBuffEffects() {
        uptimeDic.put("heal",false);
        for (StatusEffect effect : buffEffects) {
            switch (effect.getType()) {
                case "heal" -> { 
                    if (!combatantInfo.isEffectActive("devastation")) {
                        combatantInfo.addHeal(Scaler.scale(effect.getMagnitude(),combatantInfo.getAttack()/enemyCombatant.getDefense(),combatantInfo.getTroopCount())); uptimeDic.put("heal",true);
                    }
                }
                case "rage" -> { combatantInfo.addRage(effect.getMagnitude()); 
                    //if (combatantId != 0) break;
                    //System.out.println(effect.getMagnitude()); System.out.println(effect.getName()); System.out.println(combatantInfo.getRound());
                }
                case "burnDealtIncrease" -> burnDamageIncrease += effect.getMagnitude();
                case "poisonDealtIncrease" -> poisonDamageIncrease += effect.getMagnitude();
                case "bleedDealtIncrease" -> bleedDamageIncrease += effect.getMagnitude();
                case "lacerateDealtIncrease" -> lacerateDamageIncrease += effect.getMagnitude();
                case "attackBoost" -> combatantInfo.addAttack(effect.getMagnitude());
                case "defenseBoost" -> combatantInfo.addDefense(effect.getMagnitude());
                case "healthBoost" -> combatantInfo.addHealth(effect.getMagnitude());
                case "retribution" -> {combatantInfo.addRetribution(effect.getMagnitude()); totalCounter.addRetribution(effect.getMagnitude());}
                case "evasion" -> {combatantInfo.addEvasion(effect.getMagnitude()); totalCounter.addEvasion(effect.getMagnitude());}
                case "nullificationIncrease" -> {combatantInfo.addNullification(effect.getMagnitude()); totalCounter.addNullification(effect.getMagnitude());}
                case "dealtIncrease" -> dealtIncrease += effect.getMagnitude();
                case "commandDealtIncrease" -> commandDealtIncrease += effect.getMagnitude();
                case "cooperationDealtIncrease" -> cooperationDealtIncrease += effect.getMagnitude();
                case "passiveDealtIncrease" -> passiveDealtIncrease += effect.getMagnitude();
                case "counterattackDealtIncrease" -> counterattackDealtIncrease += effect.getMagnitude();
                case "healDealtIncrease" -> healDealtIncrease += effect.getMagnitude();
                case "absorptionDealtIncrease" -> absorptionDealtIncrease += effect.getMagnitude();
                case "basicAttackDamage" -> basicAttackDamage += effect.getMagnitude();
                case "counterAttackDamage" -> counterAttackDamage += effect.getMagnitude();
                case "activeDealtIncrease" -> activeDealtIncrease += effect.getMagnitude();
                case "immunityControl" -> combatantInfo.setImmunityControl(true);
                default -> System.out.println("error, type " + effect.getType() + " not found");
            }
        }
    }

    private void countDamageFactor(Skill skill) {
        double damage = skill.getMagnitude();
        double additionalDealt = 0;
        switch (skill.getCategorySpecification()) {
            case "command" -> additionalDealt += commandDealtIncrease;
            case "cooperation" -> additionalDealt += cooperationDealtIncrease;
            case "passive" -> additionalDealt += passiveDealtIncrease;
            case "counterattack" -> additionalDealt += counterattackDealtIncrease;
            case "active" -> additionalDealt += 0; // add in active damage increases
            case "N/A" -> {}
            default -> System.out.println("Unknown category specification: " + skill.getCategorySpecification());
        }
        if (skill.getDependent().equalsIgnoreCase("activeMain") || skill.getDependent().equals("activeSecondary")) { additionalDealt+= activeDealtIncrease; }
        damage *= (1 + dealtIncrease + additionalDealt + enemyCombatant.getDamageReceivedIncrease() - enemyCombatant.getNullification());
        totalCounter.addDamageFactor(damage);
        enemyCombatant.addDamageTaken(Scaler.scale(damage, combatantInfo.getAttack(), combatantInfo.getTroopCount()));
    }
}   
