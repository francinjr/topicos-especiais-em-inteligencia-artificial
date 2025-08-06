import * as React from "react";
import { Input } from "./input";

const NumericInput = React.forwardRef<
  HTMLInputElement,
  React.ComponentProps<"input">
>(({ className, ...props }, ref) => {
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (
      !/[\d]/.test(event.key) &&
      !["Backspace", "Tab", "ArrowLeft", "ArrowRight", "Delete"].includes(
        event.key
      )
    ) {
      event.preventDefault();
    }
  };

  return (
    <Input
      type="text"
      inputMode="numeric"
      pattern="[0-9]*"
      {...props}
      className={className}
      ref={ref}
      onKeyDown={handleKeyDown}
    />
  );
});

NumericInput.displayName = "NumericInput";

export { NumericInput };
