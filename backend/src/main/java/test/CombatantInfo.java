package test;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import test.combatrecords.CombatRecordOverview;

public class CombatantInfo {
    // This class is used to store combatants info for passing between combatants

    private CombatRecordOverview combatRecordOverview = new CombatRecordOverview();
    private int combatantId;
    // round info
    private int round;
    private int activeCounter;
    private int troopChange;
    // stats of the combatant
    private int troopCount;
    private double rage;
    private double attack;
    private double defense;
    private double health;
    private List<StatusEffect> absorptionList = new LinkedList<>();
    private List<StatusEffect> holderAbsorptionList = new LinkedList<>();
    // properties of the combatant
    private DebuffEffectCollection debuffEffectCollection = new DebuffEffectCollection();
    private double nullification;
    private double counterAttackDamageReduction;
    private double evasion;
    private double retribution;
    private double retributionDamage;
    private double attackBoost;
    private double defenseBoost;
    private double healthBoost;
    private boolean roundPostActiveCheck;
    private boolean immunityControl;
    private boolean isRally;
    public int buffClear;
    // setters
    public void setRound(int round) { this.round = round; }
    public void setActiveCounter(int activeCounter) { this.activeCounter = activeCounter; }
    public void setTroopCount(int troopCount) { this.troopCount = troopCount; }
    public void setRage(double rage) { this.rage = rage; }
    public void setAttack(double attack) { this.attack = attack; }
    public void setDefense(double defense) { this.defense = defense; }
    public void setHealth(double health) { this.health = health; }
    public void setNullification(double nullification) { this.nullification = nullification; }
    public void setCounterAttackDamageReduction(double counterAttackDamageReduction) { this.counterAttackDamageReduction = counterAttackDamageReduction; }
    public void setEvasion(double evasion) { this.evasion = evasion; }
    public void setRetribution(double retribution) { this.retribution = retribution; }
    public void setImmunityControl(boolean immunityControl) { this.immunityControl = immunityControl; }
    // getters
    public int getRound() { return round; }
    public int getActiveCounter() { return activeCounter; }
    public int getTroopCount() { return troopCount; }
    public double getRage() { return rage; }
    public double getAttack() { return attack + attackBoost; }
    public double getDefense() { return defense + defenseBoost; }
    public double getHealth() { return health + healthBoost; }
    public double getNullification() { return nullification; }
    public double getCounterAttackDamageReduction() { return counterAttackDamageReduction; }
    public double getEvasion() { return evasion; }
    public double getTroopChange() { return troopChange; }

    public boolean getBasicAttackCheck() { return debuffEffectCollection.getBasicAttack(); }
    public boolean getCounterAttackCheck() { return debuffEffectCollection.getCounterAttack(); }
    public boolean getMainActive() { return activeCounter == 1 && !debuffEffectCollection.isEffectActive("silence"); }
    public boolean getSecondaryActive() { return activeCounter == 3 && !debuffEffectCollection.isEffectActive("silence"); }
    public boolean checkEvasion() { return evasion != 0; }
    public boolean checkRetribution() { return retribution != 0; }
    public boolean getImmunityControl() { return immunityControl; }
    public double getRetributionDamage() { double holder = retributionDamage; retributionDamage=0; return holder; }
    public double getDamageReceivedIncrease() { return debuffEffectCollection.getDamageReceivedIncrease(); }
    public CombatRecordOverview getCombatRecordOverview() { return combatRecordOverview; }

    public CombatantInfo(int troopCount, double attack, double defense, double health, boolean isRally, int combatantId) {
        this.combatantId = combatantId;
        this.round = 1; // no tick at start
        this.activeCounter = 4;
        this.troopCount = troopCount;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.rage = 0;
        this.retributionDamage = 0;
        this.troopChange = 0;
        this.roundPostActiveCheck = true;
        this.isRally = isRally;
    }

