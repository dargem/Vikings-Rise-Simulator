import React from 'react';
import Plot from 'react-plotly.js';
import {
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalTitle,
  CloseButton,
  PlotContainer
} from '../styled';

interface PlotModalProps {
  isOpen: boolean;
  onClose: () => void;
  fightResults: number[];
}

export const PlotModal: React.FC<PlotModalProps> = ({ isOpen, onClose, fightResults }) => {
  if (!isOpen) return null;

  const data = [{
    x: fightResults,
    type: 'histogram' as const,
    marker: {
      color: 'rgba(100, 149, 237, 0.7)'
    }
  }];

  const layout = {
    title: 'Fight Results Histogram',
    xaxis: { title: 'Troops Remaining' },
    yaxis: { title: 'Frequency' },
    width: 800,
    height: 500,
    margin: { t: 50, r: 50, b: 50, l: 50 }
  };

  const handleOverlayClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <ModalOverlay onClick={handleOverlayClick}>
      <ModalContent>
        <PlotContainer style={{ position: 'relative' }}>
          <CloseButton 
            onClick={onClose}
            style={{
              position: 'absolute',
              top: '10px',
              left: '10px',
              zIndex: 1000,
              background: 'rgba(255, 255, 255, 0.9)',
              borderRadius: '50%',
              width: '30px',
              height: '30px',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontSize: '18px',
              fontWeight: 'bold'
            }}
          >
            ×
          </CloseButton>
          <Plot
            data={data}
            layout={layout}
            config={{
              displayModeBar: true,
              displaylogo: false,
              modeBarButtonsToRemove: ['pan2d', 'lasso2d']
            }}
          />
        </PlotContainer>
      </ModalContent>
    </ModalOverlay>
  );
};