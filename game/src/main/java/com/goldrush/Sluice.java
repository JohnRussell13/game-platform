package com.goldrush;

import java.util.Random;

public class Sluice extends Tool {
    public Sluice() {
        durability = 100;
        rnd = new Random();
        // System.out.println("Sluice");
    }

    public Sluice(int drb) {
        durability = drb;
        rnd = new Random();
        // System.out.println("Sluice");
    }

    public int useTool() {
        if(durability == 0) return 0;

        durability -= rnd.nextInt(50 - 20 + 1) + 20;
        if (durability < 0) durability = 0;

        return rnd.nextInt(500 + 1);
    }

    public void repair() {
        durability = 100;
    }
}
