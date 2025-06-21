// src/pages/Citas.jsx
import React from 'react';
import '../styles/Citas.css';

const Citas = () => {
    return (
        <div className="citas-wrapper">
            <div className="citas-container">
                <header className="citas-header">
                    <h2>Agenda tu Cita</h2>
                    <p>Da el primer paso hacia tu bienestar. Elige un profesional y un horario.</p>
                </header>

                <form className="cita-form">
                    <div className="form-group">
                        <label htmlFor="profesional">Selecciona un Profesional</label>
                        <select id="profesional" name="profesional">
                            <option value="">-- Elige una opción --</option>
                            <option value="dra_lopez">Dra. Ana López - Especialista en Terapia Cognitivo-Conductual</option>
                            <option value="dr_gomez">Dr. Carlos Gómez - Especialista en Ansiedad y Estrés</option>
                            <option value="dra_rios">Dra. Sofía Ríos - Especialista en Relaciones y Familia</option>
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="fecha">Elige una Fecha</label>
                        <input type="date" id="fecha" name="fecha" />
                    </div>

                    <div className="form-group">
                        <label>Horarios Disponibles para la Fecha Seleccionada</label>
                        <div className="time-slots">
                            <button type="button" className="time-slot-btn">09:00 AM</button>
                            <button type="button" className="time-slot-btn">10:00 AM</button>
                            <button type="button" className="time-slot-btn" disabled>11:00 AM</button>
                            <button type="button" className="time-slot-btn">02:00 PM</button>
                            <button type="button" className="time-slot-btn">03:00 PM</button>
                            <button type="button" className="time-slot-btn">04:00 PM</button>
                        </div>
                    </div>

                    <button type="submit" className="confirm-cita-btn">
                        Confirmar Cita
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Citas;