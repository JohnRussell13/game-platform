package com.goldrush;

import java.util.Random;

public class Cradle extends Tool {
    public Cradle() {
        durability = 100;
        rnd = new Random();
        // System.out.println("Cradle");
    }

    public Cradle(int drb) {
        durability = drb;
        rnd = new Random();
        // System.out.println("Cradle");
    }

    public int useTool() {
        if (rnd.nextInt(100) < 20) durability = 0;
        
        return rnd.nextInt(30 + 1);
    }
}
