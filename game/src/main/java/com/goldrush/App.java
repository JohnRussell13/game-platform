package com.goldrush;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    ImageView background = new ImageView(new Image(getClass().getResource("background.png").toString(), true));

    private double sceneWidth = 600;
    private double sceneHeight = 457;
    private double playerWeight = 37;
    private double playerHeight = 56;

    private double fullscreenHegiht = 1080;
    private double fullscreenWidth = 1920;
    private double ratio = fullscreenHegiht/sceneHeight;
    private double blackStripWidth = (fullscreenWidth - ratio * sceneWidth)/2;
    private double playerInitX = sceneWidth/2 - playerWeight/2;
    private double playerInitY = sceneHeight/2 - playerHeight/2;

    private boolean fullscreenFlag = false;

    private ElementStatics elementStatics = new ElementStatics();
    private Player player;

    private Rectangle blackBandL = new Rectangle();
    private Rectangle blackBandR = new Rectangle();

    private Pane layout = new Pane();



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*      GAME TITLE      */
        primaryStage.setTitle("Gold Rush - Forty-Niner");

        /*      BACKGROUND      */
        background.setFitWidth(sceneWidth);
        background.setFitHeight(sceneHeight);

        /*      BLACK SIDEBARS      */
        blackBandL.setWidth(blackStripWidth);
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
                    player.move(event.getCode(), layout, elementStatics, blackBandL, blackBandR, fullscreenFlag);
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

        if(fullscreenFlag){
            player.setStepSize(player.getStepSize() / ratio);
            layout.getChildren().remove(blackBandL);
            layout.getChildren().remove(blackBandR);
        }
        else{
            player.setStepSize(player.getStepSize() * ratio);
            layout.getChildren().add(blackBandL);
            layout.getChildren().add(blackBandR);
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