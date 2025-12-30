import type { RefreshTokenRequest, RefreshTokenResponse } from '../models';
import { Endpoint } from '../constants';
import axios from 'axios';

export const refreshAccessToken = async (refreshToken: string): Promise<string> => {
  const request: RefreshTokenRequest = { refreshToken };
  const url = import.meta.env.VITE_API_BASE_URL + Endpoint.REFRESH_TOKEN;
  const response = await axios.post<RefreshTokenResponse>(url, request);
  return response.data.newAccessToken;
};