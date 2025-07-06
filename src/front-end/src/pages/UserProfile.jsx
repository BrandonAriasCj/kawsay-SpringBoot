// src/pages/UserProfile.jsx
import React, { useState, useEffect } from 'react';
import { getPerfil, updatePerfil, uploadAndUpdateFotoPerfil } from '../services/perfilApi';
import '../styles/UserProfile.css';
import { FaUserCircle } from 'react-icons/fa';

const UserProfile = () => {
    const [profileData, setProfileData] = useState(null);
    const [formData, setFormData] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const data = await getPerfil();
                setProfileData(data);
                setFormData(data);
            } catch (error) {
                console.error('Error al obtener el perfil:', error);
            } finally {
                setLoading(false);
            }
        };
        loadProfile();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (file) {
            try {
                const uploadedUrl = await uploadAndUpdateFotoPerfil(file);
                setFormData(prev => ({ ...prev, urlFotoPerfil: uploadedUrl }));
                // Actualiza los datos del perfil principal también para reflejar el cambio inmediatamente
                setProfileData(prev => ({ ...prev, urlFotoPerfil: uploadedUrl }));
            } catch (error) {
                console.error("Error al subir imagen:", error);
                alert("Error al subir la imagen");
            }
        }
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            const updated = await updatePerfil(formData);
            setProfileData(updated);
            setFormData(updated);
            setIsEditing(false);
            alert("Perfil actualizado correctamente.");
        } catch (error) {
            console.error("Error al guardar el perfil:", error);
            alert("Error al actualizar perfil.");
        }
    };

    if (loading) return <p className="loading">Cargando perfil...</p>;
    if (!profileData) return <p>No se pudo cargar el perfil.</p>;

    // Vista de edición
    if (isEditing) {
        return (
            <div className="profile-view">
                <div className="profile-container">
                    <form className="profile-card" onSubmit={handleSave}>
                        <div className="profile-header">
                            {/* Lógica para mostrar foto o ícono en modo edición */}
                            {formData.urlFotoPerfil && formData.urlFotoPerfil !== '/uploads/default.jpg' ? (
                                <img
                                    src={`http://localhost:8081${formData.urlFotoPerfil}`}
                                    alt="Perfil"
                                    className="profile-pic"
                                />
                            ) : (
                                <div className="profile-avatar-default">
                                    <FaUserCircle />
                                </div>
                            )}
                            <input
                                id="file-upload" type="file" accept="image/*"
                                onChange={handleFileChange} style={{ display: 'none' }}
                            />
                            <label htmlFor="file-upload" className="custom-file-upload">Cambiar Foto</label>
                            <input
                                type="text" name="nombreCompleto" value={formData.nombreCompleto}
                                onChange={handleInputChange} className="profile-name-input" placeholder="Nombre Completo"
                            />
                            <p className="profile-email">{profileData.email}</p>
                        </div>
                        <div className="profile-body">
                            <label>Carrera</label>
                            <input type="text" name="carrera" value={formData.carrera} onChange={handleInputChange} />
                            <label>Descripción</label>
                            <textarea name="descripcion" value={formData.descripcion} onChange={handleInputChange} />
                        </div>
                        <div className="profile-actions">
                            <button type="button" onClick={() => setIsEditing(false)} className="btn-cancel">Cancelar</button>
                            <button type="submit" className="btn-save">Guardar Cambios</button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }

    // Vista de visualización
    return (
        <div className="profile-view">
            <div className="profile-container">
                <div className="profile-card">
                    <div className="profile-header">
                        {/* Lógica para mostrar foto o ícono */}
                        {profileData.urlFotoPerfil && profileData.urlFotoPerfil !== '/uploads/default.jpg' ? (
                            <img src={`http://localhost:8081${profileData.urlFotoPerfil}`} alt="Perfil" className="profile-pic" />
                        ) : (
                            <div className="profile-avatar-default">
                                <FaUserCircle />
                            </div>
                        )}
                        <h1 className="profile-name">{profileData.nombreCompleto}</h1>
                        <p className="profile-email">{profileData.email}</p>
                    </div>
                    <div className="profile-body">
                        <div>
                            <h2>Carrera</h2>
                            <p>{profileData.carrera || 'No especificado'}</p>
                        </div>
                        <div>
                            <h2>Descripción</h2>
                            <p>{profileData.descripcion || 'No especificado'}</p>
                        </div>
                        <div>
                            <h2>Preferencias</h2>
                            <p>{profileData.historialPreferencias?.join(', ') || 'No hay preferencias guardadas.'}</p>
                        </div>
                    </div>
                    <div className="profile-actions">
                        <button onClick={() => setIsEditing(true)} className="btn-edit">Editar Perfil</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserProfile;