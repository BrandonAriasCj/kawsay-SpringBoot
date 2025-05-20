// src/pages/Profile.jsx
import React from 'react';
import { Authenticator } from '@aws-amplify/ui-react';

const Profile = () => {
  return (
    <div className="profile-wrapper">
      <Authenticator>
        {({ signOut, user }) => (
          <div className="profile-container">
            <h2>Perfil de Usuario</h2>
            <p><strong>Nombre de usuario:</strong> {user?.username}</p>
            <p><strong>Email:</strong> {user?.attributes?.email}</p>
            <button onClick={signOut} className="sign-out-button">
              Cerrar sesión
            </button>
          </div>
        )}
      </Authenticator>
    </div>
  );
};

export default Profile;
