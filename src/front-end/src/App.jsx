// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import UserProfile from './pages/UserProfile';
import Chatbot from './pages/Chatbot';
import Grupos from './pages/GruposAyuda';
import Citas from './pages/Citas';
import GlobalLogout from './components/GlobalLogout';
import { useContext, useEffect, useState } from 'react';
import { AuthContext } from './context/AuthContext';
import Wizard from './components/Wizard';
import './App.css';

import axios from 'axios';
function AppContent() {
    const { user } = useContext(AuthContext);
    const userEmail = user?.username;
    const [showWizard, setShowWizard] = useState(false);
    const location = useLocation();

    useEffect(() => {
    if (!userEmail) return;

    const jwtToken = localStorage.getItem("jwtToken");

    axios.get("http://localhost:8081/api/perfil", {
        headers: {
        Authorization: `Bearer ${jwtToken}`
        }
    })
    .then(({ data }) => {
        setShowWizard(!data.perfilCompletado);
    })
    .catch((err) => {
        console.error("Error al obtener el perfil:", err);
        setShowWizard(true); // fallback
    });
    }, [userEmail]);


    const handleWizardComplete = () => {
        setShowWizard(false);
    };

    return (
        <>
            {}
            {location.pathname !== "/login" && <Navbar />}
            
                {showWizard && (
                    <Wizard userEmail={userEmail} onComplete={handleWizardComplete} />
                )}




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
        </>
    );
}

function App() {
    return (
        <Router>
            <AppContent />
        </Router>
    );
}

export default App;
