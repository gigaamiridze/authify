import { HttpStatus } from '../constants';

export function isUnauthorized(status: number): boolean {
  return status === HttpStatus.UNAUTHORIZED;
}

export function isForbidden(status: number): boolean {
  return status === HttpStatus.FORBIDDEN;
}

export function isInternalServerError(status: number): boolean {
  return status >= HttpStatus.INTERNAL_SERVER_ERROR;
}