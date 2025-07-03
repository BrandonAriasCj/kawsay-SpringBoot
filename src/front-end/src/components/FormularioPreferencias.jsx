// src/components/FormularioPreferencias.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FormularioPreferencias = ({
  seleccionadas,
  setSeleccionadas,
  onSubmit,
  onBack
}) => {
  const [localCatalogo, setLocalCatalogo] = useState({});
  const [loading, setLoading] = useState(true);

useEffect(() => {
  const jwtToken = localStorage.getItem('jwtToken');
  axios.get('http://localhost:8081/api/preferencias', {
    headers: {
      Authorization: `Bearer ${jwtToken}`
    }
  })
    .then(res => setLocalCatalogo(res.data))
    .catch(err => console.error('Error al cargar preferencias:', err))
    .finally(() => setLoading(false));
}, []);

  const handleCheckboxChange = valor => {
    setSeleccionadas(prev =>
      prev.includes(valor)
        ? prev.filter(v => v !== valor)
        : [...prev, valor]
    );
  };

  if (loading) {
    return <p className="text-center">Cargando preferencias…</p>;
  }

  return (
    <div className="space-y-6">
      <h2 className="text-xl font-semibold">Selecciona tus preferencias</h2>

      {Object.entries(localCatalogo).map(([tipo, valores]) => (
        <div key={tipo}>
          <h3 className="text-sm font-bold capitalize mb-2">
            {tipo.replace('_', ' ')}
          </h3>
          <div className="flex flex-wrap gap-4">
            {valores.map(valor => (
              <label key={valor} className="inline-flex items-center">
                <input
                  type="checkbox"
                  value={valor}
                  checked={seleccionadas.includes(valor)}
                  onChange={() => handleCheckboxChange(valor)}
                  className="mr-2"
                />
                <span>{valor}</span>
              </label>
            ))}
          </div>
        </div>
      ))}

      <div className="flex justify-between pt-6">
        <button
          type="button"
          onClick={onBack}
          className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
        >
          ← Volver
        </button>
        <button
          type="button"
          onClick={onSubmit}
          className="px-4 py-2 bg-purple-600 text-white rounded hover:bg-purple-700"
        >
          Finalizar
        </button>
      </div>
    </div>
  );
};

export default FormularioPreferencias;
