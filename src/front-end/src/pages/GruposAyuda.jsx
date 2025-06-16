// src/pages/GruposAyuda.jsx
import React from 'react';
import '../styles/GruposAyuda.css';

// Datos de ejemplo. En el futuro, esto vendrá de tu API backend.
const mockGroups = [
    { id: 1, name: 'Manejo de la Ansiedad', description: 'Un espacio para compartir técnicas y experiencias sobre cómo afrontar la ansiedad.', members: 42 },
    { id: 2, name: 'Viviendo con TDAH', description: 'Grupo para adultos con TDAH. Estrategias, apoyo y comprensión mutua.', members: 28 },
    { id: 3, name: 'Superando la Depresión', description: 'Juntos es más fácil. Comparte tu viaje y encuentra apoyo en un entorno seguro.', members: 55 },
    { id: 4, name: 'Crecimiento Personal', description: 'Foro para discutir libros, hábitos y metas para ser nuestra mejor versión.', members: 112 },
];

const GruposAyuda = () => {
    return (
        <div className="grupos-wrapper">
            <div className="grupos-container">
                <header className="grupos-header">
                    <h2>Encuentra tu Comunidad</h2>
                    <p>Los grupos de apoyo son espacios seguros para compartir y crecer juntos.</p>
                </header>

                <div className="grupos-actions">
                    <button className="create-group-btn">Crear Nuevo Grupo</button>
                </div>

                <div className="group-list">
                    {mockGroups.map(group => (
                        <div key={group.id} className="group-card">
                            <h3>{group.name}</h3>
                            <p>{group.description}</p>
                            <div className="group-card-footer">
                                <span>{group.members} miembros</span>
                                <button className="join-btn">Unirse al Grupo</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default GruposAyuda;