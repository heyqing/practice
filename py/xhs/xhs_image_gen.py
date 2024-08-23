"""
@FileName：xhs_image_gen.py
@Description：

@Author：HeYiQing
@Time：2024/8/11 13:00
"""
import cv2
import numpy as np


def mirage_tank(img_filename1: str, img_filename2: str, output_img_filename: str, a=0.5, b=20):
    A = cv2.imread(img_filename1)
    B = cv2.imread(img_filename2)

    height, width, _ = A.shape
    # 保证两个图片的大小一致
    B = cv2.resize(B, (width, height))

    # 计算 alpha 通道
    A_gray = cv2.cvtColor(A, cv2.COLOR_BGR2GRAY)
    B_gray = cv2.cvtColor(B, cv2.COLOR_BGR2GRAY)
    B_gray = a * B_gray + b  # 适当调暗图片B
    alpha = 255 - A_gray.astype('int') + B_gray.astype('int')
    alpha = np.clip(alpha, 1, 255).reshape(height, width, 1)

    # 使用 A 或者 B，决定了彩图来自哪个图
    # P = B / (a / 255)
    P = (A - (255 - alpha)) / (alpha / 255)

    alpha = alpha.astype('u8')
    P = np.clip(P, 0, 255)

    # 带有透明度通道的图像
    image_with_alpha = np.concatenate([P, alpha], axis=2)

    cv2.imwrite(output_img_filename, image_with_alpha)


if __name__ == '__main__':
    mirage_tank("E:\\1.jpg", "E:\\2.jpg", "output.png")
