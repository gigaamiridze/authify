import type { PropsWithChildren } from 'react';
import { message } from 'antd';
import { MessageContext } from '../contexts';

const MessageProvider = ({ children }: PropsWithChildren) => {
  const [messageApi, contextHolder] = message.useMessage();

  return (
    <MessageContext.Provider value={{ message: messageApi }}>
      {contextHolder}
      {children}
    </MessageContext.Provider>
  );
};

export default MessageProvider;