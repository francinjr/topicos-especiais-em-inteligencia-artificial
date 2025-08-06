// src/contexts/global-context.tsx

"use client";

import {
  createContext,
  useContext,
  ReactNode,
  useState,
  useEffect,
} from "react";
import { usePathname } from "next/navigation";

import { logoutDelayed, TOKEN_KEY, getToken } from "@/utils/auth";
import { useRouter } from "next/navigation";
import { toast } from "sonner";
import path from "path";
import { ApiResponse } from "@/services/api-response";
import usuarioService, {
  BuscarUserDto,
  UserRole,
} from "@/services/user/usuario-service";

interface AppContextType {
  user: BuscarUserDto | null;
  setUser: (user: BuscarUserDto) => void;
  initialPage: string;
  setInitialPage: (initialPage: string) => void;
}

const AppContext = createContext<AppContextType | undefined>(undefined);

export const AppProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<BuscarUserDto | null>(null);
  const [initialPage, setInitialPage] = useState<string>("");
  const pathname = usePathname();
  const router = useRouter();

  const publicPaths = ["/", "/recuperacao-conta"];
  const loginPath = "/";

  useEffect(() => {
    if (!user) return;

    if (user && user.isPasswordChanged) {
      let path = "";

      switch (user.role) {
        case UserRole.ADMIN:
          path = "/alimentos";
          break;
        case UserRole.CUSTOMER:
          path = "/";
          break;
        default:
          path = "/";
          break;
      }

      if (pathname === "/") {
        setInitialPage(path);
      }
    } else {
      toast.warning("Primeiro login. Você precisa alterar sua senha");
      setTimeout(() => {
        router.push("/alterar-senha");
      }, 2000);
    }
  }, [user]);

  useEffect(() => {
    if (pathname === loginPath || pathname === "/recuperacao-conta") {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem("userEmail");
      setUser(null);
      setInitialPage("");
      return;
    }

    const token = getToken();
    const email = localStorage.getItem("userEmail");

    if (token && email) {
      usuarioService
        .fetchUserByEmail(email)
        .then((response) => {
          setUser(response.data.data);
        })
        .catch((error) => {
          console.error("Erro ao buscar usuário:", error);
        });
    }
  }, [pathname]);

  return (
    <AppContext.Provider
      value={{
        user,
        setUser,
        initialPage,
        setInitialPage,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useApp = (): AppContextType => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error("useApp must be used within an AppProvider");
  }
  return context;
};
