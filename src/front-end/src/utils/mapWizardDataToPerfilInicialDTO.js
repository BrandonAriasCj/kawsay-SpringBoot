// mapper.js
// utils/mapWizardDataToPerfilInicialDTO.js
export const mapWizardDataToPerfilInicialDTO = (formData, preferenciasSeleccionadas) => {
  return {
    nombreCompleto: formData.nombreCompleto,
    carrera: formData.carrera || 'Por definir',
    descripcion: formData.descripcion || 'Usuario nuevo',
    preferencias: preferenciasSeleccionadas
  };
};
