/* eslint-disable @typescript-eslint/no-explicit-any */
import { ApiResponse, ApiResponseWithPagination } from "../api-response";
import BaseService from "../base-service";

import { apiClient } from "../config";

export interface PacienteMinDto {
  id: number;
  name: string;
}

class PacienteService extends BaseService<
  PacienteMinDto,
  PacienteMinDto,
  PacienteMinDto
> {
  constructor() {
    super("/api/customers");
  }

  async getAllCustomersSimple(): Promise<ApiResponse<PacienteMinDto[]>> {
    try {
      const response = await apiClient.get<ApiResponse<PacienteMinDto[]>>(
        `api/customers/all`
      );

      return response.data;
    } catch (error) {
      console.error("Erro ao buscar a lista de clientes:", error);
      throw error;
    }
  }
}

export default new PacienteService();
