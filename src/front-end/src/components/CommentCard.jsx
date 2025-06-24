import React, { useState } from 'react';
import { submitReplyToComment } from '../services/api';

const CommentCard = ({ comment, onCommentAdded }) => {
    const [showReplyForm, setShowReplyForm] = useState(false);
    const [replyContent, setReplyContent] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleReplySubmit = async (e) => {
        e.preventDefault();
        if (!replyContent.trim()) return;
        setIsSubmitting(true);
        try {
            // Este endpoint ya existe para responder a un comentario específico
            await submitReplyToComment(comment.id, { contenido: replyContent });
            setReplyContent('');
            setShowReplyForm(false);
            onCommentAdded(); // Llama a la función para recargar todo el árbol de comentarios
        } catch (error) {
            console.error("Error al enviar respuesta:", error);
            alert("No se pudo enviar la respuesta.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="comment-card-container">
            <div className="comment-card">
                <p className="comment-author">Usuario {comment.autorId}</p>
                <p className="comment-content">{comment.contenido}</p>
                <div className="comment-actions">
                    <button onClick={() => setShowReplyForm(!showReplyForm)}>
                        {showReplyForm ? 'Cancelar' : 'Responder'}
                    </button>
                </div>
            </div>

            {showReplyForm && (
                <form onSubmit={handleReplySubmit} className="comment-reply-form">
                    <textarea
                        placeholder={`Respondiendo a Usuario ${comment.autorId}...`}
                        value={replyContent}
                        onChange={(e) => setReplyContent(e.target.value)}
                        disabled={isSubmitting}
                    />
                    <button type="submit" disabled={isSubmitting || !replyContent.trim()}>
                        {isSubmitting ? 'Enviando...' : 'Enviar Respuesta'}
                    </button>
                </form>
            )}

            {comment.respuestas && comment.respuestas.length > 0 && (
                <div className="comment-replies">
                    {comment.respuestas.map(reply => (
                        <CommentCard key={reply.id} comment={reply} onCommentAdded={onCommentAdded} />
                    ))}
                </div>
            )}
        </div>
    );
};

export default CommentCard;