package com.vikingsrise.simulator.dto;

import java.util.List;

public class MarchDTO {
    private String id;
    private String primaryCommander;
    private String secondaryCommander;
    private double attack;
    private double defense;
    private double health;
    private int troopSize;
    private boolean isFriendly;
    private boolean isRally;
    private List<String> skills;

    // Constructors
    public MarchDTO() {}

    public MarchDTO(String id, String primaryCommander, String secondaryCommander, 
                   double attack, double defense, double health, int troopSize, 
                   boolean isFriendly, boolean isRally, List<String> skills) {
        this.id = id;
        this.primaryCommander = primaryCommander;
        this.secondaryCommander = secondaryCommander;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.troopSize = troopSize;
        this.isFriendly = isFriendly;
        this.isRally = isRally;
        this.skills = skills;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPrimaryCommander() { return primaryCommander; }
    public void setPrimaryCommander(String primaryCommander) { this.primaryCommander = primaryCommander; }

    public String getSecondaryCommander() { return secondaryCommander; }
    public void setSecondaryCommander(String secondaryCommander) { this.secondaryCommander = secondaryCommander; }

    public double getAttack() { return attack; }
    public void setAttack(double attack) { this.attack = attack; }

    public double getDefense() { return defense; }
    public void setDefense(double defense) { this.defense = defense; }

    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }

    public int getTroopSize() { return troopSize; }
    public void setTroopSize(int troopSize) { this.troopSize = troopSize; }

    public boolean isFriendly() { return isFriendly; }
    public void setFriendly(boolean friendly) { isFriendly = friendly; }

    public boolean isRally() { return isRally; }
    public void setRally(boolean rally) { isRally = rally; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
