import {
  ApiResponse,
  ApiResponseWithPagination,
  Page,
  Paged,
} from "../api-response";
import BaseService from "../base-service";
import { apiClient } from "../config";

export interface LoginDto {
  email: string;
  password: string;
}

class AutenticacaoService {
  private endpoint: string = "/api/v1/users/auth/login";
}

export default new AutenticacaoService();
