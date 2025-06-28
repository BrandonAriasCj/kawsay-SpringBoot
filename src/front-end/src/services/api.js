import axios from 'axios';
import apiClient from '../services/axiosInstance'
const API_URL = 'http://localhost:8081/api';

const apiClientsdfa = axios.create({
    baseURL: API_URL,
    headers: { 'Content-Type': 'application/json' },
});

// --- FUNCIONES DE GRUPOS ---
export const fetchGroups = async () => {
    // eslint-disable-next-line no-useless-catch
    try {
        const response = await apiClient.get('/api/grupos');
        return response.data;
    } catch (error) { throw error; }
};

export const fetchUserGroups = async (userId) => {
    // eslint-disable-next-line no-useless-catch
    try {
        const response = await apiClient.get(`/api/grupos/usuario/${userId}`);
        return response.data;
    } catch (error) { throw error; }
}

export const submitNewGroup = async ({ name, description, category }) => {
    // eslint-disable-next-line no-useless-catch
    try {
        const grupoData = {
            nombre: name,
            descripcion: description,
            categoria: category,
            creadorId: 1, // Hardcoded: Reemplazar con ID de usuario autenticado
            moderadorId: 1, // Hardcoded
        };
        const response = await apiClient.post('api/grupos', grupoData);
        return response.data;
    } catch (error) { throw error; }
};

export const joinGroup = async (groupId) => {
    // eslint-disable-next-line no-useless-catch
    try {
        const requestBody = { usuarioId: 1, grupoId: groupId }; // Hardcoded
        const response = await apiClient.post('/grupos/unirse', requestBody);
        return response.data;
    } catch (error) { throw error; }
};

// --- FUNCIONES DE PUBLICACIONES ---
export const fetchPostsByGroup = async (groupId) => {
    if (!groupId) return [];
    // eslint-disable-next-line no-useless-catch
    try {
        const response = await apiClient.get(`api/grupos/${groupId}/publicaciones`);
        return response.data.map(post => ({
            id: post.id,
            groupId: post.grupoId,
            user: `Usuario ${post.autorId}`,
            question: post.contenido.split('\n')[0],
            content: post.contenido.split('\n').slice(1).join('\n'),
            votes: 0,
        }));
    } catch (error) { throw error; }
};

export const submitNewPost = async ({ groupId, title, content }) => {
    // eslint-disable-next-line no-useless-catch
    try {
        const postData = {
            contenido: `${title}\n${content}`,
            autorId: 1, // Hardcoded
            grupoId: groupId
        };
        const response = await apiClient.post(`/grupos/${groupId}/publicaciones`, postData);
        const newPost = response.data;
        return {
            id: newPost.id,
            groupId: newPost.grupoId,
            user: `Usuario ${newPost.autorId}`,
            question: title,
            content: content,
            votes: 0,
        };
    } catch (error) { throw error; }
};

// --- FUNCIONES DE COMENTARIOS ---
export const fetchCommentsForPost = async (postId) => {
    try {
        // CORREGIDO: Apunta a la ruta unificada en PublicacionController
        const response = await apiClient.get(`/publicaciones/${postId}/comentarios/tree`);
        return response.data; // El backend ya devuelve el árbol, el frontend lo usará
    } catch (error) {
        console.error(`Error al obtener comentarios de la publicación ${postId}:`, error);
        throw error;
    }
};

export const submitComment = async (postId, { contenido }) => {
    try {
        const commentData = { contenido, autorId: 1 };
        // CORREGIDO: Apunta a la ruta unificada
        const response = await apiClient.post(`/publicaciones/${postId}/comentarios`, commentData);
        return response.data;
    } catch (error) {
        console.error(`Error al enviar comentario a la publicación ${postId}:`, error);
        throw error;
    }
};

export const submitReplyToComment = async (commentId, { contenido }) => {
    try {
        const replyData = { contenido, autorId: 1 };
        // CORREGIDO: Apunta a la nueva ruta unificada
        const response = await apiClient.post(`/publicaciones/comentarios/${commentId}/respuestas`, replyData);
        return response.data;
    } catch (error) {
        console.error(`Error al responder al comentario ${commentId}:`, error);
        throw error;
    }
};

// --- FUNCIONES DE REACCIONES ---
export const submitReaction = async (postId, reactionType = 'LIKE') => {
    // eslint-disable-next-line no-useless-catch
    try {
        const reactionData = {
            tipo: reactionType,
            usuarioId: 1, // Hardcoded
            publicacionId: postId
        };
        await apiClient.post('/reacciones', reactionData);
    } catch (error) { throw error; }
};

export const fetchReactionsForPost = async (postId) => {
    try {
        const response = await apiClient.get(`/reacciones/publicacion/${postId}`);
        return response.data;
    } catch (error) {
        console.error(`Error al obtener reacciones de la publicación ${postId}:`, error);
        return []; // Devolver array vacío en caso de error para no romper la UI
    }
};

