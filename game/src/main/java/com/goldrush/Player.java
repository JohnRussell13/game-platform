package com.goldrush;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player {
    private Image image[] = new Image[4]; // 4 directions
    private ImageView imageView;
    private String name;
    private boolean playerFlag = true;
    private double stepSize = 5;

    public Player(String title, double initHeight, double initWidth, double initX, double initY){
        name = title;

        image[0] = new Image(getClass().getResource(name + "Up.png").toString(), true);
        image[1] = new Image(getClass().getResource(name + "Down.png").toString(), true);
        image[2] = new Image(getClass().getResource(name + "Left.png").toString(), true);
        image[3] = new Image(getClass().getResource(name + "Right.png").toString(), true);

        imageView = new ImageView(image[2]); // INIT POSITION

        imageView.setFitHeight(initHeight);
        imageView.setFitWidth(initWidth);
        imageView.relocate(initX, initY); 
    }

    public void move(KeyCode direction){
        double posX = imageView.getLayoutX();
        double posY = imageView.getLayoutY();
        double newX = posX;
        double newY = posY;

        if(playerFlag){
            switch(direction){
                case W:
                    imageView.setImage(image[0]);
                    newY = newY - stepSize;
                    break;
                case S:
                    imageView.setImage(image[1]);
                    newY = newY + stepSize;
                    break;
                case A:
                    imageView.setImage(image[2]);
                    newX = newX - stepSize;
                    break;
                case D:
                    imageView.setImage(image[3]);
                    newX = newX + stepSize;
                    break;
                default:
                    break;
            }

            imageView.relocate(newX, newY);
        }
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
    public double getStepSize() {return stepSize;}

    public void setPlayerFlag(boolean val) {playerFlag = val;}
    public void setStepSize(double val) {stepSize = val;}
    
}
