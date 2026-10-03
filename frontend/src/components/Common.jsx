import { Navigate } from 'react-router-dom';
import { useAuth, homeFor } from '../context/AuthContext';

export const StatusBadge = ({ status }) => (
  <span className={`badge-s s-${(status || 'Pending').replace(/\s/g, '-')}`}>{status || 'Pending'}</span>
);

export const Msg = ({ type = 'danger', children }) =>
  children ? <div className={`alert alert-${type} py-2`} role="alert">{children}</div> : null;

export const fmtDate = (d) => (d ? new Date(d).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' }) : '-');

export function Protected({ children, roles }) {
  const { token, role } = useAuth();
  if (!token || !role) return <Navigate to="/login" replace />;
  if (roles && !roles.includes(role)) return <Navigate to={homeFor(role)} replace />;
  return children;
}
