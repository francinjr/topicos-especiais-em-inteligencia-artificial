import { Button } from "@/components/ui/button";
import { ButtonWithLoading } from "@/components/ui/button-with-loading";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogOverlay,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { ApiResponse } from "@/services/api-response";
import { AlimentoDto } from "@/services/alimento/alimento-service";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import z, { unknown } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

interface CreateAlimentoFormProps {
  createItem?: (item: AlimentoDto) => Promise<ApiResponse<AlimentoDto>>;
  updateItem?: (
    item: AlimentoDto,
    id: number
  ) => Promise<ApiResponse<AlimentoDto>>;
  item?: AlimentoDto;
  itemId?: number;
}

const schema = z.object({
  id: z.number(),
  name: z.string().min(3, "O nome deve ter pelo menos 3 caracteres"),
  sequenciaAminoacidos: z
    .array(z.string(), {
      message: "A sequência deve ser uma lista de nomes.",
    })
    .min(1, "É necessário informar ao menos uma substância alergênica."),
});

export function CreateAlimentoForm(props: CreateAlimentoFormProps) {
  const [open, setOpen] = useState<boolean>(false);

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: props.item
      ? {
          ...props.item,
        }
      : {
          name: "",
          sequenciaAminoacidos: [],
        },
  });

  useEffect(() => {
    if (props.item) {
      form.reset({
        name: props.item.name,
        sequenciaAminoacidos: props.item.sequenciaAminoacidos,
      });
    }
  }, [props.item, form]);

  const onSubmit = async (data: z.infer<typeof schema>) => {
    if (!data) return;

    const payload: AlimentoDto = {
      id: data.id,
      name: data.name,
      sequenciaAminoacidos: data.sequenciaAminoacidos,
    };

    try {
      if (props.createItem) {
        await props.createItem(payload);
      } else if (props.updateItem) {
        await props.updateItem(payload, props.itemId!);
      }
      setOpen(false);
    } catch (error) {
      console.error("Erro ao criar/atualizar produto:", error);
    }
  };

  return (
    <div>
      {props.createItem && (
        <Button
          variant="default"
          onClick={() => setOpen(true)}
          className="w-full !important sm:w-auto"
        >
          Novo alimento
        </Button>
      )}
      {props.updateItem && (
        <Button variant="outline" onClick={() => setOpen(true)}>
          Editar
        </Button>
      )}

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogOverlay className="bg-black/30 z-50" />
        <DialogContent className="sm:max-w-[825px]">
          <DialogHeader>
            <DialogTitle className="mx-auto">
              {props.updateItem ? "Editar alimento" : "Cadastrar alimento"}
            </DialogTitle>
          </DialogHeader>

          <FormProvider {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
              <div className="grid grid-cols-12 gap-4 py-4">
                <div className="col-span-12">
                  <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel className="block">Nome</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Digite o nome do produto"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                <div className="col-span-6">
                  <FormField
                    control={form.control}
                    name="sequenciaAminoacidos"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel className="block">Descrição</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Informe a substância que pode ser alergênica"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>

              <DialogFooter>
                <ButtonWithLoading
                  isLoading={form.formState.isSubmitting}
                  text="Salvar"
                  type="submit"
                  className="w-1/5"
                />
                <Button
                  variant="destructive"
                  type="button"
                  onClick={() => setOpen(false)}
                  className="w-1/5"
                >
                  Cancelar
                </Button>
              </DialogFooter>
            </form>
          </FormProvider>
        </DialogContent>
      </Dialog>
    </div>
  );
}
