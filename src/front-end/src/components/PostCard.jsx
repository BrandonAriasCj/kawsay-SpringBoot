import React, { useState, useEffect } from 'react';
import { submitReaction, fetchReactionsForPost } from '../services/api';
import CommentSection from './CommentSection';

const PostCard = ({ post }) => {
    const [votes, setVotes] = useState(0);
    const [hasVoted, setHasVoted] = useState(false);
    const [showComments, setShowComments] = useState(false);

    useEffect(() => {
        // Cargar las reacciones iniciales de la publicación
        fetchReactionsForPost(post.id).then(reactions => {
            setVotes(reactions.length);
            // Comprobar si el usuario actual ya ha votado (asumiendo ID=1)
            if (reactions.some(r => r.usuarioId === 1)) {
                setHasVoted(true);
            }
        }).catch(console.error);
    }, [post.id]);

    const handleVote = async () => {
        if (hasVoted) return;
        try {
            await submitReaction(post.id, 'LIKE');
            setVotes(prev => prev + 1);
            setHasVoted(true);
        } catch (error) {
            console.error("Error al votar:", error);
            alert("No se pudo registrar el voto.");
        }
    };

    return (
        <div className="post-card">
            <div className="post-card-header">
                <span className="post-user">{post.user}</span>
            </div>
            <h4>{post.question}</h4>
            <p>{post.content}</p>
            <div className="post-card-footer">
                <button className={`action-btn vote-btn ${hasVoted ? 'voted' : ''}`} onClick={handleVote} disabled={hasVoted}>
                    👍 Votar ({votes})
                </button>
                <button className='action-btn' onClick={() => setShowComments(prev => !prev)}>
                    💬 {showComments ? 'Ocultar Comentarios' : 'Comentar'}
                </button>
            </div>
            {showComments && <CommentSection postId={post.id} />}
        </div>
    );
};

export default PostCard;