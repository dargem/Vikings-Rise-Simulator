import styled, { createGlobalStyle } from 'styled-components';

export const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  html, body {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100%;
  }

  #root {
    margin: 0;
    padding: 0;
    width: 100%;
    min-height: 100vh;
  }
`;

export const AppContainer = styled.div`
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  position: relative;
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
  padding: 15px 20px 15px 20px; /* Added more bottom padding (25px) */
  text-align: center;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const HeaderLinks = styled.div`
  position: absolute;
  right: 20px;
  display: flex;
  gap: 10px;
  align-items: center;
`;

export const HeaderLink = styled.a`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  text-decoration: none;
  color: white;
  transition: all 0.3s ease;
  border: 1px solid rgba(255, 255, 255, 0.2);
  
  &:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  }
  
  svg {
    width: 20px;
    height: 20px;
    fill: currentColor;
  }
`;

export const Footer = styled.div`
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  text-align: center;
  margin-top: auto;
`;

export const FooterContent = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
  
  @media (min-width: 768px) {
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }
`;

export const FooterText = styled.div`
  font-size: 0.9rem;
  
  a {
    color: rgba(255, 255, 255, 0.9);
    text-decoration: none;
    
    &:hover {
      text-decoration: underline;
    }
  }
`;

export const FooterLinks = styled.div`
  display: flex;
  gap: 15px;
  justify-content: center;
  
  @media (min-width: 768px) {
    justify-content: flex-end;
  }
  
  a {
    color: rgba(255, 255, 255, 0.7);
    text-decoration: none;
    font-size: 0.85rem;
    
    &:hover {
      color: rgba(255, 255, 255, 0.9);
      text-decoration: underline;
    }
  }
`;

export const Title = styled.h1`
  margin: 0;
  font-size: 1.4rem; /* Reduced from 3rem */
  font-weight: 700;
  color: white; /* Simple white color - change this to any color you want */
  letter-spacing: -0.02em;
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
  gap: 15px; /* Increased from 10px to create more space between panels */
  padding: 2.5px 5px; /* Reduced horizontal padding from 5px to 2.5px (half) */
  margin: 20px auto 0 auto; /* Added 20px top margin to create space below header */
  max-width: 1200px;
  min-height: 0;

  @media (min-width: 768px) {
    flex-direction: row;
  }
`;

export const LeftPanel = styled.div`
  flex: 0 0 240px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 12px; /* Reduced from 20px */
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1); /* Reduced from 15px 35px */
  padding: 12px; /* Reduced from 22px */
  display: flex;
  flex-direction: column;
  gap: 8px; /* Reset to normal gap */
`;

export const CenterPanel = styled.div`
  flex: 1;
  min-width: 0;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px; /* Reduced from 20px */
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1); /* Reduced from 15px 35px */
  padding: 12px; /* Reduced from 22px */
`;

export const RightPanel = styled.div`
  flex: 0 0 400px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 12px; /* Reduced from 20px */
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1); /* Reduced from 15px 35px */
  padding: 12px; /* Reduced from 22px */
`;

export const ResultsSection = styled.div`
  background: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 20px 20px 20px 20px; /* Increased top padding to match bottom for balanced spacing */
  border: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  min-height: 0; /* Allow flex item to shrink below content size */
`;

export const ResultsTitle = styled.h3`
  margin: 0 0 15px 0; /* Added bottom margin to create space between title and results area */
  color: #333;
  font-size: 1.1rem;
  font-weight: 600;
`;

export const SectionTitle = styled.h2`
  margin: 0 0 10px 0; /* Added bottom margin for spacing between title and content */
  color: #333;
  font-size: 1.3rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  gap: 10px;
  position: relative;
  top: 0px; /* Reduced from -20px to prevent overlap */
`;

export const MarchesGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 10px;
`;

export const MarchCard = styled.div`
  background: rgba(255, 255, 255, 0.9);
  border-radius: 8px; /* Reduced from 15px */
  padding: 10px;
  border: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); /* Reduced shadow */
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  gap: 4px;

  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08); /* Reduced shadow */
  }
`;

export const MarchHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px; /* Decreased from 15px for tighter sections within panel */
`;

export const MarchTitle = styled.h3<{ isFriendly: boolean }>`
  margin: 0;
  color: ${props => props.isFriendly ? '#22c55e' : '#ef4444'};
  font-size: 0.8rem; /* Reduced from 1.1rem */
  font-weight: 600;
`;

export const TabContainer = styled.div`
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px; /* Reduced from 15px */
  padding: 8px; /* Reduced from 18px */
  height: 185px;
  display: flex;
  flex-direction: column;
