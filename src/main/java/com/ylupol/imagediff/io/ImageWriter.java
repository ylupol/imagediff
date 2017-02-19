package com.ylupol.imagediff.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public void write(BufferedImage image, String formatName, String path) {
        try {
            ImageIO.write(image, formatName, new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Error while writing image to path '" + path + "'.", e);
        }
    }
}
