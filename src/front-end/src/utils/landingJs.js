// src/scripts/landingEffects.js

export function createParticles() {
  const particlesContainer = document.getElementById('particles');
  const numberOfParticles = 50;

  for (let i = 0; i < numberOfParticles; i++) {
    const particle = document.createElement('div');
    particle.className = 'particle';
    particle.style.left = Math.random() * 100 + '%';
    particle.style.animationDelay = Math.random() * 6 + 's';
    particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
    particlesContainer.appendChild(particle);
  }
}

export function revealOnScroll() {
  const elements = document.querySelectorAll('.scroll-reveal');
  elements.forEach(element => {
    const elementTop = element.getBoundingClientRect().top;
    const elementVisible = 150;
    if (elementTop < window.innerHeight - elementVisible) {
      element.classList.add('revealed');
    }
  });
}

export function handleHeaderScroll() {
  const header = document.getElementById('header');
  if (window.scrollY > 100) {
    header.classList.add('header-scrolled');
  } else {
    header.classList.remove('header-scrolled');
  }
}

export function animateCounters() {
  const counters = document.querySelectorAll('.stat-number');
  counters.forEach(counter => {
    const target = parseInt(counter.getAttribute('data-target'));
    const increment = target / 100;
    let current = 0;

    const updateCounter = () => {
      if (current < target) {
        current += increment;
        counter.textContent = Math.floor(current);
        requestAnimationFrame(updateCounter);
      } else {
        counter.textContent = target;
      }
    };

    const observer = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          updateCounter();
          observer.disconnect();
        }
      });
    });

    observer.observe(counter);
  });
}

export function setupSmoothScrolling() {
  const links = document.querySelectorAll('a[href^="#"]');
  links.forEach(link => {
    link.addEventListener('click', (e) => {
      e.preventDefault();
      const target = document.querySelector(link.getAttribute('href'));
      if (target) {
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
  });
}

export function setupParallax() {
  document.addEventListener('mousemove', (e) => {
    const mouseX = e.clientX / window.innerWidth;
    const mouseY = e.clientY / window.innerHeight;

    const particles = document.querySelectorAll('.particle');
    particles.forEach((particle, index) => {
      const speed = (index % 5 + 1) * 0.5;
      const x = (mouseX - 0.5) * speed;
      const y = (mouseY - 0.5) * speed;
      particle.style.transform = `translate(${x}px, ${y}px)`;
    });
  });
}
