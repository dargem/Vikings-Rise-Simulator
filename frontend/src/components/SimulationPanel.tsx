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

  const handleConfigChange = (field: keyof SimulationConfig, value: number) => {
    setConfig(prev => ({ ...prev, [field]: value }));
  };

  const handleRun = () => {
    onRunSimulation({ ...config, type: activeTab });
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
                value={config.rounds || ''}
                placeholder="1000000"
                onChange={(e) => handleConfigChange('rounds', parseInt(e.target.value) || 1000000)}
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
                value={config.fights || ''}
                placeholder="30000"
                onChange={(e) => handleConfigChange('fights', parseInt(e.target.value) || 30000)}
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
                value={config.fightLength || ''}
                placeholder="20"
                onChange={(e) => handleConfigChange('fightLength', parseInt(e.target.value) || 20)}
              />
            </FormGroup>
            <FormGroup>
              <Label>Rounds per interval:</Label>
              <Input
                type="number"
                value={config.fightsPerLength || ''}
                placeholder="100000"
                onChange={(e) => handleConfigChange('fightsPerLength', parseInt(e.target.value) || 100000)}
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
