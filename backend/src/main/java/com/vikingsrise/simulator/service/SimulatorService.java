package com.vikingsrise.simulator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vikingsrise.simulator.dto.MarchDTO;
import com.vikingsrise.simulator.dto.SimulationConfigDTO;

import test.SkillDatabase;
import test.combatrecords.CombatRecordOverview;

@Service
public class SimulatorService {
    private static final Logger log = LoggerFactory.getLogger(SimulatorService.class);

    public Object runSimulation(SimulationConfigDTO config, List<MarchDTO> marches) {
        System.out.println("=== SIMULATION SERVICE DEBUG ===");
        System.out.println("Simulation type: " + config.getType());
        System.out.println("Number of marches: " + marches.size());
        
        for (int i = 0; i < marches.size(); i++) {
            MarchDTO march = marches.get(i);
            System.out.println("March " + i + ": " + 
                (march.isFriendly() ? "Friendly" : "Enemy") + 
                " - Attack: " + march.getAttack() + 
                ", Defense: " + march.getDefense() + 
                ", Health: " + march.getHealth() + 
                ", Troops: " + march.getTroopSize());
        }
        
        System.out.println("Config - Type: " + config.getType() + ", Fights: " + config.getFights());
        
        test.Simulator simulator = new test.Simulator();
        
        // Add marches to simulator
        for (MarchDTO march : marches) {
            if (march.isFriendly()) {
                simulator.setNewCombatant(
                    march.getAttack(), march.getDefense(), march.getHealth(),
                    march.getTroopSize(), march.isRally(),
                    march.getPrimaryCommander(), march.getSecondaryCommander(),
                    getSkillName(march.getSkills(), 0), getSkillName(march.getSkills(), 1),
                    getSkillName(march.getSkills(), 2), getSkillName(march.getSkills(), 3),
                    getSkillName(march.getSkills(), 4), getSkillName(march.getSkills(), 5),
                    getSkillName(march.getSkills(), 6), getSkillName(march.getSkills(), 7)
                );
            } else {
                simulator.setNewEnemyCombatant(
                    march.getAttack(), march.getDefense(), march.getHealth(),
                    march.getTroopSize(), march.isRally(),
                    march.getPrimaryCommander(), march.getSecondaryCommander(),
                    getSkillName(march.getSkills(), 0), getSkillName(march.getSkills(), 1),
                    getSkillName(march.getSkills(), 2), getSkillName(march.getSkills(), 3),
                    getSkillName(march.getSkills(), 4), getSkillName(march.getSkills(), 5),
                    getSkillName(march.getSkills(), 6), getSkillName(march.getSkills(), 7)
                );
            }
        }

        // Debug: Log march configurations
        System.out.println("=== MARCH CONFIGURATIONS DEBUG ===");
        for (int i = 0; i < marches.size(); i++) {
            MarchDTO march = marches.get(i);
            System.out.println("March " + i + ":");
            System.out.println("  Primary Commander: " + march.getPrimaryCommander());
            System.out.println("  Secondary Commander: " + march.getSecondaryCommander());
            System.out.println("  Attack: " + march.getAttack());
            System.out.println("  Defense: " + march.getDefense());
            System.out.println("  Health: " + march.getHealth());
            System.out.println("  Troop Count: " + march.getTroopSize());
            System.out.println("  Skills: " + march.getSkills());
            System.out.println("  Is Friendly: " + march.isFriendly());
        }

        switch (config.getType()) {
            case "trades":
                int rounds = config.getRounds() != null ? config.getRounds() : 1000000;
                CombatRecordOverview record = simulator.findTrades(rounds, false);
                Map<String, Object> tradesResult = new HashMap<>();
                tradesResult.put("tradesPreHeal", record.getTradesPreHeal());
                tradesResult.put("tradesPostHeal", record.getTradesPostHeal());
                tradesResult.put("enemyLostPreHeal", record.getEnemyLostPreHeal(rounds));
                tradesResult.put("combatRecords", record.getStringCombatRecords(rounds));
                return tradesResult;

            case "plotFights":
                // Debug logging to compare with GUI
                log.info("=== API PLOT FIGHTS DEBUG ===");
                log.info("March count: {}", marches.size());
                for (int i = 0; i < marches.size(); i++) {
                    MarchDTO march = marches.get(i);
                    log.info("March {}: primary={}, secondary={}, attack={}, defense={}, health={}, troopSize={}, isFriendly={}, isRally={}", 
                        i+1, march.getPrimaryCommander(), march.getSecondaryCommander(), 
                        march.getAttack(), march.getDefense(), march.getHealth(), 
                        march.getTroopSize(), march.isFriendly(), march.isRally());
                    
                    // Log skills for this march
                    log.info("March {} skills: {}", i+1, march.getSkills().toString());
                    
                    // Log individual skill details
                    for (int j = 0; j < march.getSkills().size(); j++) {
                        String skillName = getSkillName(march.getSkills(), j);
                        log.info("  Skill {}: {}", j+1, skillName);
                    }
                }
                log.info("==========================");
                
                System.out.println("=== PLOT FIGHTS DEBUG ===");
                int fights = config.getFights() != null ? config.getFights() : 30000;
                System.out.println("Number of fights to run: " + fights);
                System.out.println("About to call simulator.runFights()...");
                List<Integer> fightResults = simulator.runFights(fights);
                System.out.println("Fight results received, length: " + fightResults.size());
                System.out.println("First 10 results: " + fightResults.subList(0, Math.min(10, fightResults.size())));
                return fightResults;

            case "groupRoundSim":
                int fightLength = config.getFightLength() != null ? config.getFightLength() : 20;
                int fightsPerLength = config.getFightsPerLength() != null ? config.getFightsPerLength() : 100000;
                List<CombatRecordOverview> groupResults = simulator.groupRoundSimulator(fightsPerLength, fightLength);
                List<Map<String, Object>> formattedResults = new ArrayList<>();
                for (int i = 0; i < groupResults.size(); i++) {
                    CombatRecordOverview result = groupResults.get(i);
                    Map<String, Object> roundResult = new HashMap<>();
                    roundResult.put("round", i + 1);
                    roundResult.put("tradesPreHeal", result.getTradesPreHeal());
                    roundResult.put("tradesPostHeal", result.getTradesPostHeal());
                    formattedResults.add(roundResult);
                }
                return formattedResults;

            default:
                throw new IllegalArgumentException("Unknown simulation type: " + config.getType());
        }
    }

