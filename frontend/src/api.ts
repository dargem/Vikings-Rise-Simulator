import { March, SimulationConfig, SimulationResult, GroupRoundResult, Commander, Skill } from './types';

const API_BASE_URL = '/api';

// Simple fetch-based API client (alternative to axios)
const apiClient = {
  get: async (url: string) => {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  },
  
  post: async (url: string, data: any) => {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  }
};

export const simulatorAPI = {
  // Run simulation
  runSimulation: async (config: SimulationConfig, marches: March[]): Promise<SimulationResult | GroupRoundResult[] | number[]> => {
    const response = await apiClient.post(`${API_BASE_URL}/simulate`, {
      config,
      marches
    });
    return response;
  },

  // Get commanders
  getCommanders: async (): Promise<Commander[]> => {
    const response = await apiClient.get(`${API_BASE_URL}/commanders`);
    return response;
  },

  // Get skills
  getSkills: async (): Promise<Skill[]> => {
    const response = await apiClient.get(`${API_BASE_URL}/skills`);
    return response;
  }
};
