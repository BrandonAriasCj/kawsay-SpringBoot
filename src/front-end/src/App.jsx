// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import UserProfile from './pages/UserProfile';
import Chatbot from './pages/Chatbot';
import Grupos from './pages/GruposAyuda';
import Citas from './pages/Citas';
import GlobalLogout from './components/GlobalLogout';

import './App.css';

function App() {
    return (
        <Router>
            <Navbar />
            {}
            <main className="main-content-area">
                <Routes>
                    <Route path="/global-logout" element={<GlobalLogout />} />
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/profile" element={<UserProfile />} />
                    <Route path="/chatbot" element={<Chatbot />} />
                    <Route path="/grupos" element={<Grupos />} />
                    <Route path="/citas" element={<Citas />} />
                </Routes>
            </main>
        </Router>
    );
}

export default App;