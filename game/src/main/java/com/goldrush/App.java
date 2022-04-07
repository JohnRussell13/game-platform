package com.goldrush;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.print.PrinterAttributes;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    PopUp popUp = new PopUp();
    Menu menu = new Menu();

    private Element background = new Element("background");
    private ElementStatics elementStatics = new ElementStatics();
    private Player player = new Player("player", menu, popUp);

    private Rectangle blackBandL = new Rectangle();
    private Rectangle blackBandR = new Rectangle();

    private Pane layout = new Pane();

    private NPCs npcs = new NPCs(new String[]{"sellerFood", "sellerCradles"});
    private Game game = new Game(menu, popUp);



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
        menu.show(layout, "ABS");

        for(int i = 0; i < npcs.getSize(); i++){
            layout.getChildren().add(npcs.getNPCs()[i].getImageView());
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
                case W:
                case S:
                case A:
                case D:
                    if(fullscreenFlag) moveMap(player.move(event.getCode(), layout, elementStatics, blackBandL, blackBandR, 
                                                            fullscreenFlag, fullscreenWidth, fullscreenHegiht, npcs, game));
                    else moveMap(player.move(event.getCode(), layout, elementStatics, blackBandL, blackBandR, 
                                                            fullscreenFlag, sceneWidth, sceneHeight, npcs, game));
                    break;
                case F:
                case ESCAPE:
                    changeResolution(primaryStage);
                    break;
                case M:
                    game.changeCradle(1);
                    break;
                case N:
                    game.changeCradle(-1);
                    break;
                case K:
                    game.pressedK(layout, npcs);
                    break;
                case Q:
                    game.exitGame();
                    break;
                default:
                    break;
                }
            }
        });

        game.gameplay(layout, npcs);
    }

    private void moveMap(int type){
        ImageView bground = background.getImageView();

        double feetX = player.getImageView().getLayoutX() + player.getImageView().getFitWidth()/2;
        double feetY = player.getImageView().getLayoutY() + player.getImageView().getFitHeight();

        for(int i = elementStatics.size()-1; i >= 0; i--){
            double[] blockX = elementStatics.getElement(i).getBlockX();
            double[] blockY = elementStatics.getElement(i).getBlockY();
            if(blockX[0] - stepSize < feetX && feetX < blockX[1] + stepSize
            && blockY[0] - stepSize < feetY && feetY < blockY[1] + stepSize){
                game.enter(elementStatics.getElement(i).getName(), layout);
                if(elementStatics.getElement(i).getBlockFlag()) return;
                else break;
            }
        }

        for(int i = 0; i < npcs.getSize(); i++){
            ImageView npc = npcs.getNPCs()[i].getImageView();
            double npcFeetX = npc.getLayoutX() + npc.getTranslateX() + npc.getFitWidth()/2;
            double npcFeetY = npc.getLayoutY() + npc.getTranslateY() + npc.getFitHeight();
            if(npcFeetX - 0.8*(npc.getFitWidth()/2 + player.getImageView().getFitWidth()/2) < feetX
            && feetX < npcFeetX + 0.8*(npc.getFitWidth()/2 + player.getImageView().getFitWidth()/2)
            && npcFeetY - 0.1*npc.getFitHeight() < feetY
            && feetY < npcFeetY + 0.1*npc.getFitHeight()){
                System.out.println(i);
                game.gameplay(layout, npcs);
                return;
            }
        }

        switch(type){
        case 0:
            return;
        case 1:
            bground.relocate(bground.getLayoutX(), bground.getLayoutY() + stepSize);
            for(int i = 0; i < npcs.getSize(); i++){
                ImageView current = npcs.getNPCs()[i].getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() + stepSize);
            }
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() + stepSize);
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockY[0] += stepSize;
                blockY[1] += stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
            }
            globalY -= stepSize;
            break;
        case 2:
            bground.relocate(bground.getLayoutX(), bground.getLayoutY() - stepSize);
            for(int i = 0; i < npcs.getSize(); i++){
                ImageView current = npcs.getNPCs()[i].getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() - stepSize);
            }
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX(), current.getLayoutY() - stepSize);
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockY[0] -= stepSize;
                blockY[1] -= stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
            }
            globalY += stepSize;
            break;
        case 3:
            bground.relocate(bground.getLayoutX() + stepSize, bground.getLayoutY());
            for(int i = 0; i < npcs.getSize(); i++){
                ImageView current = npcs.getNPCs()[i].getImageView();
                current.relocate(current.getLayoutX() + stepSize, current.getLayoutY());
            }
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX() + stepSize, current.getLayoutY());
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockX[0] += stepSize;
                blockX[1] += stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
            }
            globalX -= stepSize;
            break;
        case 4:
            bground.relocate(bground.getLayoutX() - stepSize, bground.getLayoutY());
            for(int i = 0; i < npcs.getSize(); i++){
                ImageView current = npcs.getNPCs()[i].getImageView();
                current.relocate(current.getLayoutX() - stepSize, current.getLayoutY());
            }
            for(int i = 0; i < elementStatics.size(); i++){
                ImageView current = elementStatics.getElement(i).getImageView();
                current.relocate(current.getLayoutX() - stepSize, current.getLayoutY());
                double[] blockX = elementStatics.getElement(i).getBlockX();
                double[] blockY = elementStatics.getElement(i).getBlockY();
                blockX[0] -= stepSize;
                blockX[1] -= stepSize;
                elementStatics.getElement(i).setBlockX(blockX);
                elementStatics.getElement(i).setBlockY(blockY);
            }
            globalX += stepSize;
            break;
        }
                    
        layout.getChildren().remove(player.getImageView());
        layout.getChildren().add(player.getImageView());

        for(int i = 0; i < npcs.getSize(); i++){
            ImageView npc = npcs.getNPCs()[i].getImageView();
            double npcFeetX = npc.getLayoutX()+ npc.getTranslateX() + npc.getFitWidth()/2;
            if(npcFeetX - npc.getFitWidth()/2 - player.getImageView().getFitWidth()/2 < feetX
            && feetX < npcFeetX + npc.getFitWidth()/2 + player.getImageView().getFitWidth()/2
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

        if(fullscreenFlag){
            layout.getChildren().remove(blackBandL);
            layout.getChildren().add(blackBandL);
            layout.getChildren().remove(blackBandR);
            layout.getChildren().add(blackBandR);
        }
        menu.refresh(layout);
    }

    private void changeResolution(Stage primaryStage){
        changeResPkg(background.getImageView());
        changeResPkg(player.getImageView());

        changeResPkg(menu.getElement().getImageView());
        changeResPkg(menu.getElement().getImageView());

        menu.setFont(mapSize(menu.getFont()));
        popUp.setFont(mapSize(popUp.getFont()));

        menu.setBlur(mapSize(menu.getBlur()));
        popUp.setBlur(mapSize(menu.getBlur()));

        textResPkg(menu.getText(), menu.getFont(), menu.getBlur()*menu.getFont());
        textResPkg(popUp.getText(), popUp.getFont(), popUp.getBlur()*popUp.getFont());

        for(int i = 0; i < npcs.getSize(); i++){
            changeResPkg(npcs.getNPCs()[i].getImageView());
        }

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

    private void textResPkg(Text text, double font, double blr){
        text.setWrappingWidth( mapSize(text.getWrappingWidth()) );
        text.setX( mapPositionX(text.getX()) );
        text.setY( mapSize(text.getY()) );
        text.setFont(new Font(font));
        text.setLineSpacing( mapSize(text.getLineSpacing()) );
        text.setEffect(new GaussianBlur(blr));
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