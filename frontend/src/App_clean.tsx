import React, { useState, useEffect } from 'react';
import { March as MarchType, Commander, Skill, SimulationConfig, SimulationResult, GroupRoundResult } from './types';
import { simulatorAPI } from './api';
import { MarchComponent } from './components/March';
import { SimulationPanel } from './components/SimulationPanel';
import Plot from 'react-plotly.js';
import {
  AppContainer,
  Header,
  Title,
  MainContent,
  LeftPanel,
  CenterPanel,
  RightPanel,
  MarchesContainer,
  ButtonGroup,
  Button,
  ResultsArea
} from './styled';

// Plotly histogram component - exact copy of original GUI
const PlotlyHistogram: React.FC<{ 
  fightResults: number[];
}> = ({ fightResults }) => {
  const data = [{
    x: fightResults,
    type: 'histogram' as const,
    marker: {
      color: 'rgba(100, 149, 237, 0.7)'
    }
  }];

  const layout = {
    title: 'Troop Survival Distribution',
    xaxis: { 
      title: 'Troops Alive After Fight' 
    },
    yaxis: { 
      title: 'Frequency' 
    },
    bargap: 0.05,
    width: undefined,
    height: 400,
    margin: { t: 50, r: 30, b: 50, l: 60 }
  };

  const config = {
    responsive: true,
    displayModeBar: true,
    displaylogo: false,
    modeBarButtonsToRemove: ['pan2d', 'lasso2d', 'select2d']
  };

  return (
    <div style={{ 
      background: 'white',
      borderRadius: '8px',
      padding: '20px',
      margin: '20px 0',
      boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
    }}>
      <Plot
        data={data}
        layout={layout}
        config={config}
        style={{ width: '100%', height: '400px' }}
        useResizeHandler={true}
      />
    </div>
  );
};

