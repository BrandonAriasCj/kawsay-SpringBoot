// src/components/Navbar.jsx
import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext.jsx'; // ‒ tu contexto
import '../styles/Navbar.css';

const Navbar = () => {
  const { user } = useContext(AuthContext);

  return (
    <nav className="navbar">
      <h1>Kawzay</h1>
      <div className="links">
        <Link to="/">Inicio</Link>
        {!user && <Link to="/login">Login / Registro</Link>}
        {user && <Link to="/profile">Perfil</Link>}
      </div>
    </nav>
  );
};

export default Navbar;
