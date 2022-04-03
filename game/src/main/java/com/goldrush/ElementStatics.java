package com.goldrush;

import java.io.*;
import java.util.*;

public class ElementStatics {
    private ElementStatic[] elementStatic;
    private int count = 0;

    public ElementStatics(){
        /*      GET NUMBER OF ELEMENTS      */
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/static.txt"));
            while (reader.readLine() != null) count++;
            reader.close();
        } catch(Exception e) {
            e.getStackTrace();
        }

        elementStatic = new ElementStatic[count];

        /*      GET THE ELEMENTS        */
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/static.txt");
        try {
            Scanner scanIn = new Scanner(file);

            for(int i = 0; i < count; i++){
                String[] line = scanIn.nextLine().split(",", 6);
                String name = line[0];
                double initHeight = Double.parseDouble(line[1]);
                double initWidth = Double.parseDouble(line[2]);
                double initX = Double.parseDouble(line[3]);
                double initY = Double.parseDouble(line[4]);
                double block = Double.parseDouble(line[5]);
                elementStatic[i] = new ElementStatic(name, initHeight, initWidth, initX, initY, block);
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ElementStatic getElement(int pos) {return elementStatic[pos];}
    public int size() {return count;}
    
}
