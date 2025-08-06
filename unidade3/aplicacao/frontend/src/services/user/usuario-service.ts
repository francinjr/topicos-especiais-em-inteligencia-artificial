import { AxiosResponse } from "axios";
import { publicClient, apiClient } from "../config";

export interface LoginDto {
  email: string;
  password: string;
}

export enum UserRole {
  ADMIN = "ADMIN",
  CUSTOMER = "CUSTOMER",
}

export interface BuscarUserDto {
  id: number;
  email: string;
  role: UserRole;
  isPasswordChanged: boolean;
}

class UserService {
  private base = "/api/v1/users";

  async auth(credentials: LoginDto): Promise<AxiosResponse<void>> {
    return publicClient.post<void>(`${this.base}/auth/login`, credentials);
  }

  async validateUserOperation(
    credentials: LoginDto
  ): Promise<AxiosResponse<void>> {
    return apiClient.post<void>(`${this.base}/validate`, credentials);
  }

  async fetchUserByEmail(
    email: string
  ): Promise<AxiosResponse<{ data: BuscarUserDto }>> {
    return apiClient.get<{ data: BuscarUserDto }>(`${this.base}`, {
      params: { email },
    });
  }

  async solicitarAlteracaoSenha(email: string): Promise<AxiosResponse<void>> {
    return apiClient.post<void>(`${this.base}/request-password-reset`, null, {
      params: { email },
    });
  }

  async verificarCodigo(token: string): Promise<AxiosResponse<void>> {
    try {
      return await apiClient.post<void>(
        `${this.base}/verify-token?token=${token}`
      );
    } catch (error: any) {
      throw new Error("Erro ao verificar token: " + error.message);
    }
  }

  async alterarSenha(
    email: string,
    novaSenha: string
  ): Promise<AxiosResponse<void>> {
    try {
      return await apiClient.put<void>(`${this.base}/change-password`, null, {
        params: {
          email: email,
          novaSenha: novaSenha,
        },
      });
    } catch (error: any) {
      throw new Error("Erro ao alterar senha: " + error.message);
    }
  }

  async alterarSenhaSemToken(
    email: string,
    novaSenha: string
  ): Promise<AxiosResponse<void>> {
    try {
      return await apiClient.put<void>(`${this.base}/change-passwordwt`, null, {
        params: {
          email: email,
          novaSenha: novaSenha,
        },
      });
    } catch (error: any) {
      throw new Error("Erro ao alterar senha: " + error.message);
    }
  }
}
export default new UserService();
