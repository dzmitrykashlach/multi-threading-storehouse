package com.parallelcomputation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    public static void showImageWindow(Image image) {
        showImageWindow(image, image.getWidth(null), image.getHeight(null));
    }

    public static void showImageWindow(Image image, int width, int height) {

        if (image.getWidth(null) != width || image.getHeight(null) != height) {
            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }

        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel picLabel = new JLabel(new ImageIcon(image));

        BorderLayout borderLayout = new BorderLayout();
        frame.getContentPane().setLayout(borderLayout);
        frame.getContentPane().add(picLabel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void measureTime(Runnable task, String taskName) {
        long startTime = System.currentTimeMillis();
        task.run();
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(taskName + ": " + elapsed + " ms");
    }

    public static BufferedImage toBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public static int getOutValue(int[] source, int line, int x, int y, int blurSize) {
        int halfSize = blurSize / 2;
        int count = 0;
        int rsum = 0;
        int gsum = 0;
        int bsum = 0;
        for (int i = y - halfSize; i <= y + halfSize; i++) {
            for (int j = x - halfSize; j <= x + halfSize; j++) {
                if (j >=0 && i >= 0 && j < line && (i < source.length / line)) {
                    count += 1;
                    rsum += (source[i * line + j] & 0xff0000) >> 16;
                    gsum += (source[i * line + j] & 0xff00) >> 8;
                    bsum += (source[i * line + j] & 0xff);
                }
            }
        }
        return 0xff000000 | ((rsum / count) << 16) | ((gsum / count) << 8) | (bsum / count);
    }
}
