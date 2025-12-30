import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Form, Typography } from 'antd';
import { FormCard, FormInput, FormButton } from '../components';
import { useMessage, useAuth } from '../hooks';
import { Route, Color } from '../constants';
import { getErrorMessage } from '../utils';

const { Paragraph } = Typography;

interface LoginFormData {
  email: string;
  password: string;
}

function LoginPage() {
  const [loading, setLoading] = useState(false);
  const { login, getProfileInfo } = useAuth();
  const { message } = useMessage();
  const navigate = useNavigate();

  const onFinish = async (data: LoginFormData) => {
    try {
      setLoading(true);
      await login(data);
      await getProfileInfo();
      message.success('Successfully logged in!');
      await new Promise(res => setTimeout(res, 1000));
      navigate(Route.HOME);
    } catch (error) {
      message.error(getErrorMessage(error));
    } finally {
      setLoading(false);
    }
  };

  return (
    <FormCard title='Login'>
      <Form name='login' layout='vertical' autoComplete='off' onFinish={onFinish}>
        <FormInput
          name='email'
          label='Email'
          placeholder='Enter email'
          rules={[
            { required: true, message: 'Email address is required' },
            { type: 'email', message: 'Please enter a valid email address' }
          ]}
        />
        <FormInput
          name='password'
          label='Password'
          placeholder='Enter password'
          type='password'
          rules={[
            { required: true, message: 'Password is required' },
            { min: 8, message: 'Password must be at least 8 characters' },
            { max: 50, message: 'Password cannot exceed 50 characters' }
          ]}
        />
        <Link to={Route.RESET_PASSWORD}>
          <Paragraph style={{ color: Color.BRIGHT_BLUE, fontSize: 16, fontWeight: 500 }}>Forgot password?</Paragraph>
        </Link>
        <FormButton
          title={loading ? 'Logging in...' : 'Login'}
          htmlType='submit'
          loading={loading}
          disabled={loading}
        />
        <Paragraph style={{ fontSize: 16, fontWeight: 500, textAlign: 'center' }}>
          Don't have an account? <Link to={Route.REGISTER}>Register</Link>
        </Paragraph>
      </Form>
    </FormCard>
  );
}

export default LoginPage;