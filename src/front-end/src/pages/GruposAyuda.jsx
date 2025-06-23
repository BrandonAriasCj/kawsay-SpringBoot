import React, { useState, useEffect, useMemo } from 'react';
import Tabs from '../components/Tabs';
import GroupSidebar from '../components/GroupSidebar';
import Feed from '../components/Feed';
import Modal from '../components/Modal';
import GruposWelcome from '../components/GruposWelcome';
import { fetchGroups, fetchPostsByGroup, submitNewPost, submitNewGroup, joinGroup, fetchUserGroups } from '../services/api';
import '../styles/GruposAyuda.css';
import '../styles/Modal.css';

const CURRENT_USER_ID = 1;

const GruposAyuda = () => {
    const [activeTab, setActiveTab] = useState('discover');
    const [allGroups, setAllGroups] = useState([]);
    const [userGroupIds, setUserGroupIds] = useState(new Set());
    const [posts, setPosts] = useState([]);
    const [selectedGroupId, setSelectedGroupId] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState(''); // RE-AÑADIDO
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isSubmittingGroup, setIsSubmittingGroup] = useState(false);
    const [newGroupName, setNewGroupName] = useState('');
    const [newGroupDescription, setNewGroupDescription] = useState('');
    const [newGroupCategory, setNewGroupCategory] = useState('');
    const [isSubmittingPost, setIsSubmittingPost] = useState(false);

    useEffect(() => {
        const loadInitialData = async () => {
            try {
                setIsLoading(true);
                const [groupsData, userGroupsData] = await Promise.all([ fetchGroups(), fetchUserGroups(CURRENT_USER_ID) ]);
                setAllGroups(groupsData);
                setUserGroupIds(new Set(userGroupsData.map(g => g.id)));
            } catch (error) { console.error("Error al cargar datos:", error); }
            finally { setIsLoading(false); }
        };
        loadInitialData();
    }, []);

    const classifiedAndFilteredGroups = useMemo(() => {
        const lowerCaseSearch = searchTerm.toLowerCase();

        // LÓGICA CORREGIDA
        const created = allGroups.filter(g => g.creadorId === CURRENT_USER_ID && g.nombre.toLowerCase().includes(lowerCaseSearch));
        const joined = allGroups.filter(g => userGroupIds.has(g.id) && g.nombre.toLowerCase().includes(lowerCaseSearch));

        const discover = allGroups.reduce((acc, group) => {
            if (group.nombre.toLowerCase().includes(lowerCaseSearch)) {
                if (!acc[group.categoria]) acc[group.categoria] = [];
                acc[group.categoria].push(group);
            }
            return acc;
        }, {});

        return { created, joined, discover };
    }, [allGroups, userGroupIds, searchTerm]);

    const selectedGroupDetails = useMemo(() => {
        return selectedGroupId ? allGroups.find(g => g.id === selectedGroupId) : null;
    }, [selectedGroupId, allGroups]);

    useEffect(() => {
        if (!selectedGroupId) { setPosts([]); return; }
        fetchPostsByGroup(selectedGroupId).then(setPosts).catch(console.error);
    }, [selectedGroupId]);

    const handleJoinGroup = async (groupId) => {
        try {
            await joinGroup(groupId);
            alert(`Te has unido al grupo exitosamente.`);
            setUserGroupIds(prev => new Set(prev).add(groupId));
        } catch (err) { alert("Error al unirse al grupo."); }
    };

    const handleCreateGroupSubmit = async (e) => {
        e.preventDefault();
        setIsSubmittingGroup(true);
        try {
            const newGroupData = { name: newGroupName, description: newGroupDescription, category: newGroupCategory };
            const newGroup = await submitNewGroup(newGroupData);
            setAllGroups(prev => [...prev, newGroup]);
            setUserGroupIds(prev => new Set(prev).add(newGroup.id)); // IMPORTANTE: Asegurarse de que el creador es miembro
            setSelectedGroupId(newGroup.id);
            setActiveTab('created');
            setIsModalOpen(false);
            setNewGroupName(''); setNewGroupDescription(''); setNewGroupCategory('');
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

    return (
        <>
            <div className="page-wrapper-grupos">
                <div className="layout-container">
                    <aside className="sidebar-redefined">
                        <div className="sidebar-header">
                            <button className="create-new-group-btn" onClick={() => setIsModalOpen(true)}>+ Crear Nuevo Grupo</button>
                        </div>
                        <Tabs activeTab={activeTab} onTabClick={setActiveTab} />
                        <GroupSidebar
                            activeTab={activeTab}
                            groupsData={classifiedAndFilteredGroups}
                            onSelectGroup={setSelectedGroupId}
                            onJoinGroup={handleJoinGroup}
                            selectedGroupId={selectedGroupId}
                            searchTerm={searchTerm}
                            onSearchChange={setSearchTerm}
                            userGroupIds={userGroupIds}
                        />
                    </aside>
                    <main className="main-content">
                        {selectedGroupDetails ? (
                            <>
                                <header className="main-content-header-redesigned">
                                    <h2 className="group-title">{selectedGroupDetails.nombre}</h2>
                                    <span className="group-category-badge">{selectedGroupDetails.categoria}</span>
                                    <p className="group-description">{selectedGroupDetails.descripcion}</p>
                                </header>
                                <Feed posts={posts} onCreatePost={handleCreatePost} isSubmittingPost={isSubmittingPost} />
                            </>
                        ) : (
                            <GruposWelcome />
                        )}
                    </main>
                </div>
            </div>

            <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} title="Crear un Nuevo Grupo">
                <form className="create-group-form" onSubmit={handleCreateGroupSubmit}>
                    <div className="form-group"><label htmlFor="groupName">Nombre del Grupo</label><input id="groupName" type="text" value={newGroupName} onChange={(e) => setNewGroupName(e.target.value)} required /></div>
                    <div className="form-group"><label htmlFor="groupCategory">Categoría</label><input id="groupCategory" type="text" value={newGroupCategory} onChange={(e) => setNewGroupCategory(e.target.value)} required /></div>
                    <div className="form-group"><label htmlFor="groupDescription">Descripción</label><textarea id="groupDescription" value={newGroupDescription} onChange={(e) => setNewGroupDescription(e.target.value)} required /></div>
                    <button type="submit" disabled={isSubmittingGroup || !newGroupName.trim() || !newGroupDescription.trim()}>{isSubmittingGroup ? "Creando..." : "Crear Grupo"}</button>
                </form>
            </Modal>
        </>
    );
};

export default GruposAyuda;