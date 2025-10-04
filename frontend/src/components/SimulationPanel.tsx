import React, { useState } from 'react';
import { SimulationConfig } from '../types';
import {
  TabContainer,
  TabList,
  Tab,
  TabContent,
  FormGroup,
  Label,
  Input,
  Button,
  LoadingSpinner
} from '../styled';

// Default values for simulation config
const DEFAULT_ROUNDS = 150000;
const DEFAULT_FIGHTS = 10000;
const DEFAULT_FIGHT_LENGTH = 20;
const DEFAULT_FIGHTS_PER_LENGTH = 50000;

interface SimulationPanelProps {
  onRunSimulation: (config: SimulationConfig) => void;
  isLoading: boolean;
}

export const SimulationPanel: React.FC<SimulationPanelProps> = ({
  onRunSimulation,
  isLoading
}) => {
  const [activeTab, setActiveTab] = useState<'trades' | 'plotFights' | 'groupRoundSim'>('trades');
  const [config, setConfig] = useState<SimulationConfig>({
    type: 'trades',
    rounds: DEFAULT_ROUNDS,
    fights: DEFAULT_FIGHTS,
    fightLength: DEFAULT_FIGHT_LENGTH,
    fightsPerLength: DEFAULT_FIGHTS_PER_LENGTH
  });

  // Store display values separately from numeric values
  const [displayValues, setDisplayValues] = useState({
    rounds: DEFAULT_ROUNDS.toString(),
    fights: DEFAULT_FIGHTS.toString(),
    fightLength: DEFAULT_FIGHT_LENGTH.toString(),
    fightsPerLength: DEFAULT_FIGHTS_PER_LENGTH.toString()
  });

  const handleConfigChange = (field: keyof SimulationConfig, value: string) => {
    // Update display value immediately (preserves zeros)
    setDisplayValues(prev => ({ ...prev, [field]: value }));
    
    // Update numeric value
    const numValue = value === '' ? undefined : parseInt(value);
    setConfig(prev => ({ ...prev, [field]: numValue }));
  };

  const handleRun = () => {
    // Use defaults if fields are empty
    const finalConfig = {
      ...config,
      type: activeTab,
      rounds: config.rounds || DEFAULT_ROUNDS,
      fights: config.fights || DEFAULT_FIGHTS,
      fightLength: config.fightLength || DEFAULT_FIGHT_LENGTH,
      fightsPerLength: config.fightsPerLength || DEFAULT_FIGHTS_PER_LENGTH
    };
    onRunSimulation(finalConfig);
  };

  return (
    <TabContainer>
      <TabList>
        <Tab active={activeTab === 'trades'} onClick={() => setActiveTab('trades')}>
          Trades
        </Tab>
        <Tab active={activeTab === 'plotFights'} onClick={() => setActiveTab('plotFights')}>
          Plot Fights
        </Tab>
        <Tab active={activeTab === 'groupRoundSim'} onClick={() => setActiveTab('groupRoundSim')}>
          Group Round Sim
        </Tab>
      </TabList>

      <TabContent>
        {activeTab === 'trades' && (
          <>
            <FormGroup>
              <Label>Rounds:</Label>
              <Input
                type="number"
                value={displayValues.rounds}
                placeholder={DEFAULT_ROUNDS.toString()}
                onChange={(e) => handleConfigChange('rounds', e.target.value)}
              />
            </FormGroup>
            <Button variant="primary" onClick={handleRun} disabled={isLoading}>
              {isLoading ? <LoadingSpinner /> : 'Run Trades'}
            </Button>
          </>
        )}

        {activeTab === 'plotFights' && (
          <>
            <FormGroup>
              <Label>Fights:</Label>
              <Input
                type="number"
                value={displayValues.fights}
                placeholder="30000"
                onChange={(e) => handleConfigChange('fights', e.target.value)}
              />
            </FormGroup>
            <Button variant="primary" onClick={handleRun} disabled={isLoading}>
              {isLoading ? <LoadingSpinner /> : 'Run Plot Fights'}
            </Button>
          </>
        )}

        {activeTab === 'groupRoundSim' && (
          <>
            <FormGroup>
              <Label>Fight Length:<br></br>(Intervals for 1-n rounds ran)</Label>
              <Input
                type="number"
                value={displayValues.fightLength}
                placeholder="20"
                onChange={(e) => handleConfigChange('fightLength', e.target.value)}
              />
            </FormGroup>
            <FormGroup>
              <Label>Rounds per interval:</Label>
              <Input
                type="number"
                value={displayValues.fightsPerLength}
                placeholder="100000"
                onChange={(e) => handleConfigChange('fightsPerLength', e.target.value)}
              />
            </FormGroup>
            <Button variant="primary" onClick={handleRun} disabled={isLoading}>
              {isLoading ? <LoadingSpinner /> : 'Run Group Round Sim'}
            </Button>
          </>
        )}
      </TabContent>
    </TabContainer>
  );
};
