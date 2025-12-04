package com.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ResourceLoader {
    private static final Logger logger = Logger.getLogger(ResourceLoader.class.getName());
    private ResourceLoader() {
        // Private constructor to prevent instantiation
    }
    public static BufferedImage loadImage(String path) {
        try {
            // Gunakan ClassLoader untuk memuat resource
            InputStream is = ResourceLoader.class.getResourceAsStream(path);

            if (is == null) {
                String rscNotFound = "Resource tidak ditemukan: " + path; 
                logger.info(rscNotFound);
                return null;
            }

            BufferedImage image = ImageIO.read(is);
            is.close();

            if (image == null) {
                String failedDecodeImage = "Gagal decode image: " + path; 
                logger.info(failedDecodeImage);
            } else {
                String succesLoadImage = "Berhasil load image: " + path +
                        " (" + image.getWidth() + "x" + image.getHeight() + ")"; 
                logger.info(succesLoadImage);
            }

            return image;

        } catch (IOException e) {
            logger.info("Error loading image " + path + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}