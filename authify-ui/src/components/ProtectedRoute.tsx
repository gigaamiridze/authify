import { Navigate, Outlet } from 'react-router-dom';
import { Route } from '../constants';
import { useAuth } from '../hooks';

interface ProtectedRouteProps {
  permissions?: string[];
}

function ProtectedRoute({ permissions }: ProtectedRouteProps) {
  const { isAuthenticated, hasAllPermissions } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to={Route.LOGIN} replace />;
  }

  if (permissions && !hasAllPermissions(...permissions)) {
    return <Navigate to={Route.FORBIDDEN} replace />;
  }

  return <Outlet />;
}

export default ProtectedRoute;