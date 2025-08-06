/* eslint-disable @typescript-eslint/no-explicit-any */
import { ApiResponse, ApiResponseWithPagination } from "../api-response";
import BaseService from "../base-service";

import { apiClient } from "../config";

export interface AlimentoDto {
  id: number;
  name: string;
  sequenciaAminoacidos: string[];
}

class AlimentoService extends BaseService<
  AlimentoDto,
  AlimentoDto,
  AlimentoDto
> {
  constructor() {
    super("/api/foods");
  }

  async getAllergenFoodsByPatientName(
    patientName: string
  ): Promise<ApiResponse<AlimentoDto[]>> {
    try {
      const response = await apiClient.get<ApiResponse<AlimentoDto[]>>(
        `api/foods/allergensbypatientname`,
        {
          params: {
            patientName,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao buscar alimentos alergênicos para o paciente:",
        error
      );
      throw error;
    }
  }

  async getNonAllergenFoodsByPatientName(
    patientName: string
  ): Promise<ApiResponse<AlimentoDto[]>> {
    try {
      const response = await apiClient.get<ApiResponse<AlimentoDto[]>>(
        `api/foods/nonallergensbypatientname`,
        {
          params: {
            patientName,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao buscar alimentos não alergênicos para o paciente:",
        error
      );
      throw error;
    }
  }

  async getAll(): Promise<ApiResponse<AlimentoDto[]>> {
    try {
      const response = await apiClient.get<ApiResponse<AlimentoDto[]>>(
        `api/foods/all`
      );
      return response.data;
    } catch (error) {
      console.error(
        "Erro ao buscar alimentos não alergênicos para o paciente:",
        error
      );
      throw error;
    }
  }
}

export default new AlimentoService();
