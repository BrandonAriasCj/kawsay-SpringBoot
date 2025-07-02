import React, { useEffect } from 'react';
import { signOut } from '@aws-amplify/auth';
import { useNavigate } from 'react-router-dom';

const GlobalLogout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const performGlobalLogout = async () => {
            try {

                await signOut({ global: true });
                console.log("✅ Sesión global cerrada desde el manejador.");
            } catch (error) {
                console.error("❌ Error en el cierre de sesión global:", error);
            } finally {

                navigate('/login', { replace: true });
            }
        };

        performGlobalLogout();
    }, [navigate]);

    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h2>Cerrando sesión...</h2>
        </div>
    );
};

export default GlobalLogout;