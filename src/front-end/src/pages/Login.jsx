// src/pages/Login.jsx
import React, { useContext, useEffect } from 'react';
import { Amplify } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import awsExports from '../aws-exports';
import '@aws-amplify/ui-react/styles.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

Amplify.configure(awsExports);

const LoggedInHandler = ({ user, signOut }) => {
  const { setUser } = useContext(AuthContext);
  const navigate = useNavigate();

 
  useEffect(() => {
    if (user) {
      setUser(user);
      navigate('/profile');
    }
  }, [user, setUser, navigate]);

  return (
    <div className="auth-container">
      <header className="App-header">
        <h1>Bienvenido, {user?.username || 'Usuario'}</h1>
      </header>
      <main>
        <p>¡Sesión iniciada!</p>
        <button onClick={signOut} className="sign-out-button">
          Cerrar sesión
        </button>
      </main>
    </div>
  );
};

const Login = () => {
  return (
    <div className="App">
      <div className="auth-wrapper">
        <Authenticator>
          {({ signOut, user }) => (
            <LoggedInHandler user={user} signOut={signOut} />
          )}
        </Authenticator>
      </div>
    </div>
  );
};

export default Login;
