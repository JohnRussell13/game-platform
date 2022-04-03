package com.goldrush;

import java.io.*;
import java.util.*;

public class ImageInfo {
    private double initX;
    private double initY;
    private double initHeight;
    private double initWidth;
    private String name;

    public ImageInfo(){
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/config.txt");
        try {
            Scanner scanIn = new Scanner(file);
            String[] conf = scanIn.nextLine().split(",", 5);
            name = conf[0];
            initHeight = Double.parseDouble(conf[1]);
            initWidth = Double.parseDouble(conf[2]);
            initX = Double.parseDouble(conf[3]);
            initX = Double.parseDouble(conf[4]);
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public double getInitX() {return initX;}
    public double getInitY() {return initY;}
    public double getInitHeight() {return initHeight;}
    public double getInitWidth() {return initWidth;}
    public String getName() {return name;}
}
