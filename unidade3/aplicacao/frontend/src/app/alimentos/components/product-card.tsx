"use client";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import productService, {
  ReadProductDto,
} from "@/services/alimento/alimento-service";
import { useEffect, useState } from "react";

interface ProductCardProps {
  products: ReadProductDto[];
}
export default function ProductCard(props: ProductCardProps) {
  return (
    <>
      <Card className="w-4/5 mx-auto mt-4 h-[80vh] flex flex-col">
        <CardHeader>
          <CardTitle>Produtos</CardTitle>
          <CardDescription>Description</CardDescription>
        </CardHeader>
        <CardContent className="grid grid-cols-12 overflow-y-auto flex-1">
          {props.products.map((item) => (
            <Card
              key={item.id}
              className="col-span-6 grid grid-cols-12 min-h-[150px] m-2"
            >
              <div className="col-span-3 flex items-center justify-center h-full">
                <img src="/file.svg" alt="" width="120" height="120" />
              </div>
              <div className="col-span-8 flex flex-col justify-center ml-2">
                <div>{item.name}</div>
                <div>R$ {item.price}</div>
                <div>{item.availableQuantity} restante(s)</div>
                <div>{item.description}</div>
              </div>
            </Card>
          ))}
        </CardContent>
        <CardFooter className="flex justify-between">Card footer</CardFooter>
      </Card>
    </>
  );
}
