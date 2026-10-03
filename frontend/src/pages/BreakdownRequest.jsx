import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getMyVehicles, createRequest, errMsg } from '../services/api';
import { Msg } from '../components/Common';

const PROBLEMS = ['Flat tyre', 'Battery dead', 'Engine failure', 'Out of fuel', 'Overheating', 'Brake issue', 'Accident / towing', 'Locked out', 'Other'];

export default function BreakdownRequest() {
  const nav = useNavigate();
  const [vehicles, setVehicles] = useState(null);
  const [f, setF] = useState({ vehicleId: '', problemType: PROBLEMS[0], description: '', location: '' });
  const [err, setErr] = useState('');
  const [busy, setBusy] = useState(false);
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  useEffect(() => {
    getMyVehicles().then(setVehicles).catch((e) => { setVehicles([]); setErr(errMsg(e)); });
  }, []);

  const locate = () => {
    if (!navigator.geolocation) return setErr('Geolocation is not supported by this browser');
    navigator.geolocation.getCurrentPosition(
      (p) => setF((s) => ({ ...s, location: `${p.coords.latitude.toFixed(5)}, ${p.coords.longitude.toFixed(5)}` })),
      () => setErr('Could not read your location. Please type it in.')
    );
  };

  const submit = async (e) => {
    e.preventDefault(); setErr(''); setBusy(true);
    try {
      await createRequest({ problemType: f.problemType, description: f.description, location: f.location, vehicle: { id: Number(f.vehicleId) } });
      nav('/dashboard');
    } catch (x) { setErr(errMsg(x)); } finally { setBusy(false); }
  };

  return (
    <div className="container py-4" style={{ maxWidth: 720 }}>
      <h1>Request assistance</h1>
      <Msg>{err}</Msg>
      {vehicles && !vehicles.length ? (
        <div className="panel p-4">You need a vehicle first. <Link to="/vehicles">Add one here</Link>.</div>
      ) : (
        <form className="panel p-4" onSubmit={submit}>
          <label className="form-label">Vehicle</label>
          <select className="form-select mb-3" required value={f.vehicleId} onChange={set('vehicleId')}>
            <option value="">Select your vehicle</option>
            {(vehicles || []).map((v) => <option key={v.id} value={v.id}>{v.vehicleNumber} – {v.brand} {v.model}</option>)}
          </select>
          <label className="form-label">What went wrong?</label>
          <select className="form-select mb-3" value={f.problemType} onChange={set('problemType')}>{PROBLEMS.map((p) => <option key={p}>{p}</option>)}</select>
          <label className="form-label">Details</label>
          <textarea className="form-control mb-3" rows="3" required value={f.description} onChange={set('description')} placeholder="Describe what happened" />
          <label className="form-label">Your location</label>
          <div className="input-group mb-4">
            <input className="form-control" required value={f.location} onChange={set('location')} placeholder="Landmark, road, or coordinates" />
            <button type="button" className="btn btn-outline-dark" onClick={locate}>Use my location</button>
          </div>
          <button className="btn btn-sign btn-lg w-100" disabled={busy}>{busy ? 'Sending...' : 'Send request'}</button>
        </form>
      )}
    </div>
  );
}
