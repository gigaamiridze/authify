import { useContext } from 'react';
import { AuthContext } from '../contexts';

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('AuthContext not found. Make sure to wrap your application with AuthProvider');
  }
  return context;
};