`;

export const TabList = styled.div`
  display: flex;
  gap: 4px;
  margin-bottom: 20px;
  padding: 4px;
  background: rgba(0, 0, 0, 0.04);
  border-radius: 12px;
`;

export const Tab = styled.button<{ active: boolean }>`
  flex: 1;
  padding: 6px 8px; /* Reduced from 12px 16px */
  border: none;
  border-radius: 4px; /* Reduced from 8px */
  background: ${props => props.active ? 'white' : 'transparent'};
  color: ${props => props.active ? '#667eea' : '#666'};
  font-weight: ${props => props.active ? '600' : '500'};
  font-size: 0.75rem; /* Reduced from 0.9rem */
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    background: ${props => props.active ? 'white' : 'rgba(255, 255, 255, 0.5)'};
    transform: ${props => props.active ? 'translateY(-1px)' : 'none'};
    box-shadow: ${props => props.active ? '0 6px 20px rgba(102, 126, 234, 0.4)' : '0 2px 8px rgba(0,0,0,0.1)'};
  }
`;

export const TabContent = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1; /* Take remaining space after TabList */
  min-height: 0; /* Allow content to shrink */
  position: relative; /* For absolute positioning of button */
`;

export const FormArea = styled.div`
  height: 80px; /* Fixed height for form content area - increased to accommodate two horizontal inputs */
  display: flex;
  flex-direction: column;
  gap: 8px; /* Space between form elements */
  overflow: hidden; /* Hide any overflow */
`;

export const ButtonArea = styled.div`
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 32px; /* Fixed height for button area */
  display: flex;
  align-items: center;
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6px; /* Increased from 2px to add more vertical padding between labels and inputs */
`;

export const FormRow = styled.div`
  display: flex !important; /* Important to override TabContent column direction */
  flex-direction: row;
  gap: 8px; /* Space between form groups when side by side */
  align-items: center; /* Center align for inline layout */
  width: 100%;
`;

export const FormGroupInline = styled.div`
  display: flex;
  align-items: center;
  gap: 8px; /* Space between label and input */
  width: 100%; /* Take full width */
  
  /* Make the input take remaining space */
  input {
    flex: 1;
    min-width: 0; /* Allow input to shrink if needed */
  }
`;

export const Label = styled.label`
  font-weight: 600;
  color: #333;
  font-size: 0.75rem; /* Reduced from 0.9rem */
`;

interface InputProps {
  inputSize?: 'small' | 'medium' | 'large';
}

export const Input = styled.input<InputProps>`
  padding: ${props => {
    if (props.inputSize === 'small') return '2px 4px'; /* Reduced from 6px 6px */
    if (props.inputSize === 'large') return '8px 12px'; /* Reduced from 16px 20px */
    return '4px 8px'; /* Reduced from 12px 16px */
  }};
  height: ${props => {
    if (props.inputSize === 'small') return '20px'; /* Reduced from 32px */
    if (props.inputSize === 'large') return '32px'; /* Reduced from 52px */
    return '24px'; /* Reduced from 42px */
  }};
  width: ${props => {
    if (props.inputSize === 'small') return '50px'; /* Reduced from 80px */
    if (props.inputSize === 'large') return '200px'; /* Reduced from 300px */
    return '100%'; // Changed from 42px to 100% for full width
  }};
  border: 1px solid rgba(0, 0, 0, 0.1); /* Reduced from 2px */
  border-radius: ${props => {
    if (props.inputSize === 'small') return '4px'; /* Reduced from 8px */
    if (props.inputSize === 'large') return '8px'; /* Reduced from 16px */
    return '6px'; /* Reduced from 12px */
  }};
  font-size: ${props => {
    if (props.inputSize === 'small') return '9px'; /* Reduced from 12px */
    if (props.inputSize === 'large') return '11px'; /* Reduced from 16px */
    return '10px'; /* Reduced from 14px */
  }};
  background: rgba(255, 255, 255, 0.9);
  transition: all 0.3s ease;
  box-sizing: border-box;
  
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
  padding: 4px 8px; /* Reduced from 12px 16px */
  border: 1px solid rgba(0, 0, 0, 0.1); /* Reduced from 2px */
  border-radius: 6px; /* Reduced from 12px */
  font-size: 10px; /* Reduced from 14px */
  background: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  transition: all 0.3s ease;
  height: 24px; /* Added explicit height to match Input medium size */
  
  &:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.1); /* Reduced from 3px */
    background: white;
  }
`;

