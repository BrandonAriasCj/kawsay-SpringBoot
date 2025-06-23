import React from 'react';

const statusBadges = {
    creator: { text: 'Creador', className: 'status-creator' },
    joined: { text: 'Miembro', className: 'status-joined' },
};

const GroupList = ({ classifiedGroups, selectedGroupId, onSelectGroup, searchTerm, onSearchChange, onOpenCreateModal, onJoinGroup }) => (
    <aside className="sidebar">
        <button className="create-new-group-btn" onClick={onOpenCreateModal}>+ Crear Nuevo Grupo</button>
        <div className="search-container">
            <input type="text" placeholder="🔍 Buscar un grupo..." className="group-search-input" value={searchTerm} onChange={(e) => onSearchChange(e.target.value)} />
        </div>
        <nav className="group-nav-list">
            {Object.keys(classifiedGroups).length > 0 ? (
                Object.entries(classifiedGroups).map(([category, groupsInCategory]) => (
                    <div key={category} className="category-section">
                        <h3 className="category-header">{category}</h3>
                        {groupsInCategory.map(group => {
                            const badge = statusBadges[group.status];
                            return (
                                <div key={group.id} className={`group-nav-item ${group.id === selectedGroupId ? 'active' : ''}`}>
                                    <div className="group-info" onClick={() => onSelectGroup(group.id)}>
                                        <span className="group-nav-icon">{group.nombre.charAt(0).toUpperCase()}</span>
                                        <span className="group-nav-name">{group.nombre}</span>
                                        {badge && <span className={`status-badge ${badge.className}`}>{badge.text}</span>}
                                    </div>
                                    {group.status === 'discover' && (
                                        <button
                                            className="join-group-btn"
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                onJoinGroup(group.id);
                                            }}>
                                            Unirse
                                        </button>
                                    )}
                                </div>
                            );
                        })}
                    </div>
                ))
            ) : (<div className="no-groups-found">No se encontraron grupos.</div>)}
        </nav>
    </aside>
);

export default GroupList;