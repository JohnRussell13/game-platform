package com.goldrush;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class NPC {
    private ImageView imageView;
    private String name;

    private PathTransition pathTransition = new PathTransition();

    private double[] animationPoints;
    private int count = -1; // skip first line
    private int[] stage;
    private int stages;
    private int countAnimation;
    private int stagesStage;

    private int qucikStorage;

    double dX;
    double dY;

    private double speed = 0.1;

    public NPC(String title){
        name = title;

        /*  
            FORMAT IS: 
                        - WIDTH, HEIGHT, POSITION X, POSITION Y, STAGES
                        - STAGE[0]
                        - ...
                        - STAGE[STAGES-1]
                        - ANIMATIONPOINTS[0]
                        - ...
        */

        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/" + title + ".txt"));
            while (reader.readLine() != null) count++;
            reader.close();

        } catch(Exception e) {
            e.getStackTrace();
        }

        double initWidth = 0;
        double initHeight = 0;
        double initX = 0;
        double initY = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/" + name + ".txt");
        try {
            Scanner scanIn = new Scanner(file);
            String[] line = scanIn.nextLine().split(",", 5);
            initHeight = Double.parseDouble(line[0]);
            initWidth = Double.parseDouble(line[1]);
            initX = Double.parseDouble(line[2]);
            initY = Double.parseDouble(line[3]);
            stages = Integer.parseInt(line[4]);

            count -= stages;

            stage = new int[stages];
            for(int i = 0; i < stages; i++) {
                stage[i] = Integer.parseInt(scanIn.nextLine());
            }

            animationPoints = new double[count];

            for(int i = 0; i < count; i++) {
                animationPoints[i] = Double.parseDouble(scanIn.nextLine());
            }
            
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        imageView = new ImageView(new Image(getClass().getResource(name + ".png").toString(), true));
        imageView.setFitWidth(initWidth);
        imageView.setFitHeight(initHeight);
        imageView.relocate(initX, initY);
    }

    public ImageView getImageView() {return imageView;}
    public String getName() {return name;}

    public void come(int part){
        countAnimation = 0;
        stagesStage = part;

        qucikStorage = 0;
        for(int i = 0; i < part; i++){
            qucikStorage += stage[i];
        }

        recursiveWalk(true);
    }

    public void go(int part){
        countAnimation = stage[part];
        stagesStage = part;

        qucikStorage = 0;
        for(int i = 0; i < part; i++){
            qucikStorage += stage[i];
        }

        recursiveWalk(false);
    }

    private void recursiveWalk(boolean foreward){
        if(!foreward){
            countAnimation--;
            if(countAnimation < 0){
                return;
            }
        }
        else{
            if(countAnimation >= stage[stagesStage]) {
                return;
            }
        }

        dX = 0;
        dY = 0;

        if(countAnimation % 2 == 0) {
            dX = animationPoints[qucikStorage + countAnimation];
            if(!foreward) dX = -dX;
        }
        else{
            dY = animationPoints[qucikStorage + countAnimation];
            if(!foreward) dY = -dY;
        }

        double destX = dX + (imageView.getLayoutX() + imageView.getTranslateX());
        double destY = dY + (imageView.getLayoutY() + imageView.getTranslateY());

        double posX = destX-imageView.getLayoutX()+imageView.getFitWidth()/2;
        double posY = destY-imageView.getLayoutY()+imageView.getFitHeight()/2;
        MoveTo moveTo = new MoveTo(imageView.getTranslateX()+imageView.getFitWidth()/2, imageView.getTranslateY()+imageView.getFitHeight()/2);
        LineTo lineTo = new LineTo(posX, posY);

        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        double time = Math.sqrt(dX*dX + dY*dY) / speed;
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);

        pathTransition.setOnFinished(event -> {
            if(foreward) countAnimation++;
            recursiveWalk(foreward);
        });

        pathTransition.play();
    }
}