export const Button = styled.button<{ variant?: 'primary' | 'secondary' | 'danger' }>`
  padding: 6px 12px; /* Reduced from 12px 24px */
  border: none;
  border-radius: 6px; /* Reduced from 12px */
  font-weight: 600;
  font-size: 10px; /* Reduced from 14px */
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px; /* Reduced from 8px */
  
  ${props => {
    switch(props.variant) {
      case 'secondary':
        return `
          background: rgba(0, 0, 0, 0.05);
          color: #666;
          &:hover {
            background: rgba(0, 0, 0, 0.1);
            transform: translateY(-0.5px); /* Reduced from -1px for subtler movement */
          }
        `;
      case 'danger':
        return `
          background: #ef4444;
          color: white;
          &:hover {
            background: #dc2626;
            transform: translateY(-0.5px); /* Reduced from -1px for subtler movement */
            box-shadow: 0 6px 20px rgba(239, 68, 68, 0.4);
          }
        `;
      default:
        return `
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          color: white;
          &:hover {
            transform: translateY(-0.5px); /* Reduced from -1px for subtler movement */
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
          }
          &:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
          }
        `;
    }
  }}
`;

export const LoadingSpinner = styled.div`
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top: 2px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  
  @keyframes spin {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
  }
`;

export const ResultCard = styled.div`
  background: white;
  border-radius: 8px; /* Reduced from 12px */
  padding: 8px; /* Reduced from 16px */
  margin: 4px 0; /* Reduced from 8px 0 */
  border: 1px solid rgba(0, 0, 0, 0.1);
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05); /* Reduced shadow */
`;

export const ResultTitle = styled.h4`
  margin: 0 0 4px 0; /* Reduced from 8px */
  color: #333;
  font-size: 0.75rem; /* Reduced from 1rem */
  font-weight: 600;
`;

export const ResultValue = styled.div`
  font-size: 0.9rem; /* Reduced from 1.2rem */
  font-weight: 700;
  color: #667eea;
`;

export const ErrorMessage = styled.div`
  background: #fef2f2;
  border: 1px solid #fecaca;
  color: #dc2626;
  padding: 12px;
  border-radius: 8px;
  margin: 8px 0;
`;

// Additional styled components needed by App.tsx and App_clean.tsx
export const MarchesContainer = styled.div`
  display: flex; /* Changed back from grid to flex for side scrolling */
  gap: 8px; /* Reduced from 8px to half */
  margin-bottom: 0px;
  overflow-x: auto;
  padding: 1px 0 8px 0;
  width: 100%; /* Constrain to parent width */
  
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
    
    &:hover {
      background: rgba(255, 255, 255, 0.5);
    }
  }
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
  margin-bottom: 15px; /* Reduced from 25px for less spacing */
`;

export const ResultsArea = styled.div`
  background: rgba(248, 249, 250, 0.9);
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 8px; /* Reduced from 12px */
  padding: 6px; /* Reduced from 10px to match titled border spacing */
  margin: 0; /* Ensure no additional margin */
  font-family: 'JetBrains Mono', 'Fira Code', Consolas, monospace;
  font-size: 9px; /* Reduced from 13px */
  white-space: pre-wrap;
  overflow-y: auto;
  line-height: 1.3; /* Reduced from 1.5 */
  color: #333;
  flex: 1; /* Take remaining space in flex container */
  min-height: 0; /* Allow shrinking below content size */
  max-height: 305px; /* Maximum height before scrolling */

  /* Custom scrollbar for results */
  &::-webkit-scrollbar {
    width: 8px;
  }
  
  &::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0.05);
    border-radius: 4px;
  }
  
  &::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    
    &:hover {
      background: rgba(0, 0, 0, 0.2);
    }
  }
`;

// Modal components for plot display
export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
`;

export const ModalContent = styled.div`
  background: white;
  border-radius: 12px;
  padding: 20px;
  max-width: 90vw;
  max-height: 90vh;
  overflow: auto;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
  position: relative;
`;

export const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  padding-bottom: 15px;
`;

export const ModalTitle = styled.h2`
  margin: 0;
  color: #333;
  font-size: 1.5rem;
  font-weight: 700;
`;

export const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #666;
  padding: 5px;
  border-radius: 4px;
  
  &:hover {
    background: rgba(0, 0, 0, 0.1);
    color: #333;
  }
`;

export const PlotContainer = styled.div`
  width: 100%;
  min-height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const HintText = styled.p`
  font-size: 0.7rem;
  color: rgba(0, 0, 0, 0.6);
  margin: 8px 0;
  font-style: italic;
  text-align: center;
`;