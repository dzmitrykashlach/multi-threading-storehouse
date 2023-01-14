package com.parallelcomputation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
//        extract to separate method
        BufferedImage picture = ImageIO.read(new File(Constants.PICTURE_LOCATION));

        picture = Utils.toBufferedImage(picture.getScaledInstance(3840, 2160, Image.SCALE_SMOOTH));

        BufferedImage out = new BufferedImage(picture.getWidth(), picture.getHeight(), TYPE_INT_RGB);
        int[] destPixels = ((DataBufferInt) out.getRaster().getDataBuffer()).getData();
        int[] sourcePixels = ((DataBufferInt) picture.getRaster().getDataBuffer()).getData();
//        extract to separate method

        final int BLUR_SIZE = 20;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        ArrayList<Runnable> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(4);

        int portion = out.getHeight() / 4;
        for (int p = 0; p < out.getHeight(); p += portion) {
            int finalP = p;
            tasks.add(() -> {
                for (int y = finalP; y < finalP + portion; y++) {
                    for (int x = 0; x < out.getWidth(); x++) {
                        destPixels[y* out.getWidth() + x] = Utils.getOutValue(sourcePixels, out.getWidth(), x, y, BLUR_SIZE);
                    }
                }
                latch.countDown();
            });
        }

        Utils.measureTime(() -> {
            try {
                for (Runnable task: tasks) {
                    executor.submit(task);
                }

                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "blur");


        Utils.showImageWindow(out);
    }
}
