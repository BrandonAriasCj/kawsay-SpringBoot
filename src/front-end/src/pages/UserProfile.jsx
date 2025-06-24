// src/pages/UserProfile.jsx

import React, { useState, useEffect } from 'react';
import '../styles/UserProfile.css';


const mockProfileData = {
    email: 'test.user@kawsay.edu',
    nombreCompleto: 'Alex Córdova',
    carrera: 'Ingeniería de Sistemas Computacionales',
    descripcion: 'Entusiasta del desarrollo full-stack y la inteligencia artificial. En mi tiempo libre me gusta leer sobre nuevas tecnologías y jugar videojuegos de estrategia.',
    urlFotoPerfil: 'https://i.pravatar.cc/150?u=alex.cordova',
    historialPreferencias: [
        'Documentales de ciencia',
        'Podcasts sobre startups',
        'Música Lo-fi para programar',
        'Artículos de ciberseguridad'
    ]
};

const UserProfile = () => {
    const [profileData, setProfileData] = useState(mockProfileData);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState(mockProfileData);


    const [selectedFile, setSelectedFile] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);


    useEffect(() => {
        return () => {
            if (imagePreview) {
                URL.revokeObjectURL(imagePreview);
            }
        };
    }, [imagePreview]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };


    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setSelectedFile(file);
            setImagePreview(URL.createObjectURL(file));
        }
    };

    const handleSave = (e) => {
        e.preventDefault();
        console.log("Simulando guardado de datos:", formData);
        if (selectedFile) {
            console.log("Archivo de imagen listo para subir:", selectedFile);

        }

        // Actualizamos los datos del perfil localmente para la simulación
        const finalData = { ...formData, urlFotoPerfil: imagePreview || formData.urlFotoPerfil };
        setProfileData(finalData);

        setIsEditing(false);
        alert("¡Perfil guardado (simulación)!");
    };

    const currentProfilePic = imagePreview || profileData.urlFotoPerfil;

    // --- MODO EDICIÓN ---
    if (isEditing) {
        return (
            <div className="profile-page-background">
                <div className="profile-container">
                    <form className="profile-card" onSubmit={handleSave}>
                        <div className="profile-header">
                            <img src={currentProfilePic} alt="Perfil" className="profile-pic" />
                            {}
                            <input
                                id="file-upload"
                                type="file"
                                accept="image/*"
                                onChange={handleFileChange}
                                style={{ display: 'none' }}
                            />
                            <label htmlFor="file-upload" className="custom-file-upload">
                                Cambiar Foto
                            </label>

                            <input
                                type="text"
                                name="nombreCompleto"
                                value={formData.nombreCompleto}
                                onChange={handleInputChange}
                                placeholder="Nombre Completo"
                                className="profile-name-input"
                            />
                            <p className="profile-email">{profileData.email}</p>
                        </div>
                        <div className="profile-body">
                            <label>Carrera</label>
                            <input type="text" name="carrera" value={formData.carrera} onChange={handleInputChange} />

                            <label>Descripción</label>
                            <textarea name="descripcion" value={formData.descripcion} onChange={handleInputChange} placeholder="Cuéntanos un poco sobre ti..."/>
                        </div>
                        <div className="profile-actions">
                            <button type="submit" className="btn-save">Guardar Cambios</button>
                            <button type="button" onClick={() => setIsEditing(false)} className="btn-cancel">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }

    // --- MODO VISTA ---
    return (
        <div className="profile-page-background">
            <div className="profile-container">
                <div className="profile-card">
                    <div className="profile-header">
                        <img src={profileData.urlFotoPerfil} alt="Perfil" className="profile-pic" />
                        <h1 className="profile-name">{profileData.nombreCompleto}</h1>
                        <p className="profile-email">{profileData.email}</p>
                    </div>
                    <div className="profile-body">
                        <h2>Carrera</h2>
                        <p>{profileData.carrera}</p>
                        <h2>Descripción</h2>
                        <p>{profileData.descripcion}</p>
                        <h2>Historial de Preferencias</h2>
                        <ul className="preferences-list">
                            {profileData.historialPreferencias.map((item, index) => (
                                <li key={index}>{item}</li>
                            ))}
                        </ul>
                    </div>
                    <div className="profile-actions">
                        <button onClick={() => setIsEditing(true)} className="btn-edit">Editar Perfil</button>
                        {}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserProfile;