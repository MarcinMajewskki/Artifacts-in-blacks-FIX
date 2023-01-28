package org.example;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        PhotoFix photoFix = new PhotoFix("PATH TO DIRECTORY");
        photoFix.run();
    }
}