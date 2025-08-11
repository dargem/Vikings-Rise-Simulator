export interface March {
  id?: string;
  primaryCommander: string;
  secondaryCommander: string;
  attack: number;
  defense: number;
  health: number;
  troopSize: number;
  isFriendly: boolean;
  isRally: boolean;
  skills: string[];
}

export interface SimulationConfig {
  type: 'trades' | 'plotFights' | 'groupRoundSim';
  rounds?: number;
  fights?: number;
  fightLength?: number;
  fightsPerLength?: number;
}

export interface SimulationResult {
  tradesPreHeal: number;
  tradesPostHeal: number;
  enemyLostPreHeal: number;
  combatRecords: string;
}

export interface GroupRoundResult {
  round: number;
  tradesPreHeal: number;
  tradesPostHeal: number;
}

export interface Commander {
  name: string;
}

export interface Skill {
  name: string;
  type: 'normal' | 'mountSlot1' | 'mountSlot2';
}
