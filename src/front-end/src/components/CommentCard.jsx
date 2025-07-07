import React, { useState , useEffect} from 'react';
import { submitReplyToComment } from '../services/api';
import { fetchCurrentUser } from '../utils/auth';
import { FaUserCircle } from 'react-icons/fa';
import { getPerfilByUserId } from '../services/perfilApi';



const CommentCard = ({ comment, onCommentAdded }) => {
    const [showReplyForm, setShowReplyForm] = useState(false);
    const [replyContent, setReplyContent] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [authorProfile, setAuthorProfile] = useState(null);

  useEffect(() => {
    const fetchAuthor = async () => {
      try {
        const perfil = await getPerfilByUserId(comment.autorId);
        setAuthorProfile(perfil);
      } catch (err) {
        console.error("Error cargando perfil del autor:", err);
      }
    };
    fetchAuthor();
  }, [comment.autorId]);

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
                <div className="comment-header" style={{ display: 'flex', alignItems: 'center' }}>
  {authorProfile?.urlFotoPerfil && authorProfile.urlFotoPerfil !== '/uploads/default.jpg' ? (
    <img
      src={`http://localhost:8081${authorProfile.urlFotoPerfil}`}
      alt="Foto de perfil"
      style={{ width: 28, height: 28, borderRadius: '50%', marginRight: 8 }}
    />
  ) : (
    <FaUserCircle size={28} style={{ marginRight: 8 }} />
  )}
  <strong>{authorProfile?.nombreCompleto || `Usuario ${comment.autorId}`}</strong>
</div>
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
                        placeholder={`Respondiendo a ${authorProfile?.nombreCompleto || `Usuario ${comment.autorId}`}...`}

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