// utils/auth.js
import apiClient from '../services/axiosInstance';

export const fetchCurrentUser = async () => {
    try {
        const response = await apiClient.post('/api/usuarios/token');
        return response.data; // Retorna { id, correo, rolId }
    } catch (error) {
        console.error("Error al obtener el usuario autenticado:", error);
        throw error;
    }
};
