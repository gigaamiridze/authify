import 'styled-components';
import type { AppTheme } from '../styles';

declare module 'styled-components' {
  export interface DefaultTheme extends AppTheme {}
}