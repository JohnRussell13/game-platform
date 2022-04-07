package com.goldrush;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class World {
    protected static Pane layout = new Pane();
    protected static Menu menu = new Menu(layout);
    protected static PopUp popUp = new PopUp();

    protected static Rectangle blackBandL = new Rectangle();
    protected static Rectangle blackBandR = new Rectangle();
    protected static boolean fullscreenFlag = false;
}
