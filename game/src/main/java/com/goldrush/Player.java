package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Player extends Element {
    private Image image[] = new Image[4]; // 4 directions
    private boolean playerFlag = true;
    private double stepSize = 5;
    private double offset = 20;

    public Player(String name){
        super(name);
        image[0] = new Image(getClass().getResource(name + "Up.png").toString(), true);
        image[1] = new Image(getClass().getResource(name + "Down.png").toString(), true);
        image[2] = new Image(getClass().getResource(name + "Left.png").toString(), true);
        image[3] = new Image(getClass().getResource(name + "Right.png").toString(), true);
    }

    public int move(KeyCode direction, Pane layout, ElementStatics elementStatics, Rectangle blackBandL, Rectangle blackBandR, 
                    boolean screenFlag, double screenWidth, double screenHeight, NPCs npcs){
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

            for(int i = 0; i < npcs.getSize(); i++){
                ImageView npc = npcs.getNPCs()[i].getImageView();
                double npcFeetX = npc.getLayoutX() + npc.getTranslateX() + npc.getFitWidth()/2;
                double npcFeetY = npc.getLayoutY() + npc.getTranslateY() + npc.getFitHeight();
                if(npcFeetX - 0.8*(npc.getFitWidth()/2 + imageView.getFitWidth()/2) < feetX
                && feetX < npcFeetX + 0.8*(npc.getFitWidth()/2 + imageView.getFitWidth()/2)
                && npcFeetY - 0.1*npc.getFitHeight() < feetY
                && feetY < npcFeetY + 0.1*npc.getFitHeight()){
                    return -1;
                }
            }

            imageView.relocate(newX, newY);
            
            layout.getChildren().remove(imageView);
            layout.getChildren().add(imageView);

            for(int i = 0; i < npcs.getSize(); i++){
                ImageView npc = npcs.getNPCs()[i].getImageView();
                double npcFeetX = npc.getLayoutX()+ npc.getTranslateX() + npc.getFitWidth()/2;
                if(npcFeetX - npc.getFitWidth()/2 - imageView.getFitWidth()/2 < feetX
                && feetX < npcFeetX + npc.getFitWidth()/2 + imageView.getFitWidth()/2
                && npc.getLayoutY() + npc.getTranslateY() < feetY
                && feetY < npc.getLayoutY() + npc.getTranslateY() + npc.getFitHeight()){
                    layout.getChildren().remove(npc);
                    layout.getChildren().add(npc);
                }
            }

            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                if(current.getLayoutY() + elementStatics.getElement(i).getForeground()*current.getFitHeight() > feetY){
                    layout.getChildren().remove(current);
                    layout.getChildren().add(current);
                    for(int j = 0; j < npcs.getSize(); j++){
                        ImageView npc = npcs.getNPCs()[j].getImageView();
                        double npcFeetY = npc.getLayoutY() + npc.getFitHeight();
                        if(current.getLayoutY() + elementStatics.getElement(i).getForeground()*current.getFitHeight() < npcFeetY){
                            layout.getChildren().remove(npc);
                            layout.getChildren().add(npc);
                        }
                    }
                }
            }
            for(int i = 0; i < npcs.getSize(); i++){
                ImageView current = npcs.getNPCs()[i].getImageView();
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

    public double getStepSize() {return stepSize;}

    public void setPlayerFlag(boolean val) {playerFlag = val;}
    public void setStepSize(double val) {stepSize = val;}
    
}
