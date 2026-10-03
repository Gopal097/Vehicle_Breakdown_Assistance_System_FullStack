import { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { token, user, role, logout } = useAuth();
  const [open, setOpen] = useState(false);
  const nav = useNavigate();
  const close = () => setOpen(false);
  const link = (to, label) => <li className="nav-item"><NavLink to={to} onClick={close} className="nav-link">{label}</NavLink></li>;

  return (
    <header className="topbar">
      <nav className="container d-flex align-items-center justify-content-between flex-wrap py-2">
        <Link to="/" className="brand">Vehicle Breakdown <b>Assistance System</b></Link>
        <button className="btn btn-outline-light d-lg-none" onClick={() => setOpen(!open)} aria-expanded={open} aria-label="Toggle menu">Menu</button>
        <div className={`${open ? 'd-block' : 'd-none'} d-lg-flex align-items-lg-center w-100 w-lg-auto ms-lg-auto`} style={{ flex: '0 1 auto' }}>
          <ul className="navbar-nav flex-lg-row">
            {token ? (<>
              {role === 'USER' && <>{link('/dashboard', 'Dashboard')}{link('/vehicles', 'My Vehicles')}{link('/request', 'Get Help')}</>}
              {role === 'MECHANIC' && link('/mechanic', 'My Jobs')}
              {role === 'ADMIN' && link('/admin', 'Admin Console')}
            </>) : (<>{link('/login', 'Login')}{link('/register', 'Register')}</>)}
          </ul>
          {token && (
            <div className="d-flex align-items-center gap-3 ms-lg-3 py-2">
              <span className="text-light small">{user?.name} <span className="badge text-bg-warning ms-1">{role}</span></span>
              <button className="btn btn-sm btn-sign" onClick={() => { logout(); close(); nav('/'); }}>Log out</button>
            </div>
          )}
        </div>
      </nav>
    </header>
  );
}
