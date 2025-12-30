import { useContext } from 'react';
import { MessageContext } from '../contexts';

export const useMessage = () => {
  const context = useContext(MessageContext);
  if (!context) {
    throw new Error('MessageContext not found. Make sure to wrap your application with MessageProvider');
  }
  return context;
};