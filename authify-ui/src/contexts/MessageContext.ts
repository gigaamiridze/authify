import { createContext } from 'react';
import type { MessageInstance } from 'antd/es/message/interface';

interface IMessageContext {
  message: MessageInstance;
}

export const MessageContext = createContext<IMessageContext | null>(null);