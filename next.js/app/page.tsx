"use client";

import React, { useState, useEffect, useRef } from "react";
import { Navbar } from "@/components/heyqing/MyDock";
import SemiCircleButton from "@/components/heyqing/SemiCircleButton";

export default function Home() {
  const [isDockVisible, setIsDockVisible] = useState(false);
  const [forceCollapse, setForceCollapse] = useState(false);
  const semiCircleButtonRef = useRef<HTMLDivElement>(null);

  const toggleDock = () => {
    setIsDockVisible(!isDockVisible);
    setForceCollapse(false);
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        isDockVisible &&
        semiCircleButtonRef.current &&
        !semiCircleButtonRef.current.contains(event.target as Node)
      ) {
        setIsDockVisible(false);
        setForceCollapse(true);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isDockVisible]);

  return (
    <main className="relative min-h-screen">
      {/* 背景内容 */}
      <div className={`transition-all duration-300 ${isDockVisible ? 'blur-sm' : ''}`}>
        <div className="p-4">
          <h1 className="text-2xl font-bold mb-4">欢迎来到我的页面</h1>
          <p>这里是一些示例内容，当 Dock 打开时，这些内容会被模糊。</p>
        </div>
        {/* 在这里添加更多的页面内容 */}
      </div>

      {/* Navbar 组件 */}
      {isDockVisible && (
        <div className="fixed inset-0 flex items-center justify-center pointer-events-none">
          <div className="w-[75%] h-[75%] pointer-events-auto">
            <Navbar />
          </div>
        </div>
      )}

      {/* 半圆形按钮 */}
      <div ref={semiCircleButtonRef}>
        <SemiCircleButton onClick={toggleDock} isOpen={isDockVisible} forceCollapse={forceCollapse} />
      </div>
    </main>
  );
}
