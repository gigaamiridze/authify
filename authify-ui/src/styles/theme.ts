export const theme = {
  colors: {
    black: '#000000',
    white: '#FFFFFF',
    whitesmoke: '#F5F5F5',
    veryLightGray: '#EDEDED',
    lightGray: '#C1C1C1',
    gray: '#8A8A8A',
    darkGray: '#5E5E5E',
    brightBlue: '#0082E4',
    purple: '#6A5AF9',
  },
  fonts: {
    primary: '"Outfit", sans-serif',
  },
  fontSizes: {
    small: '14px',
    regular: '16px',
    medium: '18px',
    large: '22px',
    xLarge: '28px',
    xxLarge: '36px',
  },
  fontWeights: {
    thin: 100,
    extraLight: 200,
    light: 300,
    regular: 400,
    medium: 500,
    semiBold: 600,
    bold: 700,
    extraBold: 800,
    black: 900,
  },
  borderRadius: {
    small: '2px',
    regular: '5px',
    medium: '8px',
    large: '16px',
    xLarge: '20px',
    xxLarge: '26px',
    xxxLarge: '32px',
    circle: '50%',
  },
  animations: {
    easeInOut: 'all 0.25s ease-in-out',
    easeInfinite: '5s ease infinite',
    linearInfinite: '1s linear infinite',
  },
};

export type AppTheme = typeof theme;