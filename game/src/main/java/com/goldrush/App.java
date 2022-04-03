package com.goldrush;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private double sceneWidth = 600;
    private double sceneHeight = 457;

    private double fullscreenHegiht = 1080;
    private double fullscreenWidth = 1920;
    private double ratio = fullscreenHegiht/sceneHeight;
    private double blackStripWidth = (fullscreenWidth - ratio * sceneWidth)/2;

    private boolean fullscreenFlag = false;

    private ElementStatics elementStatics = new ElementStatics();
    private Pane layout = new Pane();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gold Rush - Forty-Niner");

        /*      CREATE LAYOUT       */
        for(int i = 0; i < elementStatics.size(); i++){
            layout.getChildren().add(elementStatics.getElement(i).getImageView());
        }

        /*      CREATE THE SCENE        */
        Scene scene = new Scene(layout, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*      KEYBOARD        */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
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
        
        for(int i = 0; i < elementStatics.size(); i++){
            ImageView current = elementStatics.getElement(i).getImageView();
            current.setLayoutX(mapPosition(current.getLayoutX(), 'X'));
            current.setLayoutY(mapPosition(current.getLayoutY(), 'Y'));
            current.setFitWidth(mapSize(current.getFitWidth()));
            current.setFitHeight(mapSize(current.getFitHeight()));
        }
        
        fullscreenFlag = !fullscreenFlag;
        primaryStage.setFullScreen(fullscreenFlag);
    }

    private double mapPosition(double val, char axis){
        if(axis == 'X'){
            if(fullscreenFlag) return (val - blackStripWidth) / ratio;
            else return val * ratio + blackStripWidth;
        }
        else{
            if(fullscreenFlag) return val / ratio;
            else return val * ratio;
        }
    }

    private double mapSize(double val){
        if(fullscreenFlag) return val / ratio;
        else return val * ratio;
    }
}