import { useState ,useEffect} from "react";
import FormularioPerfil from "./FormularioPerfil";
import FormularioPreferencias from "./FormularioPreferencias";
import { mapWizardDataToPerfilInicialDTO } from "../utils/mapWizardDataToPerfilInicialDTO";
import axios from "axios";
import ReactDOM from 'react-dom';



const Wizard = ({ userEmail, onComplete }) => {
  const [step, setStep] = useState(1);
  const [perfilData, setPerfilData] = useState({ nombreCompleto: "", carrera: "", descripcion: "" });
  const [seleccionadas, setSeleccionadas] = useState([]);
  const [catalogo, setCatalogo] = useState(null);
  const [loading, setLoading] = useState(false);

  const handlePerfilChange = e => {
    const { name, value } = e.target;
    setPerfilData(prev => ({ ...prev, [name]: value }));
  };

  const handleNextStep = () => setStep(2);
  const handleBack     = () => setStep(1);

  // Carga catálogo al entrar al paso 2
  useEffect(() => {
    if (step === 2 && !catalogo) {
      setLoading(true);
      axios.get("http://localhost:8081/api/preferencias")
        .then(res => setCatalogo(res.data))
        .catch(err => console.error(err))
        .finally(() => setLoading(false));
    }
  }, [step, catalogo]);

  const handleFinalSubmit = async () => {
    const payload = {
      ...perfilData,
      preferencias: seleccionadas
    };
  const jwtToken = localStorage.getItem("jwtToken");
  await axios.post(
    "http://localhost:8081/api/perfil/inicial",
    payload,
    {
      headers: {
        Authorization: `Bearer ${jwtToken}`
      }
    }
  );
  localStorage.setItem("firstLoginCompleted", "true");
  onComplete();
};

  return ReactDOM.createPortal(
    <div className="kawzay-wizard-overlay">
      <div className="kawzay-wizard-modal">
        <h2 className="text-2xl font-bold text-center mb-6 text-purple-700">
          🎉 ¡Bienvenido a Kawzay!
        </h2>

        {step === 1 && (
          <FormularioPerfil
            perfilData={perfilData}
            onChange={handlePerfilChange}
            onNext={handleNextStep}
          />
        )}

        {step === 2 && (
          loading
            ? <p className="text-center">Cargando preferencias…</p>
            : <FormularioPreferencias
                catalogo={catalogo}
                seleccionadas={seleccionadas}
                setSeleccionadas={setSeleccionadas}
                onBack={handleBack}
                onSubmit={handleFinalSubmit}
              />
        )}
      </div>
    </div>,
    document.body
  );
};

export default Wizard;