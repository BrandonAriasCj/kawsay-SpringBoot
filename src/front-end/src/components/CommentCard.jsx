import React, { useState } from 'react';
import { submitReplyToComment } from '../services/api';
import { fetchCurrentUser } from '../utils/auth';

const CommentCard = ({ comment, onCommentAdded }) => {
    const [showReplyForm, setShowReplyForm] = useState(false);
    const [replyContent, setReplyContent] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleReplySubmit = async (e) => {
        e.preventDefault();
        if (!replyContent.trim()) return;

        setIsSubmitting(true);
        try {
            const user = await fetchCurrentUser();
            await submitReplyToComment(comment.id, {
                contenido: replyContent,
                autorId: user.id
            });
            setReplyContent('');
            setShowReplyForm(false);
            onCommentAdded();
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