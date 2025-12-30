import type { ReactNode } from 'react';
import { Card, Typography } from 'antd';
import { Color } from '../constants';

const { Title } = Typography;

interface FormCardProps {
  title: string;
  children: ReactNode;
  width?: number | string;
}

function FormCard(props: FormCardProps) {
  const { title, children, width = 460 } = props;

  return (
    <Card
      title={<Title level={4} style={{ textAlign: 'center', fontSize: 28 }}>{title}</Title>}
      variant='borderless'
      style={{ width, borderRadius: 20, backgroundColor: Color.WHITE, margin: 'auto' }}
      styles={{
        header: {
          borderBottom: 'none',
          paddingTop: 25,
        }
      }}
    >
      {children}
    </Card>
  );
}

export default FormCard;