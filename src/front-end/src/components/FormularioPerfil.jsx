import React, { useState } from 'react';
import axios from 'axios';
import { Form } from 'react-router';
import '../styles/Form.css';
const FormularioPerfil = ({ perfilData, onChange, onNext}) => {

return (
    <form className="perfil-form">
      <h3 className="perfil-form-titulo">Información del perfil</h3>
      <p className="perfil-form-descripcion">Completa tus datos para personalizar tu experiencia.</p>

      <div className="perfil-form-grupo">
        <label htmlFor="nombreCompleto">Nombre completo</label>
        <input
          type="text"
          id="nombreCompleto"
          name="nombreCompleto"
          value={perfilData.nombreCompleto}
          onChange={onChange}
          required
          placeholder="Ej. Axel Mendoza"
        />
      </div>

      <div className="perfil-form-grupo">
        <label htmlFor="carrera">Carrera</label>
        <input
          type="text"
          id="carrera"
          name="carrera"
          value={perfilData.carrera}
          onChange={onChange}
          required
          placeholder="Ej. Ingeniería de Software"
        />
      </div>

      <div className="perfil-form-grupo">
        <label htmlFor="descripcion">Descripción</label>
        <textarea
          id="descripcion"
          name="descripcion"
          value={perfilData.descripcion}
          onChange={onChange}
          rows={4}
          required
          placeholder="Cuéntanos brevemente sobre ti"
        />
      </div>

      <div className="perfil-form-acciones">
        <button type="button" onClick={onNext}>Siguiente</button>
      </div>
    </form>
  );
};

export default FormularioPerfil;
