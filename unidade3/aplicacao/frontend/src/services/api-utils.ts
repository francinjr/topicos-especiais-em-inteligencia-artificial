import { ApiResponse } from "./api-response";

export function createResponseMessage(error: any): string {
  if (!error.errors || error.errors.length === 0) {
    return error.message;
  }

  let fieldsMessages = "";

  error.errors.forEach((err: any) => {
    fieldsMessages += err.message + "\n";
  });
  return `${error.message}:\n${fieldsMessages}`;
}
