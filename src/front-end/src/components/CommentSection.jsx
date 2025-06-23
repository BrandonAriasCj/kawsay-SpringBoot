import React, { useState, useEffect } from 'react';
import { fetchCommentsForPost, submitComment } from '../services/api';
import CommentCard from './CommentCard';

const CommentSection = ({ postId }) => {
    const [commentTree, setCommentTree] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [isLoading, setIsLoading] = useState(true);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const loadComments = () => {
        setIsLoading(true);
        fetchCommentsForPost(postId)
            .then(setCommentTree)
            .catch(console.error)
            .finally(() => setIsLoading(false));
    };

    useEffect(() => {
        loadComments();
    }, [postId]);

    const handleSubmitRootComment = async (e) => {
        e.preventDefault();
        if (!newComment.trim()) return;

        setIsSubmitting(true);
        try {
            // Este endpoint ya existe en tu backend para añadir un comentario a una publicación
            await submitComment(postId, { contenido: newComment });
            setNewComment('');
            loadComments(); // Recargar todo el árbol para mostrar el nuevo comentario
        } catch (error) {
            console.error("Error al enviar comentario:", error);
            alert("No se pudo enviar el comentario.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="comment-section">
            <h5 className="comment-section-title">Comentarios</h5>
            <form onSubmit={handleSubmitRootComment} className="comment-form">
                <textarea
                    placeholder="Escribe un comentario..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    disabled={isSubmitting}
                />
                <button type="submit" disabled={isSubmitting || !newComment.trim()}>
                    {isSubmitting ? 'Enviando...' : 'Comentar'}
                </button>
            </form>
            <div className="comment-list">
                {isLoading ? (
                    <p>Cargando comentarios...</p>
                ) : (
                    commentTree.length > 0 ? (
                        commentTree.map(comment => <CommentCard key={comment.id} comment={comment} onCommentAdded={loadComments} />)
                    ) : (
                        <p className="no-comments-message">Aún no hay comentarios. ¡Sé el primero en responder!</p>
                    )
                )}
            </div>
        </div>
    );
};

export default CommentSection;