import { StorageKey } from '../constants';

export function getAccessToken(): string | null {
  return localStorage.getItem(StorageKey.ACCESS_TOKEN);
}

export function setAccessToken(accessToken: string): void {
  localStorage.setItem(StorageKey.ACCESS_TOKEN, accessToken);
}

export function getRefreshToken(): string | null {
  return localStorage.getItem(StorageKey.REFRESH_TOKEN);
}

export function setTokens(accessToken: string, refreshToken: string): void {
  localStorage.setItem(StorageKey.ACCESS_TOKEN, accessToken);
  localStorage.setItem(StorageKey.REFRESH_TOKEN, refreshToken);
}

export function removeTokens(): void {
  localStorage.removeItem(StorageKey.ACCESS_TOKEN);
  localStorage.removeItem(StorageKey.REFRESH_TOKEN);
}