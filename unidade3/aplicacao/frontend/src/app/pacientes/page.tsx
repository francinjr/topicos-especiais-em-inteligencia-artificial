"use client";
import React, { useState, useMemo, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import { Search, Loader2, Wheat, Salad } from "lucide-react";
import alimentoService from "@/services/alimento/alimento-service";
import pacienteService, {
  PacienteMinDto,
} from "@/services/paciente/paciente-service";

// Definindo a interface para o DTO do Alimento
export interface AlimentoDto {
  id: number;
  name: string;
  sequenciaAminoacidos: string[];
}

export default function ConsultaAlergiasPacientePage() {
  const [pacientes, setPacientes] = useState<PacienteMinDto[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isFetchingPatients, setIsFetchingPatients] = useState(true);

  // 1. ATUALIZAÇÃO: O estado agora espera um array de AlimentoDto
  const [searchResults, setSearchResults] = useState<{
    title: string;
    foods: AlimentoDto[]; // Alterado de string[] para AlimentoDto[]
    patientName: string;
  } | null>(null);

  const [isDialogOpen, setIsDialogOpen] = useState(false);

  // 2. ATUALIZAÇÃO: A função agora extrai o array de AlimentoDto da resposta
  const handleFetchFoods = async (
    patientName: string,
    fetchType: "allergen" | "non-allergen"
  ) => {
    setIsLoading(true);
    setIsDialogOpen(true);
    setSearchResults(null);

    const title =
      fetchType === "allergen"
        ? "Alimentos Alergênicos"
        : "Alimentos Não Alergênicos";

    try {
      // Assumindo que o serviço agora retorna ApiResponse<AlimentoDto[]>
      const response =
        fetchType === "allergen"
          ? await alimentoService.getAllergenFoodsByPatientName(patientName)
          : await alimentoService.getNonAllergenFoodsByPatientName(patientName);

      // Extrai o array de dados da resposta
      setSearchResults({ title, foods: response.data, patientName });
    } catch (error) {
      console.error("Falha ao buscar alimentos:", error);
      setSearchResults({
        title,
        foods: [], // Retorna um array vazio em caso de erro
        patientName,
      });
    } finally {
      setIsLoading(false);
    }
  };

  const filteredPacientes = useMemo(() => {
    if (!searchTerm) return pacientes;
    return pacientes.filter((p) =>
      p.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }, [searchTerm, pacientes]);

  useEffect(() => {
    async function fetchPatients() {
      try {
        const response = await pacienteService.getAllCustomersSimple();
        setPacientes(response.data);
      } catch (error) {
        console.error("Erro ao carregar a lista de pacientes:", error);
      } finally {
        setIsFetchingPatients(false);
      }
    }
    fetchPatients();
  }, []);

  if (isFetchingPatients) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loader2 className="h-16 w-16 animate-spin text-blue-600" />
      </div>
    );
  }

  return (
    <>
      <div className="container mx-auto my-8 p-8">
        {/* Título e Barra de Busca */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-800">
            Consulta de Alimentos por Paciente
          </h1>
          <p className="text-gray-500 mt-1">
            Selecione um paciente para ver os alimentos alergênicos e não
            alergênicos.
          </p>
        </div>

        <div className="relative w-full sm:max-w-sm mb-8">
          <Input
            placeholder="Pesquisar paciente..."
            className="w-full pr-10"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <Search
            className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500"
            size={20}
          />
        </div>

        {/* Grid de Cards de Pacientes */}
        {filteredPacientes.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {filteredPacientes.map((paciente) => (
              <Card
                key={paciente.id}
                className="transition-all hover:shadow-lg hover:-translate-y-1"
              >
                <CardHeader>
                  <CardTitle className="text-xl text-gray-900">
                    {paciente.name}
                  </CardTitle>
                </CardHeader>
                <CardContent className="flex flex-col gap-3">
                  <Button
                    variant="outline"
                    onClick={() => handleFetchFoods(paciente.name, "allergen")}
                  >
                    <Wheat className="mr-2 h-4 w-4 text-red-500" />
                    Alimentos Alergênicos
                  </Button>
                  <Button
                    variant="outline"
                    onClick={() =>
                      handleFetchFoods(paciente.name, "non-allergen")
                    }
                  >
                    <Salad className="mr-2 h-4 w-4 text-green-600" />
                    Alimentos Não Alergênicos
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>
        ) : (
          <div className="text-center py-12 text-gray-500">
            <p>Nenhum paciente encontrado.</p>
          </div>
        )}
      </div>

      {/* Caixa de Diálogo para exibir os resultados */}
      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle className="text-2xl">
              {searchResults?.title || "Buscando..."}
            </DialogTitle>
            <DialogDescription>
              Resultados para o paciente:{" "}
              <span className="font-semibold text-primary">
                {searchResults?.patientName}
              </span>
            </DialogDescription>
          </DialogHeader>
          <div className="py-4 min-h-[150px] flex items-center justify-center">
            {isLoading ? (
              <Loader2 className="h-12 w-12 animate-spin text-blue-600" />
            ) : searchResults && searchResults.foods.length > 0 ? (
              <ul className="list-disc list-inside space-y-2 w-full">
                {/* 3. ATUALIZAÇÃO: Mapeia o array de objetos e exibe food.name */}
                {searchResults.foods.map((food) => (
                  <li key={food.id} className="text-gray-700">
                    {food.name}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-gray-500">
                Nenhum alimento encontrado nesta categoria.
              </p>
            )}
          </div>
        </DialogContent>
      </Dialog>
    </>
  );
}
