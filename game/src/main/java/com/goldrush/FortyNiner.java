package com.goldrush;

import java.util.*;

public class FortyNiner {
    private int endurance;
    private int money;
    private ArrayList<Tool> tools;
    private Random rnd;

    FortyNiner() {
        tools = new ArrayList<>();
        endurance = 100;
        money = 100;

        Pan pan = new Pan();
        tools.add(pan);
        Sluice sluice = new Sluice();
        tools.add(sluice);

        rnd = new Random();
        // System.out.println("FortyNiner");
    }

    FortyNiner(int edr, int mny, ArrayList<Tool> tls) {
        endurance = edr;
        money = mny;

        tools = tls;

        rnd = new Random();
        // System.out.println("FortyNiner");
    }

    public int getEndurance(){
        return endurance;
    }

    public int getMoney(){
        return money;
    }

    public ArrayList<Tool> getTools(){
        return tools;
    }

    public void setEndurance(int end){
        endurance = end;
    }

    public void setMoney(int mny){
        money = mny;
    }

    public void setTools(Tool tl){ // we can only add new cradles
        tools.add(tl);
    }

    public void useTools() {
        if(endurance == 0) {
            System.out.println("Low endurance! Go to a saloon for Heaven's sake!");
            return;
        }
        int newMoney;

        Tool tool = (Tool) tools.get(0);
        newMoney = tool.useTool();
        money += newMoney;
        System.out.println("Pan brought you $" + newMoney);

        tool = (Tool) tools.get(1);
        newMoney = tool.useTool();
        money += newMoney;
        System.out.println("Sluice brought you $" + newMoney);

        int dumped = 0;
        
        newMoney = money;
        for(int i = 2; i < tools.size(); i++){
            tool = (Tool) tools.get(i);
            money += tool.useTool();

            if(tool.getDurability() == 0) {
                tools.remove(tool);
                dumped++;
            }
        }

        System.out.println("Cradles brought you $" + (money - newMoney));

        switch(dumped) {
            case 0: 
                System.out.println("No cradles were harmed.");
                break;
            case 1: 
                System.out.println("Dumped 1 broken cradle.");
                break;
            default: 
                System.out.println("Dumped " + dumped + " broken cradles.");
                break;
        }

        // System.out.println("Used tools. Current status:");
        
        // tool = (Tool) tools.get(1);
        // System.out.println("Sluice: " + tool.getDurability() + "%");

        // for(int i = 2; i < tools.size(); i++){
        //     tool = (Tool) tools.get(i);
        //     // System.out.println("Cradle: " + tool.getDurability() + "%");
        // }

        System.out.println("Current Seated Liberty stack: $" + money + ".");
    }

    public int buyFood() {
        int newMoney = rnd.nextInt(50 - 30 + 1) + 30;
        money -= newMoney;

        if(money < 0){
            newMoney += money;
            money = 0;

            System.out.println("Oh no! You'll have to work with an empty belly, since you have no more money...");
        }
        else{
            System.out.println("Bought food, spent $" + newMoney + " of ye old chips!");
        }

        return newMoney;
    }

    public void loseEndurance() {
        endurance -= rnd.nextInt(25  - 10 + 1) + 10;
        if(endurance < 0) endurance = 0;

        System.out.println("Lost endurance - now at " + endurance + "%.");
    }

    public void itIsSundayAgain(String option) {        
        switch(option) {
            case "house":
                System.out.println("Even God rested on Sunday!");
                // nothing
                break;
            case "work":
                System.out.println("Work is priority number one!");
                fixSluice();
                break;
            case "saloon":
                System.out.println("Enjoy your night's out! Have one for me!");
                goToSaloon();
                break;
            default:
                break;
        }
    }

    private void goToSaloon() {
        money -= rnd.nextInt(200 - 50 + 1) + 50;

        endurance += rnd.nextInt(50 - 5 + 1) + 5;
        if(endurance > 100) endurance = 100;

        System.out.println("You saved $" + money + " for next Sunday! Endurance right now is at " + endurance + "%!");
    }

    private void fixSluice() {
        if(money < 100) {
            System.out.println("Turns out you don't have enough money to fix the sluice! Better luck next time.");
        }
        else {
            money -= 100;
    
            Sluice sluice = (Sluice) tools.get(1);
            sluice.repair();
    
            System.out.println("Only $" + money + " left after fixing the ruddy sluice!");
        }
    }
}
