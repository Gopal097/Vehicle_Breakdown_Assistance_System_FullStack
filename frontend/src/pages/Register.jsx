import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { register, errMsg } from '../services/api';
import { Msg } from '../components/Common';

export default function Register() {
  const nav = useNavigate();
  const [f, setF] = useState({ name: '', email: '', phone: '', password: '' });
  const [err, setErr] = useState('');
  const [busy, setBusy] = useState(false);
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  const submit = async (e) => {
    e.preventDefault(); setErr('');
    if (!/^[0-9]{10}$/.test(f.phone)) return setErr('Phone number must be exactly 10 digits');
    if (f.password.length < 8) return setErr('Password must be at least 8 characters');
    setBusy(true);
    try { await register(f); nav('/login'); }
    catch (x) { setErr(errMsg(x)); }
    finally { setBusy(false); }
  };

  return (
    <div className="container py-5 auth-wrap">
      <div className="panel p-4">
        <h2>Create account</h2>
        <Msg>{err}</Msg>
        <form onSubmit={submit}>
          <label className="form-label" htmlFor="n">Full name</label>
          <input id="n" className="form-control mb-3" required value={f.name} onChange={set('name')} />
          <label className="form-label" htmlFor="e">Email</label>
          <input id="e" type="email" className="form-control mb-3" required value={f.email} onChange={set('email')} />
          <label className="form-label" htmlFor="p">Phone (10 digits)</label>
          <input id="p" inputMode="numeric" maxLength={10} className="form-control mb-3" required value={f.phone} onChange={set('phone')} />
          <label className="form-label" htmlFor="w">Password (min 8 characters)</label>
          <input id="w" type="password" className="form-control mb-3" required value={f.password} onChange={set('password')} />
          <button className="btn btn-sign w-100" disabled={busy}>{busy ? 'Creating...' : 'Register'}</button>
        </form>
        <p className="mt-3 mb-0 small">Already registered? <Link to="/login">Log in</Link></p>
      </div>
    </div>
  );
}
