import axios from 'axios';

const api = axios.create({ baseURL: '/api' });

// Attach the JWT to every request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Expired / invalid token -> back to login
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response && err.response.status === 401 && localStorage.getItem('token')) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

// Turn any backend error shape into a readable string
export const errMsg = (err) => {
  const d = err?.response?.data;
  if (!d) return err?.response ? 'Request failed' : 'Cannot reach the server. Is Spring Boot running on port 8080?';
  if (typeof d === 'string') return d;
  if (d.message && typeof d.message === 'string') return d.message;
  const vals = Object.values(d).filter((v) => typeof v === 'string');
  return vals.length ? vals.join(', ') : 'Request failed';
};

export const STATUSES = ['Pending', 'Assigned', 'In Progress', 'Resolved', 'Cancelled'];

// Auth
export const login = (email, password) => api.post('/auth/login', { email, password }).then((r) => r.data); // { token, name, email, role }
export const register = (u) => api.post('/users', u).then((r) => r.data);
// Users (admin)
export const getUsers = () => api.get('/users').then((r) => r.data);
export const deleteUser = (id) => api.delete(`/users/${id}`);
// Vehicles (wrapped in ApiResponse -> .data.data)
export const getVehicles = () => api.get('/vehicles').then((r) => r.data.data); // admin: all
export const getMyVehicles = () => api.get('/vehicles/my').then((r) => r.data.data);
export const addVehicle = (v) => api.post('/vehicles', v).then((r) => r.data.data);
export const deleteVehicle = (id) => api.delete(`/vehicles/${id}`);
// Requests
export const getRequests = () => api.get('/requests').then((r) => r.data); // admin: all
export const getMyRequests = () => api.get('/requests/my').then((r) => r.data);
export const createRequest = (r) => api.post('/requests', r).then((x) => x.data);
export const deleteRequest = (id) => api.delete(`/requests/${id}`);
// Mechanics
export const getMechanics = () => api.get('/mechanics').then((r) => r.data);
export const addMechanic = (m) => api.post('/admin/mechanics', m).then((r) => r.data);
export const getMyMechanicProfile = () => api.get('/mechanics/me').then((r) => r.data);
export const getMyAssignments = () => api.get('/mechanics/me/requests').then((r) => r.data);
export const setMyAvailability = (available) => api.put('/mechanics/me/availability', null, { params: { available } }).then((r) => r.data);
export const updateMyJobStatus = (id, status) => api.put(`/mechanics/me/requests/${id}/status`, null, { params: { status } }).then((r) => r.data);
export const deleteMechanic = (id) => api.delete(`/mechanics/${id}`);
// Admin actions
export const assignMechanic = (reqId, mechId) => api.put(`/admin/assign/${reqId}/${mechId}`).then((r) => r.data);
export const updateStatus = (reqId, status) => api.put(`/admin/status/${reqId}`, null, { params: { status } }).then((r) => r.data);

export default api;
