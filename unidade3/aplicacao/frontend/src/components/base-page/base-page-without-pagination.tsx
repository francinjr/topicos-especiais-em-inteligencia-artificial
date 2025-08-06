import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
} from "@radix-ui/react-select";
import { Button } from "../ui/button";
import { Card, CardContent } from "../ui/card";
import { Input } from "../ui/input";
import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../ui/table";
import { Search, ChevronDown, ChevronLeft, ChevronRight } from "lucide-react";

// A interface Header permanece a mesma.
export interface Header<T> {
  key: keyof T;
  label: string;
  render?: (item: T) => React.ReactNode;
}

// A interface ActionType permanece a mesma.
type ActionType<T> =
  | { type: "create"; component: React.ReactNode }
  | { type: "update"; component: (item: T, itemId: number) => React.ReactNode }
  | { type: "delete"; component: (itemId: number) => React.ReactNode };

// Props foram simplificadas: `getPageByKey` e `setAllItems` foram removidas.
interface BasePageWithoutPaginationProps<T> {
  title: string;
  headers: Header<T>[];
  items: T[]; // Agora espera o array completo de itens.
  searchKey?: keyof T;
  actions?: ActionType<T>[];
  getRowClassName?: (item: T) => string;
}

export function BasePageWithoutPagination<
  T extends { id: number; imageUrl?: string }
>({
  title,
  headers,
  items, // Recebe o array completo de itens.
  searchKey,
  actions = [],
  getRowClassName,
}: BasePageWithoutPaginationProps<T>) {
  const [search, setSearch] = useState<string>("");
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [itemsPerPage, setItemsPerPage] = useState<number>(10);

  // 1. Filtra a lista completa de itens com base no termo de busca.
  const filteredItems = searchKey
    ? items.filter((item) =>
        String(item[searchKey]).toLowerCase().includes(search.toLowerCase())
      )
    : items;

  // 2. Calcula o total de páginas com base nos itens filtrados.
  const totalPages = Math.max(
    1,
    Math.ceil(filteredItems.length / itemsPerPage)
  );

  // 3. Pega apenas os itens para a página atual a partir da lista filtrada.
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedItems = filteredItems.slice(
    startIndex,
    startIndex + itemsPerPage
  );

  const goToPreviousPage = () => {
    if (currentPage > 1) setCurrentPage((prev) => prev - 1);
  };

  const goToNextPage = () => {
    if (currentPage < totalPages) setCurrentPage((prev) => prev + 1);
  };

  // Reseta para a primeira página sempre que a busca ou o número de itens por página mudar.
  useEffect(() => {
    setCurrentPage(1);
  }, [search, itemsPerPage]);

  // Garante que a página atual não seja maior que o total de páginas existentes.
  // Útil se os itens forem filtrados e o número total de páginas diminuir.
  useEffect(() => {
    if (currentPage > totalPages) {
      setCurrentPage(totalPages);
    }
  }, [currentPage, totalPages]);

  return (
    <div className="container mx-auto my-8 p-8 grid grid-cols-12">
      <div className="col-span-12">
        <p className="text-2xl">{title}</p>
      </div>

      <div className="col-span-12 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-2 py-2">
        <div className="relative w-full sm:max-w-xs">
          <Input
            placeholder="Pesquisar"
            className="w-full pr-10"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <Search
            className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500"
            size={20}
          />
        </div>

        <div className="flex flex-wrap gap-2 justify-end">
          {actions
            .filter((a) => a.type === "create")
            .map((action, index) => (
              <div key={index}>{action.component}</div>
            ))}
        </div>
      </div>

      <div className="col-span-12 py-1">
        <Card className="w-full max-h-[400px] overflow-x-auto overflow-y-auto">
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  {headers.map((header) => (
                    <TableHead key={String(header.key)}>
                      {header.label}
                    </TableHead>
                  ))}
                  <TableHead>Ações</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {/* O mapeamento agora usa 'paginatedItems' que é calculado no cliente */}
                {paginatedItems.map((item) => (
                  <TableRow
                    key={item.id}
                    className={getRowClassName ? getRowClassName(item) : ""}
                  >
                    {headers.map((header) => (
                      <TableCell key={String(header.key)}>
                        {header.key === "imageUrl" && item.imageUrl ? (
                          <img
                            src={item.imageUrl}
                            alt="Imagem do item"
                            className="h-10 w-10 object-cover rounded"
                          />
                        ) : header.render ? (
                          header.render(item)
                        ) : (
                          String(item[header.key] ?? "—")
                        )}
                      </TableCell>
                    ))}
                    <TableCell className="h-full">
                      <div className="flex items-center gap-2 h-full">
                        {actions.map((action, index) => {
                          switch (action.type) {
                            case "update":
                              return (
                                <div key={index}>
                                  {action.component(item, item.id)}
                                </div>
                              );
                            case "delete":
                              return (
                                <div key={index}>
                                  {action.component(item.id)}
                                </div>
                              );
                            default:
                              return null;
                          }
                        })}
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      {/* A seção de paginação permanece a mesma, mas agora é alimentada pela lógica do cliente */}
      <div className="a mt-1 justify-self-center col-span-12 sm:col-span-4 flex items-center">
        <span className="text-sm text-gray-600 mr-2">Itens por página:</span>
        <Select
          value={String(itemsPerPage)}
          onValueChange={(value) => {
            setItemsPerPage(Number(value));
          }}
        >
          <SelectTrigger className="min-w-fit h-8 text-sm px-3 py-1 border rounded-md bg-white text-black shadow-sm inline-flex items-center justify-between focus:outline-none focus:ring-2 focus:ring-blue-500">
            <span>{itemsPerPage}</span>
            <ChevronDown className="h-4 w-4 ml-2 text-gray-500" />
          </SelectTrigger>
          <SelectContent
            position="popper"
            className="w-[var(--radix-select-trigger-width)] z-50 bg-white border border-gray-200 rounded-md shadow-md text-black"
          >
            {[10, 20, 30, 50].map((option) => (
              <SelectItem
                key={option}
                value={String(option)}
                className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-sm"
              >
                {option}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div className="b mt-1 justify-self-center col-span-12 sm:col-span-4 flex items-center">
        <p className="text-sm text-gray-600">
          Página {currentPage} de {totalPages}
        </p>
      </div>

      <div className="c mt-1 justify-self-center col-span-12 sm:col-span-4 flex items-center gap-2">
        <Button
          variant="outline"
          size="icon"
          onClick={goToPreviousPage}
          disabled={currentPage === 1}
        >
          <ChevronLeft className="h-4 w-4" />
        </Button>
        <Button
          variant="outline"
          size="icon"
          onClick={goToNextPage}
          disabled={currentPage === totalPages}
        >
          <ChevronRight className="h-4 w-4" />
        </Button>
      </div>
    </div>
  );
}
