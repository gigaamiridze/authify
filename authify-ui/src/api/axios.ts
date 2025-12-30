import axios from 'axios';
import {
  isPublicEndpoint,
  getAccessToken,
  setAccessToken,
  getRefreshToken,
  removeTokens,
  isUnauthorized,
  isForbidden,
  isInternalServerError
} from '../utils';
import { refreshAccessToken } from './refreshToken.ts';
import { Route } from '../constants';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

function logout() {
  removeTokens();
  window.location.href = Route.LOGIN;
}

api.interceptors.request.use(request => {
  if (isPublicEndpoint(request.url)) {
    return request;
  }

  const accessToken = getAccessToken();
  if (accessToken) {
    request.headers.Authorization = `Bearer ${accessToken}`;
  }
  return request;
});

api.interceptors.response.use(response => response, async error => {
  const originalRequest = error.config;
  if (!originalRequest || !error.response) {
    return Promise.reject(error);
  }

  const status = error.response.status;
  if (isForbidden(status)) {
    window.location.href = Route.FORBIDDEN;
    return Promise.reject(error);
  }

  if (isInternalServerError(status)) {
    window.location.href = Route.INTERNAL_SERVER_ERROR;
    return Promise.reject(error);
  }

  if (isUnauthorized(status) && isPublicEndpoint(originalRequest.url)) {
    return Promise.reject(error);
  }

  if (!isUnauthorized(status) || originalRequest._retry) {
    return Promise.reject(error);
  }

  originalRequest._retry = true;
  const refreshToken = getRefreshToken();
  if (!refreshToken) {
    logout();
    return Promise.reject(error);
  }

  try {
    const newAccessToken = await refreshAccessToken(refreshToken);
    setAccessToken(newAccessToken);
    originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
    return api(originalRequest);
  } catch (refreshError) {
    logout();
    return Promise.reject(refreshError);
  }
});