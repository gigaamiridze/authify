import { useNavigate, useLocation } from 'react-router-dom';
import { Flex, Skeleton } from 'antd';
import { AuthifyButton } from '../styles/components';
import { Route } from '../constants';
import { useAuth } from '../hooks';
import DropdownMenu from './DropdownMenu';
import HeaderLogo from './HeaderLogo';
import ArrowRight from './ArrowRight';

function Header() {
  const { isAuthenticated, isLoadingAuth } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const isHomePage = location.pathname === Route.HOME;

  return (
    <Flex
      justify='space-between'
      align='center'
      style={{
        backgroundColor: 'transparent',
        paddingTop: 25,
        paddingBottom: 25,
        paddingLeft: 50,
        paddingRight: 50,
      }}
    >
      <HeaderLogo isHomePage={isHomePage} />
      {isHomePage && (
        isLoadingAuth ? (
          <Skeleton.Button active size='large' shape='circle' style={{ width: 50, height: 50 }} />
        ) : (
          isAuthenticated ? (
            <DropdownMenu />
          ) : (
            <AuthifyButton
              size='large'
              color='default'
              variant='outlined'
              icon={<ArrowRight />}
              iconPlacement='end'
              onClick={() => navigate(Route.LOGIN)}
            >
              Login
            </AuthifyButton>
          )
        )
      )}
    </Flex>
  );
}

export default Header;