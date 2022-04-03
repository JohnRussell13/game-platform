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
        /*  
            FORMAT IS: 
                        - TITLE
                        - WIDTH
                        - HEIGHT
                        - POSITION X
                        - POSTIION Y
                        - AFTER THIS PART IMAGE GOES BEHIND THE PLAYER
                        - START X BORDER
                        - END X BORDER
                        - START Y BORDER
                        - END Y BORDER
                        - TYPE (BLOCKING OR PASSING)
        */
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/static.txt");
        try {
            Scanner scanIn = new Scanner(file);

            for(int i = 0; i < count; i++){
                String[] line = scanIn.nextLine().split(",", 11);
                String name = line[0];
                double initHeight = Double.parseDouble(line[1]);
                double initWidth = Double.parseDouble(line[2]);
                double initX = Double.parseDouble(line[3]);
                double initY = Double.parseDouble(line[4]);
                double foreground = Double.parseDouble(line[5]);
                double blockStartX = Double.parseDouble(line[6]);
                double blockEndX = Double.parseDouble(line[7]);
                double blockStartY = Double.parseDouble(line[8]);
                double blockEndY = Double.parseDouble(line[9]);
                boolean block = Double.parseDouble(line[10]) == 1;
                elementStatic[i] = new ElementStatic(name, initHeight, initWidth, initX, initY, foreground,
                                                    blockStartX, blockEndX, blockStartY, blockEndY, block);
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ElementStatic getElement(int pos) {return elementStatic[pos];}
    public int size() {return count;}
    
}
