// src/components/Navbar.jsx
import React, { useContext, useEffect, useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext.jsx';
import { signOut } from '@aws-amplify/auth';
import '../styles/Navbar.css';

// Importa tu logo y el ícono
import kawsaiLogo from '../assets/kawsai-logo.png';
import { FaUserCircle } from 'react-icons/fa';

import { urlBaseBack } from '../utils/path.js';

const Navbar = () => {
    const { user } = useContext(AuthContext);
    const [perfil, setPerfil] = useState(null);
    const [dropdownVisible, setDropdownVisible] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPerfil = async () => {
            if (!user) return;
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await fetch(`${urlBaseBack}/api/perfil`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setPerfil(data);
                }
            } catch (error) {
                console.error('Error al cargar perfil:', error);
            }
        };
        fetchPerfil();
    }, [user]);

    const handleLogout = async () => {
        try {
            await signOut();
            localStorage.removeItem('jwtToken');
            window.location.href = '/login';
        } catch (err) {
            console.error('❌ Error al cerrar sesión:', err);
        }
    };

    const toggleDropdown = () => setDropdownVisible(!dropdownVisible);

    // Función para obtener la clase activa para NavLink
    const getNavLinkClass = ({ isActive }) => isActive ? 'nav-link active' : 'nav-link';

    return (
        <header className="main-header">
            <Link to="/" className="logo">
                <img src={kawsaiLogo} alt="KawsAi Logo" className="logo-img" />
                <span>KawsAi</span>
            </Link>

            <nav className="nav-left">
                <NavLink to="/" className={getNavLinkClass}>Inicio</NavLink>
                <NavLink to="/chatbot" className={getNavLinkClass}>Chatbot</NavLink>
                <NavLink to="/grupos" className={getNavLinkClass}>Grupos de Apoyo</NavLink>
                <NavLink to="/citas" className={getNavLinkClass}>Agendar Cita</NavLink>
            </nav>

            <div className="nav-right">
                {!user && <Link to="/login" className="nav-link">Login</Link>}

                {user && perfil && (
                    <div className="user-menu-container" onClick={toggleDropdown}>
                        <div className="user-info">
                            <span className="user-name">{perfil.nombreCompleto}</span>
                            {/* muestra foto o ícono por defecto */}
                            {perfil.urlFotoPerfil && perfil.urlFotoPerfil !== '/uploads/default.jpg' ? (
                                <img
                                    src={`${urlBaseBack}${perfil.urlFotoPerfil}`}
                                    alt="Perfil"
                                    className="user-avatar"
                                />
                            ) : (
                                <div className="user-avatar-icon-default">
                                    <FaUserCircle />
                                </div>
                            )}
                        </div>

                        {dropdownVisible && (
                            <div className="user-dropdown">
                                <Link to="/profile" className="dropdown-item">Perfil</Link>
                                <button onClick={handleLogout} className="dropdown-item">
                                    Cerrar sesión
                                </button>
                            </div>
                        )}
                    </div>
                )}
            </div>
        </header>
    );
};

export default Navbar;