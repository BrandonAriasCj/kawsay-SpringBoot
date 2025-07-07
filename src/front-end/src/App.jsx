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
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Landing from './pages/Landing';
import ProtectedRoute from './context/ProtectedRoute';
import NotFound from './pages/NotFound';

function AppContent() {
    const { user } = useContext(AuthContext);
    const userEmail = user?.username;
    const [showWizard, setShowWizard] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();

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


      useEffect(() => {
    // Si el usuario está logeado y está en /main o /login, redirígelo a Home
        if (user && (location.pathname === '/main' || location.pathname === '/login')) {
        navigate('/');
        }
    }, [user, location.pathname, navigate]);

    const handleWizardComplete = () => {
        setShowWizard(false);
    };

    return (
        <>
            {}
            {user && location.pathname !== "/login" && <Navbar />}

                {showWizard && (
                    <Wizard userEmail={userEmail} onComplete={handleWizardComplete} />
                )}




            <main className="main-content-area">
                <Routes>
                    <Route path="/global-logout" element={<GlobalLogout />} />
                    <Route path="/main" element={<Landing />} />
                    <Route path="/" element={<ProtectedRoute><Home /> </ProtectedRoute>} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/profile" element={<ProtectedRoute><UserProfile /></ProtectedRoute>} />
                    <Route path="/chatbot" element={<ProtectedRoute><Chatbot /></ProtectedRoute>} />
                    <Route path="/grupos" element={<ProtectedRoute><Grupos /></ProtectedRoute>} />
                    <Route path="/citas" element={<ProtectedRoute><Citas /></ProtectedRoute>} />
                    <Route path="*" element={<NotFound />} />
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
