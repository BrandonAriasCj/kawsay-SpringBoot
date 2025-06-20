import React from 'react';

const PostCard = ({ post }) => {
    return (
        <div className="post-card">
            <div className="post-card-header">
                <span className="post-user">{post.user}</span>
            </div>
            <h4>{post.question}</h4>
            <p>{post.content}</p>
            <div className="post-card-footer">
                <button className='action-btn'>👍 Votar ({post.votes})</button>
                <button className='action-btn'>💬 Responder</button>
            </div>
        </div>
    );
};
export default PostCard;