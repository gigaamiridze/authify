export interface ProfileResponse {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  emailVerified: boolean;
  permissions: Set<string>;
}