    public List<Map<String, String>> getCommanders() {
        List<Map<String, String>> commanders = new ArrayList<>();
        try {
            Set<String> commanderNames = SkillDatabase.commanderNamesSet;
            for (String name : commanderNames) {
                Map<String, String> commander = new HashMap<>();
                commander.put("name", name);
                commanders.add(commander);
            }
        } catch (Exception e) {
            // Fallback to basic commanders
            Map<String, String> naCommander = new HashMap<>();
            naCommander.put("name", "N/A");
            commanders.add(naCommander);
        }
        return commanders;
    }

    public List<Map<String, String>> getSkills() {
        List<Map<String, String>> skills = new ArrayList<>();
        try {
            // Collect all skill names first
            List<String> allSkillNames = new ArrayList<>();
            
            // Add all skill names to a single list
            if (SkillDatabase.skillNamesSet != null) {
                allSkillNames.addAll(SkillDatabase.skillNamesSet);
            }
            if (SkillDatabase.firstSlotMountSkillNamesSet != null) {
                allSkillNames.addAll(SkillDatabase.firstSlotMountSkillNamesSet);
            }
            if (SkillDatabase.secondSlotMountSkillNamesSet != null) {
                allSkillNames.addAll(SkillDatabase.secondSlotMountSkillNamesSet);
            }
            
            // Sort all skill names alphabetically (case-insensitive)
            Collections.sort(allSkillNames, String.CASE_INSENSITIVE_ORDER);
            
            // Create skill objects in alphabetical order
            for (String name : allSkillNames) {
                Map<String, String> skill = new HashMap<>();
                skill.put("name", name);
                
                // Determine skill type based on which set contains the skill
                if (SkillDatabase.skillNamesSet != null && SkillDatabase.skillNamesSet.contains(name)) {
                    skill.put("type", "normal");
                } else if (SkillDatabase.firstSlotMountSkillNamesSet != null && SkillDatabase.firstSlotMountSkillNamesSet.contains(name)) {
                    skill.put("type", "mountSlot1");
                } else if (SkillDatabase.secondSlotMountSkillNamesSet != null && SkillDatabase.secondSlotMountSkillNamesSet.contains(name)) {
                    skill.put("type", "mountSlot2");
                } else {
                    skill.put("type", "normal"); // default fallback
                }
                
                skills.add(skill);
            }
        } catch (Exception e) {
            // Fallback to basic skills if there's any error
            Map<String, String> naSkill = new HashMap<>();
            naSkill.put("name", "N/A");
            naSkill.put("type", "normal");
            skills.add(naSkill);
        }
        return skills;
    }

    private String getSkillName(List<String> skills, int index) {
        return (skills != null && index < skills.size()) ? skills.get(index) : "N/A";
    }
}
