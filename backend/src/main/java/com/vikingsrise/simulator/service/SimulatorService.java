package com.vikingsrise.simulator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vikingsrise.simulator.dto.MarchDTO;
import com.vikingsrise.simulator.dto.SimulationConfigDTO;

import test.Simulator;
import test.SkillDatabase;
import test.combatrecords.CombatRecordOverview;

@Service
public class SimulatorService {

    public Object runSimulation(SimulationConfigDTO config, List<MarchDTO> marches) {
        Simulator simulator = new Simulator();
        
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
                int fights = config.getFights() != null ? config.getFights() : 30000;
                List<Integer> fightResults = simulator.runFights(fights);
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
