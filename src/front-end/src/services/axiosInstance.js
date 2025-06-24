// axiosInstance.js

import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8081/',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Interceptor para agregar el token a cada request
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default instance;
