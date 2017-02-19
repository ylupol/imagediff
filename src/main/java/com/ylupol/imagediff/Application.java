package com.ylupol.imagediff;

import com.ylupol.imagediff.io.ImageReader;
import com.ylupol.imagediff.io.ImageWriter;

import java.awt.image.BufferedImage;

public class Application {

    private static final String IMAGE_TYPE = "png";

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("3 arguments should be specified: pathToImage1, pathToImage2, pathToImageWithDiffs");
            System.exit(1);
        }

        String pathToImage1 = args[0];
        String pathToImage2 = args[1];
        String pathToImageWithDiffs = args[2];

        ImageReader imageReader = new ImageReader();
        BufferedImage image1 = imageReader.read(pathToImage1);
        BufferedImage image2 = imageReader.read(pathToImage2);

        BufferedImage diff = new ImagesDifferencesFinder().getImageWithDiffs(image1, image2);

        new ImageWriter().write(diff, IMAGE_TYPE, pathToImageWithDiffs);
    }
}
