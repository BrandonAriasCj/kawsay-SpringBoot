import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
//import Login from './pages/Login';
import Profile from './pages/Profile';
import Chatbot from './pages/Chatbot';
import Grupos from './pages/GruposAyuda';
import Citas from './pages/Citas';
import Login from './pages/Login';


function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} /> 
        <Route path="/profile" element={<Profile />} />
        <Route path="/chatbot" element={<Chatbot />} />
          <Route path="/grupos" element={<Grupos />} />
          <Route path="/citas" element={<Citas />} />
      </Routes>
    </Router>
  );
}

export default App;
