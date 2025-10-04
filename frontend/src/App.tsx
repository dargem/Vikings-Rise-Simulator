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
  HeaderLinks,
  HeaderLink,
  Title,
  MainContent,
  LeftPanel,
  CenterPanel,
  MarchesContainer,
  ButtonGroup,
  Button,
  ResultsArea,
  ResultsSection,
  ResultsTitle,
  Footer,
  FooterContent,
  FooterText,
  FooterLinks,
  HintText
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
    },
    {
      id: '2',
      primaryCommander: 'N/A',
      secondaryCommander: 'N/A',
      attack: 830,
      defense: 696,
      health: 396,
      troopSize: 300000,
      isFriendly: false,
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
        
        // Format the combatRecords to round all numbers to 3 decimal places
        const formattedCombatRecords = tradesResult.combatRecords.replace(
          /\d+\.?\d*E?[+-]?\d*/g, 
          (match) => {
            const num = parseFloat(match);
            if (num >= 1e6 || num <= -1e6) {
              // For very large numbers, use scientific notation with 3 decimal places
              return num.toExponential(3);
            } else {
              // For normal numbers, use 3 decimal places
              return num.toFixed(3);
            }
          }
        );
        
        setResults(`Simulation complete! 
Trades Pre-Heal: ${parseFloat(tradesResult.tradesPreHeal.toString()).toFixed(3)}
Trades Post-Heal: ${parseFloat(tradesResult.tradesPostHeal.toString()).toFixed(3)}
Enemy Lost Per Round Pre Heal: ${parseFloat(tradesResult.enemyLostPreHeal.toString()).toFixed(3)}

Per Combatant Rundown: 
${formattedCombatRecords}`);
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
        const avgTroops = (troopResults.reduce((sum, val) => sum + val, 0) / troopResults.length).toFixed(3);
        const minTroops = Math.min(...troopResults);
        const maxTroops = Math.max(...troopResults);
        const friendlyWins = troopResults.filter(troops => troops > 0).length;
        const enemyWins = troopResults.filter(troops => troops < 0).length;
        const draws = troopResults.filter(troops => troops === 0).length;
        
        // Set raw data for Plotly histogram
        setFightResults(troopResults);
        
        setResults(`Fight simulation complete! Click "View Histogram" to see detailed results.
Total Fights: ${troopResults.length.toLocaleString()}
Friendly Wins: ${friendlyWins.toLocaleString()} (${(friendlyWins/troopResults.length*100).toFixed(3)}%)
Enemy Wins: ${enemyWins.toLocaleString()} (${(enemyWins/troopResults.length*100).toFixed(3)}%)
Draws: ${draws.toLocaleString()} (${(draws/troopResults.length*100).toFixed(3)}%)

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
          <HeaderLinks>
            <HeaderLink 
              href="https://github.com/dargem/Vikings-Rise-Simulator" 
              target="_blank" 
              rel="noopener noreferrer"
              title="View on GitHub"
            >
              <svg viewBox="0 0 24 24">
                <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
              </svg>
            </HeaderLink>
          </HeaderLinks>
        </Header>
      
      {dataLoading ? (
        <div style={{ padding: '50px', textAlign: 'center' }}>
          <h2>Loading commanders and skills...</h2>
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
            <HintText>Turning down the number of rounds is suggested for speed</HintText>
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
      
      <Footer>
        <FooterContent>
          <FooterText>
          VR Monte Carlo Simulator | Built by Dargem
          </FooterText>
          <FooterText>
            report bugs or feature suggestions @ genericname4 / dark on discord
          </FooterText>
          <FooterLinks>
            <a href="https://github.com/dargem/Vikings-Rise-Simulator" target="_blank" rel="noopener noreferrer">
              Leave A Star
            </a>
            <a href="https://github.com/dargem" target="_blank" rel="noopener noreferrer">
              Dev
            </a>
          </FooterLinks>
        </FooterContent>
      </Footer>
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
