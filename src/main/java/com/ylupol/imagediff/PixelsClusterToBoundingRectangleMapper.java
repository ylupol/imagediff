package com.ylupol.imagediff;

import java.awt.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class PixelsClusterToBoundingRectangleMapper implements Function<Set<Point>, Rectangle> {

    private static final Comparator<Point> X_AXIS_COMPARATOR = Comparator.comparing(Point::getX);

    private static final Comparator<Point> Y_AXIS_COMPARATOR =
            Comparator.comparing(Point::getY, Comparator.reverseOrder());

    @Override
    public Rectangle apply(Set<Point> points) {

        Optional<Point> northWest = points.stream().min(X_AXIS_COMPARATOR.thenComparing(Y_AXIS_COMPARATOR));
        Optional<Point> southEast = points.stream().max(X_AXIS_COMPARATOR.thenComparing(Y_AXIS_COMPARATOR));

        int width = southEast.get().x - northWest.get().x;
        int height = southEast.get().y - northWest.get().y;

        return new Rectangle(northWest.get(), new Dimension(width, height));
    }
}
