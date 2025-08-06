"use client";

import {
  Home,
  Users,
  Settings,
  LogOut,
  ArrowRightLeft,
  Box,
  Apple,
} from "lucide-react";
import SidebarItem from "./side-bar-item";
import { useApp } from "@/contexts/global-context";
import { usePathname } from "next/navigation";
import Link from "next/link";
import { UserRole } from "@/services/user/usuario-service";
import { toast } from "sonner";
import { logoutDelayed } from "@/utils/auth";

const navLinks = [
  {
    label: "Início",
    href: "/",
    icon: <Home size={20} />,
    roles: [UserRole.ADMIN],
  },
  {
    label: "Alimentos",
    href: "/alimentos",
    icon: <Apple size={20} />,
    roles: [UserRole.ADMIN],
  },
  {
    label: "Pacientes",
    href: "/pacientes",
    icon: <Users size={20} />,
    roles: [UserRole.ADMIN],
  },
  {
    label: "Diagnósticos",
    href: "/diagnosticos",
    icon: <Box size={20} />,
    roles: [UserRole.ADMIN],
  },
];

export default function Sidebar() {
  const { user } = useApp();
  const pathname = usePathname();

  const accessibleLinks = user
    ? navLinks.filter((link) => link.roles.includes(user.role))
    : [];

  if (!user) {
    return null;
  }

  return (
    <aside className="h-[90vh] bg-white shadow-md border-r flex rounded-lg flex-col">
      <nav className="flex flex-col p-4">
        {accessibleLinks.map((link) => (
          <Link href={link.href} key={link.label} passHref>
            <SidebarItem
              icon={link.icon}
              label={link.label}
              active={pathname === link.href}
            />
          </Link>
        ))}
      </nav>

      <div className="w-full [&>button]:w-full px-4 mb-4 mt-auto">
        <SidebarItem
          icon={<LogOut size={20} />}
          label="Sair"
          onClick={() => {
            toast.error("Saindo do sistema.");
            logoutDelayed(2000);
          }}
        />
      </div>
    </aside>
  );
}
