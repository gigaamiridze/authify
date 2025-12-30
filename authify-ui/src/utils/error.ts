import { AxiosError } from 'axios';
import type { ErrorInfo } from '../models';

export function getErrorMessage(error: unknown): string {
  if (error instanceof AxiosError && error.response?.data) {
    const data = error.response.data as ErrorInfo;
    return data.message;
  }
  if (error instanceof Error) return error.message;
  return 'Something went wrong';
}