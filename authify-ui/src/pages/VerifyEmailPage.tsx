import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Card, Flex, Skeleton, Input, Button, Typography } from 'antd';
import { useOtpConfig, useAuth, useMessage } from '../hooks';
import { getErrorMessage, isBlank } from '../utils';
import { Color, Route } from '../constants';

const { Title, Text } = Typography;

function VerifyEmailPage() {
  const navigate = useNavigate();
  const [otp, setOtp] = useState('');
  const [loading, setLoading] = useState(false);
  const { otpConfig, isLoadingOtpConfig } = useOtpConfig();
  const { isAuthenticated, user, verifyEmail } = useAuth();
  const { message } = useMessage();

  const onVerifyEmail = async () => {
    if (isBlank(otp)) {
      message.error('OTP is required');
      return;
    }

    if (otp.length !== otpConfig.verifyOtpLength) {
      message.error(`Please enter a ${otpConfig.verifyOtpLength}-digit OTP`);
      return;
    }

    if (!user?.email) {
      return;
    }

    try {
      setLoading(true);
      const request = { email: user.email, otp };
      await verifyEmail(request);
      message.success('Your email has been successfully verified!');
      navigate(Route.HOME);
    } catch (error) {
      message.error(getErrorMessage(error));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isAuthenticated && user && user.emailVerified) {
      navigate(Route.HOME);
    }
  }, [isAuthenticated, user]);

  return (
    <Card
      title={<Title level={4} style={{ textAlign: 'center', fontSize: 26 }}>Email Verify OTP</Title>}
      variant='borderless'
      style={{
        width: 460,
        height: 305,
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
      {isLoadingOtpConfig ? (
        <Flex vertical justify='space-between' align='center' style={{ width: '100%' }}>
          <Skeleton.Input active style={{ width: 290, height: 16, marginTop: 7 }} />
          <Skeleton.Input active style={{ width: 370, height: 55, borderRadius: 6, marginTop: 25, marginBottom: 30 }} />
          <Skeleton.Button active size='large' style={{ width: 370, height: 42, borderRadius: 6 }} />
        </Flex>
      ) : (
        <>
          <Text style={{ fontSize: 16, fontWeight: 500 }}>
            Enter the {otpConfig.verifyOtpLength}-digit code sent to your email
          </Text>
          <Input.OTP
            length={otpConfig.verifyOtpLength}
            size='large'
            separator=''
            value={otp}
            onChange={(value) => setOtp(value)}
          />
          <Button
            type='primary'
            htmlType='submit'
            variant='outlined'
            loading={loading}
            disabled={loading}
            onClick={onVerifyEmail}
            style={{ width: '100%', height: 42, fontSize: 16, fontWeight: 500 }}
          >
            {loading ? 'Verifying...' : 'Verify Email'}
          </Button>
        </>
      )}
    </Card>
  );
}

export default VerifyEmailPage;