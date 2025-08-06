import { Loader2 } from "lucide-react";
import { Button, ButtonProps } from "../ui/button";

interface ButtonWithLoadingProps extends ButtonProps {
  isLoading: boolean;
  text: string;
}

export const ButtonWithLoading = ({
  text,
  isLoading = false,
  ...props
}: ButtonWithLoadingProps) => {
  return (
    <Button {...props} disabled={isLoading}>
      {isLoading && <Loader2 className="animate-spin" />}
      {text}
    </Button>
  );
};
