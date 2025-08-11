import styled from 'styled-components';

export const AppContainer = styled.div`
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  position: relative;
`;

export const Header = styled.div`
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
  padding: 30px 20px;
  text-align: center;
  position: relative;
`;

export const Title = styled.h1`
  margin: 0;
  font-size: 3rem;
  font-weight: 800;
  background: linear-gradient(135deg, #ffffff 0%, #f0f0f0 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  letter-spacing: -1px;
`;

export const CreditText = styled.div`
  position: absolute;
  bottom: 10px;
  right: 20px;
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.6);
  font-weight: 400;
`;

export const MainContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 25px;
  padding: 30px;
  max-width: 1600px;
  margin: 0 auto;

  @media (min-width: 768px) {
    flex-direction: row;
  }
`;

export const LeftPanel = styled.div`
  flex: 0 0 320px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  padding: 25px;
`;

export const CenterPanel = styled.div`
  flex: 1;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  padding: 25px;
`;

export const RightPanel = styled.div`
  flex: 0 0 420px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  padding: 25px;

  h3 {
    margin-top: 0;
    color: #333;
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 20px;
  }
`;

export const TabContainer = styled.div`
  margin-bottom: 25px;
`;

export const TabList = styled.div`
  display: flex;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 4px;
  margin-bottom: 20px;
  gap: 4px;
`;

export const Tab = styled.button<{ active: boolean }>`
  flex: 1;
  padding: 12px 16px;
  border: none;
  background: ${props => props.active 
    ? 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' 
    : 'rgba(255, 255, 255, 0.2)'};
  color: ${props => props.active ? 'white' : '#333'};
  cursor: pointer;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  box-shadow: ${props => props.active ? '0 4px 15px rgba(102, 126, 234, 0.3)' : 'none'};
  
  &:hover {
    background: ${props => props.active 
      ? 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' 
      : 'rgba(255, 255, 255, 0.3)'};
    transform: ${props => props.active ? 'translateY(-1px)' : 'none'};
    box-shadow: ${props => props.active ? '0 6px 20px rgba(102, 126, 234, 0.4)' : '0 2px 8px rgba(0,0,0,0.1)'};
  }
`;

export const TabContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 18px;
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export const Label = styled.label`
  font-weight: 600;
  color: #333;
  font-size: 0.9rem;
`;

export const Input = styled.input`
  padding: 12px 16px;
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  font-size: 14px;
  background: rgba(255, 255, 255, 0.9);
  transition: all 0.3s ease;
  
  &:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    background: white;
  }

  &::placeholder {
    color: rgba(0, 0, 0, 0.4);
  }
`;

export const Select = styled.select`
  padding: 12px 16px;
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  font-size: 14px;
  background: rgba(255, 255, 255, 0.9);
  transition: all 0.3s ease;
  
  &:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    background: white;
  }
`;

export const Button = styled.button<{ variant?: 'primary' | 'secondary' | 'danger' }>`
  padding: 12px 24px;
  border: none;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  
  ${props => {
    switch (props.variant) {
      case 'primary':
        return `
          background: linear-gradient(135deg, #4caf50 0%, #45a049 100%);
          color: white;
          box-shadow: 0 4px 15px rgba(76, 175, 80, 0.3);
          &:hover:not(:disabled) {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(76, 175, 80, 0.4);
          }
        `;
      case 'danger':
        return `
          background: linear-gradient(135deg, #f44336 0%, #da190b 100%);
          color: white;
          box-shadow: 0 4px 15px rgba(244, 67, 54, 0.3);
          &:hover:not(:disabled) {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(244, 67, 54, 0.4);
          }
        `;
      case 'secondary':
      default:
        return `
          background: linear-gradient(135deg, #2196f3 0%, #1976d2 100%);
          color: white;
          box-shadow: 0 4px 15px rgba(33, 150, 243, 0.3);
          &:hover:not(:disabled) {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(33, 150, 243, 0.4);
          }
        `;
    }
  }}
  
  &:disabled {
    background: #ccc !important;
    cursor: not-allowed !important;
    transform: none !important;
    box-shadow: none !important;
    opacity: 0.7;
  }

  &:active:not(:disabled) {
    transform: translateY(0);
  }
`;

export const MarchesContainer = styled.div`
  display: flex;
  gap: 20px;
  margin-bottom: 25px;
  overflow-x: auto;
  padding: 10px 0 15px 0;
  
  /* Custom scrollbar styling */
  &::-webkit-scrollbar {
    height: 10px;
  }
  
  &::-webkit-scrollbar-track {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 5px;
  }
  
  &::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.3);
    border-radius: 5px;
  }
  
  &::-webkit-scrollbar-thumb:hover {
    background: rgba(255, 255, 255, 0.5);
  }
`;

export const MarchCard = styled.div`
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 16px;
  padding: 20px;
  width: 320px;
  flex-shrink: 0;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
  }
`;

export const MarchHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 18px;
`;

export const MarchTitle = styled.h3<{ isFriendly: boolean }>`
  margin: 0;
  color: ${props => props.isFriendly ? '#4caf50' : '#f44336'};
  font-size: 1.2rem;
  font-weight: 700;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
  margin-bottom: 25px;
`;

export const ResultsArea = styled.div`
  background: rgba(248, 249, 250, 0.9);
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  padding: 20px;
  font-family: 'JetBrains Mono', 'Fira Code', Consolas, monospace;
  font-size: 13px;
  white-space: pre-wrap;
  max-height: 450px;
  overflow-y: auto;
  line-height: 1.5;
  color: #333;

  /* Custom scrollbar for results */
  &::-webkit-scrollbar {
    width: 8px;
  }
  
  &::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0.05);
    border-radius: 4px;
  }
  
  &::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.2);
    border-radius: 4px;
  }
  
  &::-webkit-scrollbar-thumb:hover {
    background: rgba(0, 0, 0, 0.3);
  }
`;

export const LoadingSpinner = styled.div`
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-top: 3px solid #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  
  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
`;

export const ModernCard = styled.div`
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 20px;
  transition: all 0.3s ease;

  &:hover {
    background: rgba(255, 255, 255, 0.15);
    transform: translateY(-2px);
  }
`;