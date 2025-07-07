// src/pages/Home.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css';
import kawsaiLogo from '@/assets/kawsai-logo.png'; // Usando tu logo
import {
    FaSpa, FaBrain, FaBookOpen, FaWalking,
    FaComments, FaUsers, FaCalendarCheck
} from 'react-icons/fa';

const Home = () => {
    const navigate = useNavigate();

    return (
        <div className="home-page-content">
            <div className="hero-section">
                <h1>Tu Espacio de Bienestar Digital</h1>
                <p className="subtitle">Explora un chatbot de apoyo emocional, conecta en grupos de ayuda y agenda citas con profesionales. Estamos aquí para escucharte y orientarte.</p>
                <FaSpa className="hero-icon" />
                <button className="btn btn-primary" onClick={() => navigate('/chatbot')}>
                    Comenzar conversación
                </button>
            </div>

            <div className="kawsay-meaning-section">
                <div className="glass-card kawsay-card">
                    <div className="kawsay-icon-wrapper">
                        {}
                        <img src={kawsaiLogo} alt="Logo de Kawsay" className="kawsay-logo-img" />
                    </div>
                    <div className="kawsay-text">
                        <h2>¿Sabías que "Kawsay" es una palabra Quechua?</h2>
                        <p>Significa <strong>"vida"</strong> y <strong>"vivir"</strong>. Hemos elegido este nombre porque nuestra misión es ayudarte a vivir una vida más plena, consciente y en equilibrio emocional.</p>
                    </div>
                </div>
            </div>

            <div className="wellbeing-section">
                <h2>Rituales de Bienestar para tu Día</h2>
                <div className="wellbeing-grid">
                    <div className="wellbeing-card">
                        <div className="wellbeing-icon-wrapper"><FaBrain /></div>
                        <h4>Pausa Mental</h4>
                        <p>Dedica 5 minutos a la meditación. Cierra los ojos y concéntrate en tu respiración.</p>
                    </div>
                    <div className="wellbeing-card">
                        <div className="wellbeing-icon-wrapper"><FaBookOpen /></div>
                        <h4>Diario de Gratitud</h4>
                        <p>Antes de dormir, escribe tres cosas por las que te sientas agradecido/a hoy.</p>
                    </div>
                    <div className="wellbeing-card">
                        <div className="wellbeing-icon-wrapper"><FaWalking /></div>
                        <h4>Movimiento Consciente</h4>
                        <p>Sal a caminar 15 minutos sin música. Presta atención a los sonidos a tu alrededor.</p>
                    </div>
                </div>
            </div>

            <div className="features-section">
                <h2>¿Qué puedes hacer aquí?</h2>
                <div className="features-grid">
                    <div className="feature-card">
                        <FaComments />
                        <h3>Chatbot Inteligente</h3>
                        <p>Habla en un espacio seguro 24/7 sobre cómo te sientes.</p>
                    </div>
                    <div className="feature-card">
                        <FaUsers />
                        <h3>Grupos de Apoyo</h3>
                        <p>Comparte experiencias y encuentra apoyo en la comunidad.</p>
                    </div>
                    <div className="feature-card">
                        <FaCalendarCheck />
                        <h3>Agenda de Citas</h3>
                        <p>Conecta con psicólogos profesionales para una sesión.</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Home;