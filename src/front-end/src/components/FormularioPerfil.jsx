import React, { useState } from 'react';
import axios from 'axios';
import { Form } from 'react-router';

const FormularioPerfil = ({ perfilData, onChange, onNext}) => {

  return (
  <div className="space-y-4">
  <label className="block">
    <span className="text-sm font-semibold">Nombre completo</span>
    <input
      type="text"
      name="nombreCompleto"
      value={perfilData.nombreCompleto}
      onChange={onChange}
      required
      className="mt-1 w-full border p-2 rounded"
      placeholder="Ej. Axel Mendoza"
    />
  </label>

  <label className="block">
    <span className="text-sm font-semibold">Carrera</span>
    <input
      type="text"
      name="carrera"
      value={perfilData.carrera}
      onChange={onChange}
      required
      className="mt-1 w-full border p-2 rounded"
      placeholder="Ej. Ingeniería de Software"
    />
  </label>

  <label className="block">
    <span className="text-sm font-semibold">Descripción</span>
    <textarea
      name="descripcion"
      value={perfilData.descripcion}
      onChange={onChange}
      rows={3}
      required
      className="mt-1 w-full border p-2 rounded"
      placeholder="Cuéntanos brevemente sobre ti"
    />
  </label>

  <div className="flex justify-end pt-4">
    <button
      type="button"
      onClick={onNext} // esta función cambia al paso 2
      className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
    >
      Siguiente
    </button>
  </div>
</div>
  );
};

export default FormularioPerfil;
