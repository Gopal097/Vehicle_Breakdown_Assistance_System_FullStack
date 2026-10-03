import { useEffect, useState, useCallback } from 'react';
import { getRequests, getMechanics, getUsers, getVehicles, assignMechanic, updateStatus, addMechanic, deleteMechanic, deleteUser, deleteRequest, errMsg, STATUSES } from '../services/api';
import { Msg, StatusBadge, fmtDate } from '../components/Common';

const emptyM = { name: '', phone: '', experience: '', garageName: '', location: '', email: '', password: '' };

export default function AdminDashboard() {
  const [tab, setTab] = useState('requests');
  const [d, setD] = useState({ requests: [], mechanics: [], users: [], vehicles: [] });
  const [m, setM] = useState(emptyM);
  const [err, setErr] = useState('');

  const load = useCallback(() => Promise.all([getRequests(), getMechanics(), getUsers(), getVehicles()])
    .then(([requests, mechanics, users, vehicles]) => setD({ requests: requests.sort((a, b) => b.id - a.id), mechanics, users: users.filter((u) => u.role === 'USER'), vehicles }))
    .catch((e) => setErr(errMsg(e))), []);
  useEffect(() => { load(); }, [load]);

  const run = (fn) => async (...a) => { setErr(''); try { await fn(...a); await load(); } catch (x) { setErr(errMsg(x)); } };
  const assign = run((r, mech) => assignMechanic(r, mech));
  const status = run((r, s) => updateStatus(r, s));
  const delMech = run((id) => window.confirm('Delete this mechanic and their login? Open jobs go back to Pending.') && deleteMechanic(id));
  const delUser = run((id) => window.confirm('Delete this user and their vehicles?') && deleteUser(id));
  const delReq = run((id) => window.confirm('Delete this request?') && deleteRequest(id));
  const saveMech = async (e) => {
    e.preventDefault();
    setErr('');
    try { await addMechanic({ ...m, experience: Number(m.experience) }); setM(emptyM); await load(); }
    catch (x) { setErr(errMsg(x)); }
  };
  const setMf = (k) => (e) => setM({ ...m, [k]: e.target.value });
  const free = d.mechanics.filter((x) => x.availability);
  const pending = d.requests.filter((r) => r.status === 'Pending').length;

  return (
    <div className="container py-4">
      <h1>Admin console</h1>
      <Msg>{err}</Msg>
      <div className="row g-3 mb-4">
        {[['Requests', d.requests.length], ['Pending', pending], ['Mechanics', d.mechanics.length], ['Users', d.users.length]].map(([l, n]) => (
          <div className="col-6 col-md-3" key={l}><div className="stat"><div className="stat-n">{n}</div>{l}</div></div>
        ))}
      </div>
      <div className="tabs-row" role="tablist">
        {['requests', 'mechanics', 'users', 'vehicles'].map((t) => (
          <button key={t} role="tab" className={tab === t ? 'on' : ''} onClick={() => setTab(t)}>{t[0].toUpperCase() + t.slice(1)}</button>
        ))}
      </div>

      {tab === 'requests' && (
        <div className="panel table-responsive">
          <table className="table mb-0">
            <thead><tr><th>#</th><th>Vehicle / Owner</th><th>Problem</th><th>Location</th><th>Raised</th><th>Mechanic</th><th>Status</th><th></th></tr></thead>
            <tbody>
              {d.requests.map((r) => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.vehicle?.vehicleNumber}<div className="small text-secondary">{r.vehicle?.user?.name} {r.vehicle?.user?.phone}</div></td>
                  <td>{r.problemType}<div className="small text-secondary">{r.description}</div></td>
                  <td>{r.location}</td><td>{fmtDate(r.requestDate)}</td>
                  <td>
                    <select className="form-select form-select-sm" value={r.mechanic?.id || ''} onChange={(e) => e.target.value && assign(r.id, e.target.value)}>
                      <option value="">{r.mechanic ? r.mechanic.name : 'Assign...'}</option>
                      {free.filter((x) => x.id !== r.mechanic?.id).map((x) => <option key={x.id} value={x.id}>{x.name} – {x.location}</option>)}
                    </select>
                  </td>
                  <td>
                    <StatusBadge status={r.status} />
                    <select className="form-select form-select-sm mt-1" value="" onChange={(e) => e.target.value && status(r.id, e.target.value)}>
                      <option value="">Change...</option>{STATUSES.map((s) => <option key={s}>{s}</option>)}
                    </select>
                  </td>
                  <td><button className="btn btn-sm btn-outline-danger" onClick={() => delReq(r.id)}>Delete</button></td>
                </tr>
              ))}
              {!d.requests.length && <tr><td colSpan="8" className="text-center text-secondary py-4">No requests.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {tab === 'mechanics' && (
        <div className="row g-4">
          <div className="col-lg-4">
            <form className="panel p-3" onSubmit={saveMech}>
              <h3>Add mechanic</h3>
              <input className="form-control mb-2" placeholder="Name" required value={m.name} onChange={setMf('name')} />
              <input className="form-control mb-2" placeholder="Phone (10 digits)" required maxLength={10} value={m.phone} onChange={setMf('phone')} />
              <input className="form-control mb-2" type="number" min="0" placeholder="Experience (years)" required value={m.experience} onChange={setMf('experience')} />
              <input className="form-control mb-2" placeholder="Garage name" required value={m.garageName} onChange={setMf('garageName')} />
              <input className="form-control mb-2" placeholder="Location / area" required value={m.location} onChange={setMf('location')} />
              <input className="form-control mb-2" type="email" placeholder="Login email" required value={m.email} onChange={setMf('email')} />
              <input className="form-control mb-3" type="password" placeholder="Login password (min 8)" required minLength={8} value={m.password} onChange={setMf('password')} />
              <button className="btn btn-sign w-100">Create mechanic + login</button>
            </form>
          </div>
          <div className="col-lg-8"><div className="panel table-responsive">
            <table className="table mb-0">
              <thead><tr><th>Name</th><th>Garage</th><th>Phone</th><th>Login</th><th>Exp.</th><th>Area</th><th>Status</th><th></th></tr></thead>
              <tbody>{d.mechanics.map((x) => (
                <tr key={x.id}><td>{x.name}</td><td>{x.garageName}</td><td>{x.phone}</td><td>{x.email}</td><td>{x.experience} yrs</td><td>{x.location}</td>
                  <td><span className={`badge-s ${x.availability ? 's-Resolved' : 's-Cancelled'}`}>{x.availability ? 'Available' : 'Busy'}</span></td>
                  <td><button className="btn btn-sm btn-outline-danger" onClick={() => delMech(x.id)}>Delete</button></td></tr>
              ))}</tbody>
            </table>
          </div></div>
        </div>
      )}

      {tab === 'users' && (
        <div className="panel table-responsive"><table className="table mb-0">
          <thead><tr><th>#</th><th>Name</th><th>Email</th><th>Phone</th><th></th></tr></thead>
          <tbody>{d.users.map((u) => (
            <tr key={u.id}><td>{u.id}</td><td>{u.name}</td><td>{u.email}</td><td>{u.phone}</td>
              <td><button className="btn btn-sm btn-outline-danger" onClick={() => delUser(u.id)}>Delete</button></td></tr>
          ))}</tbody>
        </table></div>
      )}

      {tab === 'vehicles' && (
        <div className="panel table-responsive"><table className="table mb-0">
          <thead><tr><th>Number</th><th>Type</th><th>Brand / Model</th><th>Fuel</th><th>Owner</th></tr></thead>
          <tbody>{d.vehicles.map((v) => (
            <tr key={v.id}><td><strong>{v.vehicleNumber}</strong></td><td>{v.vehicleType}</td><td>{v.brand} {v.model}</td><td>{v.fuelType}</td><td>{v.user?.name}</td></tr>
          ))}</tbody>
        </table></div>
      )}
    </div>
  );
}
