import React, { useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import '../styles/NotFound.css';
import { createParticles } from '../utils/landingJs';

const NotFound = () => {

    useEffect(() => {
  const container = document.getElementById('particles');
  if (container) {
    createParticles();
  }
}, []);



  return (
    <>
    <NavLink to="/" className="back-home-button">← Volver al inicio</NavLink>
    <div className="not-found-page">
       
      <div id="particles"></div>
      <div className="not-found-content">

        
        <img src="cat.gif" />
        <h1>404</h1>
        <p>La página que buscas no existe.</p>
      </div>
    </div>
    </>
  );
};

export default NotFound;
