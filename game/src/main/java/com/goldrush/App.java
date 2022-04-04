package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.print.PrinterAttributes;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private double globalX = 0;
    private double globalY = 0;
    private double sceneWidth;
    private double sceneHeight;
    private double playerWeight;
    private double playerHeight;
    private double fullscreenHegiht;
    private double fullscreenWidth;
    private double ratio;
    private double backgroundWidth;
    private double backgroundHeight;
    private double blackStripWidth;
    private double playerInitX;
    private double playerInitY;
    private double stepSize = 5;

    private boolean fullscreenFlag = false;

    private ImageView background = new ImageView(new Image(getClass().getResource("background.png").toString(), true));
    private ElementStatics elementStatics = new ElementStatics();
    private Player player;
    private Menu menu = new Menu();

    private Rectangle blackBandL = new Rectangle();
    private Rectangle blackBandR = new Rectangle();

    private Pane layout = new Pane();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*      BACKGROUND CONFIG       */
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/config.txt");
        try {
            Scanner scanIn = new Scanner(file);
            String[] line = scanIn.nextLine().split(",", 2);
            sceneWidth = Double.parseDouble(line[0]);
            sceneHeight = Double.parseDouble(line[1]);

            line = scanIn.nextLine().split(",", 2);
            backgroundWidth = Double.parseDouble(line[0]);
            backgroundHeight = Double.parseDouble(line[1]);

            line = scanIn.nextLine().split(",", 2);
            playerWeight = Double.parseDouble(line[0]);
            playerHeight = Double.parseDouble(line[1]);
            
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        fullscreenHegiht = Screen.getPrimary().getVisualBounds().getMaxY();
        fullscreenWidth = Screen.getPrimary().getVisualBounds().getMaxX();

        ratio = fullscreenHegiht/sceneHeight;
        blackStripWidth = (fullscreenWidth - ratio * sceneWidth)/2;
        playerInitX = sceneWidth/2 - playerWeight/2;
        playerInitY = sceneHeight/2 - playerHeight/2;

        /*      GAME TITLE      */
        primaryStage.setTitle("Gold Rush - Forty-Niner");

        /*      BACKGROUND      */
        background.setFitWidth(backgroundWidth);
        background.setFitHeight(backgroundHeight);
        background.relocate(-backgroundWidth/2, -backgroundHeight/2);

        /*      BLACK SIDEBARS      */
        blackBandL.setWidth(0); // for moveMap
        blackBandL.setHeight(fullscreenHegiht);
        blackBandL.relocate(0, 0);
        blackBandR.setWidth(blackStripWidth);
        blackBandR.setHeight(fullscreenHegiht);
        blackBandR.relocate(blackStripWidth + ratio * sceneWidth, 0);

        /*      CREATE PLAYER       */
        player = new Player("player", playerHeight, playerWeight, playerInitX, playerInitY);

        /*      CREATE LAYOUT       */
        layout.getChildren().add(background);
        for(int i = 0; i < elementStatics.size(); i++){
            layout.getChildren().add(elementStatics.getElement(i).getImageView());
        }
        layout.getChildren().add(player.getImageView());
        layout.getChildren().add(menu.getImageView());

        /*      CREATE THE SCENE        */
        Scene scene = new Scene(layout, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*      KEYBOARD        */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                case W:
                case S:
                case A:
                case D:
                    if(fullscreenFlag) moveMap(player.move(event.getCode(), layout, elementStatics, blackBandL, blackBandR, 
                                                            fullscreenFlag, fullscreenWidth, fullscreenHegiht));
                    else moveMap(player.move(event.getCode(), layout, elementStatics, blackBandL, blackBandR, 
                                                            fullscreenFlag, sceneWidth, sceneHeight));
                    layout.getChildren().remove(menu.getImageView());
                    layout.getChildren().add(menu.getImageView());
                    break;
                case F:
                case ESCAPE:
                    changeResolution(primaryStage);
                    break;
                default:
                    break;
                }
            }
        });
    }

    private void moveMap(int type){
        switch(type){
        case 0:
            return;
        case 1:
            background.relocate(background.getLayoutX(), background.getLayoutY() + stepSize);
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() + stepSize);
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockY[0] += stepSize;
                blockY[1] += stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
                globalY -= stepSize;
            }
            break;
        case 2:
            background.relocate(background.getLayoutX(), background.getLayoutY() - stepSize);
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() - stepSize);
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockY[0] -= stepSize;
                blockY[1] -= stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
                globalY += stepSize;
            }
            break;
        case 3:
            background.relocate(background.getLayoutX() + stepSize, background.getLayoutY());
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX() + stepSize, current.getLayoutY());
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockX[0] += stepSize;
                blockX[1] += stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
                globalX -= stepSize;
            }
            break;
        case 4:
            background.relocate(background.getLayoutX() - stepSize, background.getLayoutY());
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX() - stepSize, current.getLayoutY());
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockX[0] -= stepSize;
                blockX[1] -= stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
                globalX += stepSize;
            }
            break;
        }
    }

    private void changeResolution(Stage primaryStage){
        background.setLayoutX(mapPositionX(background.getLayoutX()));
        background.setLayoutY(mapSize(background.getLayoutY()));
        background.setFitWidth(mapSize(background.getFitWidth()));
        background.setFitHeight(mapSize(background.getFitHeight()));

        for(int i = 0; i < elementStatics.size(); i++){
            ImageView current = elementStatics.getElement(i).getImageView();
            current.setLayoutX(mapPositionX(current.getLayoutX()));
            current.setLayoutY(mapSize(current.getLayoutY()));
            current.setFitWidth(mapSize(current.getFitWidth()));
            current.setFitHeight(mapSize(current.getFitHeight()));

            elementStatics.getElement(i).setBlockX(mapPositionX(elementStatics.getElement(i).getBlockX()));
            elementStatics.getElement(i).setBlockY(mapSize(elementStatics.getElement(i).getBlockY()));
        }

        ImageView current = player.getImageView();
        current.setLayoutX(mapPositionX(current.getLayoutX()));
        current.setLayoutY(mapSize(current.getLayoutY()));
        current.setFitWidth(mapSize(current.getFitWidth()));
        current.setFitHeight(mapSize(current.getFitHeight()));

        current = menu.getImageView();
        current.setLayoutX(mapPositionX(current.getLayoutX()));
        current.setLayoutY(mapSize(current.getLayoutY()));
        current.setFitWidth(mapSize(current.getFitWidth()));
        current.setFitHeight(mapSize(current.getFitHeight()));

        if(fullscreenFlag){
            player.setStepSize(player.getStepSize() / ratio);
            layout.getChildren().remove(blackBandL);
            layout.getChildren().remove(blackBandR);
            blackBandL.setWidth(0); // for mapMove
        }
        else{
            player.setStepSize(player.getStepSize() * ratio);
            layout.getChildren().add(blackBandL);
            layout.getChildren().add(blackBandR);
            blackBandL.setWidth(blackStripWidth);
            // blackBandL.setHeight(fullscreenHegiht);
            // blackBandR.setWidth(blackStripWidth);
            // blackBandR.setHeight(fullscreenHegiht);
        }
        
        fullscreenFlag = !fullscreenFlag;
        primaryStage.setFullScreen(fullscreenFlag);
    }

    private double mapPositionX(double val){
        if(fullscreenFlag) return (val - blackStripWidth) / ratio;
        else return val * ratio + blackStripWidth;
    }

    private double mapSize(double val){
        if(fullscreenFlag) return val / ratio;
        else return val * ratio;
    }

    private double[] mapPositionX(double[] val){
        double[] result = new double[2];
        if(fullscreenFlag){
            result[0] = (val[0] - blackStripWidth) / ratio;
            result[1] = (val[1] - blackStripWidth) / ratio;
        }
        else{
            result[0] = val[0] * ratio + blackStripWidth;
            result[1] = val[1] * ratio + blackStripWidth;
        }
        
        return result;
    }

    private double[] mapSize(double[] val){
        double[] result = new double[2];
        if(fullscreenFlag){
            result[0] = val[0] / ratio;
            result[1] = val[1] / ratio;
        }
        else{
            result[0] = val[0] * ratio;
            result[1] = val[1] * ratio;
        }

        return result;
    }
}