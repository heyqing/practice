package com.heyqing.qrcode;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * ClassName:MyQRCodeImage
 * Package:com.heyqing
 * Description:
 *
 * @Date:2024/7/17
 * @Author:Heyqing
 */
public class MyQRCodeImage implements QRCodeImage {

    private BufferedImage bufferedImage;

    public MyQRCodeImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public int getPixel(int i, int i1) {
        return bufferedImage.getRGB(i,i1);
    }
}
