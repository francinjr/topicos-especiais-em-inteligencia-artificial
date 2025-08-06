import { useState } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
  DialogOverlay,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

interface DeleteProductDialogProps {
  itemId: number;
  deleteItem: (id: number) => void;
}

export function DeleteProductDialog({
  itemId,
  deleteItem,
}: DeleteProductDialogProps) {
  const [open, setOpen] = useState(false);

  const handleSubmit = () => {
    deleteItem(itemId);
    setOpen(false); // fecha o dialog após deletar
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogOverlay className="bg-black/30 z-50" />
      <DialogTrigger asChild>
        <Button variant="destructive" className="w-20">
          Deletar
        </Button>
      </DialogTrigger>

      <DialogContent className="sm:max-w-[450px]">
        <DialogHeader>
          <DialogTitle className="text-center">Deletar produto</DialogTitle>
          <div className="text-center py-8">
            Tem certeza que deseja deletar esse item?
          </div>
        </DialogHeader>

        <DialogFooter className="flex justify-end gap-2">
          <Button variant="destructive" onClick={handleSubmit}>
            Confirmar
          </Button>
          <Button variant="outline" onClick={() => setOpen(false)}>
            Cancelar
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
