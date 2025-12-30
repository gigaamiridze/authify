import { Outlet, useLocation } from 'react-router-dom';
import { Layout } from 'antd';
import { Route } from '../constants';
import { Header } from '../components';

function Root() {
  const location = useLocation();
  const isHomePage = location.pathname === Route.HOME;
  const background = isHomePage ? 'white' : 'linear-gradient(90deg, #6A5AF9, #8268F9)';

  return (
    <Layout style={{ height: '100vh', background }}>
      <Header />
      <Outlet />
    </Layout>
  );
}

export default Root;