function App() {
  const [marches, setMarches] = useState<MarchType[]>([
    {
      id: '1',
      primaryCommander: 'N/A',
      secondaryCommander: 'N/A',
      attack: 830,
      defense: 696,
      health: 396,
      troopSize: 300000,
      isFriendly: true,
      isRally: false,
      skills: ['N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A']
    }
  ]);
  
  const [commanders, setCommanders] = useState<Commander[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [dataLoading, setDataLoading] = useState(true);
  const [results, setResults] = useState<string>('Results will appear here...');
  const [fightResults, setFightResults] = useState<number[] | null>(null);

  // Load commanders and skills from backend on component mount
  useEffect(() => {
    const loadData = async () => {
      try {
        setDataLoading(true);
        const [commandersData, skillsData] = await Promise.all([
          simulatorAPI.getCommanders(),
          simulatorAPI.getSkills()
        ]);
        setCommanders(commandersData);
        setSkills(skillsData);
      } catch (error) {
        console.error('Failed to load data from backend:', error);
        // Fallback to basic data if backend is not available
        setCommanders([{ name: 'N/A' }]);
        setSkills([{ name: 'N/A', type: 'normal' }]);
      } finally {
        setDataLoading(false);
      }
    };

    loadData();
  }, []);

  const generateId = () => Date.now().toString();

  const addMarch = () => {
    const newMarch: MarchType = {
      id: generateId(),
      primaryCommander: 'N/A',
      secondaryCommander: 'N/A',
      attack: 830,
      defense: 696,
      health: 396,
      troopSize: 300000,
      isFriendly: true,
      isRally: false,
      skills: ['N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A']
    };
    setMarches(prev => [...prev, newMarch]);
  };

  const addEnemyDummy = () => {
    const newDummy: MarchType = {
      id: generateId(),
      primaryCommander: 'N/A',
      secondaryCommander: 'N/A',
      attack: 830,
      defense: 696,
      health: 396,
      troopSize: 300000,
      isFriendly: false,
      isRally: false,
      skills: ['N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A']
    };
    setMarches(prev => [...prev, newDummy]);
  };

  const cloneMarch = (march: MarchType) => {
    const cloned: MarchType = {
      ...march,
      id: generateId()
    };
    setMarches(prev => [...prev, cloned]);
  };

  const updateMarch = (updatedMarch: MarchType) => {
    setMarches(prev => prev.map(m => m.id === updatedMarch.id ? updatedMarch : m));
  };

  const removeMarch = (id: string) => {
    setMarches(prev => prev.filter(m => m.id !== id));
  };

  const runSimulation = async (config: SimulationConfig) => {
    setIsLoading(true);
    setFightResults(null); // Clear previous histogram data
    setResults('Running simulation, please wait...');
    
    try {
      // Use actual API call instead of mock
      const result = await simulatorAPI.runSimulation(config, marches);
      
      if (config.type === 'trades') {
        const tradesResult = result as SimulationResult;
        setResults(`Simulation complete! 
Trades Pre-Heal: ${tradesResult.tradesPreHeal}
Trades Post-Heal: ${tradesResult.tradesPostHeal}
Enemy Lost Per Round Pre Heal: ${tradesResult.enemyLostPreHeal}

Per Combatant Rundown: 
${tradesResult.combatRecords}`);
      } else if (config.type === 'groupRoundSim') {
        const groupResults = result as GroupRoundResult[];
        
        let resultText = 'Group Round Simulation Results (Rounds: TradesPreHeal, TradesPostHeal):\n';
        groupResults.forEach(result => {
          resultText += `${result.round.toString().padStart(2)}: ${result.tradesPreHeal.toFixed(3)}, ${result.tradesPostHeal.toFixed(3)}\n`;
        });
        setResults(resultText);
      } else if (config.type === 'plotFights') {
        const troopResults = result as number[];
        
        // Calculate statistics for display
        const avgTroops = (troopResults.reduce((sum, val) => sum + val, 0) / troopResults.length).toFixed(0);
        const minTroops = Math.min(...troopResults);
        const maxTroops = Math.max(...troopResults);
        const friendlyWins = troopResults.filter(troops => troops > 0).length;
        const enemyWins = troopResults.filter(troops => troops < 0).length;
        const draws = troopResults.filter(troops => troops === 0).length;
        
        // Set raw data for Plotly histogram
        setFightResults(troopResults);
        
        setResults(`Fight simulation complete! View troop survival histogram below.
Total Fights: ${troopResults.length.toLocaleString()}
Friendly Wins: ${friendlyWins.toLocaleString()} (${(friendlyWins/troopResults.length*100).toFixed(1)}%)
Enemy Wins: ${enemyWins.toLocaleString()} (${(enemyWins/troopResults.length*100).toFixed(1)}%)
Draws: ${draws.toLocaleString()} (${(draws/troopResults.length*100).toFixed(1)}%)
Average Troops Alive: ${avgTroops}
Range: ${minTroops} to ${maxTroops} troops`);
      }
    } catch (error) {
      setResults(`Error: ${error instanceof Error ? error.message : 'Unknown error'}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <AppContainer>
      <Header>
        <Title>Vikings Rise Simulator</Title>
      </Header>
      
      {dataLoading ? (
        <div style={{ padding: '50px', textAlign: 'center' }}>
          <h2>Loading commanders and skills...</h2>
          <p>Please wait while we load the data from your skill database.</p>
        </div>
      ) : (
        <MainContent>
          <LeftPanel>
            <SimulationPanel onRunSimulation={runSimulation} isLoading={isLoading} />
          </LeftPanel>
          
          <CenterPanel>
            <ButtonGroup>
              <Button variant="primary" onClick={addMarch}>
                + Add March
              </Button>
              <Button variant="danger" onClick={addEnemyDummy}>
                + Add Enemy Dummy
              </Button>
            </ButtonGroup>
            
            <MarchesContainer>
              {marches.map(march => (
                <MarchComponent
                  key={march.id}
                  march={march}
                  commanders={commanders}
                  skills={skills}
                  onUpdate={updateMarch}
                  onRemove={() => removeMarch(march.id!)}
                  onClone={() => cloneMarch(march)}
                />
              ))}
            </MarchesContainer>
          </CenterPanel>
          
          <RightPanel>
            <h3>Results</h3>
            <ResultsArea>
              {results}
              {fightResults && (
                <PlotlyHistogram fightResults={fightResults} />
              )}
            </ResultsArea>
          </RightPanel>
        </MainContent>
      )}
    </AppContainer>
  );
}

export default App;
