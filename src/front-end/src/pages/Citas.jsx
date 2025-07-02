import React, { useState, useEffect } from 'react';
import '../styles/Citas.css';
import api from '../services/axiosInstance';


const Citas = () => {

    const [psicologos, setPsicologos] = useState([]);
    const [horariosDisponibles, setHorariosDisponibles] = useState([]);

    const [psicologoSeleccionado, setPsicologoSeleccionado] = useState('');
    const [fechaSeleccionada, setFechaSeleccionada] = useState('');
    const [horarioSeleccionado, setHorarioSeleccionado] = useState(null);

    const [cargandoHorarios, setCargandoHorarios] = useState(false);
    const [mensaje, setMensaje] = useState({ texto: '', tipo: '' });

    useEffect(() => {
        const cargarPsicologos = async () => {
            try {
                const response = await api.get('/api/horarios-citas/psicologos');
                setPsicologos(response.data);
            } catch (error) {
                console.error("Error al cargar psicólogos:", error);
                setMensaje({ texto: 'No se pudo cargar la lista de profesionales.', tipo: 'error' });
            }
        };
        cargarPsicologos();
    }, []);


    useEffect(() => {
        if (psicologoSeleccionado && fechaSeleccionada) {
            const cargarHorarios = async () => {
                setCargandoHorarios(true);
                setHorariosDisponibles([]);
                setHorarioSeleccionado(null);
                try {
                    const response = await api.get(`/api/horarios-citas/disponibles/${psicologoSeleccionado}?fecha=${fechaSeleccionada}`);
                    setHorariosDisponibles(response.data);
                } catch (error) {
                    console.error("Error al cargar horarios:", error);
                    setMensaje({ texto: 'Error al cargar horarios para esta fecha.', tipo: 'error' });
                } finally {
                    setCargandoHorarios(false);
                }
            };
            cargarHorarios();
        }
    }, [psicologoSeleccionado, fechaSeleccionada]);


    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!psicologoSeleccionado || !fechaSeleccionada || !horarioSeleccionado) {
            setMensaje({ texto: 'Por favor, completa todos los campos.', tipo: 'error' });
            return;
        }

        try {
            const datosCita = {
                psicologoId: parseInt(psicologoSeleccionado),
                fecha: fechaSeleccionada,
                hora: horarioSeleccionado.hora,
            };
            await api.post('/api/horarios-citas/citas/agendar', datosCita);
            setMensaje({ texto: '¡Tu cita ha sido agendada con éxito!', tipo: 'exito' });

            // Opcional: resetear el formulario
            setPsicologoSeleccionado('');
            setFechaSeleccionada('');
            setHorarioSeleccionado(null);
            setHorariosDisponibles([]);

        } catch (error) {
            console.error("Error al agendar la cita:", error);
            setMensaje({ texto: error.response?.data?.message || 'El horario ya no está disponible. Por favor, elige otro.', tipo: 'error' });
        }
    };

    return (
        <div className="citas-wrapper">
            <div className="citas-container">
                <header className="citas-header">
                    <h2>Agenda tu Cita</h2>
                    <p>Da el primer paso hacia tu bienestar. Elige un profesional y un horario.</p>
                </header>

                <form className="cita-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="profesional">Selecciona un Profesional</label>
                        <select
                            id="profesional"
                            name="profesional"
                            value={psicologoSeleccionado}
                            onChange={(e) => setPsicologoSeleccionado(e.target.value)}
                            required
                        >
                            <option value="">-- Elige una opción --</option>
                            {psicologos.map((p) => (
                                <option key={p.id} value={p.id}>
                                    {p.nombreCompleto}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="fecha">Elige una Fecha</label>
                        <input
                            type="date"
                            id="fecha"
                            name="fecha"
                            value={fechaSeleccionada}
                            onChange={(e) => setFechaSeleccionada(e.target.value)}
                            disabled={!psicologoSeleccionado}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Horarios Disponibles para la Fecha Seleccionada</label>
                        <div className="time-slots">
                            {cargandoHorarios ? (
                                <p>Cargando horarios...</p>
                            ) : horariosDisponibles.length > 0 ? (
                                horariosDisponibles.map((horario, index) => (
                                    <button
                                        key={index}
                                        type="button"
                                        className={`time-slot-btn ${horarioSeleccionado?.hora === horario.hora ? 'selected' : ''}`}
                                        onClick={() => setHorarioSeleccionado(horario)}
                                    >
                                        {horario.hora} ({horario.modalidad.charAt(0)})
                                    </button>
                                ))
                            ) : (
                                fechaSeleccionada && <p>No hay horarios disponibles para esta fecha.</p>
                            )}
                        </div>
                    </div>

                    {mensaje.texto && (
                        <div className={`mensaje ${mensaje.tipo}`}>
                            {mensaje.texto}
                        </div>
                    )}

                    <button type="submit" className="confirm-cita-btn">
                        Confirmar Cita
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Citas;