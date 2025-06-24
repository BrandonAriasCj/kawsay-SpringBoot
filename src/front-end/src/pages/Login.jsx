// src/pages/Login.jsx
import React, { useContext, useEffect } from 'react';
import { getCurrentUser, fetchAuthSession } from '@aws-amplify/auth';
import '../styles/Login.css';

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
  const initialized = React.useRef(false);

  useEffect(() => {
    if (initialized.current) return;
    initialized.current = true;

    (async () => {
      try {
        const user = await getCurrentUser();
        const token = (await fetchAuthSession()).tokens?.idToken?.toString();

        if (!user || !token) return console.warn("⚠️ Falta usuario o token");

        await fetch('http://localhost:8082/api/usuarios/token', {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          
        });
    console.log("📦 JWT completo:", token);
    console.log("🧾 Payload del JWT:", payload);
        setUser(user);
        navigate('/profile');
      } catch (err) {
        console.error("❌ Falló la sesión:", err);
      }
    })();
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
      <div className="login-page-container">
        {}
        <Authenticator>
          {({ signOut, user }) => (

              <LoggedInHandler user={user} signOut={signOut} />
          )}
        </Authenticator>
      </div>
  );
};

export default Login;
