import React, { useContext, useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext.jsx';
import { signOut } from '@aws-amplify/auth';
import '../styles/Navbar.css';

const Navbar = () => {
    const { user } = useContext(AuthContext);
    const [perfil, setPerfil] = useState(null);
    const [dropdownVisible, setDropdownVisible] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPerfil = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await fetch('http://localhost:8081/api/perfil', {
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

        if (user) fetchPerfil();
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

    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
    };

    return (
        <nav className="main-header navbar">
            <h1>KawsAi</h1>

            <div className="nav-left">
                <Link to="/">Inicio</Link>
                <Link to="/chatbot">Chatbot</Link>
                <Link to="/grupos">Grupos de Apoyo</Link>
                <Link to="/citas">Agendar Cita</Link>
            </div>

            <div className="nav-right">
                {!user && <Link to="/login">Login / Registro</Link>}

                {user && perfil && (
                    <div className="user-menu-container" onClick={toggleDropdown}>
                        <div className="user-info">
                            <span className="user-name">{perfil.nombreCompleto}</span>
                            <img
                                src={`http://localhost:8081${perfil.urlFotoPerfil}`}
                                alt="Perfil"
                                className="user-avatar"
                            />
                        </div>

                        {dropdownVisible && (
                            <div className="user-dropdown">
                                <Link to="/profile" className="dropdown-item">Perfil</Link>
                                <button onClick={handleLogout} className="dropdown-item logout-btn">
                                    Cerrar sesión
                                </button>
                            </div>
                        )}
                    </div>
                )}
            </div>
        </nav>
    );
};

export default Navbar;
