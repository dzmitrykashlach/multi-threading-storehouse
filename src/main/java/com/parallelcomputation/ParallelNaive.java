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

public class ParallelNaive {

    public static void main(String[] args) throws IOException {
        //        extract to separate method
        BufferedImage picture = ImageIO.read(new File(Constants.PICTURE_LOCATION));

        picture = Utils.toBufferedImage(picture.getScaledInstance(3840, 2160, Image.SCALE_SMOOTH));

        BufferedImage out = new BufferedImage(picture.getWidth(), picture.getHeight(), TYPE_INT_RGB);
        int[] destPixels = ((DataBufferInt) out.getRaster().getDataBuffer()).getData();
        int[] sourcePixels = ((DataBufferInt) picture.getRaster().getDataBuffer()).getData();
//        extract to separate method

        int taskCount = out.getWidth() * out.getHeight();

        final int BLUR_SIZE = 20;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        ArrayList<Runnable> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int y = 0; y < out.getHeight(); y++) {
            for (int x = 0; x < out.getWidth(); x++) {
                int finalY = y;
                int finalX = x;
                tasks.add(() -> {
                    destPixels[finalY * out.getWidth() + finalX] = Utils.getOutValue(sourcePixels, out.getWidth(), finalX, finalY, BLUR_SIZE);
                    latch.countDown();
                });
            }
        }

        long startTime = System.currentTimeMillis();

        for(Runnable task: tasks) {
            executor.submit(task);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time" + ": " + elapsed + " ms");

        Utils.showImageWindow(out);
    }
}
