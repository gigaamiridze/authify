import { Route, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';
import { Route as Routes } from './constants';
import {
  Root,
  HomePage,
  LoginPage,
  RegisterPage,
  NotFoundPage,
  ForbiddenPage,
  ResetPasswordPage,
  VerifyEmailPage,
  InternalServerErrorPage
} from './pages';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path={Routes.HOME} Component={Root}>
      <Route index Component={HomePage} />
      <Route path={Routes.LOGIN} Component={LoginPage} />
      <Route path={Routes.REGISTER} Component={RegisterPage} />
      <Route path={Routes.RESET_PASSWORD} Component={ResetPasswordPage} />
      <Route path={Routes.VERIFY_EMAIL} Component={VerifyEmailPage} />
      <Route path={Routes.FORBIDDEN} Component={ForbiddenPage} />
      <Route path={Routes.INTERNAL_SERVER_ERROR} Component={InternalServerErrorPage} />
      <Route path='*' Component={NotFoundPage} />
    </Route>
  )
);