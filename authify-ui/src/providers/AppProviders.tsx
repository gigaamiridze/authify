import type { PropsWithChildren } from 'react';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { ConfigProvider } from 'antd';
import MessageProvider from './MessageProvider.tsx';
import AuthProvider from './AuthProvider.tsx';
import { router } from '../router.tsx';
import { theme } from '../styles';

function AppProviders({ children }: PropsWithChildren) {
  return (
    <ThemeProvider theme={theme}>
      <ConfigProvider
        theme={{
          token: {
            fontFamily: 'Outfit',
          }
        }}
      >
        <AuthProvider>
          <MessageProvider>
            <RouterProvider router={router} />
            {children}
          </MessageProvider>
        </AuthProvider>
      </ConfigProvider>
    </ThemeProvider>
  );
}

export default AppProviders;