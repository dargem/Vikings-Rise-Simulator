package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Skill {
    private String name;
    private String categorySpecification;
    private String effectType;
    private String triggerRequirement;
    private double magnitude;
    private int skillDuration;
    private double triggerChance;
    private int roundsToTrigger;
    private int hitsPerCooldown;
    private int cooldown;
    private String dependent; //for "associated" skills
    private boolean removable;
    @JsonIgnore
    private int currentCooldown;
    @JsonIgnore
    private boolean triggerOpposite;
    @JsonIgnore
    public int delay;
    //default constructor for Jackson
    public Skill() {}
    // Getters and Setters, needed for Jackson
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategorySpecification() { return categorySpecification; }
    public void setCategorySpecification(String categorySpecification) { this.categorySpecification = categorySpecification; }
    public String getEffectType() { return effectType; }
    public void setEffectType(String effectType) { this.effectType = effectType; }
    public String getTriggerRequirement() { return triggerRequirement; }
    public void setTriggerRequirement(String triggerRequirement) { this.triggerRequirement = triggerRequirement; }
    public double getMagnitude() { return magnitude; }
    public void setMagnitude(double magnitude) { this.magnitude = magnitude; }
    public int getSkillDuration() { return skillDuration; }
    public void setSkillDuration(int skillDuration) { this.skillDuration = skillDuration; }
    public double getTriggerChance() { return triggerChance; }
    public void setTriggerChance(double triggerChance) { this.triggerChance = triggerChance; }
    public int getRoundsToTrigger() { return roundsToTrigger; }
    public void setRoundsToTrigger(int roundsToTrigger) { this.roundsToTrigger = roundsToTrigger; }
    public int getHitsPerCooldown() { return hitsPerCooldown; }
    public void setHitsPerCooldown(int hitsPerCooldown) { this.hitsPerCooldown = hitsPerCooldown; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public int getDuration() { return skillDuration; }
    public void setCurrentCooldown(int cooldown) { this.currentCooldown = cooldown; }
    public String getDependent () { return dependent; }
    public void setDependent (String dependent) { this.dependent=dependent;}
    public void setTriggerOpposite (Boolean oppositeCheck) { this.triggerOpposite = oppositeCheck; }
    public boolean getRemovable() { return this.removable; }
    public void setRemovable(boolean setting) { this.removable = setting; }

    // Only call this manually if you need a skill by name
    public static Skill loadFromJsonByName(String skillName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = SkillDatabase.class.getClassLoader().getResourceAsStream("test/SkillDatabase.json");
            if (is == null) {
                throw new FileNotFoundException("SkillDatabase.json not found in resources");
            }
            List<Skill> skills = objectMapper.readValue(
                    is,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Skill.class)
            );

            for (Skill skill : skills) {
                if (skill.getName().equalsIgnoreCase(skillName)) {
                    // modify this skill for opposite triggers like e.g. on not bleed
                    if (skill.getTriggerRequirement().charAt(0) == '!') {
                        skill.setTriggerRequirement(skill.getTriggerRequirement().substring(1));
                        skill.setTriggerOpposite(true);
                    }
                    else { skill.setTriggerOpposite(false); }
                    skill.checkTriggerDelay();
                    return skill;
                }
            }

            System.err.println("Skill not found: " + skillName);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return new Skill(); // return empty fallback to avoid null
    }

    public void checkTriggerDelay() {
        delay = 0; // got patched
        //if (name.contains("Devastating Charge")) { delay = -1; } // to account for a bug ingame, makes devastating charge trigger r1, r7 etc
        //else { delay = 0; }
    }

    public boolean shouldTrigger(int round, HashMap<String, Boolean> uptimeDic, Set<String> triggeredSet) {
        if (currentCooldown>0) { currentCooldown--; }
        if (!dependent.equalsIgnoreCase("N/A") && !triggeredSet.contains(dependent)) {return false;} //for skills triggering on other skills
        if (triggerRequirement.equals("N/A")) { return checkTrigger(round + delay); }
        try { 
            if (!triggerOpposite && uptimeDic.get(triggerRequirement)) { return checkTrigger(round + delay); } 
            if (triggerOpposite && !uptimeDic.get(triggerRequirement)) { return checkTrigger(round + delay); } 
            else { return false; }

        } catch (Exception e) {
            System.out.println("Unknown trigger requirement: " + triggerRequirement);
            System.out.println("check for error");
            return false;
        }
    }

    private boolean checkTrigger(int round) {
        if (roundsToTrigger != -1 && round % roundsToTrigger == 0 && currentCooldown == 0) {
            currentCooldown = cooldown;
            //System.out.println("The skill " + name + " just triggered on round " + round);
            return true;
        }
        if (Math.random() < triggerChance && currentCooldown == 0) {
            currentCooldown = cooldown;
            //System.out.println("The skill " + name + " just triggered on round " + round);
            return true;
        }
        return false;
    }

    //converts the maxed skill to what it will be at given star level and level
    private double mountMagnitudeScalar(double magnitude, int star, int level) {
        switch (star) {
            case 1 -> {
                return magnitude; //collect data ;
            }
            case 2 -> {
                return magnitude; //collect data ;
            }
            case 3 -> {
                return magnitude * 0.042*level+0.207;
            }
            case 4 -> {
                return magnitude * 0.0479*level+0.245;
            }
            case 5 -> {
                return magnitude * 0.0568*level+0.283;
            }
            case 6 -> {
                return magnitude * 0.0667*level+0.333;
            }
            default -> {
                System.out.println("Unknown star level: " + star);
                return magnitude;
            }
        }
    }    
}
