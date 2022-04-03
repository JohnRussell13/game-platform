package com.goldrush;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ElementStatic {
    private Image image;
    private ImageView imageView;
    private String name;
    private double foreground;
    private double[] blockX = new double[2];
    private double[] blockY = new double[2];
    private boolean blockFlag;


    public ElementStatic(String title, double initHeight, double initWidth, double initX, double initY, double fore,
                        double startX, double endX, double startY, double endY, boolean block){
        name = title;
        foreground = fore;

        blockX[0] = startX*initWidth + initX;
        blockX[1] = endX*initWidth + initX;
        blockY[0] = startY*initHeight + initY;
        blockY[1] = endY*initHeight + initY;
        blockFlag = block;

        image = new Image(getClass().getResource(name + ".png").toString(), true);
        imageView = new ImageView(image);

        imageView.setFitHeight(initHeight);
        imageView.setFitWidth(initWidth);
        imageView.relocate(initX, initY); 
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
    public double getForeground() {return foreground;}
    public double[] getBlockX() {return blockX;}
    public double[] getBlockY() {return blockY;}

    public boolean getBlockFlag() {return blockFlag;}

    public void setBlockX(double[] val) {blockX = val;}
    public void setBlockY(double[] val) {blockY = val;}
}
