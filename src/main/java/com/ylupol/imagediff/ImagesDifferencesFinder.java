package com.ylupol.imagediff;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ImagesDifferencesFinder {

    private Set<Rectangle> rectanglesToExclude = new HashSet<>();

    public ImagesDifferencesFinder() {

    }

    public ImagesDifferencesFinder(Set<Rectangle> rectanglesToExclude) {
        this.rectanglesToExclude = rectanglesToExclude;
    }

    public BufferedImage getImageWithDiffs(BufferedImage image1, BufferedImage image2) {

        validateSizeEquality(image1, image2);

        DifferentPixelsFinder differentPixelsFinder = new DifferentPixelsFinder(rectanglesToExclude);
        Set<Point> differentPixels = differentPixelsFinder.getDifferentPixelsAsPoints(image1, image2);

        if (differentPixels.isEmpty()) {
            return image1;
        }

        Set<Set<Point>> clusters = new ClustersFinder().findPixelsClusters(differentPixels);

        Set<Rectangle> rectangles = toBoundingRectangles(clusters);

        drawRectangles(image2, rectangles);

        return image2;
    }

    private Set<Rectangle> toBoundingRectangles(Set<Set<Point>> clusters) {
        return clusters.stream()
                .map(new PixelsClusterToBoundingRectangleMapper())
                .collect(Collectors.toSet());
    }

    private void drawRectangles(BufferedImage image, Set<Rectangle> rectangles) {

        Graphics graphics = image.getGraphics();

        rectangles.forEach(r ->
        {
            graphics.setColor(Color.RED);
            graphics.draw3DRect((int) r.getX(),
                    (int) r.getY(),
                    (int) r.getWidth(),
                    (int) r.getHeight(), false);
        });
    }

    private void validateSizeEquality(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            throw new IllegalArgumentException("images have different size");
        }
    }
}