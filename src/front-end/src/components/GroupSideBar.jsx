import React, { useState, useEffect } from 'react';

const GroupSidebar = ({ activeTab, groupsData, onSelectGroup, onJoinGroup, selectedGroupId, searchTerm, onSearchChange, userGroupIds,userId }) => {
    const [selectedCategory, setSelectedCategory] = useState(null);

    // Reiniciar la categoría seleccionada al cambiar de pestaña
    useEffect(() => {
        if (activeTab === 'discover') {
            const categories = Object.keys(groupsData.discover);
            // Selecciona la primera categoría por defecto si no hay ninguna seleccionada
            if (!selectedCategory && categories.length > 0) {
                setSelectedCategory(categories[0]);
            }
        }
    }, [activeTab, groupsData.discover, selectedCategory]);

    const renderDiscoverTab = () => {
        const categories = Object.keys(groupsData.discover);
        const groupsToShow = selectedCategory ? groupsData.discover[selectedCategory] : [];

        return (
            <div className="discover-layout">
                <div className="category-list">
                    <h4>Categorías</h4>
                    {categories.length > 0 ? (
                        categories.map(category => (
                            <button
                                key={category}
                                className={`category-button ${selectedCategory === category ? 'active' : ''}`}
                                onClick={() => setSelectedCategory(category)}
                            >
                                {category}
                            </button>
                        ))
                    ) : <p className="empty-message">No hay grupos para descubrir.</p>
                    }
                </div>
                <div className="group-list-panel">
                    {selectedCategory && (
                        groupsToShow.map(group => {
                            const isMember = userGroupIds.has(group.id);
                            return (
                                <div key={group.id} className="discover-group-card">
                                    <h5>{group.nombre}</h5>
                                    <p>{group.descripcion}</p>
                                    <div className="discover-card-footer">
                                        <button onClick={() => onSelectGroup(group.id)}>Ver Feed</button>
                                        {group.creadorId === userId ? (
                                            <div className="group-badge badge-creator">
                                                👑 Creador
                                            </div>
                                        ) : userGroupIds.has(group.id) ? (
                                            <div className="group-badge badge-member">
                                                ✅ Miembro
                                            </div>
                                        ) : (
                                            <button className="join-btn modern-btn" onClick={() => onJoinGroup(group.id)}>
                                                ➕ Unirse
                                            </button>
                                        )}


                                    </div>
                                </div>
                            );
                        })
                    )}
                </div>
            </div>
        );
    };

    const renderSimpleList = (groups, status) => (
        <div className="simple-group-list">
            {groups.length > 0 ? (
                groups.map(group => (
                    <div
                        key={group.id}
                        className={`simple-list-item ${selectedGroupId === group.id ? 'active' : ''}`}
                        onClick={() => onSelectGroup(group.id)}
                    >
                        <span className="group-icon">{group.nombre.charAt(0).toUpperCase()}</span>
                        <span>{group.nombre}</span>
                        {status === 'creator' && <span className="creator-badge">Creador</span>}
                    </div>
                ))
            ) : <p className="empty-message">No hay grupos en esta sección.</p>
            }
        </div>
    );

    return (
        <div className="group-sidebar-content-wrapper">
            <div className="search-container-redefined">
                <input type="text" placeholder="🔍 Buscar un grupo..." className="group-search-input" value={searchTerm} onChange={(e) => onSearchChange(e.target.value)} />
            </div>
            <div className="group-sidebar-content">
                {activeTab === 'discover' && renderDiscoverTab()}
                {activeTab === 'joined' && renderSimpleList(groupsData.joined, 'joined')}
                {activeTab === 'created' && renderSimpleList(groupsData.created, 'creator')}
            </div>
        </div>
    );
};

export default GroupSidebar;