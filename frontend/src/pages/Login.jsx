import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth, homeFor } from '../context/AuthContext';
import { errMsg } from '../services/api';
import { Msg } from '../components/Common';

export default function Login() {
  const { login } = useAuth();
  const nav = useNavigate();
  const [f, setF] = useState({ email: '', password: '' });
  const [err, setErr] = useState('');
  const [busy, setBusy] = useState(false);

  const submit = async (e) => {
    e.preventDefault(); setErr(''); setBusy(true);
    try { const u = await login(f.email.trim(), f.password); nav(homeFor(u.role)); }
    catch (x) { setErr(x.response?.status === 401 ? 'Invalid email or password' : errMsg(x)); }
    finally { setBusy(false); }
  };

  return (
    <div className="container py-5 auth-wrap">
      <div className="panel p-4">
        <h2>Log in</h2>
        <Msg>{err}</Msg>
        <form onSubmit={submit}>
          <label className="form-label" htmlFor="email">Email</label>
          <input id="email" type="email" className="form-control mb-3" required value={f.email} onChange={(e) => setF({ ...f, email: e.target.value })} />
          <label className="form-label" htmlFor="pw">Password</label>
          <input id="pw" type="password" className="form-control mb-3" required value={f.password} onChange={(e) => setF({ ...f, password: e.target.value })} />
          <button className="btn btn-sign w-100" disabled={busy}>{busy ? 'Signing in...' : 'Log in'}</button>
        </form>
        <p className="mt-3 mb-0 small">New here? <Link to="/register">Create an account</Link></p>
      </div>
    </div>
  );
}
