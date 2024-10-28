import React from "react";
import { FloatingDock } from "@/components/ui/floating-dock";
import { BackgroundBeamsWithCollision } from "@/components/ui/background-beams-with-collision";
import {
  IconBrandGithub,
  IconBrandX,
  IconExchange,
  IconHome,
  IconNewSection,
  IconTerminal2,
} from "@tabler/icons-react";
import Image from "next/image";

export function Navbar() {
  return (
    <div className="w-full h-full relative overflow-hidden rounded-3xl">
      <BackgroundBeamsWithCollision>
        <div className="flex flex-col items-center justify-between h-full py-4">
          <div className="text-center">
            <h1 className="text-2xl md:text-3xl lg:text-4xl font-bold text-black dark:text-white font-sans tracking-tight mb-2">
              HQweb
            </h1>
            <div className="relative mx-auto inline-block w-max [filter:drop-shadow(0px_1px_3px_rgba(27,_37,_80,_0.14))]">
              <div className="absolute left-0 top-[1px] bg-clip-text bg-no-repeat text-transparent bg-gradient-to-r from-purple-500 via-violet-500 to-pink-500 [text-shadow:0_0_rgba(0,0,0,0.1)]">
                <span className="text-lg md:text-xl lg:text-2xl">导航栏</span>
              </div>
              <div className="relative bg-clip-text text-transparent bg-no-repeat bg-gradient-to-r from-purple-500 via-violet-500 to-pink-500">
                <span className="text-lg md:text-xl lg:text-2xl">导航栏</span>
              </div>
            </div>
          </div>
          <div className="flex-grow flex items-center justify-center">
            <MyDock />
          </div>
        </div>
      </BackgroundBeamsWithCollision>
    </div>
  )
}

export function MyDock() {
  const links = [
    {
      title: "Home",
      icon: (
        <IconHome className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
    {
      title: "Products",
      icon: (
        <IconTerminal2 className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
    {
      title: "Components",
      icon: (
        <IconNewSection className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
    {
      title: "HQweb",
      icon: (
        <Image
          src="/public/images/HQweb_write.jpg"
          width={20}
          height={20}
          alt="HQweb "
        />
      ),
      href: "#",
    },
    {
      title: "Changelog",
      icon: (
        <IconExchange className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
    {
      title: "Twitter",
      icon: (
        <IconBrandX className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
    {
      title: "GitHub",
      icon: (
        <IconBrandGithub className="h-full w-full text-neutral-500 dark:text-neutral-300" />
      ),
      href: "#",
    },
  ];
  return (
    <div className="w-full">
      <FloatingDock
        mobileClassName="translate-y-20 scale-75 transform-gpu" // 移动端样式
        desktopClassName="scale-75 transform-gpu" // 桌面端样式
        items={links}
      />
    </div>
  );
}
