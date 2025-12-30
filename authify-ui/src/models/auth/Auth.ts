export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  emailVerified: boolean;
  permissions: Set<string>;
}

export interface AuthState {
  user: User | Partial<User> | null;
}