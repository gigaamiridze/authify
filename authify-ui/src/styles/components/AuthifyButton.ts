import styled, { css } from 'styled-components';
import { Button } from 'antd';

const AuthifyButton = styled(Button)`
  ${({ theme }) => css`
    font-weight: ${theme.fontWeights.medium};
    border: 1px solid ${theme.colors.black};
    border-radius: ${theme.borderRadius.xLarge};
    transition: ${theme.animations.easeInOut};
  `}
  
  &:hover {
    ${({ theme }) => css`
      background-color: ${theme.colors.black} !important;
      color: ${theme.colors.white} !important;
      border-color: ${theme.colors.black} !important;
    `}
  }
`;

export default AuthifyButton;