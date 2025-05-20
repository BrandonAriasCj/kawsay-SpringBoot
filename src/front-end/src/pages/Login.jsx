// src/pages/Login.jsx
import React, { useEffect } from 'react';
import { Amplify } from 'aws-amplify';
import { Authenticator, useAuthenticator } from '@aws-amplify/ui-react';
import awsExports from '../aws-exports';
import '@aws-amplify/ui-react/styles.css';
import { useNavigate } from 'react-router-dom';

Amplify.configure(awsExports);

const Login = () => {
  const { user } = useAuthenticator((context) => [context.user]);
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      navigate('/profile');
    }
  }, [user, navigate]);

  return (
    <div className="App">
      <div className="auth-wrapper">
        <Authenticator />
      </div>
    </div>
  );
};

export default Login;
