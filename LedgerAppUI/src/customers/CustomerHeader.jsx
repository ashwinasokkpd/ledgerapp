import { NavLink, Link } from "react-router";
import { Menu } from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  Sheet,
  SheetContent,
  SheetTrigger,
  SheetTitle,
  SheetHeader,
  SheetFooter,
  SheetClose,
} from "@/components/ui/sheet";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import useAuth from "@/AuthContext";

const linkStyles = ({ isActive }) =>
  `text-sm font-medium transition-colors hover:text-foreground/80 ${isActive ? "text-foreground font-semibold" : "text-foreground/60"
  }`;

export default function Header() {
  const { logout, loginUser } = useAuth();
  const initial = loginUser?.username?.[0]?.toUpperCase() ?? "?";

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="mx-auto flex h-14 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8">
        <div className="flex items-center space-x-6">
          <NavLink to="/" className="font-bold">
            Ledger
          </NavLink>
          <nav className="hidden items-center space-x-6 md:flex">

          </nav>
        </div>

        <div className="flex items-center space-x-4">
          {/* Desktop user menu */}
          <div className="hidden items-center md:flex">
            <DropdownMenu>
              <DropdownMenuTrigger
                render={
                  <Button
                    variant="ghost"
                    size="icon"
                    className="rounded-full"
                    aria-label="Open user menu"
                  />
                }
              >
                <Avatar>
                  <AvatarImage src="https://github.com/shadcn.png" alt="" />
                  <AvatarFallback>{initial}</AvatarFallback>
                </Avatar>
              </DropdownMenuTrigger>

              <DropdownMenuContent className="w-40">
                <DropdownMenuGroup>
                  <DropdownMenuItem>Profile</DropdownMenuItem>
                  <DropdownMenuItem>Billing</DropdownMenuItem>
                  <DropdownMenuItem>Settings</DropdownMenuItem>
                </DropdownMenuGroup>
                <DropdownMenuSeparator />
                <DropdownMenuGroup>
                  <DropdownMenuItem variant="destructive" onClick={logout}>
                    Log out
                  </DropdownMenuItem>
                </DropdownMenuGroup>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>

          {/* Mobile menu */}
          <div className="flex md:hidden">
            <Sheet>
              <SheetTrigger
                render={
                  <Button variant="ghost" size="icon" aria-label="Open navigation" />
                }
              >
                <Menu />
              </SheetTrigger>
              <SheetContent>
                <SheetHeader>
                  <SheetTitle>Navigation</SheetTitle>
                </SheetHeader>
                <nav className="flex flex-col gap-2 px-4">
                  <SheetClose render={<Link to="/" className="text-sm font-medium" />}>
                    Home
                  </SheetClose>
                </nav>
                <SheetFooter>
                  <Button variant="destructive" onClick={logout}>
                    Log out
                  </Button>
                </SheetFooter>
              </SheetContent>
            </Sheet>
          </div>
        </div>
      </div>
    </header>
  );
}