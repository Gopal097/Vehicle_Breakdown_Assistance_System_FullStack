import { useEffect, useState } from 'react';
import { getMyVehicles, addVehicle, deleteVehicle, errMsg } from '../services/api';
import { Msg } from '../components/Common';

const empty = { vehicleNumber: '', vehicleType: 'Car', brand: '', model: '', fuelType: 'Petrol' };

export default function Vehicles() {
  const [list, setList] = useState([]);
  const [f, setF] = useState(empty);
  const [err, setErr] = useState('');
  const [ok, setOk] = useState('');
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  const load = () => getMyVehicles().then(setList).catch((e) => setErr(errMsg(e)));
  useEffect(() => { load(); }, []);

  const submit = async (e) => {
    e.preventDefault(); setErr(''); setOk('');
    try { await addVehicle({ ...f, vehicleNumber: f.vehicleNumber.toUpperCase() }); setF(empty); setOk('Vehicle added'); load(); }
    catch (x) { setErr(errMsg(x)); }
  };
  const remove = async (id) => {
    if (!window.confirm('Delete this vehicle and its requests?')) return;
    try { await deleteVehicle(id); load(); } catch (x) { setErr(errMsg(x)); }
  };

  return (
    <div className="container py-4">
      <h1>My vehicles</h1>
      <Msg>{err}</Msg><Msg type="success">{ok}</Msg>
      <div className="row g-4">
        <div className="col-lg-4">
          <form className="panel p-3" onSubmit={submit}>
            <h3>Add a vehicle</h3>
            <input className="form-control mb-2" placeholder="Registration no. (e.g. KA01AB1234)" required value={f.vehicleNumber} onChange={set('vehicleNumber')} />
            <select className="form-select mb-2" value={f.vehicleType} onChange={set('vehicleType')}>
              {['Car', 'Bike', 'Scooter', 'Truck', 'Bus', 'Van'].map((t) => <option key={t}>{t}</option>)}
            </select>
            <input className="form-control mb-2" placeholder="Brand" required value={f.brand} onChange={set('brand')} />
            <input className="form-control mb-2" placeholder="Model" required value={f.model} onChange={set('model')} />
            <select className="form-select mb-3" value={f.fuelType} onChange={set('fuelType')}>
              {['Petrol', 'Diesel', 'CNG', 'Electric', 'Hybrid'].map((t) => <option key={t}>{t}</option>)}
            </select>
            <button className="btn btn-sign w-100">Save vehicle</button>
          </form>
        </div>
        <div className="col-lg-8">
          <div className="panel table-responsive">
            <table className="table mb-0">
              <thead><tr><th>Number</th><th>Type</th><th>Brand / Model</th><th>Fuel</th><th></th></tr></thead>
              <tbody>
                {list.map((v) => (
                  <tr key={v.id}><td><strong>{v.vehicleNumber}</strong></td><td>{v.vehicleType}</td><td>{v.brand} {v.model}</td><td>{v.fuelType}</td>
                    <td className="text-end"><button className="btn btn-sm btn-outline-danger" onClick={() => remove(v.id)}>Delete</button></td></tr>
                ))}
                {!list.length && <tr><td colSpan="5" className="text-center text-secondary py-4">No vehicles yet. Add one to request help.</td></tr>}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
