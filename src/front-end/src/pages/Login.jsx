// src/pages/Login.jsx
import React, { useContext, useEffect } from 'react';
import { getCurrentUser, fetchAuthSession } from '@aws-amplify/auth';

import { Amplify} from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import awsExports from '../aws-exports';
import '@aws-amplify/ui-react/styles.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

Amplify.configure(awsExports);

const LoggedInHandler = ({ user, signOut }) => {
  const { setUser } = useContext(AuthContext);
  const navigate = useNavigate();

  console.log("🧍 Usuario autenticado:", user);
  console.log("📧 Email:", user?.attributes?.email);
useEffect(() => {
  const obtenerToken = async () => {
    try {
      const currentUser = await getCurrentUser(); // ← esto asegura que hay sesión
      const session = await fetchAuthSession();   // ← ahora sí, obtenemos los tokens

      const token = session.tokens?.idToken?.toString();
      const email = currentUser.signInDetails?.loginId;

      if (!token || !email) {
        console.warn("⚠️ No se pudo extraer email o token.");
        return;
      }

      console.log("📧 Email:", email);
      console.log("🔐 ID Token:", token);

      await fetch('http://localhost:8081/api/usuario/registro-email', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ email }),
      });

      setUser(currentUser);
      navigate('/profile');
    } catch (err) {
      console.error("❌ Error al obtener sesión:", err);
    }
  };

  obtenerToken();
}, [setUser, navigate]);


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
