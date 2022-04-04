package com.goldrush;

import java.io.File;
import java.util.Scanner;

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
    private double offset = 20;

    public Player(String title){
        name = title;
        double initWidth = 0;
        double initHeight = 0;
        double initX = 0;
        double initY = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/" + name + ".txt");
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

        image[0] = new Image(getClass().getResource(name + "Up.png").toString(), true);
        image[1] = new Image(getClass().getResource(name + "Down.png").toString(), true);
        image[2] = new Image(getClass().getResource(name + "Left.png").toString(), true);
        image[3] = new Image(getClass().getResource(name + "Right.png").toString(), true);

        imageView = new ImageView(image[2]); // INIT POSITION

        imageView.setFitHeight(initHeight);
        imageView.setFitWidth(initWidth);
        imageView.relocate(initX, initY); 
    }

    public int move(KeyCode direction, Pane layout, ElementStatics elementStatics, Rectangle blackBandL, Rectangle blackBandR, 
                    boolean screenFlag, double screenWidth, double screenHeight){
        double posX = imageView.getLayoutX();
        double posY = imageView.getLayoutY();
        double newX = posX;
        double newY = posY;

        if(playerFlag){
            switch(direction){
                case W:
                    imageView.setImage(image[0]);
                    newY = newY - stepSize;
                    if(newY - offset < 0) return 1;
                    break;
                case S:
                    imageView.setImage(image[1]);
                    newY = newY + stepSize;
                    if(screenHeight < newY + imageView.getFitHeight() + offset) return 2;
                    break;
                case A:
                    imageView.setImage(image[2]);
                    newX = newX - stepSize;
                    if(newX - offset < blackBandL.getWidth()) return 3;
                    break;
                case D:
                    imageView.setImage(image[3]);
                    newX = newX + stepSize;
                    if(screenWidth < blackBandL.getWidth() + newX + imageView.getFitWidth() + offset) return 4;
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
                    if(elementStatics.getElement(i).getBlockFlag()) return -1;
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
        return 0;
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}
    public double getStepSize() {return stepSize;}

    public void setPlayerFlag(boolean val) {playerFlag = val;}
    public void setStepSize(double val) {stepSize = val;}
    
}
