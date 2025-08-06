import { ApiResponse, ApiResponseWithPagination } from "./api-response";
import { apiClient } from "./config";

class BaseService<Dto, CreateDto, UpdateDto> {
  private endpoint: string;

  constructor(endpoint: string) {
    this.endpoint = endpoint;
  }

  public getEndpoint(): string {
    return this.endpoint;
  }

  async getPage(
    name: string,
    page: number,
    size: number
  ): Promise<ApiResponseWithPagination<Dto>> {
    try {
      const response = await apiClient.get<ApiResponseWithPagination<Dto>>(
        this.endpoint,
        {
          params: { name, page, size },
        }
      );

      return response.data;
    } catch (error: any) {
      console.error(`Erro ao buscar os produtos:`, error);
      throw error.response?.data;
    }
  }

  async create(data: CreateDto): Promise<ApiResponse<Dto>> {
    try {
      const response = await apiClient.post<ApiResponse<Dto>>(
        this.endpoint,
        data
      );
      return response.data;
    } catch (error: any) {
      console.error(`Erro ao criar ${this.endpoint}:`, error);
      throw error.response?.data;
    }
  }

  async update(data: UpdateDto, id: number): Promise<ApiResponse<Dto>> {
    try {
      const response = await apiClient.put<ApiResponse<Dto>>(
        `${this.endpoint}/${id}`,
        data
      );
      return response.data;
    } catch (error: any) {
      console.error(`Erro ao atualizar ${this.endpoint} com ID ${id}:`, error);
      throw error.response?.data;
    }
  }

  async delete(id: number): Promise<void> {
    try {
      await apiClient.delete(`${this.endpoint}/${id}`);
    } catch (error: any) {
      console.error(`Erro ao deletar ${this.endpoint} com ID ${id}:`, error);
      throw error.response?.data;
    }
  }
}

export default BaseService;
