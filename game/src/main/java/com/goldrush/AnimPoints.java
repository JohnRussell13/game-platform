package com.goldrush;

import java.io.*;
import java.util.*;

public class AnimPoints {
    private double[] pos; // FIRST ENTRY IS X, THEN GOES Y, THEN X AGAIN ETC.

    private int count;

    public AnimPoints(String loc){
        // GET THE NUMBER OF BRIDGES AND INIT ARRAY
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/" + loc + ".txt"));
            while (reader.readLine() != null) count++;
            reader.close();
        } catch(Exception e) {
            e.getStackTrace();
        }

        pos = new double[count];

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/" + loc + ".txt");
        try {
            Scanner scanIn = new Scanner(file);
            for(int i = 0; i < count; i++) {
                pos[i] = Double.parseDouble(scanIn.nextLine());
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public double getPos(int ind) {return pos[ind];}
    public void setPos(double val, int ind) {pos[ind] = val;}

    public int getCount() {return count;}
}
