import React, { useState, useEffect } from 'react';
import { submitReaction, fetchReactionsForPost } from '../services/api';
import CommentSection from './CommentSection';
import { fetchCurrentUser } from '../utils/auth';

const PostCard = ({ post }) => {
    const [votes, setVotes] = useState(0);
    const [hasVoted, setHasVoted] = useState(false);
    const [showComments, setShowComments] = useState(false);


    useEffect(() => {
        const loadReactions = async () => {
            try {
                const [reactions, user] = await Promise.all([
                    fetchReactionsForPost(post.id),
                    fetchCurrentUser()
                ]);
                setVotes(reactions.length);
                if (reactions.some(r => r.usuarioId === user.id)) {
                    setHasVoted(true);
                }
            } catch (error) {
                console.error("Error al cargar reacciones:", error);
            }
        };
        loadReactions();
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