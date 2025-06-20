// src/pages/GruposAyuda.jsx
import React, { useState, useEffect } from 'react';
import GroupList from '../components/GroupList';
import Feed from '../components/Feed';
import Modal from '../components/Modal';
import GruposWelcome from '../components/GruposWelcome';
import { fetchGroups, fetchPostsByGroup, submitNewPost, submitNewGroup } from '../services/api';
import '../styles/GruposAyuda.css';
import '../styles/Modal.css';

const GruposAyuda = () => {
    const [groups, setGroups] = useState([]);
    const [posts, setPosts] = useState([]);
    const [selectedGroupId, setSelectedGroupId] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isSubmittingGroup, setIsSubmittingGroup] = useState(false);
    const [newGroupName, setNewGroupName] = useState('');
    const [newGroupDescription, setNewGroupDescription] = useState('');
    const [isSubmittingPost, setIsSubmittingPost] = useState(false);

    useEffect(() => {
        const handler = setTimeout(() => {
            fetchGroups(searchTerm).then(data => {
                setGroups(data);
                if (selectedGroupId && !data.find(g => g.id === selectedGroupId)) {
                    setSelectedGroupId(null);
                }
            }).catch(setError);
        }, 300);
        return () => clearTimeout(handler);
    }, [searchTerm, selectedGroupId]);

    useEffect(() => {
        if (!selectedGroupId) {
            setPosts([]);
            return;
        }
        setIsLoading(true);
        setError(null);
        fetchPostsByGroup(selectedGroupId).then(setPosts).catch(setError).finally(() => setIsLoading(false));
    }, [selectedGroupId]);

    const handleCreateGroupSubmit = async (e) => {
        e.preventDefault();
        setIsSubmittingGroup(true);
        try {
            const newGroup = await submitNewGroup({ name: newGroupName, description: newGroupDescription });
            setSearchTerm('');
            setGroups(prev => [...prev, newGroup]);
            setSelectedGroupId(newGroup.id);
            setIsModalOpen(false);
            setNewGroupName('');
            setNewGroupDescription('');
        } catch (err) { alert("Error al crear el grupo."); }
        finally { setIsSubmittingGroup(false); }
    };

    const handleCreatePost = async ({ title, content }) => {
        setIsSubmittingPost(true);
        try {
            const newPost = await submitNewPost({ groupId: selectedGroupId, title, content });
            setPosts(prev => [newPost, ...prev]);
        } catch (err) { alert("Error al publicar."); }
        finally { setIsSubmittingPost(false); }
    };

    const selectedGroupName = groups.find(g => g.id === selectedGroupId)?.name;

    return (
        <>
            <div className="page-wrapper-grupos">
                <div className="layout-container">
                    <GroupList
                        groups={groups}
                        selectedGroupId={selectedGroupId}
                        onSelectGroup={setSelectedGroupId}
                        searchTerm={searchTerm}
                        onSearchChange={setSearchTerm}
                        onOpenCreateModal={() => setIsModalOpen(true)}
                    />
                    <main className="main-content">
                        <header className="main-content-header">
                            <h2>{selectedGroupName || 'Grupos de Apoyo'}</h2>
                        </header>

                        {}
                        {selectedGroupId ? (
                            <Feed posts={posts} isLoading={isLoading} error={error} onCreatePost={handleCreatePost} isSubmittingPost={isSubmittingPost} />
                        ) : (
                            <GruposWelcome />
                        )}
                    </main>
                </div>
            </div>

            <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} title="Crear un Nuevo Grupo">
                <form className="create-group-form" onSubmit={handleCreateGroupSubmit}>
                    <div className="form-group"><label htmlFor="groupName">Nombre del Grupo</label><input id="groupName" type="text" placeholder="Ej: Técnicas de Mindfulness" value={newGroupName} onChange={(e) => setNewGroupName(e.target.value)} required /></div>
                    <div className="form-group"><label htmlFor="groupDescription">Descripción</label><textarea id="groupDescription" placeholder="Describe el propósito de este grupo..." value={newGroupDescription} onChange={(e) => setNewGroupDescription(e.target.value)} required /></div>
                    <button type="submit" disabled={isSubmittingGroup || !newGroupName.trim() || !newGroupDescription.trim()}>{isSubmittingGroup ? "Creando..." : "Crear Grupo"}</button>
                </form>
            </Modal>
        </>
    );
};

export default GruposAyuda;