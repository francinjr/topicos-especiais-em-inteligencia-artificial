import type { Metadata } from "next";
import { Geist, Geist_Mono, Lexend } from "next/font/google";
import "./globals.css";

import SideBar from "@/components/layout/side-bar";
import { ThemeProvider } from "next-themes";
import { Toaster } from "sonner";
import { AppProvider } from "@/contexts/global-context";
import TopBar from "@/components/layout/top-bar";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

const lexend = Lexend({
  subsets: ["latin"],
  weight: ["100", "400", "700", "900"],
  variable: "--font-lexend",
  display: "swap",
});

export const metadata: Metadata = {
  title: "Allergen-Free",
  description: "Aplicação para identificar alimentos alergênicos",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <AppProvider>
      <html lang="pt-BR">
        <body
          className={`${geistSans.variable} ${geistMono.variable} light w-screen h-screen antialiased`}
        >
          <div className="grid grid-cols-12">
            <div className="col-span-12 border-b-2">
              <TopBar />
            </div>
            <div className="col-span-2">
              <SideBar />
            </div>
            <div className="col-span-10">
              {children}
              <Toaster
                richColors
                position="top-right"
                toastOptions={{
                  style: {
                    fontSize: 14,
                    fontWeight: "bold",
                  },
                  className: "whitespace-pre-line",
                }}
              />
            </div>
          </div>
        </body>
      </html>
    </AppProvider>
  );
}
