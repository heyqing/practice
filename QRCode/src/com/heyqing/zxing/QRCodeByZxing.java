package com.heyqing.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:QRCodeByZxing
 * Package:com.heyqing
 * Description:
 * 二维码生成与解析
 * Zxing
 *
 * @Date:2024/7/17
 * @Author:Heyqing
 */
public class QRCodeByZxing {
    /**
     * 生成二维码
     *
     * @param width
     * @param height
     * @param name
     * @param format
     * @param content
     */
    public static String generateQRCodeByZxing(int width, int height, String name, String format, String content) {
        /**
         * 定义二维码参数
         */
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            String filename = name + "." + format;
            Path filepath = Paths.get("QRCode", "src", "com", "heyqing", "generate", filename);
            MatrixToImageWriter.writeToPath(bitMatrix, format, filepath);
            MatrixToImageWriter.writeToPath(bitMatrix, format, filepath);
            System.out.println("二维码生成完成！！！ \n请前往【" + filepath.toString() + "】查看");
            return filepath.toString();
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析二维码
     *
     * @param filepath
     * @return
     */
    public static Result parserQRCodeByZxing(String filepath) {
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(filepath);
        try {
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(
                    new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            /**
             * 定义二维码参数
             */
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

            Result result = formatReader.decode(binaryBitmap, hints);
            System.out.println("解析结果：" + result.toString());
            System.out.println("二维码格式：" + result.getBarcodeFormat());
            System.out.println("二维码文本内容：" + result.getText());
            image.flush();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
