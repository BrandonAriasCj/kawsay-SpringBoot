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
          {!perfilData.nombreCompleto.trim() && (
    <p className="text-sm text-red-500 mt-1">* Este campo es obligatorio</p>
  )}
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
          {!perfilData.carrera.trim() && (
    <p className="text-sm text-red-500 mt-1">* Este campo es obligatorio</p>
  )}
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
          {!perfilData.descripcion.trim() && (
    <p className="text-sm text-red-500 mt-1">* Este campo es obligatorio</p>
  )}
      </div >
      <div className="perfil-form-acciones">
        <button
            type="button"
            onClick={onNext}
            className="px-5 py-2 rounded-md bg-purple-600 text-white hover:bg-purple-700"
        >
            Siguiente
        </button>
      </div>

    </form>
  );
};

export default FormularioPerfil;
