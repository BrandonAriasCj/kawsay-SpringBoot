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
  console.log("📡 useEffect activado: verificando usuario...");

  const sendTokenToBackEnd = async () => {
    if (user && user.signInUserSession) {
      const token = user.signInUserSession.idToken.jwtToken;
      console.log("🔐 TOKEN capturado desde Cognito:", token); // ← Verifica si aparece en la consola del navegador

      try {
        const response = await fetch('http://localhost:8080/api/usuario/token', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `${token}`,
          },
        });

        const responseData = await response.text();
        console.log("📬 Respuesta del backend:", responseData); // ← ¿Se imprime algo aquí?
      } catch (error) {
        console.error("❌ Error en el fetch al backend:", error);
      }

      setUser(user);
      navigate('/profile');
    } else {
      console.warn("⚠️ Usuario o sesión aún no disponible.");
    }
  };

  sendTokenToBackEnd();
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
