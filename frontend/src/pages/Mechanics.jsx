import { useEffect, useState, useCallback } from 'react';
import { useAuth } from '../context/AuthContext';
import { getMyMechanicProfile, getMyAssignments, updateMyJobStatus, setMyAvailability, errMsg } from '../services/api';
import { Msg, StatusBadge, fmtDate } from '../components/Common';

// Logged-in mechanic: own profile, availability and assigned jobs
export default function Mechanics() {
  const { user } = useAuth();
  const [mech, setMech] = useState(null);
  const [jobs, setJobs] = useState([]);
  const [err, setErr] = useState('');

  const load = useCallback(() => Promise.all([getMyMechanicProfile(), getMyAssignments()])
    .then(([m, j]) => { setMech(m); setJobs(j); }).catch((e) => setErr(errMsg(e))), []);
  useEffect(() => { load(); }, [load]);

  const setStatus = async (id, s) => { setErr(''); try { await updateMyJobStatus(id, s); load(); } catch (x) { setErr(errMsg(x)); } };
  const toggle = async () => { setErr(''); try { await setMyAvailability(!mech.availability); load(); } catch (x) { setErr(errMsg(x)); } };
  const open = jobs.filter((j) => ['Assigned', 'In Progress'].includes(j.status)).length;

  return (
    <div className="container py-4">
      <h1>Hello, {user?.name}</h1>
      <Msg>{err}</Msg>
      {mech && (
        <div className="row g-3 mb-4">
          <div className="col-md-4"><div className="stat"><div className="stat-n">{open}</div>Open jobs</div></div>
          <div className="col-md-4"><div className="stat ok"><div className="stat-n">{jobs.filter((j) => j.status === 'Resolved').length}</div>Resolved</div></div>
          <div className="col-md-4">
            <div className="stat info d-flex flex-column gap-2">
              <div><span className={`badge-s ${mech.availability ? 's-Resolved' : 's-Cancelled'}`}>{mech.availability ? 'Available for jobs' : 'Not available'}</span></div>
              <button className="btn btn-sm btn-outline-dark align-self-start" onClick={toggle}>Mark {mech.availability ? 'unavailable' : 'available'}</button>
            </div>
          </div>
        </div>
      )}
      <div className="panel">
        <div className="panel-h"><h2>Jobs assigned to me</h2></div>
        <div className="table-responsive">
          <table className="table mb-0">
            <thead><tr><th>#</th><th>Customer / Vehicle</th><th>Problem</th><th>Location</th><th>Raised</th><th>Status</th><th>Update</th></tr></thead>
            <tbody>
              {jobs.map((r) => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.vehicle?.user?.name} <span className="text-secondary">{r.vehicle?.user?.phone}</span><div className="small">{r.vehicle?.vehicleNumber} · {r.vehicle?.brand} {r.vehicle?.model}</div></td>
                  <td>{r.problemType}<div className="small text-secondary">{r.description}</div></td>
                  <td>{r.location}</td><td>{fmtDate(r.requestDate)}</td>
                  <td><StatusBadge status={r.status} /></td>
                  <td className="text-nowrap">
                    {r.status === 'Assigned' && <button className="btn btn-sm btn-sign" onClick={() => setStatus(r.id, 'In Progress')}>Start job</button>}
                    {r.status === 'In Progress' && <button className="btn btn-sm btn-success" onClick={() => setStatus(r.id, 'Resolved')}>Mark resolved</button>}
                  </td>
                </tr>
              ))}
              {!jobs.length && <tr><td colSpan="7" className="text-center text-secondary py-4">No jobs assigned yet.</td></tr>}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
