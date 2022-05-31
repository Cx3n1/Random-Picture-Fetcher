package dev.cx3n1;

public class Launcher {

    private static final String DESTINATION_DIRECTORY_NAME = "Directory101";

    public static void main(String[] args) {
        PhotoRetriever.generatePicturesInDirectory(DESTINATION_DIRECTORY_NAME, 10);
    }

}
