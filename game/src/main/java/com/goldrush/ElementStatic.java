package com.goldrush;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ElementStatic {
    private Image image;
    private ImageView imageView;
    private String name;

    public ElementStatic(String title, double initHeight, double initWidth, double initX, double initY){
        name = title;

        image = new Image(getClass().getResource(name + ".png").toString(), true);
        imageView = new ImageView(image);

        imageView.setFitHeight(initHeight);
        imageView.setFitWidth(initWidth);
        imageView.relocate(initX, initY); 
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
}