    // methods
    public void tickRound() {
        // tick the round
        //System.out.println(troopChange);
        //System.out.println(activeCounter);
        immunityControl = false;
        round++;
        troopCount+=troopChange;
        List<StatusEffect> expired = new LinkedList<>();
        // transfer shields over
        for (StatusEffect newEffect : holderAbsorptionList) {
            boolean replaced = false;
            for (int i = 0; i < absorptionList.size(); i++) {
                StatusEffect existing = absorptionList.get(i);
                if (existing.getName().equals(newEffect.getName())) {
                    absorptionList.set(i, newEffect); // Replace old effect with new one if exists already
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                absorptionList.add(newEffect);
            }
        }
        holderAbsorptionList.clear();

        holderAbsorptionList.clear();
        for (StatusEffect absorptionEffect : absorptionList) {
            absorptionEffect.tick(); // decrease duration
            if (absorptionEffect.isExpired()) {
                expired.add(absorptionEffect);
            }
        }
        absorptionList.removeAll(expired);
        // reset values
        attackBoost = 0;
        defenseBoost = 0;
        healthBoost = 0;
        retributionDamage = 0;
        troopChange = 0;
        nullification = 0;
        evasion = 0;
        retribution = 0;
        debuffEffectCollection.tickAll();
        debuffEffectCollection.runInfo();
        // so that damage counts the round after like ingame
        for (Map.Entry<Integer,Double> entry : debuffEffectCollection.getDamageById().entrySet()) {
            addDamageTakenPostDefense(entry.getKey(), entry.getValue());
        }
        //addDamageTakenPostDefense(debuffEffectCollection.getTotalDamage()); 

        rage -= debuffEffectCollection.getRageDamp();
        attackBoost -= debuffEffectCollection.getAttackDamp();
        defenseBoost -= debuffEffectCollection.getDefenseDamp();
        healthBoost -= debuffEffectCollection.getHealthDamp();
        buffClear=0;
    }

    public void tickRage() {
        // this is done in round initialisation so moved here
        //System.out.println(roundPostActiveCheck);
        if (roundPostActiveCheck) { rage += 100; roundPostActiveCheck = false; }
        else { rage+=90; }
        if (rage >= 1000) {
            if (!debuffEffectCollection.isEffectActive("silence")) { 
                rage = 0; 
                // if the active gets delayed it doesn't reset seem to reset rage, delayed first active makes a delayed second
                roundPostActiveCheck = true;
            }
            activeCounter = 0;
        }
        //if (debuffEffectCollection.isEffectActive("silence")) {System.out.println("Silence active");}
        activeCounter++;
        if (debuffEffectCollection.isEffectActive("silence")) { 
            if (activeCounter == 1 || activeCounter == 3) { // keeps them locked in at 0,2 if silenced
                activeCounter--;
            }
        }
    }

    public void addRage (double rage) { this.rage += rage; }
    public void addAttack (double attackBoost) { this.attackBoost += attackBoost; }
    public void addDefense (double defenseBoost) { this.defenseBoost += defenseBoost; }
    public void addHealth (double healthBoost) { this.healthBoost += healthBoost; }
    public void addRetribution (double retribution) { this.retribution += retribution; }
    public void addEvasion (double evasion) { this.evasion += evasion; }
    public void addNullification (double nullification) { this.nullification += nullification; }
    public void addBuffClear () { buffClear ++; } 
    public boolean isEffectActive(String type) { return debuffEffectCollection.isEffectActive(type); }

    public void resetRandom() {
        debuffEffectCollection.resetRandom();
    }
    public boolean isAbsorptionActive() { return !absorptionList.isEmpty(); }
    public double getRetribution() { return retribution; }
    public int getBuffClear() { return buffClear; }
    public int getCombatantId() { return combatantId; }

    public void addDamageTaken (int attackerId, double scaledDamage) { 
        scaledDamage /= defense;
        retributionDamage += scaledDamage * retribution; 
        for (StatusEffect absorption : absorptionList) {
            double holder = absorption.getMagnitude();
            if (holder > 0) {
                if (holder < scaledDamage) {scaledDamage -= holder; absorption.setMagnitude(0);}
                else {absorption.addMagnitude(-scaledDamage); return;}
            }
        }
        scaledDamage /= health;
        combatRecordOverview.addEnemyTroopsKilled(attackerId, combatantId, scaledDamage);
        troopChange -= scaledDamage;
    }

    public void addDamageTakenPostDefense (int attackerId, double scaledDamagePostDefense) {
        
        for (StatusEffect absorption : absorptionList) {
            double holder = absorption.getMagnitude();
            if (holder > 0) {
                if (holder < scaledDamagePostDefense) {scaledDamagePostDefense -= holder; absorption.setMagnitude(0);}
                else {absorption.addMagnitude(-scaledDamagePostDefense); return;}
            }
        }        
        scaledDamagePostDefense /= health;
        combatRecordOverview.addEnemyTroopsKilled(attackerId, combatantId, scaledDamagePostDefense);
        //System.out.println(scaledDamagePostDefense);
        troopChange -= scaledDamagePostDefense;
    }

    public void addAbsorption (StatusEffect scaledAbsorption) {
        // should already be divided by targets defense to scale the magnitude
        //System.out.println(scaledAbsorption.getMagnitude());
        holderAbsorptionList.add(scaledAbsorption);
    }

    public void addHeal (double scaledHealing) {
        // should already be divided by targets defense
        scaledHealing /= health;
        // doesn't add to troop change so heavily wounded pre heal can be checked
        combatRecordOverview.addTroopsHealed(combatantId, scaledHealing);
        troopChange += scaledHealing;
    }

    public void addDebuffEffect(int id, StatusEffect statusEffect) {
        if (immunityControl) { // divine shield immunity effect
            String effectType = statusEffect.getType();
            if (effectType.equals("disarm") || 
                effectType.equals("brokenBlade") || 
                effectType.equals("silence")) {
                return;
            }
        }
        // rally based immunity effect
        if (true) { // currently works for both field and rallies due to ingame bugs
            if (SkillDatabase.immunityEffectSet.contains(statusEffect.getType()) &&
                debuffEffectCollection.isEffectActive(statusEffect.getType())) {
                return;
            }
        }
        debuffEffectCollection.addEffect(id, statusEffect);
    }


    public void addDamageDebuffEffect (int id, StatusEffect statusEffect) {
        // need to scale using defence
        double holder = statusEffect.getMagnitude();
        holder /= defense; // health is done on the way back
        statusEffect.setMagnitude(holder);
        debuffEffectCollection.addEffect(id, statusEffect);
    }

    public void purify() {
        debuffEffectCollection.clear();
    }

    public void resetRound() {
        absorptionList.clear();
        debuffEffectCollection = new DebuffEffectCollection();
        debuffEffectCollection.runInfo();
        round = 1;
        rage = 0;
        activeCounter=4;
        roundPostActiveCheck=true;
    }

    public void debuffClear(int debuffsToClear) {
        for (int i = 0; i < debuffsToClear; i++) {
            debuffEffectCollection.removeEffectRandom();
        }
    }
}
