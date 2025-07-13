// axiosInstance.js

import axios from 'axios';
import { urlBaseBack } from '../utils/path';

const instance = axios.create({
  baseURL: `${urlBaseBack()}`,
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
