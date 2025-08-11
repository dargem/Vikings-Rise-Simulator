package com.vikingsrise.simulator.dto;

import java.util.List;

public class SimulationRequestDTO {
    private SimulationConfigDTO config;
    private List<MarchDTO> marches;

    // Constructors
    public SimulationRequestDTO() {}

    public SimulationRequestDTO(SimulationConfigDTO config, List<MarchDTO> marches) {
        this.config = config;
        this.marches = marches;
    }

    // Getters and setters
    public SimulationConfigDTO getConfig() { return config; }
    public void setConfig(SimulationConfigDTO config) { this.config = config; }

    public List<MarchDTO> getMarches() { return marches; }
    public void setMarches(List<MarchDTO> marches) { this.marches = marches; }
}
