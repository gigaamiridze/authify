import { Link } from 'react-router-dom';
import { Flex, Typography } from 'antd';
import { Route, Color } from '../constants';
import { authify, authifyWhite } from '../assets/images';

const { Title } = Typography;

function HeaderLogo({ isHomePage }: { isHomePage: boolean }) {
  const content = (
    <Flex justify='space-between' align='center' gap='small'>
      <img
        src={isHomePage ? authify : authifyWhite}
        width={36}
        height={36}
        alt='Authify Logo'
      />
      <Title
        style={{
          fontSize: 22,
          fontWeight: 'bold',
          color: isHomePage ? Color.BLACK : Color.WHITE,
        }}
      >
        Authify
      </Title>
    </Flex>
  );

  return isHomePage ? content : <Link to={Route.HOME}>{content}</Link>;
}

export default HeaderLogo;