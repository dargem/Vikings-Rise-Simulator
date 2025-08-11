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
    rounds: 1000000,
    fights: 30000,
    fightLength: 20,
    fightsPerLength: 100000
  });
  
  // Store display values separately from numeric values
  const [displayValues, setDisplayValues] = useState({
    rounds: '1000000',
    fights: '30000',
    fightLength: '20',
    fightsPerLength: '100000'
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
      rounds: config.rounds || 1000000,
      fights: config.fights || 30000,
      fightLength: config.fightLength || 20,
      fightsPerLength: config.fightsPerLength || 100000
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
                placeholder="1000000"
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
              <Label>Fight Length (intervals for 1-n rounds ran):</Label>
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
