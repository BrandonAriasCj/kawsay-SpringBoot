// src/App.jsx
import { useContext , useState,useEffect} from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import UserProfile from './pages/UserProfile';
import Chatbot from './pages/Chatbot';
import Grupos from './pages/GruposAyuda';
import Citas from './pages/Citas';
import GlobalLogout from './components/GlobalLogout';
import Wizard from './components/Wizard';
import { AuthContext } from './context/AuthContext';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import './App.css';

function App() {
    const { user } = useContext(AuthContext); // Usa el contexto
    const userEmail = user?.username;
    const [showWizard, setShowWizard] = useState(false);
      
      useEffect(() => {
            if (userEmail && !localStorage.getItem('firstLoginCompleted_${userEmail}')) {
            setShowWizard(true);
            } else {
            setShowWizard(false);
            }
        }, [userEmail]);

        const handleWizardComplete = () => {
            setShowWizard(false);
        };
    return (
        <Router>
            <Navbar />

            <main className="main-content-area relative">
                            {/* 🔐 Mostrar el wizard si corresponde */}
            {showWizard && (
                <Wizard userEmail={userEmail} onComplete={handleWizardComplete} />
            )}

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