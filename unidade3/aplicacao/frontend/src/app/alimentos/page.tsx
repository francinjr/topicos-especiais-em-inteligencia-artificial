"use client";

import ProductCard from "./components/product-card";
import { useEffect, useState } from "react";

import { useApp } from "@/contexts/global-context";
import { BasePage, Header } from "@/components/base-page/base-page";

import { toast } from "sonner";
import { ApiResponse } from "@/services/api-response";
import { UpdateProductForm } from "./components/update-product-form";
import { DeleteProductDialog } from "./components/delete-product-dialog";
import { createResponseMessage } from "@/services/api-utils";
import alimentoService, {
  AlimentoDto,
} from "@/services/alimento/alimento-service";
import { CreateAlimentoForm } from "./components/create-alimento-form";
import { BasePageWithoutPagination } from "@/components/base-page/base-page-without-pagination";

const headers: Header<AlimentoDto>[] = [
  {
    key: "name",
    label: "Nome",
  },
];

export default function AlimentosPage() {
  const [foods, setFoods] = useState<AlimentoDto[]>([]);

  useEffect(() => {
    fetchFoods();
  }, []);

  async function fetchFoods() {
    const response = await alimentoService.getAll();

    setFoods(response.data);
    return response.data;
  }

  const createItem = async (
    product: AlimentoDto
  ): Promise<ApiResponse<AlimentoDto>> => {
    try {
      const newProduct = await alimentoService.create(product);

      const updated = [...foods, newProduct.data];

      setFoods(updated);

      toast.success(newProduct.message);
      return newProduct;
    } catch (error: any) {
      toast.error(createResponseMessage(error));
      throw error;
    }
  };

  const updateItem = async (
    institution: AlimentoDto,
    id: number
  ): Promise<ApiResponse<AlimentoDto>> => {
    try {
      const updatedInstitution = await alimentoService.update(institution, id);

      const updatedList = foods.map((item) =>
        item.id === id ? updatedInstitution.data : item
      );

      setFoods(updatedList);

      toast.success(updatedInstitution.message);
      return updatedInstitution;
    } catch (error: any) {
      toast.error(createResponseMessage(error));
      throw error;
    }
  };

  const deleteItem = async (id: number) => {
    try {
      await alimentoService.delete(id);

      const filtered = foods.filter((i) => i.id !== id);

      setFoods(filtered);

      toast.success("Instituição deletada com sucesso");
    } catch (error: any) {
      toast.error(createResponseMessage(error));
      throw error;
    }
  };

  return (
    <div>
      <BasePageWithoutPagination
        title="Alimentos"
        headers={headers}
        items={foods}
        searchKey="name"
        actions={[]}
      />
    </div>
  );
}
