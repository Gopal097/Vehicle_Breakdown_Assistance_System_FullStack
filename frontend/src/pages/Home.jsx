import { Link } from 'react-router-dom';
import { useAuth, homeFor } from '../context/AuthContext';

const steps = [
  ['Report', 'Tell us the problem, the vehicle and where you are stopped.'],
  ['Dispatch', 'An admin assigns the nearest available mechanic to your request.'],
  ['Track', 'Follow the status from Pending to Resolved from your dashboard.'],
];

export default function Home() {
  const { token, role } = useAuth();
  return (
    <>
      <section className="hero pt-5 pb-5">
        <div className="container py-4 pb-5">
          <div className="row align-items-center g-4">
            <div className="col-lg-7">
              <h1>Stuck on the road? Help is dispatched.</h1>
              <p className="mt-3">Register your vehicles once, raise a breakdown request in under a minute, and watch a mechanic get assigned.</p>
              <div className="d-flex gap-2 mt-4 mb-4">
                <Link className="btn btn-sign btn-lg" to={token ? (role === 'USER' ? '/request' : homeFor(role)) : '/register'}>{token ? (role === 'USER' ? 'Request assistance' : 'Open my console') : 'Create an account'}</Link>
                {!token && <Link className="btn btn-outline-light btn-lg" to="/login">Log in</Link>}
              </div>
            </div>
            <div className="col-lg-5">
              <div className="sos">
                <div className="small text-uppercase" style={{ color: '#98a3af' }}>Request lifecycle</div>
                <div className="num mt-1">Pending → Assigned → In Progress → Resolved</div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section className="container mt-5">
        <div className="row g-4">
          {steps.map(([t, d]) => (
            <div className="col-md-4" key={t}>
              <div className="panel h-100 p-4"><h3>{t}</h3><p className="mb-0 text-secondary">{d}</p></div>
            </div>
          ))}
        </div>
      </section>
    </>
  );
}
