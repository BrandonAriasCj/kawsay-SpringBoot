// src/components/FormularioPreferencias.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/FormPreferences.css';   // ⬅️ importa los estilos
import { urlBaseBack } from '../utils/path';

const FormularioPreferencias = ({
  seleccionadas,
  setSeleccionadas,
  onSubmit,
  onBack,
}) => {
  const [localCatalogo, setLocalCatalogo] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const jwtToken = localStorage.getItem('jwtToken');
    axios
      .get(`${urlBaseBack}/api/preferencias`, {
        headers: { Authorization: `Bearer ${jwtToken}` },
      })
      .then((res) => setLocalCatalogo(res.data))
      .catch((err) => console.error('Error al cargar preferencias:', err))
      .finally(() => setLoading(false));
  }, []);

  const handleCheckboxChange = (valor) => {
    setSeleccionadas((prev) =>
      prev.includes(valor) ? prev.filter((v) => v !== valor) : [...prev, valor]
    );
  };

  if (loading) return <p className="form-loading">Cargando preferencias…</p>;

  return (
    <div className="form-wrapper">
      <h2 className="form-title">Selecciona tus preferencias</h2>

      {Object.entries(localCatalogo).map(([tipo, valores]) => (
        <div key={tipo} className="form-section">
          <h3 className="form-section-title">{tipo.replace('_', ' ')}</h3>

          <div className="form-checkbox-group">
            {valores.map((valor) => (
              <label key={valor} className="form-checkbox-label">
                <input
                  type="checkbox"
                  value={valor}
                  checked={seleccionadas.includes(valor)}
                  onChange={() => handleCheckboxChange(valor)}
                  className="form-checkbox-input"
                />
                <span>{valor}</span>
              </label>
            ))}
          </div>
        </div>
      ))}

      <div className="form-actions">
        <button type="button" onClick={onBack} className="btn btn-secondary">
          ← Volver
        </button>

        <button type="button" onClick={onSubmit} className="btn btn-primary">
          Finalizar
        </button>
      </div>
    </div>
  );
};

export default FormularioPreferencias;
