import apiClient from './axiosInstance';
import { fetchCurrentUser } from '../utils/auth';

// === GRUPOS ===
export const fetchGroups = async () => {
    const response = await apiClient.get('/api/grupos');
    return response.data;
};

export const fetchUserGroups = async (userId) => {
    const response = await apiClient.get(`/api/grupos/usuario/${userId}`);
    return response.data;
};

export const submitNewGroup = async ({ name, description, category }) => {
    const grupoData = {
        nombre: name,
        descripcion: description,
        categoria: category,
    };
    const response = await apiClient.post('/api/grupos', grupoData);
    return response.data;
};

export const joinGroup = async (groupId) => {
    const response = await apiClient.post('/api/grupos/unirse', {
        grupoId: groupId
    });
    return response.data;
};



// === PUBLICACIONES ===
export const fetchPostsByGroup = async (groupId) => {
    const response = await apiClient.get(`/api/grupos/${groupId}/publicaciones`);
    return response.data.map(post => ({
        id: post.id,
        groupId: post.grupoId,
        user: `Usuario ${post.autorId}`,
        question: post.contenido.split('\n')[0],
        content: post.contenido.split('\n').slice(1).join('\n'),
        votes: 0,
    }));
};

export const submitNewPost = async ({ groupId, title, content }) => {
    const user = await fetchCurrentUser();
    const userId = user?.id;
    const postData = {
        contenido: `${title}\n${content}`,
        autorId: userId,
        grupoId: groupId,
    };
    const response = await apiClient.post(`/api/grupos/${groupId}/publicaciones`, postData);
    const newPost = response.data;
    return {
        id: newPost.id,
        groupId: newPost.grupoId,
        user: `Usuario ${newPost.autorId}`,
        question: title,
        content,
        votes: 0,
    };
};

// === COMENTARIOS ===
export const fetchCommentsForPost = async (postId) => {
    const response = await apiClient.get(`/api/publicaciones/${postId}/comentarios/tree`);
    return response.data;
};

export const submitComment = async (postId, { contenido }) => {
    const user = await fetchCurrentUser();
    const userId = user?.id;
    const commentData = { contenido, autorId: userId };
    const response = await apiClient.post(`/api/publicaciones/${postId}/comentarios`, commentData);
    return response.data;
};

export const submitReplyToComment = async (commentId, { contenido }) => {
    const user = await fetchCurrentUser();
    const userId = user?.id;
    const replyData = { contenido, autorId: userId };
    const response = await apiClient.post(`/api/publicaciones/comentarios/${commentId}/respuestas`, replyData);
    return response.data;
};

// === REACCIONES ===
export const submitReaction = async (postId, reactionType = 'LIKE') => {
    const user = await fetchCurrentUser();
    const userId = user?.id;
    const reactionData = {
        tipo: reactionType,
        usuarioId: userId,
        publicacionId: postId,
    };
    await apiClient.post('/api/reacciones', reactionData);
};

export const fetchReactionsForPost = async (postId) => {
    const response = await apiClient.get(`/api/reacciones/publicacion/${postId}`);
    return response.data;
};
