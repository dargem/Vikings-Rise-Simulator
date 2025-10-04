import React, { useState, useEffect } from 'react';
import { March as MarchType, Commander, Skill, SimulationConfig, SimulationResult, GroupRoundResult } from './types';
import { simulatorAPI } from './api';
import { MarchComponent } from './components/March';
import { SimulationPanel } from './components/SimulationPanel';
import { PlotModal } from './components/PlotModal';
import {
  GlobalStyle,
  AppContainer,
  Header,
  Title,
  MainContent,
  LeftPanel,
  CenterPanel,
  MarchesContainer,
  ButtonGroup,
  Button,
  ResultsArea,
  ResultsSection,
  ResultsTitle
} from './styled';

const App: React.FC = () => {
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
  const [isPlotModalOpen, setIsPlotModalOpen] = useState<boolean>(false);

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
    // Validate that we have at least 1 friendly and 1 enemy march
    const friendlyMarches = marches.filter(march => march.isFriendly);
    const enemyMarches = marches.filter(march => !march.isFriendly);
    
    if (friendlyMarches.length === 0 || enemyMarches.length === 0) {
      setResults('Needs 1 enemy and 1 friendly march to run simulation');
      return;
    }
    
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
        
        setResults(`Fight simulation complete! Click "View Histogram" to see detailed results.
Total Fights: ${troopResults.length.toLocaleString()}
Friendly Wins: ${friendlyWins.toLocaleString()} (${(friendlyWins/troopResults.length*100).toFixed(1)}%)
Enemy Wins: ${enemyWins.toLocaleString()} (${(enemyWins/troopResults.length*100).toFixed(1)}%)
Draws: ${draws.toLocaleString()} (${(draws/troopResults.length*100).toFixed(1)}%)

Average Troops: ${avgTroops}
Min Troops: ${minTroops}
Max Troops: ${maxTroops}`);
      }
    } catch (error) {
      setResults(`Error: ${error instanceof Error ? error.message : 'Unknown error'}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <GlobalStyle />
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
            <ResultsSection>
              <ResultsTitle>Results</ResultsTitle>
              <ResultsArea>
                {results}
                {fightResults && (
                  <div style={{ marginTop: '10px' }}>
                    <Button variant="primary" onClick={() => setIsPlotModalOpen(true)}>
                      View Histogram
                    </Button>
                  </div>
                )}
              </ResultsArea>
            </ResultsSection>
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
        </MainContent>
      )}
    </AppContainer>
    
    {fightResults && (
      <PlotModal 
        isOpen={isPlotModalOpen}
        onClose={() => setIsPlotModalOpen(false)}
        fightResults={fightResults}
      />
    )}
    </>
  );
}

export default App;
