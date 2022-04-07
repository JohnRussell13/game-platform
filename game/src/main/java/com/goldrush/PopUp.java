package com.goldrush;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PopUp {
    private boolean nextFlag = false; // is it OK to press K and go next

    private Text text = new Text();
    private Element popUp = new Element("popUp");
    private double blur = 0; // (double)1/15;
    private double font = 30;
    
    public PopUp(){
        /*      SET TEXT        */
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(popUp.getImageView().getFitWidth()*0.8);
        text.setX( popUp.getImageView().getLayoutX() + popUp.getImageView().getFitWidth()*0.5 - text.getWrappingWidth()*0.5 );
        text.setY( popUp.getImageView().getLayoutY() + popUp.getImageView().getFitHeight()*0.2 );
        text.setFont(new Font("Hothead", font));
        text.setLineSpacing(popUp.getImageView().getFitHeight()*0.05);
        text.setEffect(new GaussianBlur(blur*font));
    }

    public void setFlag(boolean val) {nextFlag = val;}
    public boolean getFlag() {return nextFlag;}

    public double getFont() {return font;}
    public void setFont(double val) {font = val;}
    public double getBlur() {return blur;}
    public void setBlur(double val) {blur = val;}
    public Element getElement() {return popUp;}
    public Text getText() {return text;}

    public void setMessage(String msg) {text.setText(msg);}
    public String getMessage() {return text.getText();}
    public void appendMessage(String msg) {
        text.setText(text.getText() + msg);
    }
    public void show(Pane layout) {
        layout.getChildren().add(popUp.getImageView());
        layout.getChildren().add(text);
    }

    public void hide(Pane layout) {
        layout.getChildren().remove(popUp.getImageView());
        layout.getChildren().remove(text);
    }

}
