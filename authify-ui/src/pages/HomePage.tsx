import { useNavigate } from 'react-router-dom';
import { Flex, Typography, Skeleton } from 'antd';
import { AuthifyButton } from '../styles/components';
import { Route, Color } from '../constants';
import { security } from '../assets/images';
import { useAuth } from '../hooks';

const { Title, Paragraph } = Typography;

function HomePage() {
  const { isLoadingAuth, isAuthenticated, user } = useAuth();
  const navigate = useNavigate();

  return (
    <Flex
      vertical
      justify='center'
      align='center'
      gap='middle'
      style={{ height: '100%' }}
    >
      {isLoadingAuth ? (
        <Flex vertical justify='space-between' align='center'>
          <Skeleton.Avatar active size={140} />
          <Skeleton.Input active style={{ width: 190, height: 22, marginTop: 20, marginBottom: 30 }} />
          <Skeleton.Input active style={{ width: 430, height: 35, marginBottom: 25 }} />
          <Skeleton active paragraph={false} style={{ width: 470 }} />
          <Skeleton active paragraph={false} style={{ width: 300, marginTop: 10 }} />
        </Flex>
      ) : (
        <>
          <img
            src={security}
            width={140}
            height={140}
            alt='Security'
          />
          <Title level={3} style={{ fontSize: 22 }}>
            Hey {isAuthenticated && user?.firstName ? user.firstName : 'Developer'} 👋
          </Title>
          <Title
            level={2}
            style={{ fontSize: 40, fontWeight: 'bold' }}
          >
            Welcome to our product
          </Title>
          <Paragraph style={{ color: Color.DARK_GRAY, fontSize: 20, textAlign: 'center' }}>
            Let's start with a quick product tour and you can setup<br /> the authentication in on time!
          </Paragraph>
          {!isAuthenticated && (
            <AuthifyButton
              size='large'
              color='default'
              variant='outlined'
              style={{ width: 160 }}
              onClick={() => navigate(Route.REGISTER)}
            >
              Get Started
            </AuthifyButton>
          )}
        </>
      )}
    </Flex>
  );
}

export default HomePage;