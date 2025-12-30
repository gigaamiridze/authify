import type { User, AuthState } from '../models';
import { AuthAction } from '../constants';

type AuthAction =
  | { type: typeof AuthAction.LOGIN; payload: Partial<User> }
  | { type: typeof AuthAction.LOGOUT }
  | { type: typeof AuthAction.EMAIL_VERIFY }
  | { type: typeof AuthAction.SET_PROFILE_INFO, payload: User };

export const authReducer = (state: AuthState, action: AuthAction) => {
  switch (action.type) {
    case AuthAction.LOGIN: {
      const { id, email } = action.payload;
      return {
        ...state,
        user: {
          ...state.user,
          id,
          email
        }
      };
    }
    case AuthAction.LOGOUT: {
      return {
        ...state,
        user: null
      };
    }
    case AuthAction.EMAIL_VERIFY: {
      return {
        ...state,
        user: {
          ...state.user,
          emailVerified: true
        }
      };
    }
    case AuthAction.SET_PROFILE_INFO: {
      return {
        ...state,
        user: action.payload
      };
    }
    default:
      return state;
  }
};