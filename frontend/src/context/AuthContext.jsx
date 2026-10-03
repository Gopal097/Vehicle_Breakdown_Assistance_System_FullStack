import { createContext, useContext, useState, useCallback } from 'react';
import { login as loginApi } from '../services/api';

const AuthContext = createContext(null);
export const useAuth = () => useContext(AuthContext);

const readUser = () => { try { return JSON.parse(localStorage.getItem('user')); } catch { return null; } };

export const homeFor = (role) => (role === 'ADMIN' ? '/admin' : role === 'MECHANIC' ? '/mechanic' : '/dashboard');

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [user, setUser] = useState(readUser()); // { name, email, role }

  const login = useCallback(async (email, password) => {
    const { token: t, name, email: e, role } = await loginApi(email, password);
    const u = { name, email: e, role };
    localStorage.setItem('token', t);
    localStorage.setItem('user', JSON.stringify(u));
    setToken(t);
    setUser(u);
    return u;
  }, []);

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setToken(null);
    setUser(null);
  };

  const role = token && user ? user.role : null;
  return <AuthContext.Provider value={{ token, user, role, login, logout }}>{children}</AuthContext.Provider>;
}
