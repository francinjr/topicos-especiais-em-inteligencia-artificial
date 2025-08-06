"use client";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";

import Image from "next/image";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { toast } from "sonner";
import { useApp } from "@/contexts/global-context";
import usuarioService from "@/services/user/usuario-service";
import { Input } from "@/components/ui/input";
import { ButtonWithLoading } from "@/components/ui/button-with-loading";
import { Button } from "@/components/ui/button";
import { saveEmail, saveToken } from "@/utils/auth";

const signInSchema = z.object({
  email: z
    .string({ required_error: "E-mail é obrigatório" })
    .email("E-mail inválido"),
  password: z
    .string()
    .min(6, { message: "Senha deve ter no mínimo 6 caracteres" }),
});

type SignInSchema = z.infer<typeof signInSchema>;

export default function SignInPage() {
  const router = useRouter();
  const form = useForm<SignInSchema>({
    resolver: zodResolver(signInSchema),
    defaultValues: {
      email: "admin@gmail.com",
      password: "admin123",
    },
  });
  const [apiError, setApiError] = useState<string>("");
  const [isLoadingAuth, setIsLoadingAuth] = useState<boolean>(false);

  const { initialPage, setUser } = useApp();

  const onSubmit = (data: SignInSchema) => {
    auth(data);
  };

  const auth = async (credentials: SignInSchema) => {
    try {
      setIsLoadingAuth(true);
      const response = await usuarioService.auth(credentials);

      if (response.status === 200) {
        const token = response.headers["authorization"];
        if (token) {
          saveToken(token);
          saveEmail(credentials.email);
        }

        const fetchUserResponse = await usuarioService.fetchUserByEmail(
          credentials.email
        );
        const userData = fetchUserResponse.data.data;
        setUser(userData);
        setApiError("");
      }
    } catch (error: any) {
      const msg =
        error.response?.data?.message || error.message || "Erro desconhecido";
      toast.error(msg);
      setApiError(msg);
      setIsLoadingAuth(false);
    }
  };

  useEffect(() => {
    if (initialPage) {
      router.push(initialPage);
    }
  }, [initialPage]);
  const redirectToPasswordRecoveryPage = () => {
    router.push("/recuperacao-conta");
  };

  return (
    <div className="grid grid-cols-12 min-h-screen items-center px-4 sm:px-8 lg:px-20">
      <div className="col-span-12 fixed top-0 left-0 right-0 bg-primary flex gap-4 items-center justify-center z-10 py-8">
        <Image
          src="/allergen-free.png"
          alt="Logo Upanema"
          width={323}
          height={98}
        />
      </div>

      <div className="col-span-12 justify-self-center self-center mb-12">
        <header className="w-full sm:w-[350px] space-y-4 mb-4">
          <h2 className="text-3xl sm:text-4xl font-bold text-primary text-center mt-12">
            Acessar conta
          </h2>
          <p className="text-sm text-center">
            Bem-vindo(a)! Por favor, digite suas credenciais para ter acesso ao
            sistema.
          </p>
        </header>

        {apiError && (
          <p className="text-sm text-red-500 text-center font-semibold mb-4">
            {apiError}
          </p>
        )}

        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="space-y-4 w-full sm:w-[350px] justify-self-center"
          >
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>E-mail</FormLabel>
                  <FormControl>
                    <Input placeholder="Digite seu E-mail" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Senha</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Digite sua senha"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <ButtonWithLoading
              text="Entrar"
              isLoading={isLoadingAuth}
              className="w-full text-lg"
              size="lg"
              type="submit"
              disabled={isLoadingAuth}
            />
          </form>
        </Form>

        <Button
          variant="ghost"
          className="w-full text-sm mt-2"
          onClick={redirectToPasswordRecoveryPage}
        >
          Esqueceu a senha?
        </Button>
      </div>
    </div>
  );
}
