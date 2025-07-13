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
import { urlBaseBack } from '../utils/path';
import { urlBaseFrontAdmin } from '../utils/path';

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
        console.log("token:", idToken)
        const accessToken = session.tokens?.accessToken?.toString();
        console.log("accesToken ", idToken)
        const payload = JSON.parse(atob(accessToken.split('.')[1]));
        const grupos = payload['cognito:groups'] || [];

        await fetch(`${urlBaseBack()}/api/usuarios/token`, {
          method: 'POST',
          headers: { Authorization: `Bearer ${idToken}`, 'Content-Type': 'application/json' },
        });

        localStorage.setItem('jwtToken', idToken);
        setUser(loggedUser);

        if (grupos.includes('PSICOLOGO')) {
          const psychologistWindow = window.open( urlBaseFrontAdmin, '_blank');
          setTimeout(() => {
            psychologistWindow.postMessage({ type: 'AUTH_TOKEN', token: idToken }, urlBaseFrontAdmin);
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