import React, { useEffect, useState } from 'react';
import '../styles/Chatbot.css';
import kawsaiLogo from '../assets/kawsai-logo.png';
import axios from 'axios';

const Chatbot = () => {
  
  
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const idUsuario = 2;

  useEffect(()=>{
          axios.get(`http://localhost:8080/api/${idUsuario}/mensajes10/`)
          .then(response => {
            setMessages(() => response.data);
            console.log("asdfasd")
            console.log(messages)
          })
      .catch(err => console.log(err));
      console.log("Se ejecuta la seccion del axios")
  }, []);


  async function persistirAndTraer() {
    try {
      const response = await axios.post(`http://localhost:8080/api/request/${idUsuario}/mensaje/`, `${input}`,
        {
          headers: {
            'Content-Type': 'text/plain'
          }
        }
      ).then()
      console.log(response)
      axios.get(`http://localhost:8080/api/${idUsuario}/mensajes10/`)
          .then(response => {
            setMessages(() => response.data);
            console.log("asdfasd")
            console.log(messages)
          })
      .catch(err => console.log(err));
      console.log("Se ejecuta la seccion del axios")
    } catch(error) {
      console.error(error)
    }
  }


  const handleSend = () => {
    if (input.trim() === '') return;

    console.log("texto buscado: " + input)
    const newMessages = [{ type: 'assistant', content: "✨✍️✨✍️" }, { type: 'user', content: input }, ...messages];
    setMessages(newMessages);

    persistirAndTraer()




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
          <div key={idx} className={`message ${msg.type.toLowerCase()}`}>
            {msg.content}
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
