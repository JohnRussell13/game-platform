package com.goldrush;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

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

    public void move(KeyCode direction, Pane layout, ElementStatics elementStatics, Rectangle blackBandL, Rectangle blackBandR, boolean screenFlag){
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

            double feetX = newX + imageView.getFitWidth()/2;
            double feetY = newY + imageView.getFitHeight();

            for(int i = elementStatics.size()-1; i >= 0; i--){
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                if(blockX[0] < feetX && feetX < blockX[1]
                && blockY[0] < feetY && feetY < blockY[1]){
                    
                    if(elementStatics.getElement(i).getBlockFlag()) return;
                    else break;
                }
            }

            imageView.relocate(newX, newY);
            
            layout.getChildren().remove(imageView);
            layout.getChildren().add(imageView);

            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                if(current.getLayoutY() + elementStatics.getElement(i).getForeground()*current.getFitHeight() > feetY){
                    layout.getChildren().remove(current);
                    layout.getChildren().add(current);
                }
            }

            if(screenFlag){
                layout.getChildren().remove(blackBandL);
                layout.getChildren().add(blackBandL);
                layout.getChildren().remove(blackBandR);
                layout.getChildren().add(blackBandR);
            }
        }
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
    public double getStepSize() {return stepSize;}

    public void setPlayerFlag(boolean val) {playerFlag = val;}
    public void setStepSize(double val) {stepSize = val;}
    
}
