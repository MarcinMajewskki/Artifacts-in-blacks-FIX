package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PhotoFix extends IOException {
    private final String FILE_PATH;

    public PhotoFix(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public void run() throws IOException {
        getJpgFiles().forEach(file -> {
            try {
                changePixelsInPhoto(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("End ...");
    }
    public List<File> getJpgFiles() {
        return Arrays.stream(Objects.requireNonNull(new File(this.FILE_PATH)
                        .listFiles(File::isFile)))
                .filter(file -> file.getName()
                        .endsWith(".jpg"))
                .collect(Collectors.toList());
    }
    public void changePixelsInPhoto(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int pixel = img.getRGB(x, y);

                Color color = new Color(pixel, true);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (red < 5 && blue < 5 && green < 5) {
                    red = 5;
                    blue = 5;
                    green = 5;
                }

                color = new Color(red, green, blue);
                img.setRGB(x, y, color.getRGB());
            }
        }
        file = new File(file.getPath().replace(".jpg","_fix.jpg"));
        ImageIO.write(img, "jpg", file);
        System.out.println(file.getName() + " is done");
    }
}