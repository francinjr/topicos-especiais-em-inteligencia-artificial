// src/config.ts
import { getToken, logoutDelayed } from "@/utils/auth";
import axios from "axios";
import { toast } from "sonner";

export const publicClient = axios.create({
  baseURL: "http://localhost:8080/",
  headers: { "Content-Type": "application/json" },
});

export const apiClient = axios.create({
  baseURL: "http://localhost:8080/",
  headers: { "Content-Type": "application/json" },
});

apiClient.interceptors.request.use((config) => {
  const token = getToken();
  console.log("[apiClient] interceptor — token:", token, "url:", config.url);
  if (token) {
    config.headers!["Authorization"] = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (res) => res,
  (error) => {
    const status = error.response?.status;

    if (status === 403) {
      toast.error("Você não tem autorização para executar essa operação.");
    } else if (status === 401) {
      toast.error("Sessão expirada. Faça login novamente.");
      logoutDelayed(3000);
    } else if (status === 500) {
      toast.error("Erro interno no servidor.");
    }

    return Promise.reject(error);
  }
);
