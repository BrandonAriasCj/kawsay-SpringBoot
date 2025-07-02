import React, { useContext, useEffect, useState } from 'react';
import { getCurrentUser, fetchAuthSession } from '@aws-amplify/auth';
import { Amplify } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';
import awsExports from '../aws-exports';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import '../styles/Login.css';

Amplify.configure(awsExports);

const Login = () => {
  const { setUser } = useContext(AuthContext);
  const [loggedUser, setLoggedUser] = useState(null);
  const [signOutFunc, setSignOutFunc] = useState(null);
  const [isRedirecting, setIsRedirecting] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (!loggedUser || isRedirecting) return;

    const handleAuth = async () => {
      try {
        setIsRedirecting(true);
        const session = await fetchAuthSession();
        const idToken = session.tokens?.idToken?.toString();
        const accessToken = session.tokens?.accessToken?.toString();

        const payload = JSON.parse(atob(accessToken.split('.')[1]));
        const grupos = payload['cognito:groups'] || [];

        await fetch('http://localhost:8081/api/usuarios/token', {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${idToken}`,
            'Content-Type': 'application/json',
          },
        });

        localStorage.setItem('jwtToken', idToken);
        setUser(loggedUser);

        if (grupos.includes('PSICOLOGO')) {
          // 1. Abrimos el panel del psicólogo en una nueva pestaña.
          // Guardamos una referencia a esa nueva ventana.
          const psychologistWindow = window.open('http://localhost:5174', '_blank');

          // 2. Esperamos un momento para asegurarnos de que la nueva ventana haya cargado.
          setTimeout(() => {
            // 3. Enviamos el token de forma segura usando postMessage.
            // El segundo argumento especifica el origen exacto al que se puede enviar el mensaje.
            // Esto es una medida de seguridad crucial.
            psychologistWindow.postMessage({
              type: 'AUTH_TOKEN',
              token: idToken
            }, 'http://localhost:5174');

            // Opcional: Redirigir la ventana actual a una página de "sesión iniciada" o de vuelta al inicio.
            navigate('/');

          }, 1000); // 1 segundo de espera suele ser suficiente.

        } else {
          // El flujo del estudiante no cambia.
          localStorage.setItem('jwtToken', idToken);
          setUser(loggedUser);
          navigate('/');
        }
      } catch (err) {
        console.error("❌ Error autenticando:", err);
      }
    };

    handleAuth();
  }, [loggedUser, isRedirecting, navigate, setUser]);

  return (
      <div className="login-page-container">
        <Authenticator>
          {({ signOut, user }) => {
            if (!loggedUser) {
              setLoggedUser(user);
              setSignOutFunc(() => signOut);
            }

            return null;
          }}
        </Authenticator>
      </div>
  );
};

export default Login;
