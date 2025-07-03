// mapper.js
export const mapWizardDataToPerfilInicialDTO = (formData, preferences) => {
  // Extraer las preferencias como un array de strings
  const preferencias = Object.entries(preferences)
    .map(([key, value]) => value)
    .filter(Boolean); // evitar valores vacíos

  return {
    nombreCompleto: formData.nickname,
    carrera: 'Por definir',          // si no lo pides en el paso 1, asigna default
    descripcion: 'Usuario nuevo',    // opcional
    preferencias: preferencias
  };
};
