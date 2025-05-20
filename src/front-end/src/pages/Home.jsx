import React from 'react';
import '../styles/Home.css';

const Home = () => (
  <div className="home-container">
    <h1>Bienvenido a Kawzay</h1>
    <p>Explora un chatbot de apoyo emocional diseñado para escucharte, orientarte y ayudarte a sentirte mejor.</p>

    <img 
      src="/images/emotional-support.png" 
      alt="Chatbot de apoyo emocional" 
      className="home-image" 
    />

    <button className="start-button" onClick={() => window.location.href = '/chatbot'}>
      Comenzar conversación
    </button>

    <section className="features">
      <h2>¿Qué puedes hacer con Kawzay?</h2>
      <ul>
        <li>🧘‍♀️ Hablar sobre tus emociones en un espacio seguro.</li>
        <li>📚 Recibir consejos de bienestar y salud mental.</li>
        <li>🎯 Establecer metas diarias para tu bienestar emocional.</li>
        <li>📆 Agendar citas con un psicólogo.</li>
      </ul>
    </section>
  </div>
);

export default Home;
