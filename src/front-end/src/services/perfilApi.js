import apiClient from './axiosInstance';

// Obtener perfil del usuario autenticado
export const getPerfil = async () => {
    const response = await apiClient.get('/api/perfil');
    return response.data;
};

// Actualizar perfil del usuario autenticado
export const updatePerfil = async (perfilData) => {
    const response = await apiClient.put('/api/perfil', perfilData);
    return response.data;
};
export const uploadAndUpdateFotoPerfil = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await apiClient.post('/api/perfil/foto/actualizar', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
    return response.data; // URL de la imagen
};