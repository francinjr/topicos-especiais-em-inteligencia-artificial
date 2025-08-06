/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { toast } from "sonner";
import { useForm } from "react-hook-form";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../components/ui/form";
import { useState } from "react";
import usuarioService from "@/services/user/usuario-service";
import { Input } from "@/components/ui/input";
import { ButtonWithLoading } from "@/components/ui/button-with-loading";
import { Button } from "@/components/ui/button";
import { NumericInput } from "@/components/ui/numeric-input";

const signInSchema = z.object({
  email: z
    .string({ required_error: "E-mail é obrigatório" })
    .email("E-mail é obrigatório"),
});

const recoverPasswordSchema = z
  .object({
    recoverPasswordToken: z
      .string({ required_error: "O token é obrigatório" })
      .length(6, "O token deve ter exatamente 6 dígitos"),
    newPassword: z.string({ required_error: "Nova senha é obrigatória" }),
    confirmNewPassword: z.string({
      required_error: "Confirmação de nova senha é obrigatória",
    }),
  })
  .refine((data) => data.newPassword === data.confirmNewPassword, {
    message:
      "A senha do campo de confirmação de senha precisa ser igual à senha",
    path: ["confirmNewPassword"],
  });

type SignInSchema = z.infer<typeof signInSchema>;
type RecoverPasswordSchema = z.infer<typeof recoverPasswordSchema>;

export default function RecuperacaoContaPage() {
  const [isRequestingRecovery, setIsRequestingRecovery] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);
  const router = useRouter();
  const form = useForm<SignInSchema>({
    resolver: zodResolver(signInSchema),
    defaultValues: {
      email: "",
    },
  });
  const recoverPasswordForm = useForm<RecoverPasswordSchema>({
    resolver: zodResolver(recoverPasswordSchema),
    defaultValues: {
      recoverPasswordToken: "",
      newPassword: "",
      confirmNewPassword: "",
    },
  });
  const [apiError, setApiError] = useState<string>("");
  const [showTokenVerificationField, setShowTokenVerificationField] =
    useState<boolean>(false);
  const [email, setEmail] = useState<string>("");

  const onSubmit = (data: SignInSchema) => {
    console.log("onSubmit chamado com email:", data.email);
    requestPasswordRecovery(data.email);
  };

  const requestPasswordRecovery = async (email: string) => {
    try {
      setIsRequestingRecovery(true);
      const response = await usuarioService.solicitarAlteracaoSenha(email);
      if (response.status === 200) {
        setEmail(email);
        toast.success(
          "Solicitação de recuperação realizada com sucesso. Verifique seu E-mail"
        );
        setShowTokenVerificationField(true);
      }
    } catch (error: any) {
      setApiError(error.message);
      toast.error(error.message);
      setIsRequestingRecovery(false);
    }
  };

  const verifyTokenAndChangePassword = async (data: RecoverPasswordSchema) => {
    try {
      setIsChangingPassword(true);
      const codeVerifyResponse = await usuarioService.verificarCodigo(
        data.recoverPasswordToken
      );
      if (codeVerifyResponse.status === 200) {
        await changePassword(data);
      }
    } catch (error: any) {
      setApiError(error.message);
      toast.error(error.message);
      setIsChangingPassword(false);
    }
  };

  const changePassword = async (data: RecoverPasswordSchema) => {
    try {
      setIsChangingPassword(true);
      const alterarSenhaResponse = await usuarioService.alterarSenha(
        email,
        data.newPassword
      );
      if (alterarSenhaResponse.status === 200) {
        toast.success("Senha alterada com sucesso! Realize o login");
        setTimeout(() => {
          router.push("/");
        }, 2000);
      }
    } catch (error: any) {
      setApiError(error.message);
      toast.error(error.message);
      setIsChangingPassword(false);
    }
  };

  const redirectToLoginPage = () => {
    console.log("redirectToLoginPage chamado");
    router.push("/");
  };

  return (
    <div className="grid grid-cols-12 min-h-screen items-center px-4 sm:px-8 lg:px-20">
      <div className="col-span-12 fixed top-0 left-0 right-0 bg-primary flex gap-4 items-center justify-center z-10 py-8">
        <Image
          src="/RentNowLogoV3.png"
          alt="Logo RentNow"
          width={323}
          height={98}
        />
      </div>

      <div className="col-span-12 justify-self-center self-center mb-12">
        <header className="w-full sm:w-[350px] space-y-4 mb-4">
          <h2 className="text-3xl sm:text-4xl font-bold text-primary text-center">
            Recuperação de acesso
          </h2>
          <p className="text-sm text-center">
            Bem-vindo(a)! Por favor, preencha os campos abaixo para recuperar
            seu acesso
          </p>
        </header>
        <div>
          {apiError && (
            <p className="text-sm text-red-500 text-center font-semibold">
              {apiError}
            </p>
          )}
        </div>
        {!showTokenVerificationField ? (
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(onSubmit)}
              className="space-y-4 w-full sm:w-[350px] justify-self-center"
              autoComplete="off"
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
              <ButtonWithLoading
                text="Solicitar Recuperação"
                isLoading={isRequestingRecovery}
                className="w-full text-lg"
                size="lg"
                type="submit"
                disabled={isRequestingRecovery}
              />
            </form>
          </Form>
        ) : (
          <Form key="recoverPasswordForm" {...recoverPasswordForm}>
            <form
              onSubmit={recoverPasswordForm.handleSubmit(
                verifyTokenAndChangePassword
              )}
              className="space-y-4 w-full sm:w-[350px] justify-self-center"
              autoComplete="off"
            >
              <FormField
                control={recoverPasswordForm.control}
                name="recoverPasswordToken"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Código de recuperação</FormLabel>
                    <FormControl>
                      <NumericInput
                        placeholder="Digite o código de recuperação"
                        maxLength={6}
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={recoverPasswordForm.control}
                name="newPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nova senha</FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="Digite a nova senha"
                        autoComplete="new-password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={recoverPasswordForm.control}
                name="confirmNewPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Confirme a nova senha</FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="Confirme a nova senha"
                        autoComplete="new-password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <ButtonWithLoading
                text="Alterar Senha"
                isLoading={isChangingPassword}
                className="w-full text-lg"
                size="lg"
                type="submit"
                disabled={isChangingPassword}
              />
            </form>
          </Form>
        )}
        <Button
          variant="ghost"
          className="w-full text-sm"
          onClick={redirectToLoginPage}
        >
          <p className="text-red-500 font-bold text-md">Cancelar</p>
        </Button>
      </div>
    </div>
  );
}
