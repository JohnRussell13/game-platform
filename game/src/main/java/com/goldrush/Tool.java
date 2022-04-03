package com.goldrush;

import java.util.Random;

abstract public class Tool {
    protected int durability;
    protected Random rnd;

    public Tool() {
        durability = 100;
        rnd = new Random();
        //System.out.println("Tool");
    }

    public int getDurability() {
        return durability;
    }

    abstract public int useTool();
}
