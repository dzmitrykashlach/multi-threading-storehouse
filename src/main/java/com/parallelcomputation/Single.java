package com.parallelcomputation;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Single {

    public static void main(String[] args) throws IOException {
        //        extract to separate method
        BufferedImage picture = ImageIO.read(new File(Constants.PICTURE_LOCATION));

        picture = Utils.toBufferedImage(picture.getScaledInstance(3840, 2160, Image.SCALE_SMOOTH));

        BufferedImage out = new BufferedImage(picture.getWidth(), picture.getHeight(), TYPE_INT_RGB);
        int[] destPixels = ((DataBufferInt) out.getRaster().getDataBuffer()).getData();
        int[] sourcePixels = ((DataBufferInt) picture.getRaster().getDataBuffer()).getData();

        //        extract to separate method
        final int BLUR_SIZE = 4;

        Utils.measureTime(() -> {
            for (int y = 0; y < out.getHeight(); y++) {
                for (int x = 0; x < out.getWidth(); x++) {
                    destPixels[y * out.getWidth() + x] = Utils.getOutValue(sourcePixels, out.getWidth(), x, y, BLUR_SIZE);
                }
            }
        }, "blur");

        Utils.showImageWindow(out);
    }
}
