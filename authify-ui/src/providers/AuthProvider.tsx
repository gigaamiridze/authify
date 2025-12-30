import { useEffect, useState, useReducer, type PropsWithChildren } from 'react';
import type {
  AuthState,
  RegisterRequest,
  RegisterResponse,
  LoginRequest,
  LoginResponse,
  ResetPasswordRequest,
  VerifyEmailRequest,
  SendOtpRequest,
  ProfileResponse
} from '../models';
import { getAccessToken, setTokens, removeTokens } from '../utils';
import { AuthContext, authReducer } from '../contexts';
import { Endpoint, AuthAction } from '../constants';
import { api } from '../api';

const initialState: AuthState = {
  user: null,
};

const AuthProvider = ({ children }: PropsWithChildren) => {
  const [state, dispatch] = useReducer(authReducer, initialState);
  const [isLoadingAuth, setIsLoadingAuth] = useState(false);

  useEffect(() => {
    initAuth();
  }, []);

  const initAuth = async () => {
    const accessToken = getAccessToken();
    if (!accessToken) {
      return;
    }

    try {
      setIsLoadingAuth(true);
      await getProfileInfo();
      await new Promise(res => setTimeout(res, 1000));
    } catch (error) {
      removeTokens();
    } finally {
      setIsLoadingAuth(false);
    }
  };

  const hasPermission = (permission: string): boolean => {
    const user = state.user;
    if (!user) return false;
    return user.permissions?.has(permission) ?? false;
  };

  const hasAnyPermission = (...permissions: string[]): boolean => {
    const user = state.user;
    if (!user) return false;
    return permissions.some(permission => user.permissions?.has(permission));
  };

  const hasAllPermissions = (...permissions: string[]): boolean => {
    const user = state.user;
    if (!user) return false;
    return permissions.every(permission => user.permissions?.has(permission));
  };

  const register = async (data: RegisterRequest): Promise<RegisterResponse> => {
    const response = await api.post<RegisterResponse>(Endpoint.REGISTER, data);
    return response.data;
  };

  const login = async (data: LoginRequest): Promise<LoginResponse> => {
    const response = await api.post<LoginResponse>(Endpoint.LOGIN, data);
    const result = response.data;
    const { id, email, accessToken, refreshToken } = result;
    setTokens(accessToken, refreshToken);
    dispatch({ type: AuthAction.LOGIN, payload: { id, email } });
    return result;
  };

  const logout = async (): Promise<void> => {
    await api.post(Endpoint.LOGOUT);
    removeTokens();
    dispatch({ type: AuthAction.LOGOUT });
  };

  const getProfileInfo = async (): Promise<ProfileResponse> => {
    const email = state.user?.email;
    const response = email ? await getProfileByEmail(email) : await getCurrentProfile();
    const user = {
      ...response,
      permissions: new Set(response.permissions)
    };
    dispatch({ type: AuthAction.SET_PROFILE_INFO, payload: user });
    return user;
  };

  const getProfileByEmail = async (email: string): Promise<ProfileResponse> => {
    const response = await api.get<ProfileResponse>(`${Endpoint.PROFILE}/${email}`);
    return response.data;
  };

  const getCurrentProfile = async (): Promise<ProfileResponse> => {
    const response = await api.get<ProfileResponse>(Endpoint.PROFILE);
    return response.data;
  };

  const resetPassword = async (data: ResetPasswordRequest): Promise<void> => {
    await api.post(Endpoint.RESET_PASSWORD, data);
  };

  const verifyEmail = async (data: VerifyEmailRequest): Promise<void> => {
    await api.post(Endpoint.VERIFY_EMAIL, data);
    dispatch({ type: AuthAction.EMAIL_VERIFY });
  };

  const sendResetOtp = async (data: SendOtpRequest): Promise<void> => {
    await api.post(Endpoint.SEND_RESET_OTP, data);
  };

  const sendVerifyOtp = async (): Promise<void> => {
    const user = state.user;
    if (!user?.email) {
      return;
    }
    const request: SendOtpRequest = { email: user.email };
    await api.post(Endpoint.SEND_VERIFY_OTP, request);
  };

  const value = {
    ...state,
    isLoadingAuth,
    isAuthenticated: !!state.user,
    hasPermission,
    hasAnyPermission,
    hasAllPermissions,
    register,
    login,
    logout,
    getProfileInfo,
    resetPassword,
    verifyEmail,
    sendResetOtp,
    sendVerifyOtp,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;