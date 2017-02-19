package com.ylupol.imagediff;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ClustersFinder {

    private static final int RADIUS = 5;

    public Set<Set<Point>> findPixelsClusters(Set<Point> pixels) {

        Set<Point> processedPixels = new HashSet<>();
        Set<Set<Point>> clusters = new HashSet<>();

        for (Point pixel : pixels) {

            if (processedPixels.contains(pixel)) {
                continue;
            }

            Set<Point> cluster = new HashSet<>();

            LinkedList<Point> queue = new LinkedList<>();
            queue.add(pixel);

            while (!queue.isEmpty()) {

                Point p = queue.remove();
                cluster.add(p);

                Set<Point> pixelsAround = findPixelsAround(p, pixels, RADIUS);

                pixelsAround.removeAll(queue);
                pixelsAround.removeAll(cluster);

                queue.addAll(pixelsAround);
            }

            clusters.add(cluster);
            processedPixels.addAll(cluster);
        }

        return clusters;
    }

    private Set<Point> findPixelsAround(Point pixel, Set<Point> pixels, int radius) {

        Set<Point> pixelsAround = getPixelsAround(pixel, radius);
        pixelsAround.retainAll(pixels);

        return pixelsAround;
    }

    private Set<Point> getPixelsAround(Point pixel, int radius) {

        Set<Point> pixelsAround = new HashSet<>();

        int minX = pixel.x - radius;
        int maxX = pixel.x + radius;
        int minY = pixel.y - radius;
        int maxY = pixel.y + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {

                Point candidate = new Point(x, y);

                if (!candidate.equals(pixel)) {
                    pixelsAround.add(candidate);
                }
            }
        }

        return pixelsAround;
    }
}
