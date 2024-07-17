package com.heyqing.qrcode;


import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * ClassName:QRCodeByQRCode
 * Package:com.heyqing
 * Description:
 * 二维码生成与解析
 * QRCode
 *
 * @Date:2024/7/17
 * @Author:Heyqing
 */
public class QRCodeByQRCode {
    /**
     * 生成二维码
     *
     * @param name
     * @param format
     * @param content
     * @return
     */
    public static String generateQRCodeByQRCode(String name, String format, String content) {
        Qrcode qrcode = new Qrcode();
        /**
         * 纠错等级，建议 M
         */
        qrcode.setQrcodeErrorCorrect('M');
        /**
         * 字符类型，A：代表字母，N：代表数字，B:代表其他字符
         */
        qrcode.setQrcodeEncodeMode('B');
        /**
         * 版本，建议 7
         */
        int version = 7;
        qrcode.setQrcodeVersion(version);

        int width = 67 + 12 * (version - 1);
        int height = 67 + 12 * (version - 1);
        /**
         * 设置绘图基础面板
         */
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.BLACK);
        graphics.clearRect(0, 0, width, height);
        /**
         * 偏移量
         */
        int pixoff = 2;
        byte[] contentBytes = content.getBytes();
        /**
         * 字节填充绘图
         */
        if (contentBytes.length > 0 && contentBytes.length < 120) {
            boolean[][] s = qrcode.calQrcode(contentBytes);
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        graphics.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        graphics.dispose();
        image.flush();
        String filename = name + "." + format;
        String path = Paths.get("QRCode", "src", "com", "heyqing", "generate", filename).toString();
        File filepath = new File(path);
        try {
            ImageIO.write(image, format, filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("二维码生成完成！！！ \n请前往【" + filepath.toString() + "】查看");
        return filepath.toString();
    }

    /**
     * 解析二维码
     *
     * @param filepath
     * @return
     */
    public static String parserQRCodeByQRCode(String filepath) {
        File file = new File(filepath);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            QRCodeDecoder qrCodeDecoder = new QRCodeDecoder();
            String result = new String(qrCodeDecoder.decode(new MyQRCodeImage(bufferedImage)), "gb2312");
            System.out.println("解析结果：" + result);
            bufferedImage.flush();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
