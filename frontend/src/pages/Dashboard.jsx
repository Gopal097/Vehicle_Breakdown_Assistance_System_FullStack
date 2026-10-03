import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getMyRequests, getMyVehicles, errMsg } from '../services/api';
import { Msg, StatusBadge, fmtDate } from '../components/Common';

export default function Dashboard() {
  const { user } = useAuth();
  const [vehicles, setVehicles] = useState([]);
  const [requests, setRequests] = useState([]);
  const [err, setErr] = useState('');

  useEffect(() => {
    Promise.all([getMyVehicles(), getMyRequests()])
      .then(([v, r]) => { setVehicles(v); setRequests(r); })
      .catch((e) => setErr(errMsg(e)));
  }, []);

  const active = requests.filter((r) => !['Resolved', 'Cancelled'].includes(r.status)).length;
  const done = requests.filter((r) => r.status === 'Resolved').length;

  return (
    <div className="container py-4">
      <div className="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
        <h1 className="mb-0">Welcome, {user?.name}</h1>
        <Link to="/request" className="btn btn-sign">Request assistance</Link>
      </div>
      <Msg>{err}</Msg>
      <div className="row g-3 mb-4">
        <div className="col-sm-4"><div className="stat"><div className="stat-n">{vehicles.length}</div>Vehicles</div></div>
        <div className="col-sm-4"><div className="stat info"><div className="stat-n">{active}</div>Active requests</div></div>
        <div className="col-sm-4"><div className="stat ok"><div className="stat-n">{done}</div>Resolved</div></div>
      </div>
      <div className="panel">
        <div className="panel-h"><h2>My breakdown requests</h2></div>
        <div className="table-responsive">
          <table className="table mb-0">
            <thead><tr><th>#</th><th>Vehicle</th><th>Problem</th><th>Location</th><th>Mechanic</th><th>Status</th><th>Raised</th></tr></thead>
            <tbody>
              {requests.map((r) => (
                <tr key={r.id}>
                  <td>{r.id}</td><td>{r.vehicle?.vehicleNumber}</td><td>{r.problemType}</td><td>{r.location}</td>
                  <td>{r.mechanic ? `${r.mechanic.name} (${r.mechanic.phone})` : <span className="text-secondary">Awaiting assignment</span>}</td>
                  <td><StatusBadge status={r.status} /></td><td>{fmtDate(r.requestDate)}</td>
                </tr>
              ))}
              {!requests.length && <tr><td colSpan="7" className="text-center text-secondary py-4">No requests yet.</td></tr>}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
