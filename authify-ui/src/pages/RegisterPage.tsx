import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Form, Typography } from 'antd';
import { FormCard, FormInput, FormButton } from '../components';
import { useMessage, useAuth } from '../hooks';
import { Route, Color } from '../constants';
import { getErrorMessage } from '../utils';

const { Paragraph } = Typography;

interface RegisterFormData {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

function RegisterPage() {
  const [loading, setLoading] = useState(false);
  const { message } = useMessage();
  const { register } = useAuth();
  const navigate = useNavigate();

  const onFinish = async (data: RegisterFormData) => {
    try {
      setLoading(true);
      await register(data);
      message.success('User registered successfully');
      await new Promise(res => setTimeout(res, 1000));
      navigate(Route.LOGIN);
    } catch (error) {
      message.error(getErrorMessage(error));
    } finally {
      setLoading(false);
    }
  };

  return (
    <FormCard title='Create Account'>
      <Form name='register' layout='vertical' autoComplete='off' onFinish={onFinish}>
        <FormInput
          name='firstName'
          label='First Name'
          placeholder='Enter first name'
          rules={[
            { required: true, message: 'First name is required' },
            { max: 50, message: 'First name cannot exceed 50 characters' }
          ]}
        />
        <FormInput
          name='lastName'
          label='Last Name'
          placeholder='Enter last name'
          rules={[
            { required: true, message: 'Last name is required' },
            { max: 50, message: 'Last name cannot exceed 50 characters' }
          ]}
        />
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
          title={loading ? 'Creating account...' : 'Register'}
          htmlType='submit'
          loading={loading}
          disabled={loading}
        />
        <Paragraph style={{ fontSize: 16, fontWeight: 500, textAlign: 'center' }}>
          Already have an account? <Link to={Route.LOGIN}>Login</Link>
        </Paragraph>
      </Form>
    </FormCard>
  );
}

export default RegisterPage;