package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DebuffEffectCollection {
    private HashMap<Integer, List<StatusEffect>> effectsById = new HashMap<>();
    private HashMap<String, Boolean> activeEffectTypes = new HashMap<>();

    double damageTotal;
    double attackDamp;
    double defenseDamp;
    double healthDamp;
    double rageDamp;
    double damageReceivedIncrease;
    boolean basicAttack;
    boolean counterAttack;

    public boolean getBasicAttack() { return basicAttack; }
    public boolean getCounterAttack() { return counterAttack; }
    public double getTotalDamage() { return damageTotal; }
    public double getAttackDamp() { return attackDamp; }
    public double getDefenseDamp() { return defenseDamp; }
    public double getHealthDamp() { return healthDamp; }
    public double getRageDamp() { return rageDamp; }
    public double getDamageReceivedIncrease() { return damageReceivedIncrease; }
    public boolean isEffectActive(String type) { return activeEffectTypes.getOrDefault(type, false); }

    public void clear() {
        Iterator<Integer> mapIterator = effectsById.keySet().iterator();
        while (mapIterator.hasNext()) {
            Integer id = mapIterator.next();
            List<StatusEffect> effectList = effectsById.get(id);
            effectList.removeIf(effect -> effect.getRemovable());

            if (effectList.isEmpty()) {
                mapIterator.remove(); // only removes effects applied to this round
            }
        }
    }

    public void removeEffectRandom() {

        List<StatusEffect> allEffects = new ArrayList<>();
        for (List<StatusEffect> effectList : effectsById.values()) {
            for (StatusEffect effect : effectList) {
                if (effect.getRemovable()) {
                    allEffects.add(effect);
                }
            }
        }

        // If empty cancel
        if (allEffects.isEmpty()) {
            return;
        }

        // Select a random effect
        Random rand = new Random();
        StatusEffect effectToRemove = allEffects.get(rand.nextInt(allEffects.size()));

        // Now, iterate through effectsById to find and remove the chosen effect
        // We need to iterate through the entries to modify the original lists
        // and potentially remove keys from the HashMap.
        Iterator<Integer> idIterator = effectsById.keySet().iterator();
        while (idIterator.hasNext()) {
            Integer id = idIterator.next();
            List<StatusEffect> effectList = effectsById.get(id);
            if (effectList != null) {
                if (effectList.remove(effectToRemove)) { // This attempts to remove the object
                    // If the effect was found and removed from this list,
                    // check if the list is now empty and remove the key.
                    if (effectList.isEmpty()) {
                        idIterator.remove(); // Safely remove the key from the map
                    }
                    break; // Effect found and removed, no need to continue searching
                }
            }
        }
    }


    public void addEffect(int id, StatusEffect newEffect) {
        List<StatusEffect> effectList = effectsById.computeIfAbsent(id, k -> new ArrayList<>());

        // Remove any effect with the same name
        effectList.removeIf(effect -> effect.getName().equals(newEffect.getName()));

        // Add the new or updated effect
        effectList.add(newEffect);
    }

    public void tickAll() {
        damageTotal = 0;
        attackDamp = 0;
        defenseDamp = 0;
        healthDamp = 0;
        rageDamp = 0;
        damageReceivedIncrease = 0;
        
        for (List<StatusEffect> effectList : effectsById.values()) {
            Iterator<StatusEffect> iterator = effectList.iterator();
            while (iterator.hasNext()) {
                StatusEffect effect = iterator.next();
                effect.tick();
                if (effect.isExpired()) {
                    iterator.remove();
                }
            }
        }
    }

    public void runInfo() {
        activeEffectTypes.clear();
        basicAttack = true;
        counterAttack = false;
        for (List<StatusEffect> list : effectsById.values()) {
            for (StatusEffect effect : list) {
                //System.out.println(effect.getName());
                activeEffectTypes.put(effect.getType(),true);
                if (SkillDatabase.damageEffectSet.contains(effect.getType())) {
                    damageTotal += effect.getMagnitude();
                }
                else {
                    switch (effect.getType()) {
                        case "attackDamp" -> attackDamp+=effect.getMagnitude();
                        case "defenseDamp" -> defenseDamp+=effect.getMagnitude();
                        case "healthDamp" -> healthDamp+=effect.getMagnitude();
                        case "rageDamp" -> rageDamp+=effect.getMagnitude();
                        case "disarm" -> basicAttack = false;
                        case "brokenBlade" -> counterAttack = false;
                        case "damageReceivedIncrease" -> damageReceivedIncrease += effect.getMagnitude();
                        //default -> System.out.println(effect.getType());
                        // add debuff clearing, silence done elsewhere
                    }
                }
            }
        }
    }
}
