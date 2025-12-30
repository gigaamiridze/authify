import { useState, useEffect } from 'react';
import type { OtpConfig } from '../models';
import { getErrorMessage } from '../utils';
import { Endpoint } from '../constants';
import { api } from '../api';

const initialState: OtpConfig = {
  resetOtpLength: 6,
  verifyOtpLength: 6
};

export const useOtpConfig = () => {
  const [otpConfig, setOtpConfig] = useState<OtpConfig>(initialState);
  const [isLoadingOtpConfig, setIsLoadingOtpConfig] = useState(false);

  const getOtpConfig = async () => {
    try {
      setIsLoadingOtpConfig(true);
      const response = await api.get<OtpConfig>(Endpoint.OTP_CONFIGURATION);
      setOtpConfig(response.data);
    } catch (error) {
      console.error(getErrorMessage(error));
    } finally {
      setIsLoadingOtpConfig(false);
    }
  };

  useEffect(() => {
    getOtpConfig();
  }, []);

  return { otpConfig, isLoadingOtpConfig };
};