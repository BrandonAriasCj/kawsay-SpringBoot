import "../styles/Landing.css";
import kawsaiLogo from "../assets/kawsai-logo.png";
import { useEffect } from "react";
import {
  createParticles,
  revealOnScroll,
  handleHeaderScroll,
  animateCounters,
  setupSmoothScrolling,
  setupParallax
} from "../utils/landingJs";





const Landing =() => {
  useEffect(() => {
    createParticles();
    animateCounters();
    setupSmoothScrolling();
    setupParallax();
    revealOnScroll();

    const onScroll = () => {
      revealOnScroll();
      handleHeaderScroll();
    };

    const onResize = () => {
      revealOnScroll();
    };

    window.addEventListener('scroll', onScroll);
    window.addEventListener('resize', onResize);

    return () => {
      window.removeEventListener('scroll', onScroll);
      window.removeEventListener('resize', onResize);
    };
  }, []);

    return (

            <div className="landing-page">
                <div className="particles-system" id="particles"></div>
                    <div className="particles-system" id="particles"></div>
                    
            
                    <div className="floating-elements">
                        <div className="floating-icon parallax-slow">
                            <img src="cat.gif" alt="AI Brain"/>
                        </div>
                        <div className="floating-icon parallax-medium">
                            <img src="penst.gif" alt="Robot"/>
                        </div>
                        <div className="floating-icon parallax-fast">
                            <img src="felizzzz.gif" alt="Neural Network"/>
                        </div>
                        <div className="floating-icon parallax-slow">
                            <img src="robotIA.gif" alt="AI Chat"/>
                        </div>
                        <div className="floating-icon parallax-medium">
                            <img src="arc.gif" alt="Machine Learning"/>
                        </div>
                        <div className="floating-icon parallax-fast">
                            <img src="emoj.gif" alt="AI Assistant"/>
                        </div>
                    </div>


                    <header className="main-header">
                        <nav className="nav-left">
                            <img src={kawsaiLogo} alt="KawsAi Logo" className="logo-img" />
                            <a href="/main" className="logo">
                               KawsAi
                            </a>
                            <nav clasdsName="collapse navbar-collapse nav-center">
                                <a href="#inicio" className="nav-link" style={{ marginRight: '20px' }}>Inicio</a>
                                <a href="#características" className="nav-link" style={{ marginRight: '20px' }}>Características</a>
                            </nav>
                        </nav>
                        <div className="nav-right">
                            <a href="/login" className="btn btn-primary">Comenzar</a>
                        </div>
                    </header>

                    
                    <section className="hero-section" id="inicio">
                        <div className="hero-content">
                            <img src={kawsaiLogo} />
                            <h1 className="hero-title">KawsAi</h1>
                            <h1 className="hero-title">Tu compañero IA</h1>
                            <p className="hero-subtitle">Descubre el poder de la inteligencia artificial conversacional más avanzada. Crea conversaciones interesantes, confidenciales y muy amigables</p>
                            <a href="#características" className="hero-cta">Explorar Ahora</a>
                        </div>
                    </section>

            
                    <section className="features-section" id="características">
                        <h2 className="section-title scroll-reveal">Características Revolucionarias</h2>
                        <div className="features-grid">
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">🧠</div>
                                <h3>IA Conversacional</h3>
                                <p>Mantén conversaciones naturales y fluidas con nuestra IA avanzada que comprende el contexto y las emociones.</p>
                            </div>
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">⚡</div>
                                <h3>Respuestas Instantáneas</h3>
                                <p>Obtén respuestas en tiempo real con procesamiento ultrarrápido y precisión incomparable.</p>
                            </div>
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">❤️</div>
                                <h3>Empatía</h3>
                                <p>Interactua con nuestro modelo, no existen respuestas buenas o malas. No será juzgado</p>
                            </div>
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">🔒</div>
                                <h3>Privacidad Total</h3>
                                <p>Tus conversaciones están protegidas con encriptación de nivel militar y privacidad garantizada.</p>
                            </div>
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">👨‍👩‍👧‍👦</div>
                                <h3>Chat grupal</h3>
                                <p>Interactúa con mas usuarios como tú, comparte experiencias , anécdotas ,gusto y mucho más !</p>
                            </div>
                            <div className="neon-card scroll-reveal">
                                <div className="neon-icon">🚀</div>
                                <h3>Evolución Continua</h3>
                                <p>Nuestra IA aprende y mejora constantemente, adaptándose a tus necesidades únicas.</p>
                            </div>
                        </div>
                    </section>
        
                    <div/>
            <div/>
            </div>


)

};
export default Landing;