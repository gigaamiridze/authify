export const Endpoint = {
  REGISTER: '/auth/register',
  LOGIN: '/auth/login',
  LOGOUT: '/auth/logout',
  PROFILE: '/profile',
  REFRESH_TOKEN: '/auth/refresh-token',
  RESET_PASSWORD: '/auth/reset-password',
  VERIFY_EMAIL: '/auth/verify-email',
  SEND_RESET_OTP: '/auth/send-reset-otp',
  SEND_VERIFY_OTP: '/auth/send-verify-otp',
  OTP_CONFIGURATION: '/configuration/otp',
};

export const PUBLIC_ENDPOINTS = [
  Endpoint.REGISTER,
  Endpoint.LOGIN,
  Endpoint.REFRESH_TOKEN,
  Endpoint.RESET_PASSWORD
];