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

// Track visitor counter when the site loads
export const trackVisitor = async (): Promise<void> => {
  try {
    const response = await fetch('https://api.counterapi.dev/v2/vr-sim/p1ewv1gs/up', {
      method: 'GET',
    });
    
    if (response.ok) {
      const data = await response.json();
      console.log('Visitor tracked:', data);
    }
  } catch (error) {
    // Silently fail - don't break the app if counter API is down
    console.log('Counter API unavailable:', error);
  }
};
