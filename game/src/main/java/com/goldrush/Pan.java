package com.goldrush;

import java.util.Random;

public class Pan extends Tool {
    public Pan() {
        durability = 100;
        rnd = new Random();
        // System.out.println("Pan");
    }

    public int useTool() {
        return rnd.nextInt(60 + 1);
    }
}