package com.heyqing;


import com.google.zxing.Result;
import com.heyqing.qrcode.QRCodeByQRCode;
import com.heyqing.zxing.QRCodeByZxing;

/**
 * ClassName:QRCode
 * Package:com.heyqing
 * Description:
 * 使用zxing生成二维码
 *
 * @Date:2024/7/17
 * @Author:Heyqing
 */
public class QRCode {

    public static void main(String[] args) {
        zxingTest();
        qrcodeTest();
    }

    /**
     * zxing
     */
    private static void zxingTest() {
        String filepath = QRCodeByZxing.generateQRCodeByZxing(300, 300,
                "github", "png", "https://github.com/heyqing");
        Result result = QRCodeByZxing.parserQRCodeByZxing(filepath);
        System.out.println(result.toString());
    }

    /**
     * qrcode
     */
    private static void qrcodeTest() {
        String filepath = QRCodeByQRCode.generateQRCodeByQRCode("gitee", "png", "https://gitee.com/heyqing");
        String res = QRCodeByQRCode.parserQRCodeByQRCode(filepath);
        System.out.println(res);
    }
}
