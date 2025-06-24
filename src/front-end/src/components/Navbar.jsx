// src/components/Navbar.jsx
import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext.jsx';
import '../styles/Navbar.css';

const Navbar = () => {
  const { user } = useContext(AuthContext);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    const handleMouseMove = (e) => {
      if (e.clientY < 80) {
        setIsVisible(true);
      } else {
        setIsVisible(false);
      }
    };

    window.addEventListener('mousemove', handleMouseMove);

    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
    };
  }, []);





  return (
    <nav className={`main-header ${isVisible ? 'show' : 'hide'} navbar`}>
      <h1>KawzAi</h1>
      <div className="links">
        <Link to="/">Inicio</Link>
        <Link to="/chatbot">Chatbot</Link>
          <Link to="/grupos">Grupos de Apoyo </Link>
          <Link to="/citas">Agendar Cita</Link>
        <Link to="/profile">Perfil</Link>
        {!user && <Link to="/login">Login / Registro</Link>}
        {user && <Link to="/profile">Perfil</Link>}

      </div>
    </nav>
  );
};

export default Navbar;
