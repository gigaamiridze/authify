import { MailOutlined, LogoutOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { Dropdown, Button, type MenuProps } from 'antd';
import { getAvatarLetter, getErrorMessage } from '../utils';
import { Route, Color, Permission } from '../constants';
import { useMessage, useAuth } from '../hooks';

function DropdownMenu() {
  const { user, hasAllPermissions, logout, sendVerifyOtp } = useAuth();
  const { message } = useMessage();
  const navigate = useNavigate();
  const hasVerifyEmailPermissions = hasAllPermissions(Permission.SYSTEM_USER_SEND_VERIFY_OTP, Permission.SYSTEM_USER_VERIFY_EMAIL);

  const onVerifyEmail = async () => {
    try {
      await sendVerifyOtp();
      message.success('OTP has been sent to your email');
      navigate(Route.VERIFY_EMAIL);
    } catch (error) {
      message.error(getErrorMessage(error));
    }
  };

  const onLogout = async () => {
    try {
      await logout();
      navigate(Route.HOME);
    } catch (error) {
      message.error(getErrorMessage(error));
    }
  };

  const items: MenuProps['items'] = [];
  if (hasVerifyEmailPermissions && !user?.emailVerified) {
    items.push({
      key: 'verify-email',
      label: 'Verify email',
      icon: <MailOutlined />,
      onClick: onVerifyEmail,
    });
  }

  items.push({
    key: 'logout',
    label: 'Logout',
    icon: <LogoutOutlined />,
    onClick: onLogout,
  });

  return (
    <Dropdown menu={{ items }} trigger={['click']}>
      <Button
        shape='circle'
        size='large'
        style={{
          color: Color.WHITE,
          backgroundColor: Color.BLACK,
          width: 50,
          height: 50,
        }}
      >
        {getAvatarLetter(user?.firstName)}
      </Button>
    </Dropdown>
  );
}

export default DropdownMenu;