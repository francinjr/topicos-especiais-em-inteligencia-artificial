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
import { Table } from "../ui/table";
import {
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../ui/table";
import { Search, ChevronDown, ChevronLeft, ChevronRight } from "lucide-react";
import {
  ApiResponse,
  ApiResponseWithPagination,
  Paged,
} from "@/services/api-response";

export interface Header<T> {
  key: keyof T;
  label: string;
  render?: (item: T) => React.ReactNode;
}

type ActionType<T> =
  | { type: "create"; component: React.ReactNode }
  | { type: "update"; component: (item: T, itemId: number) => React.ReactNode }
  | { type: "delete"; component: (itemId: number) => React.ReactNode };

interface BasePageProps<T> {
  title: string;
  headers: Header<T>[];
  items: T[];
  searchKey?: keyof T;
  actions?: ActionType<T>[];
  getRowClassName?: (item: T) => string;
  setAllItems: (items: []) => void;
  getPageByKey: (key: any, page: number, size: number) => any;
}

export function BasePage<T extends { id: number; imageUrl?: string }>({
  title,
  headers,
  items,
  searchKey,
  actions = [],
  getRowClassName,
  setAllItems,
  getPageByKey,
}: BasePageProps<T>) {
  const [search, setSearch] = useState<string>("");
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [itemsPerPage, setItemsPerPage] = useState<number>(10);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [totalItemsQuantity, setTotalItemsQuantity] = useState<number>(0);

  const selectedItemsPerPage = itemsPerPage.toString();

  const filteredItems = searchKey
    ? items.filter((item) =>
        String(item[searchKey]).toLowerCase().includes(search.toLowerCase())
      )
    : items;

  const paginatedItems = filteredItems.slice(0, itemsPerPage);

  const goToPreviousPage = () => {
    if (currentPage > 1) setCurrentPage((prev) => prev - 1);
  };

  const goToNextPage = () => {
    if (currentPage < totalPages) setCurrentPage((prev) => prev + 1);
  };

  useEffect(() => {
    const fetchPage = async () => {
      setIsLoading(true);
      try {
        const pageToFetch = currentPage - 1;
        const response = await getPageByKey(search, pageToFetch, itemsPerPage);

        setAllItems(response.content);
        setTotalItemsQuantity(response.page.totalElements);
        console.log("Buscou uma nova página: " + response.page.number);
      } catch (error) {
        console.error("Erro ao buscar página:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchPage();
  }, [currentPage, itemsPerPage, search]);

  useEffect(() => {
    setTotalPages(Math.max(1, Math.ceil(totalItemsQuantity / itemsPerPage)));
  }, [totalItemsQuantity, itemsPerPage]);

  useEffect(() => {
    setCurrentPage(1);
  }, [search, itemsPerPage]);

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
                {paginatedItems.map((item) => (
                  <TableRow
                    key={item.id}
                    className={getRowClassName ? getRowClassName(item) : ""}
                  >
                    {headers.map((header) => (
                      <TableCell key={String(header.key)}>
                        {header.key === "imageUrl" && item.imageUrl ? (
                          <img />
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

      <div className="a mt-1 justify-self-center col-span-12 sm:col-span-4 flex items-center">
        <span className="text-sm text-gray-600 mr-2">Itens por página:</span>
        <Select
          value={selectedItemsPerPage}
          onValueChange={(value) => {
            setItemsPerPage(Number(value));
            setCurrentPage(1);
          }}
        >
          <SelectTrigger className="min-w-fit h-8 text-sm px-3 py-1 border rounded-md bg-white text-black shadow-sm inline-flex items-center justify-between focus:outline-none focus:ring-2 focus:ring-blue-500">
            <span>{selectedItemsPerPage}</span>
            <ChevronDown className="h-4 w-4 ml-2 text-gray-500" />
          </SelectTrigger>
          <SelectContent
            position="popper"
            className="w-[var(--radix-select-trigger-width)] z-50 bg-white border border-gray-200 rounded-md shadow-md text-black"
          >
            {[10, 20, 30, 50].map((option) => (
              <SelectItem
                key={option}
                value={option.toString()}
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
          Página {currentPage} de {totalPages || 1}
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
