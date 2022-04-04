package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Menu {
    private ImageView imageView;

    public Menu(){
        double menuWidth = 0;
        double menuHeight = 0;
        double menuX = 0;
        double menuY = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/menu.txt");
        try {
            Scanner scanIn = new Scanner(file);
            String[] line = scanIn.nextLine().split(",", 4);
            menuHeight = Double.parseDouble(line[0]);
            menuWidth = Double.parseDouble(line[1]);
            menuX = Double.parseDouble(line[2]);
            menuY = Double.parseDouble(line[3]);
            
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        imageView = new ImageView(new Image(getClass().getResource("menu.png").toString(), true));
        imageView.setFitWidth(menuWidth);
        imageView.setFitHeight(menuHeight);
        imageView.relocate(menuX, menuY);
    }

    public ImageView getImageView() {return imageView;}
}
