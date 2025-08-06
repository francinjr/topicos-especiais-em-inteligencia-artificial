import { cn } from "@/lib/utils";

interface SidebarItemProps {
  icon: React.ReactNode;
  label: string;
  active?: boolean;
  onClick?: () => void;
}

export default function SidebarItem({
  icon,
  label,
  active = false,
  onClick,
}: SidebarItemProps) {
  return (
    <button
      onClick={onClick}
      className={cn(
        "flex items-center gap-3 px-3 py-2 rounded-md text-sm font-medium hover:bg-gray-100 transition-all w-full",
        active && "bg-gray-100 text-primary"
      )}
    >
      {icon}
      <span className="truncate">{label}</span>
    </button>
  );
}
