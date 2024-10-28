import React, { useState, useEffect } from "react";
import { motion, useMotionValue, useSpring } from "framer-motion";

interface SemiCircleButtonProps {
  onClick?: () => void;
  isOpen: boolean;
  forceCollapse: boolean;
}

const SemiCircleButton: React.FC<SemiCircleButtonProps> = ({ onClick, isOpen, forceCollapse }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  // 初始位置设置在中间和下边的中间
  const y = useMotionValue(window.innerHeight * 5 / 8);
  const springY = useSpring(y, { stiffness: 1000, damping: 50 });

  useEffect(() => {
    if (isOpen) {
      setIsExpanded(true);
    } else if (forceCollapse) {
      setIsExpanded(false);
    }
  }, [isOpen, forceCollapse]);

  const handleClick = () => {
    if (isOpen) {
      setIsExpanded(false);
    }
    if (onClick) {
      onClick();
    }
  };

  const handleDrag = (event: any, info: any) => {
    const windowHeight = window.innerHeight;
    const buttonHeight = 128; // 按钮高度

    let newY = info.point.y;

    // 限制Y轴的移动范围
    newY = Math.max(buttonHeight / 2, Math.min(windowHeight - buttonHeight / 2, newY));

    y.set(newY);
  };

  return (
    <motion.button
      drag="y"
      dragConstraints={{ top: 0, bottom: window.innerHeight }}
      onDrag={handleDrag}
      style={{ y: springY }}
      onClick={handleClick}
      onMouseEnter={() => !isOpen && setIsExpanded(true)}
      onMouseLeave={() => !isOpen && setIsExpanded(false)}
      className={`fixed left-0 h-32 bg-black text-white flex items-center transition-all duration-300 ${isExpanded ? 'w-32' : 'w-4'
        } rounded-r-full pr-4 justify-end`}
      whileHover={{ scale: 1.1 }}
      whileTap={{ scale: 0.9 }}
    >
      {isExpanded && (
        <span className="text-lg font-bold">
          {isOpen ? 'Off' : 'On'}
        </span>
      )}
    </motion.button>
  );
};

export default SemiCircleButton;
