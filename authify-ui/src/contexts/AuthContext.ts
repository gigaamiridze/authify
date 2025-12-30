import { createContext } from 'react';
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

interface IAuthContext extends AuthState {
  isLoadingAuth: boolean;
  isAuthenticated: boolean;
  hasPermission: (permission: string) => boolean;
  hasAnyPermission: (...permissions: string[]) => boolean;
  hasAllPermissions: (...permissions: string[]) => boolean;
  register: (data: RegisterRequest) => Promise<RegisterResponse>;
  login: (data: LoginRequest) => Promise<LoginResponse>;
  logout: () => Promise<void>;
  getProfileInfo: () => Promise<ProfileResponse>;
  resetPassword: (data: ResetPasswordRequest) => Promise<void>;
  verifyEmail: (data: VerifyEmailRequest) => Promise<void>;
  sendResetOtp: (data: SendOtpRequest) => Promise<void>;
  sendVerifyOtp: () => Promise<void>;
}

export const AuthContext = createContext<IAuthContext | null>(null);