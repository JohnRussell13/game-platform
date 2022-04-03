package com.goldrush;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ElementStatic {
    private Image image;
    private ImageView imageView;
    private String name;
    private double blockPercentage;

    public ElementStatic(String title, double initHeight, double initWidth, double initX, double initY, double block){
        name = title;
        blockPercentage = block;

        image = new Image(getClass().getResource(name + ".png").toString(), true);
        imageView = new ImageView(image);

        imageView.setFitHeight(initHeight);
        imageView.setFitWidth(initWidth);
        imageView.relocate(initX, initY); 
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
    public double getBlock() {return blockPercentage;}
}
