import React from 'react';

const GroupIcon = () => (
    <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style={{ color: '#c4b4d1', marginBottom: '1rem' }}>
        <path d="M17 21V19C17 16.7909 15.2091 15 13 15H5C2.79086 15 1 16.7909 1 19V21" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        <path d="M9 11C11.2091 11 13 9.20914 13 7C13 4.79086 11.2091 3 9 3C6.79086 3 5 4.79086 5 7C5 9.20914 6.79086 11 9 11Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        <path d="M23 21V19C22.9992 17.1856 21.8219 15.6023 20 15" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        <path d="M16 3.13C17.9238 3.59378 19.4062 5.07622 19.87 7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
    </svg>
);

const GruposWelcome = () => {
    return (
        <div className="grupos-welcome">
            <GroupIcon />
            <h2>Bienvenido a los Grupos de Apoyo</h2>
            <p>Selecciona un grupo de la lista para ver las publicaciones o crea uno nuevo para empezar.</p>
        </div>
    );
};

export default GruposWelcome;