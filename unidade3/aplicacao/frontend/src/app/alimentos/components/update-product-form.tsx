import { AlimentoDto } from "@/services/alimento/alimento-service";
import { ApiResponse } from "@/services/api-response";
import { CreateAlimentoForm } from "./create-alimento-form";

interface UpdateProductFormProps {
  item?: AlimentoDto;
  itemId?: number;
  updateItem: (
    item: AlimentoDto,
    id: number
  ) => Promise<ApiResponse<AlimentoDto>>;
}

export function UpdateProductForm(props: UpdateProductFormProps) {
  return (
    <CreateAlimentoForm
      item={props.item}
      itemId={props.itemId}
      updateItem={props.updateItem}
    />
  );
}
