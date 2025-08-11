declare module 'react-plotly.js' {
  import { Component } from 'react';

  export interface PlotParams {
    data: any[];
    layout?: any;
    config?: any;
    style?: React.CSSProperties;
    className?: string;
    useResizeHandler?: boolean;
    onInitialized?: (figure: any, layout: any) => void;
    onUpdate?: (figure: any, layout: any) => void;
    onPurge?: (figure: any, layout: any) => void;
    onError?: (err: Error) => void;
  }

  export default class Plot extends Component<PlotParams> {}
}
