package com.goldrush;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Menu {
    private Element menu = new Element("menu");
    
    private Text text = new Text();
    private double blur = (double)1/15;
    private double font = 6;

    public Menu(){
        /*      SET TEXT        */
        text.setTextAlignment(TextAlignment.LEFT);
        text.setWrappingWidth(menu.getImageView().getFitWidth()*0.8);
        text.setFont(new Font(font));
        text.setX( menu.getImageView().getLayoutX() + menu.getImageView().getFitWidth()*0.5 - text.getWrappingWidth()*0.5 );
        text.setY( menu.getImageView().getLayoutY() + menu.getImageView().getFitHeight()*0.2 );
        text.setLineSpacing(menu.getImageView().getFitHeight()*0.05);
        text.setEffect(new GaussianBlur(blur*font));
    }

    public double getFont() {return font;}
    public void setFont(double val) {font = val;}
    public double getBlur() {return blur;}
    public void setBlur(double val) {blur = val;}
    public Element getElement() {return menu;}
    public Text getText() {return text;}

    public void setMessage(String msg) {text.setText(msg);}
    public String getMessage() {return text.getText();}

    public void show(Pane layout, String msg) {
        layout.getChildren().remove(menu.getImageView());
        layout.getChildren().remove(text);

        text.setText(msg);

        layout.getChildren().add(menu.getImageView());
        layout.getChildren().add(text);
    }


    public void refresh(Pane layout) {
        layout.getChildren().remove(menu.getImageView());
        layout.getChildren().remove(text);

        layout.getChildren().add(menu.getImageView());
        layout.getChildren().add(text);
    }
    
}
