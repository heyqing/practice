"""
@FileName：generate_image.py
@Description：

@Author：HeYiQing
@Time：2024/8/11 18:02
"""
import sys
import cv2
import numpy as np

def main():
    img_filename1 = sys.argv[1]
    img_filename2 = sys.argv[2]
    output_img_filename = sys.argv[3]

    A = cv2.imread(img_filename1)
    B = cv2.imread(img_filename2)

    height, width, _ = A.shape
    B = cv2.resize(B, (width, height))

    A_gray = cv2.cvtColor(A, cv2.COLOR_BGR2GRAY)
    B_gray = cv2.cvtColor(B, cv2.COLOR_BGR2GRAY)
    B_gray = 0.5 * B_gray + 20
    alpha = 255 - A_gray.astype('int') + B_gray.astype('int')
    alpha = np.clip(alpha, 1, 255).reshape(height, width, 1)

    P = (A - (255 - alpha)) / (alpha / 255)
    alpha = alpha.astype('u8')
    P = np.clip(P, 0, 255)

    image_with_alpha = np.concatenate([P, alpha], axis=2)
    cv2.imwrite(output_img_filename, image_with_alpha)

if __name__ == '__main__':
    main()