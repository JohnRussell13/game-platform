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
    private double blackStripWidth;
    private double playerInitX;
    private double playerInitY;
    private double stepSize = 5;

    private boolean fullscreenFlag = false;

    private Element background = new Element("background");
    private Element menu = new Element("menu");
    private ElementStatics elementStatics = new ElementStatics();
    private Player player = new Player("player");

    private Rectangle blackBandL = new Rectangle();
    private Rectangle blackBandR = new Rectangle();

    private Pane layout = new Pane();

    private NPC seller = new NPC("seller");


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
            
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        fullscreenHegiht = Screen.getPrimary().getVisualBounds().getMaxY();
        fullscreenWidth = Screen.getPrimary().getVisualBounds().getMaxX();

        ratio = fullscreenHegiht/sceneHeight;
        blackStripWidth = (fullscreenWidth - ratio * sceneWidth)/2;

        /*      GAME TITLE      */
        primaryStage.setTitle("Gold Rush - Forty-Niner");

        /*      BLACK SIDEBARS      */
        blackBandL.setWidth(0); // for moveMap
        blackBandL.setHeight(fullscreenHegiht);
        blackBandL.relocate(0, 0);
        blackBandR.setWidth(blackStripWidth);
        blackBandR.setHeight(fullscreenHegiht);
        blackBandR.relocate(blackStripWidth + ratio * sceneWidth, 0);

        /*      CREATE LAYOUT       */
        layout.getChildren().add(background.getImageView());
        for(int i = 0; i < elementStatics.size(); i++){
            layout.getChildren().add(elementStatics.getElement(i).getImageView());
        }
        layout.getChildren().add(player.getImageView());
        layout.getChildren().add(seller.getImageView());
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
                case P:
                    seller.come(0);
                    break;
                case O:
                    seller.go(0);
                    break;
                default:
                    break;
                }
            }
        });
    }

    private void moveMap(int type){
        ImageView bground = background.getImageView();
        switch(type){
        case 0:
            return;
        case 1:
            bground.relocate(bground.getLayoutX(), bground.getLayoutY() + stepSize);
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
            bground.relocate(bground.getLayoutX(), bground.getLayoutY() - stepSize);
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
            bground.relocate(bground.getLayoutX() + stepSize, bground.getLayoutY());
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
            bground.relocate(bground.getLayoutX() - stepSize, bground.getLayoutY());
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
        changeResPkg(background.getImageView());
        changeResPkg(player.getImageView());
        changeResPkg(menu.getImageView());


        for(int i = 0; i < elementStatics.size(); i++){
            changeResPkg(elementStatics.getElement(i).getImageView());

            elementStatics.getElement(i).setBlockX(mapPositionX(elementStatics.getElement(i).getBlockX()));
            elementStatics.getElement(i).setBlockY(mapSize(elementStatics.getElement(i).getBlockY()));
        }

        stepSize = mapSize(stepSize);

        /*      BLACK SIDEBARS      */
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
        }
        
        fullscreenFlag = !fullscreenFlag;
        primaryStage.setFullScreen(fullscreenFlag);
    }

    private void changeResPkg(ImageView imageView){
        imageView.setLayoutX(mapPositionX(imageView.getLayoutX()));
        imageView.setLayoutY(mapSize(imageView.getLayoutY()));
        imageView.setFitWidth(mapSize(imageView.getFitWidth()));
        imageView.setFitHeight(mapSize(imageView.getFitHeight()));
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