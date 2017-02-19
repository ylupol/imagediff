package com.ylupol.imagediff.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {

    public BufferedImage read(String pathToImage) {
        try {
            return ImageIO.read(new File(pathToImage));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading image from path='" + pathToImage + "'.", e);
        }
    }
}
