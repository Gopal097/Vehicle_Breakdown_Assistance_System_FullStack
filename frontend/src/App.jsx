import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import { Protected } from './components/Common';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Vehicles from './pages/Vehicles';
import BreakdownRequest from './pages/BreakdownRequest';
import Mechanics from './pages/Mechanics';
import AdminDashboard from './pages/AdminDashboard';

export default function App() {
  return (
    <>
      <Navbar />
      <main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<Protected roles={['USER']}><Dashboard /></Protected>} />
          <Route path="/vehicles" element={<Protected roles={['USER']}><Vehicles /></Protected>} />
          <Route path="/request" element={<Protected roles={['USER']}><BreakdownRequest /></Protected>} />
          <Route path="/mechanic" element={<Protected roles={['MECHANIC']}><Mechanics /></Protected>} />
          <Route path="/admin" element={<Protected roles={['ADMIN']}><AdminDashboard /></Protected>} />
          <Route path="*" element={<div className="container py-5"><h2>Page not found</h2></div>} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}
