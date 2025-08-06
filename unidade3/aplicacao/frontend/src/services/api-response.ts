"use client";

export interface ValidationField {
  name: string;
  message: string;
}

export interface ApiResponse<T> {
  message: string;
  data: T;
  errors?: ValidationField[];
  path: string;
}

export interface Page {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
}

export interface Paged<T> {
  content: T[];
  page: Page;
}

export interface ApiResponseWithPagination<T> {
  message: string;
  data: Paged<T>;
  errors?: ValidationField[];
  path: string;
}
