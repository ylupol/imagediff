package com.ylupol.imagediff;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class DifferentPixelsFinder {

    private static final float CHANNEL_MAX_VALUE = 255;

    private static final int PIXELS_DIFF_PERCENTAGE_THRESHOLD = 10;

    private Set<Rectangle> rectanglesToExclude;

    public DifferentPixelsFinder(Set<Rectangle> rectanglesToExclude) {
        this.rectanglesToExclude = rectanglesToExclude;
    }

    public Set<Point> getDifferentPixelsAsPoints(BufferedImage image1, BufferedImage image2) {

        Set<Point> differentPixels = new HashSet<>();

        for (int i = 0; i < image1.getWidth(); i++) {
            for (int j = 0; j < image1.getHeight(); j++) {

                Point pixel = new Point(i, j);

                if (isInExcludedArea(pixel)) {
                    continue;
                }

                int rgb1 = image1.getRGB(i, j);
                int rgb2 = image2.getRGB(i, j);

                if (arePixelsDifferent(rgb1, rgb2)) {
                    differentPixels.add(new Point(i, j));
                }

            }
        }

        return differentPixels;
    }

    private boolean isInExcludedArea(Point pixel) {
        return rectanglesToExclude.stream().filter(r -> r.contains(pixel)).count() > 0;
    }

    private boolean arePixelsDifferent(int rgb1, int rgb2) {

        // short-circuit check
        if (rgb1 == rgb2) {
            return false;
        }

        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = (rgb1) & 0xff;

        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = (rgb2) & 0xff;

        float redDiffRatio = (float) Math.abs(r1 - r2) / CHANNEL_MAX_VALUE;
        float greenDiffRatio = (float) Math.abs(g1 - g2) / CHANNEL_MAX_VALUE;
        float blueDiffRatio = (float) Math.abs(b1 - b2) / CHANNEL_MAX_VALUE;

        float pixelDiffRatio = (redDiffRatio + greenDiffRatio + blueDiffRatio) / 3;

        return pixelDiffRatio > PIXELS_DIFF_PERCENTAGE_THRESHOLD / 100;
    }
}
