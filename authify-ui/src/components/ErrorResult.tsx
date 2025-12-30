import type { CSSProperties } from 'react';
import { useNavigate } from 'react-router-dom';
import { Flex, Result } from 'antd';
import { AuthifyButton } from '../styles/components';
import { Route, Color } from '../constants';

interface ErrorResultProps {
  status: '403' | '404' | '500';
  title: string;
  subTitle: string;
}

function ErrorResult(props: ErrorResultProps) {
  const { status, title, subTitle } = props;
  const navigate = useNavigate();

  const titleStyles: CSSProperties = {
    color: Color.WHITE,
    fontSize: 28,
    fontWeight: 500,
  };

  const subTitleStyles: CSSProperties = {
    color: Color.WHITE,
    fontSize: 16,
    fontWeight: 500,
  };

  return (
    <Flex justify='center' align='center' style={{ margin: 'auto' }}>
      <Result
        status={status}
        title={<h3 style={titleStyles}>{title}</h3>}
        subTitle={<p style={subTitleStyles}>{subTitle}</p>}
        extra={
          <AuthifyButton
            size='large'
            color='default'
            variant='outlined'
            style={{ width: 160 }}
            onClick={() => navigate(Route.HOME)}
          >
            Back Home
          </AuthifyButton>
        }
      />
    </Flex>
  );
}

export default ErrorResult;