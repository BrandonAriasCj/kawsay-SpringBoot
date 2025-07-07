import React, { useState, useEffect } from 'react';
import { submitReaction, fetchReactionsForPost } from '../services/api';
import CommentSection from './CommentSection';
import { fetchCurrentUser } from '../utils/auth';
import { getPerfilByUserId } from '../services/perfilApi'; // Asegúrate de que esta función esté definida en tu API
import { FaUserCircle } from 'react-icons/fa';

const PostCard = ({ post }) => {
    const [votes, setVotes] = useState(0);
    const [hasVoted, setHasVoted] = useState(false);
    const [showComments, setShowComments] = useState(false);
    const [profileData, setProfileData] = useState(null);
    const [authorProfile, setAuthorProfile] = useState(null);


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

        useEffect(() => {
        const fetchAuthorProfile = async () => {
            try {
            const perfil = await getPerfilByUserId(post.autorId);
            setAuthorProfile(perfil);
            } catch (error) {
            console.error("Error al obtener perfil del autor:", error);
            }
        };

        if (post.autorId) {
            fetchAuthorProfile();
        }
        }, [post.autorId]);



    return (
        <div className="post-card">
            <div className="post-card-header">


            {authorProfile?.urlFotoPerfil && authorProfile.urlFotoPerfil !== '/uploads/default.jpg' ? (
                <img
                src={`http://localhost:8081${authorProfile.urlFotoPerfil}`}
                alt="Foto de perfil"
                className="author-avatar"
                style={{ width: '32px', height: '32px', borderRadius: '50%', marginRight: '8px' }}
                />
            ) : (
                <div className="author-avatar-icon-default">
                <FaUserCircle size={32} />
                </div>
            )}
            <span className="post-user">
                {authorProfile?.nombreCompleto || `Usuario ${post.autorId}`}
            </span>

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
                <br></br>
            </div>
            <>
            {showComments && <CommentSection postId={post.id} />}
            </>
            
        </div>
    );
};

export default PostCard;