// src/components/GroupList.jsx
import React from 'react';

const GroupList = ({ groups, selectedGroupId, onSelectGroup, searchTerm, onSearchChange, onOpenCreateModal }) => (
    <aside className="sidebar">
        <button className="create-new-group-btn" onClick={onOpenCreateModal}>+ Crear Nuevo Grupo</button>
        <div className="search-container">
            <input type="text" placeholder="🔍 Buscar un grupo..." className="group-search-input" value={searchTerm} onChange={(e) => onSearchChange(e.target.value)} />
        </div>
        <nav className="group-nav-list">
            {groups.length > 0 ? (
                groups.map(group => (
                    <div key={group.id} className={`group-nav-item ${group.id === selectedGroupId ? 'active' : ''}`} onClick={() => onSelectGroup(group.id)}>
                        {/* Lógica para mostrar insignia o icono */}
                        {group.icon === 'NEW' ?
                            <span className="group-nav-new-badge">NEW</span> :
                            <span className="group-nav-icon">{group.icon}</span>
                        }
                        <span className="group-nav-name">{group.name}</span>
                    </div>
                ))
            ) : (<div className="no-groups-found">No se encontraron grupos.</div>)}
        </nav>
    </aside>
);

export default GroupList;