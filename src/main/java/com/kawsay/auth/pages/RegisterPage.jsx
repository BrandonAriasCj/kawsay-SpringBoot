import { useState } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import axios from "axios";
import { FaKey } from "react-icons/fa"; // Ícono para el campo opcional de token
import "../styles/Register.css";

const RegisterPage = () => {
  const [showToken, setShowToken] = useState(false); // Control para mostrar el campo opcional

  const RegisterSchema = Yup.object().shape({
    email: Yup.string().email("Correo inválido").required("Correo es obligatorio"),
    nickname: Yup.string().min(3, "Mínimo 3 caracteres").required("Nickname es obligatorio"),
    password: Yup.string().min(6, "Mínimo 6 caracteres").required("Contraseña es obligatoria"),
    token: Yup.string().optional(),
  });

  const handleRegister = async (values) => {
    try {
      const res = await axios.post("http://127.0.0.1:8000/api/registro/", values);
      alert("Usuario registrado con éxito");
    } catch (error) {
      console.error("Error en registro:", error.response?.data);
    }
  };

  return (
    <div className="register-container">
      <h2>Registro de Usuario</h2>
      <Formik initialValues={{ email: "", nickname: "", password: "", token: "" }} validationSchema={RegisterSchema} onSubmit={handleRegister}>
        {() => (
          <Form>
            <div>
              <Field type="email" name="email" placeholder="Correo electrónico" />
              <ErrorMessage name="email" component="div" />
            </div>

            <div>
              <Field type="text" name="nickname" placeholder="Nickname" />
              <ErrorMessage name="nickname" component="div" />
            </div>

            <div>
              <Field type="password" name="password" placeholder="Contraseña" />
              <ErrorMessage name="password" component="div" />
            </div>

            <div>
              <FaKey onClick={() => setShowToken(!showToken)} style={{ cursor: "pointer", fontSize: "20px" }} />
              {showToken && (
                <>
                  <Field type="text" name="token" placeholder="Token opcional" />
                  <ErrorMessage name="token" component="div" />
                </>
              )}
            </div>

            <button type="submit">Registrarse</button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default RegisterPage;
