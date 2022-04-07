package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Player extends Element {
    private Image image[] = new Image[4]; // 4 directions
    private boolean playerFlag = true;
    private double stepSize = 5;

    public Player(String name, Menu mn, PopUp pu){
        super(name);
        image[0] = new Image(getClass().getResource(name + "Up.png").toString(), true);
        image[1] = new Image(getClass().getResource(name + "Down.png").toString(), true);
        image[2] = new Image(getClass().getResource(name + "Left.png").toString(), true);
        image[3] = new Image(getClass().getResource(name + "Right.png").toString(), true);
    }

    public boolean getPlayerFlag() {return playerFlag;}
    public void setPlayerFlag(boolean val) {playerFlag = val;}
    public void setImage(int val) {this.getImageView().setImage(image[val]);}

    public double getStepSize() {return stepSize;}

    public void setStepSize(double val) {stepSize = val;}
    
}
