import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Flex, Card, Form, Steps, Space, Input, Button, Typography } from 'antd';
import { MailOutlined, LockOutlined } from '@ant-design/icons';
import { useAuth, useMessage } from '../hooks';
import { Color, Route } from '../constants';
import { getErrorMessage } from '../utils';

const { Title, Text } = Typography;

interface ResetPasswordFormData {
  email: string;
  otp: string;
  newPassword: string;
}

function ResetPasswordPage() {
  const [form] = Form.useForm<ResetPasswordFormData>();
  const [current, setCurrent] = useState(0);
  const [loading, setLoading] = useState(false);
  const { sendResetOtp, resetPassword } = useAuth();
  const { message } = useMessage();
  const navigate = useNavigate();
  const otpLength = 6;

  const steps = [
    {
      key: 'email',
      title: <span style={{ color: current === 0 ? Color.WHITE : Color.LIGHT_GRAY }}>Email</span>,
      text: 'Enter your registered email address',
      content: (
        <Form.Item
          name='email'
          style={{ marginBottom: 0 }}
          rules={[
            { required: true, message: 'Email address is required' },
            { type: 'email', message: 'Please enter a valid email address' }
          ]}
        >
          <Space.Compact size='large' style={{ width: '100%' }}>
            <Space.Addon
              style={{
                width: 50,
                backgroundColor: Color.VERY_LIGHT_GRAY,
                border: 'none',
                paddingLeft: 20,
                borderTopLeftRadius: 25,
                borderBottomLeftRadius: 25
              }}
            >
              <MailOutlined />
            </Space.Addon>
            <Input
              placeholder='Enter email address'
              style={{
                width: '100%',
                height: 50,
                fontSize: 16,
                fontWeight: 500,
                color: Color.BLACK,
                backgroundColor: Color.VERY_LIGHT_GRAY,
                border: 'none',
                borderTopRightRadius: 25,
                borderBottomRightRadius: 25,
              }}
            />
          </Space.Compact>
        </Form.Item>
      ),
    },
    {
      key: 'otp',
      title: <span style={{ color: current === 1 ? Color.WHITE : Color.LIGHT_GRAY }}>OTP</span>,
      text: `Enter the ${otpLength}-digit code sent to your email`,
      content: (
        <Form.Item
          name='otp'
          style={{ marginBottom: 0 }}
          rules={[
            { required: true, message: 'OTP is required' },
            { len: 6, message: `Please enter a ${otpLength}-digit OTP` }
          ]}
        >
          <Input.OTP
            length={otpLength}
            size='large'
            separator=''
          />
        </Form.Item>
      ),
    },
    {
      key: 'newPassword',
      title: <span style={{ color: current === 2 ? Color.WHITE : Color.LIGHT_GRAY }}>New Password</span>,
      text: 'Enter your new password',
      content: (
        <Form.Item
          name='newPassword'
          style={{ marginBottom: 0 }}
          rules={[
            { required: true, message: 'Password is required' },
            { min: 8, message: 'Password must be at least 8 characters' },
            { max: 50, message: 'Password cannot exceed 50 characters' }
          ]}
        >
          <Space.Compact size='large' style={{ width: '100%' }}>
            <Space.Addon
              style={{
                width: 50,
                backgroundColor: Color.VERY_LIGHT_GRAY,
                border: 'none',
                paddingLeft: 20,
                borderTopLeftRadius: 25,
                borderBottomLeftRadius: 25
              }}
            >
              <LockOutlined />
            </Space.Addon>
            <Input.Password
              placeholder='Enter password'
              style={{
                width: '100%',
                height: 50,
                fontSize: 16,
                fontWeight: 500,
                color: Color.BLACK,
                backgroundColor: Color.VERY_LIGHT_GRAY,
                border: 'none',
                paddingRight: 20,
                borderTopRightRadius: 25,
                borderBottomRightRadius: 25,
              }}
            />
          </Space.Compact>
        </Form.Item>
      ),
    },
  ];

  const items = steps.map((item) => ({ key: item.key, title: item.title }));

  const next = async () => {
    const values = await form.validateFields();

    const isEmailStep = current === 0;
    if (isEmailStep) {
      try {
        setLoading(true);
        await sendResetOtp({ email: values.email });
        message.success('OTP has been sent to your email');
      } catch (error) {
        message.error(getErrorMessage(error));
        return;
      } finally {
        setLoading(false);
      }
    }

    setCurrent(prev => prev + 1);
  };

  const prev = () => {
    setCurrent(prev => prev - 1);
  };

  const onSubmit = async () => {
    await form.validateFields();
    const values = await form.getFieldsValue(true);

    try {
      setLoading(true);
      await resetPassword(values);
      message.success('Password reset successfully!');
      navigate(Route.LOGIN);
    } catch (error) {
      message.error(getErrorMessage(error));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Flex
      vertical
      justify='center'
      align='flex-start'
      gap='middle'
      style={{ margin: 'auto' }}
    >
      <Steps current={current} items={items} style={{ width: '100%' }} />
      <Card
        title={<Title level={4} style={{ textAlign: 'center', fontSize: 26 }}>Reset Password</Title>}
        variant='borderless'
        style={{
          width: 460,
          height: 238,
          borderRadius: 20,
          backgroundColor: Color.WHITE,
          margin: 'auto',
        }}
        styles={{
          header: {
            borderBottom: 'none',
            paddingTop: 45,
          },
          body: {
            padding: '7px 45px 45px',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            rowGap: 25,
          }
        }}
      >
        <Text style={{ fontSize: 16, fontWeight: 500 }}>
          {steps[current].text}
        </Text>
        <Form
          form={form}
          name='reset-password'
          layout='vertical'
          autoComplete='off'
          style={{ width: '100%' }}
        >
          {steps[current].content}
        </Form>
      </Card>
      <Flex justify='space-between' align='center' gap='middle'>
        {current < steps.length - 1 && (
          <Button type='primary' loading={loading} disabled={loading} onClick={next}>
            Next
          </Button>
        )}
        {current === steps.length - 1 && (
          <Button type='primary' loading={loading} disabled={loading} onClick={onSubmit}>
            Submit
          </Button>
        )}
        {current > 0 && (
          <Button onClick={prev}>Prev</Button>
        )}
      </Flex>
    </Flex>
  );
}

export default ResetPasswordPage;