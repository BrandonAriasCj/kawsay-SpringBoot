import React, { useState } from 'react';
import '../styles/Chatbot.css';
import kawsaiLogo from '../assets/kawsai-logo.png';

const Chatbot = () => {
  const [messages, setMessages] = useState([
    { sender: 'bot', text: 'Hola, soy Kawzay. ¿Cómo te sientes hoy?' },
  ]);
  const [input, setInput] = useState('');

  const handleSend = () => {
    if (input.trim() === '') return;

    const newMessages = [...messages, { sender: 'user', text: input }];
    setMessages(newMessages);

    setTimeout(() => {
      setMessages(prev => [
        ...prev,
        { sender: 'bot', text: 'Gracias por compartirlo. Estoy aquí para ti.' },
      ]);
    }, 1000);

    setInput('');
  };

return (
  <div className="chatbot-wrapper">
    <div className="chatbot-container">
      <div className="chat-header">
  <img src={kawsaiLogo} alt="KawsAI Logo" className="chat-logo" />
  KawsAI - Historias Interactivas
</div>


      <div className="chat-window">
        {messages.map((msg, idx) => (
          <div key={idx} className={`message ${msg.sender}`}>
            {msg.text}
          </div>
        ))}
      </div>
      <div className="input-area">
        <input
          type="text"
          value={input}
          placeholder="Escribe algo..."
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSend()}
        />
        <button onClick={handleSend}>Enviar</button>
      </div>
    </div>
  </div>
);

};

export default Chatbot;
