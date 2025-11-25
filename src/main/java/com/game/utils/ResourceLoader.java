package com.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ResourceLoader {
    public static BufferedImage loadImage(String path) {
        try {
            // Gunakan ClassLoader untuk memuat resource
            InputStream is = ResourceLoader.class.getResourceAsStream(path);

            if (is == null) {
                System.err.println("Resource tidak ditemukan: " + path);
                return null;
            }

            BufferedImage image = ImageIO.read(is);
            is.close();

            if (image == null) {
                System.err.println("Gagal decode image: " + path);
            } else {
                System.out.println("Berhasil load image: " + path +
                        " (" + image.getWidth() + "x" + image.getHeight() + ")");
            }

            return image;

        } catch (IOException e) {
            System.err.println("Error loading image " + path + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}