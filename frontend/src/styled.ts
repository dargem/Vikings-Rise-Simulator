import styled from 'styled-components';

export const AppContainer = styled.div`
  min-height: 100vh;
  background-color: #f5f5f5;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
`;

export const Header = styled.div`
  background-color: #28359b;
  color: white;
  padding: 20px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
`;

export const Title = styled.h1`
  margin: 0;
  font-size: 2rem;
  font-weight: bold;
`;

export const MainContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;

  @media (min-width: 768px) {
    flex-direction: row;
  }
`;

export const LeftPanel = styled.div`
  flex: 0 0 300px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
`;

export const CenterPanel = styled.div`
  flex: 1;
  background: #e8eaf6;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
`;

export const RightPanel = styled.div`
  flex: 0 0 400px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
`;

export const TabContainer = styled.div`
  margin-bottom: 20px;
`;

export const TabList = styled.div`
  display: flex;
  border-bottom: 2px solid #e0e0e0;
  margin-bottom: 15px;
`;

export const Tab = styled.button<{ active: boolean }>`
  padding: 10px 15px;
  border: none;
  background: ${props => props.active ? '#28359b' : 'transparent'};
  color: ${props => props.active ? 'white' : '#666'};
  cursor: pointer;
  border-radius: 4px 4px 0 0;
  font-weight: ${props => props.active ? 'bold' : 'normal'};
  
  &:hover {
    background: ${props => props.active ? '#28359b' : '#f0f0f0'};
  }
`;

export const TabContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

export const Label = styled.label`
  font-weight: 500;
  color: #333;
`;

export const Input = styled.input`
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  
  &:focus {
    outline: none;
    border-color: #28359b;
    box-shadow: 0 0 0 2px rgba(40, 53, 155, 0.1);
  }
`;

export const Select = styled.select`
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  background: white;
  
  &:focus {
    outline: none;
    border-color: #28359b;
    box-shadow: 0 0 0 2px rgba(40, 53, 155, 0.1);
  }
`;

export const Button = styled.button<{ variant?: 'primary' | 'secondary' | 'danger' }>`
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
  
  ${props => {
    switch (props.variant) {
      case 'primary':
        return `
          background-color: #4caf50;
          color: white;
          &:hover {
            background-color: #45a049;
          }
        `;
      case 'danger':
        return `
          background-color: #f44336;
          color: white;
          &:hover {
            background-color: #da190b;
          }
        `;
      case 'secondary':
      default:
        return `
          background-color: #2196f3;
          color: white;
          &:hover {
            background-color: #1976d2;
          }
        `;
    }
  }}
  
  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
`;

export const MarchesContainer = styled.div`
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  overflow-x: auto;
  padding-bottom: 10px;
  
  /* Custom scrollbar styling */
  &::-webkit-scrollbar {
    height: 8px;
  }
  
  &::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 4px;
  }
  
  &::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 4px;
  }
  
  &::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
  }
`;

export const MarchCard = styled.div`
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  width: 300px;
  flex-shrink: 0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
`;

export const MarchHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
`;

export const MarchTitle = styled.h3<{ isFriendly: boolean }>`
  margin: 0;
  color: ${props => props.isFriendly ? '#4caf50' : '#f44336'};
  font-size: 1.1rem;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-bottom: 20px;
`;

export const ResultsArea = styled.div`
  background: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 15px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  white-space: pre-wrap;
  max-height: 400px;
  overflow-y: auto;
  line-height: 1.4;
`;

export const LoadingSpinner = styled.div`
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 3px solid #f3f3f3;
  border-top: 3px solid #28359b;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  
  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
`;
