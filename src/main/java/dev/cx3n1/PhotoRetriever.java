package dev.cx3n1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class PhotoRetriever {

    private static final String MAIN_URL_OF_SITE = "https://prnt.sc/";

    public static void generatePicturesInDirectory(String directory, int pictureAmount) {
        Set<String> urls = new TreeSet<>();

        for (int i = 0; i < pictureAmount; ) {
            String imgLoc = generateRandomImageLocation();
            if (urls.add(imgLoc)) {
                String siteURL = MAIN_URL_OF_SITE + imgLoc;
                String imgSrc = "";
                System.out.println(imgLoc);
                try {
                    Document document = Jsoup.connect(siteURL).get();

                    Elements images =
                            document.select(".screenshot-image[src~=(?i)\\.(png|jpe?g|gif)]");

                    imgSrc = images.attr("src");
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                try {
                    URL url = new URL(imgSrc);
                    BufferedImage bi = ImageIO.read(url);
                    File outputFile = new File(directory + "/" + i + "_" + imgLoc + ".png");
                    ImageIO.write(bi, "png", outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                i++;
            }
        }
    }

    private static String generateRandomImageLocation() {
        Random rand = new Random(LocalTime.now().toEpochSecond(LocalDate.EPOCH, ZoneOffset.UTC));

        return String.valueOf(
                (char) (rand.nextInt(97, 97 + 26))) +
                (char) (rand.nextInt(97, 97 + 26)) +
                rand.nextInt(0, 9) +
                rand.nextInt(0, 9) +
                rand.nextInt(0, 9) +
                rand.nextInt(0, 9);
    }

}
