import React, { useState } from 'react';

const CreatePostForm = ({ onSubmit, isSubmitting }) => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!title.trim() || !content.trim()) return;
        onSubmit({ title, content });
        setTitle('');
        setContent('');
    };

    return (
        <div className="create-post-container">
            <h4>Crear una nueva publicación</h4>
            <form onSubmit={handleSubmit} className="create-post-form">
                <input type="text" placeholder="Escribe un título..." value={title} onChange={(e) => setTitle(e.target.value)} disabled={isSubmitting}/>
                <textarea placeholder="Describe tu pregunta o comparte tu experiencia..." value={content} onChange={(e) => setContent(e.target.value)} disabled={isSubmitting}/>
                <button type="submit" disabled={isSubmitting || !title.trim() || !content.trim()}>
                    {isSubmitting ? 'Publicando...' : 'Publicar'}
                </button>
            </form>
        </div>
    );
};
export default CreatePostForm;