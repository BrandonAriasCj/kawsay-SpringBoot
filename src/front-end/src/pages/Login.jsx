// src/pages/Login.jsx
import React, { useContext, useEffect, useState } from 'react';
import { fetchAuthSession } from 'aws-amplify/auth';
import { Amplify } from 'aws-amplify';
import { Authenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';
import awsExports from '../aws-exports';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import '../styles/Login.css';

import kawsaiLogo from '../assets/kawsai-logo.png';

Amplify.configure(awsExports);

const FormHeader = () => (
    <div className="form-header">
      <h2>¡Bienvenido a KawsAi!</h2>
      <p>Completa tus datos para continuar.</p>
    </div>
);


const Login = () => {
  const { setUser } = useContext(AuthContext);
  const [loggedUser, setLoggedUser] = useState(null);
  const [isRedirecting, setIsRedirecting] = useState(false);
  const navigate = useNavigate();
  const [userFromAuthenticator, setUserFromAuthenticator] = useState(null);


  useEffect(() => {
    if (userFromAuthenticator && !loggedUser) {
      setLoggedUser(userFromAuthenticator);
    }
  }, [userFromAuthenticator, loggedUser]);

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
          headers: { Authorization: `Bearer ${idToken}`, 'Content-Type': 'application/json' },
        });

        localStorage.setItem('jwtToken', idToken);
        setUser(loggedUser);

        if (grupos.includes('PSICOLOGO')) {
          const psychologistWindow = window.open('http://localhost:5174', '_blank');
          setTimeout(() => {
            psychologistWindow.postMessage({ type: 'AUTH_TOKEN', token: idToken }, 'http://localhost:5174');
            navigate('/');
          }, 1000);
        } else {
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
      <div className="login-page-wrapper">
        <div className="login-split-container">
          {}
          <div className="login-visual-side">
            <img src={kawsaiLogo} alt="Kawsai Logo" className="visual-side-logo" />
            <h1 className="visual-side-title">KawsAi</h1>
            <p className="visual-side-subtitle">Tu espacio de bienestar digital.</p>
          </div>

          {}
          <div className="login-form-side">
            <Authenticator
                components={{
                  Header: FormHeader,
                }}
            >
              {({ user }) => {
                if (!userFromAuthenticator && user) {
                  setTimeout(() => setUserFromAuthenticator(user), 0);
                }
                return null;
              }}
            </Authenticator>
          </div>
        </div>
      </div>
  );
};

export default Login;