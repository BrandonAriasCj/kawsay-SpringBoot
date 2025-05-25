import React, { useState } from 'react';
import axios from 'axios';

const FirstLoginModal = ({ userEmail, onComplete }) => {
  const [formData, setFormData] = useState({
    nickname: '',
    token: '',
  });

  const [preferences, setPreferences] = useState({
    tipoContenido: '',
    formaPensar: '',
    tipoFinal: '',
    reaccionHistoria: '',
    rolProtagonista: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (preferences.hasOwnProperty(name)) {
      setPreferences((prev) => ({ ...prev, [name]: value }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

    const handleSubmit = async (e) => {
    e.preventDefault();

    try {

            localStorage.setItem('userPreferences', JSON.stringify(preferences));


            const dataToSend = {
            mail: userEmail,
            password: "12345", 
            nickname: formData.nickname,
            ...(formData.token && { token: formData.token }), 
            };

            console.log("Datos enviados a la API:", JSON.stringify(dataToSend, null, 2));

            await axios.post('http://127.0.0.1:8000/api/registro/', dataToSend);

            localStorage.setItem('firstLoginCompleted', 'true');
            onComplete(); 
        } catch (error) {
            console.error('Error al registrar datos:', error);
        }
    };


  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-lg">
        <h2 className="text-xl font-bold mb-4">¡Personaliza tu experiencia!</h2>
        <form onSubmit={handleSubmit} className="space-y-4">

    
          <input name="nickname" placeholder="Alias o Nickname" onChange={handleChange} required className="w-full p-2 border" />
          <input name="token" placeholder="Token (opcional)" onChange={handleChange} className="w-full p-2 border" />

          <label className="block font-semibold">Tus preferencias</label>
          <select name="tipoContenido" onChange={handleChange} required className="w-full p-2 border">
            <option value="">Selecciona...</option>
            <option>Acción</option>
            <option>Comedia</option>
            <option>Psicológico</option>
          </select>

          <button type="submit" className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700 w-full">
            Enviar
          </button>
        </form>
      </div>
    </div>
  );
};

export default FirstLoginModal;
