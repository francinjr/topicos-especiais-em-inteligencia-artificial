export const TOKEN_KEY = "authToken";

export function saveToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

export function saveEmail(email: string) {
  localStorage.setItem("userEmail", email);
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem("userEmail");
}

export function logoutDelayed(delayMs = 3000) {
  logout();
  setTimeout(() => {
    window.location.href = "/";
  }, delayMs);
}
