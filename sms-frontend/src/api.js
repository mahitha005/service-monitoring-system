import axios from "axios";

const api = axios.create({
  baseURL: "https://service-monitoring-system.onrender.com/",
  headers: {
    "Content-Type": "application/json"
  }
});

api.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  if (token && !config.url.startsWith("/auth")) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;