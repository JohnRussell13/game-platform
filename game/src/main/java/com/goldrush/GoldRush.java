package com.goldrush;

import java.util.*;
import java.io.*;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;

    private int week;

    public GoldRush() {
        savedGame = new File("GoldRushSaved.txt");
        // System.out.println("GoldRush");
    }

    public int loadGame() {
        week = 0;
        int endurance = 0;
        int money = 0;
        ArrayList<Tool> tools = new ArrayList<>();

        try {
            Scanner scanIn = new Scanner(savedGame);
            String data;
            int durability;
            String regex = "[^\\d]+";
            
            data = scanIn.nextLine();
            week = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            endurance = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            money = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            durability = Integer.parseInt(data.split(regex)[1]);

            Pan pan = new Pan();
            tools.add(pan);
            Sluice sluice = new Sluice(durability);
            tools.add(sluice);

            while(scanIn.hasNextLine()) {
                data = scanIn.nextLine();
                durability = Integer.parseInt(data.split(regex)[1]);

                Cradle cradle = new Cradle(durability);
                tools.add(cradle);
            }

            fortyNiner = new FortyNiner(endurance, money, tools);
            scanIn.close();

            System.out.println("Game loaded.");
        } catch (FileNotFoundException e){
            System.out.println("Starting new game.");
        }
        return week;
    }

    public int survive() {
        if(fortyNiner == null) {
            fortyNiner = new FortyNiner();
            week = 1;
        }
        return week;
    }

    public FortyNiner getFortyNiner(){
        return fortyNiner;
    }

    public void saveGame(int week) {
        try {
            FileOutputStream fos = new FileOutputStream(savedGame);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            bw.write("Week no. " + week);
            bw.newLine();

            bw.write("49er endurance: " + fortyNiner.getEndurance() + "%");
            bw.newLine();

            bw.write("49er money: $" + fortyNiner.getMoney());
            bw.newLine();

            ArrayList<Tool> tools = fortyNiner.getTools();
            Tool sluice = (Tool) tools.get(1);

            bw.write("Sluice durability: " + sluice.getDurability() + "%");
            bw.newLine();

            for(int i = 2; i < tools.size(); i++){
                Tool cradle = (Tool) tools.get(i);

                bw.write("Cradle durability: " + cradle.getDurability() + "%");
                bw.newLine();
            }

            bw.close();

        } catch (FileNotFoundException e){
            // File was not found
            e.printStackTrace();
        } catch (IOException e) {
            // Problem when writing to the file
            e.printStackTrace();
        }
    }
    
}
