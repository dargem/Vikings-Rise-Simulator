package com.vikingsrise.simulator.dto;

public class SimulationConfigDTO {
    private String type; // 'trades', 'plotFights', 'groupRoundSim'
    private Integer rounds;
    private Integer fights;
    private Integer fightLength;
    private Integer fightsPerLength;

    // Constructors
    public SimulationConfigDTO() {}

    public SimulationConfigDTO(String type, Integer rounds, Integer fights, 
                              Integer fightLength, Integer fightsPerLength) {
        this.type = type;
        this.rounds = rounds;
        this.fights = fights;
        this.fightLength = fightLength;
        this.fightsPerLength = fightsPerLength;
    }

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getRounds() { return rounds; }
    public void setRounds(Integer rounds) { this.rounds = rounds; }

    public Integer getFights() { return fights; }
    public void setFights(Integer fights) { this.fights = fights; }

    public Integer getFightLength() { return fightLength; }
    public void setFightLength(Integer fightLength) { this.fightLength = fightLength; }

    public Integer getFightsPerLength() { return fightsPerLength; }
    public void setFightsPerLength(Integer fightsPerLength) { this.fightsPerLength = fightsPerLength; }
}
