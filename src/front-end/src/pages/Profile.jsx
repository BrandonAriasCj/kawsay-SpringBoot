import React, { useEffect, useState } from 'react';
import FirstLoginModal from '../components/FirstLoginModal';
import axios from 'axios';
import { withAuthenticator } from '@aws-amplify/ui-react';

const Profile = ({ user, signOut }) => {
  const userEmail = user?.attributes?.email;

  const [showModal, setShowModal] = useState(() => {
    return localStorage.getItem("firstLoginCompleted") !== "true";
  });

  const [profileData, setProfileData] = useState(null);

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const response = await axios.get("http://127.0.0.1:8000/api/profile/", {
          params: { mail: userEmail }
        });
        setProfileData(response.data);
      } catch (error) {
        console.error("Error al obtener los datos del perfil:", error);
      }
    };

    if (!showModal && userEmail) {
      fetchProfileData();
    }
  }, [showModal, userEmail]);

  const handleComplete = () => {
    setShowModal(false);
  };

  return (
    <div className="profile-container">
      {userEmail && showModal && (
        <FirstLoginModal userEmail={userEmail} onComplete={handleComplete} />
      )}

      {!showModal && profileData ? (
        <div className="profile-content">
          <h1>Bienvenido, {profileData.nickname}!</h1>
          <p>Correo: {profileData.mail}</p>
          <p>Preferencias: {profileData.preferencias.join(', ')}</p>

        </div>
      ) : (
      
          <button 
            onClick={signOut} 
            className="sign-out-button"
            style={{ padding: "10px", backgroundColor: "red", color: "white", fontSize: "16px" }}
          >
            Cerrar sesión
          </button>
      )}
    </div>
  );
};


export default withAuthenticator(Profile);
