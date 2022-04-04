package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {
    private ImageView imageView;

    public Background(){
        double initWidth = 0;
        double initHeight = 0;
        double initX = 0;
        double initY = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/background.txt");
        try {
            Scanner scanIn = new Scanner(file);
            String[] line = scanIn.nextLine().split(",", 4);
            initHeight = Double.parseDouble(line[0]);
            initWidth = Double.parseDouble(line[1]);
            initX = Double.parseDouble(line[2]);
            initY = Double.parseDouble(line[3]);
            
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        imageView = new ImageView(new Image(getClass().getResource("background.png").toString(), true));
        imageView.setFitWidth(initWidth);
        imageView.setFitHeight(initHeight);
        imageView.relocate(initX, initY);
    }

    public ImageView getImageView() {return imageView;}
}
