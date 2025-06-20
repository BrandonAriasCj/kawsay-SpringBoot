import React from 'react';
import PostCard from './PostCard';
import CreatePostForm from './CreatePostForm';

const Feed = ({ posts, isLoading, error, onCreatePost, isSubmittingPost }) => (
    <div className="feed">
        <CreatePostForm onSubmit={onCreatePost} isSubmitting={isSubmittingPost} />
        <hr className="feed-separator" />

        {isLoading && <div className="feed-status">Cargando publicaciones...</div>}
        {error && <div className="feed-status error">Error al cargar las publicaciones.</div>}
        {!isLoading && !error && posts.length === 0 && <div className="feed-status">Aún no hay publicaciones. ¡Sé el primero!</div>}

        {!isLoading && !error && posts.map(post => <PostCard key={post.id} post={post} />)}
    </div>
);
export default Feed;