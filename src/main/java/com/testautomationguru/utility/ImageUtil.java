package com.testautomationguru.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

class ImageUtil {

    static Logger logger = Logger.getLogger(ImageUtil.class.getName());

    static boolean compareAndHighlight(BufferedImage img1, BufferedImage img2, String fileName, boolean highlight, boolean generateAllCompareImage, int colorCode) throws IOException {

        final int w = img1.getWidth();
        final int h = img1.getHeight();
        //从图像数据的一部分返回默认RGB颜色模型（TYPE_INT_ARGB）和默认sRGB颜色空间中的整数像素数组。
        int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
            if (!(java.util.Arrays.equals(p1, p2))) {
                logger.warning(fileName + "  Image compared - does not match");
                if (highlight) {
                    for (int i = 0; i < p1.length; i++) {
                        if (p1[i] != p2[i]  && -1!=p2[i]) {
    //                        logger.warning("p1[i]: " + p1[i]);
    //                        logger.warning("p2[i]: " + p2[i]);
    //                        logger.warning("colorCode: " + colorCode);
                            p2[i] = colorCode;
                        }
                    }
                    /**
                     * TYPE_BYTE_GRAY 指定宽高、图像字节灰度
                     * TYPE_INT_RGB 创建一个不带透明色的对象
                     * TYPE_INT_ARGB 创建一个带透明色的对象
                     */
                    BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    out.setRGB(0, 0, w, h, p2, 0, w);
                    saveImage(out, fileName);
                }
                return false;
            }
            if (generateAllCompareImage) {
                BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                out.setRGB(0, 0, w, h, p2, 0, w);
                saveImage(out, fileName);
            }

        return true;
    }

    static void saveImage(BufferedImage image, String file) {
        try {
            File outputfile = new File(file);
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
