import React, { useState, useEffect } from 'react';
import { FaRegCalendarCheck, FaTimesCircle } from 'react-icons/fa';
import '../styles/Citas.css';
import api from '../services/axiosInstance';

// --- Componente interno para la tarjeta de cita ---
const TarjetaCita = ({ cita }) => {
    const formatDate = (dateString) => {
        if (!dateString) return 'Fecha no disponible';
        const date = new Date(dateString);
        if (isNaN(date.getTime())) return 'Fecha inválida';
        return date.toLocaleDateString('es-ES', { day: '2-digit', month: 'long', year: 'numeric', timeZone: 'UTC' });
    };

    const formatTime = (timeString) => {
        if (!timeString) return '--';
        const [hour, minute] = timeString.split(':');
        return `${hour}:${minute}`;
    };

    return (
        <div className="tarjeta-cita">
            <div className="tarjeta-cita-header">
                <FaRegCalendarCheck className="me-2" />
                <span>Cita Confirmada</span>
            </div>
            <div className="tarjeta-cita-body">
                <p><strong>Profesional:</strong> {cita.psicologoNombre}</p>
                <p><strong>Fecha:</strong> {formatDate(cita.fechaCita)}</p>
                <p><strong>Hora:</strong> {formatTime(cita.horaInicio)}</p>
                <p><strong>Modalidad:</strong> <span className="text-capitalize">{cita.modalidad ? cita.modalidad.toLowerCase() : 'N/A'}</span></p>
            </div>
            <div className="tarjeta-cita-footer">
                <button className="btn-cancelar-cita">
                    <FaTimesCircle className="me-2" />
                    Cancelar
                </button>
            </div>
        </div>
    );
};

// --- Componente principal de la página de Citas ---
const Citas = () => {
    const [psicologos, setPsicologos] = useState([]);
    const [horariosDisponibles, setHorariosDisponibles] = useState([]);
    const [psicologoSeleccionado, setPsicologoSeleccionado] = useState('');
    const [fechaSeleccionada, setFechaSeleccionada] = useState('');
    const [horarioSeleccionado, setHorarioSeleccionado] = useState(null);
    const [cargandoHorarios, setCargandoHorarios] = useState(false);
    const [mensaje, setMensaje] = useState({ texto: '', tipo: '' });
    const [misCitas, setMisCitas] = useState([]);

    const cargarMisCitas = async () => {
        try {
            const misCitasRes = await api.get('/api/horarios-citas/citas/mis-citas');
            setMisCitas(misCitasRes.data);
        } catch (error) {
            console.error("Error al cargar mis citas:", error);
        }
    };

    useEffect(() => {
        const cargarDatosIniciales = async () => {
            try {
                const psicologosRes = await api.get('/api/horarios-citas/psicologos');
                setPsicologos(psicologosRes.data);
                cargarMisCitas();
            } catch (error) {
                console.error("Error al cargar psicólogos:", error);
                setMensaje({ texto: 'No se pudo cargar la lista de profesionales.', tipo: 'error' });
            }
        };
        cargarDatosIniciales();
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

            cargarMisCitas();

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
                        <label>Horarios Disponibles</label>
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
                                        {horario.hora}
                                    </button>
                                ))
                            ) : (
                                fechaSeleccionada && <p>No hay horarios disponibles.</p>
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

            <div className="citas-container mis-citas-seccion">
                <header className="citas-header">
                    <h2>Mis Próximas Citas</h2>
                </header>
                <div className="lista-citas-agendadas">
                    {misCitas.length > 0 ? (
                        misCitas.map(cita => <TarjetaCita key={cita.id} cita={cita} />)
                    ) : (
                        <p className="no-citas-mensaje">Aún no tienes citas programadas.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Citas;