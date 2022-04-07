package com.goldrush;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Game {
    private int week;
    private GoldRush goldRush = new GoldRush();
    private String msg = "";
    private String location = "";
    private int gp_fsm = 0;
    private int foodPrice = 0;
    private int cradleCount = 0;

    private boolean fs = true; // non-fullscreen flag
    private boolean fps = true; // non-popUpS flag
    private boolean fph = true; // non-popUpH flag
    private boolean fpw = true; // non-popUpW flag
    private boolean fp = true; // non-popUp flag
    private boolean fpm = false; // non-popUpM flag
    private boolean fsa[] = {false, false}; // seller flag
    // private boolean fnf = false; // near seller flag
    // private boolean fnc = false; // near seller flag
    private boolean fss[] = {false, false}; // selling seller flag
    private boolean fsg = false; // selling seller flag
    private boolean fad = true; // AnimPoint direction flag
    private boolean fbc = false; // AnimPoint direction flag
    private boolean feg = false; // EndGame read from keyboard flag
    private boolean cmpltAnim = false;
    private boolean cmpltAnimB = false;
    private boolean cmpltAnimC = false;
    private boolean cmpltAnimD = false;

    private int countAI = -1;

    public Game() {}
    
    private void makeFortyNiner(){
        week = goldRush.loadGame();
        week = goldRush.survive();
    }


    public void gameplay(Pane layout, NPCs npcs, Text textPopUp, Text textMenu, Element popUp){
        switch(gp_fsm){
        case 0:
            makeFortyNiner();
            msg = "GOLD RUSH\n";
            msg += "In this game, you are an ol' timer - 49er!";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            layout.getChildren().add(popUp.getImageView());
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;
            break;
        case 1:
            msg = "Every weekend you can go to the saloon, rest at home or fix the broken sluice.";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            break;
        case 2:
            msg = "You will also have to buy food, but you may buy cradles as well.";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            break;
        case 3:
            layout.getChildren().remove(popUp.getImageView());
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            break;
        case 4:       
            switch(location) {
                case "house":
                    msg = "Even God rested on Sunday!";
                    msg+= "\n\nPress K to navigate";
                    break;
                case "work":
                    msg = "Work is priority number one!";
                    msg+= "\n\nPress K to navigate";
                    break;
                case "saloon":
                    msg = "Enjoy your night's out!\n";
                    msg+= "Have one for me!";
                    msg+= "\n\nPress K to navigate";
                    break;
                default:
                    msg = "";
                    break;
            }
            goldRush.getFortyNiner().itIsSundayAgain(location);
            textPopUp.setText(msg);
            layout.getChildren().add(popUp.getImageView());
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;
            break;
        case 5:
            layout.getChildren().remove(popUp.getImageView());
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            sellerComes(npcs.getNPCs()[0], 0);
            break;
        case 6:
            buyFood(layout, textPopUp, popUp);
            break;
        case 7:
            sellerGoes(layout, npcs.getNPCs()[0], textPopUp, popUp, 0);
            break;
        case 8:
            cmpltAnimC = true;
            msg = "You can now buy some more of those sweet cradles.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                fpm = false;
                fp = false;
            }
            else {
                fpm = true;
                fp = false;
            }
            textPopUp.setText(msg);
            layout.getChildren().add(popUp.getImageView());
            layout.getChildren().add(textPopUp);
            break;
        case 9:
            layout.getChildren().remove(popUp.getImageView());
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            sellerComes(npcs.getNPCs()[1], 1);
            break;
        case 10:
            buyCradles(layout, textPopUp, popUp);
            break;
        case 11:
            sellerGoes(layout, npcs.getNPCs()[1], textPopUp, popUp, 1);
            break;
        case 12:
            cmpltAnimC = true;
            msg = "You worked hard this week and some tools are now destroyed.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                fpm = false;
                fp = false;
            }
            else {
                fpm = true;
                fp = false;
            }
            textPopUp.setText(msg);
            layout.getChildren().add(popUp.getImageView());
            layout.getChildren().add(textPopUp);
            goldRush.getFortyNiner().useTools();
            goldRush.getFortyNiner().loseEndurance();
            break;
        case 13:
            layout.getChildren().remove(popUp.getImageView());
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            week++;
            msg = "It's weekend again!";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            layout.getChildren().add(popUp.getImageView());
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;

            if(week == 20) {
                endgame(layout, textPopUp, popUp);
            }
            break;
        default:
            return;
        }
        menuDisplay(textMenu);
        gp_fsm++;
        if(gp_fsm >= 14){
            gp_fsm = 3;
        }
    }

    private void endgame(Pane layout, Text textPopUp, Element popUp){
        layout.getChildren().remove(popUp.getImageView());
        layout.getChildren().remove(textPopUp);
        msg = "Congratulations!\n";
        msg += "You WON!\n";
        msg += "You survived 20 weeks in the Wild West!\n";
        msg += "Press Q to leave or\n";
        msg += "press K to continue..."; // not sure why, but K deals with it without custom flag
        textPopUp.setText(msg);
        layout.getChildren().add(popUp.getImageView());
        layout.getChildren().add(textPopUp);
    }

    private void sellerComes(NPC npc, int id){
        fss[id] = true;
        fsa[id] = true;
        countAI = 0;
        fad = true;
        fsg = true;
        cmpltAnim = false;
        npc.getImageView().setImage(npc.getImageView().getImage());

        msg = "Food for this week will cost you $";
        msg += foodPrice;
        msg += ".";
        msg += "\n\nPress K to navigate";

        cmpltAnimD = false;
        npc.come(0);
    }

    private void sellerGoes(Pane layout, NPC npc, Text textPopUp, Element popUp, int id){
        cmpltAnimB = false;
        layout.getChildren().remove(popUp.getImageView());
        layout.getChildren().remove(textPopUp);
        fpm = true;
        fp = true;

        fss[id] = false;
        fsa[id] = true;
        fad = false;

        if(id == 0){
            msg = "You worked hard this week and some tools are now destroyed.";
            msg += "\n\nPress K to navigate";
        }
        else{
            fbc = false;

            int newMoney = cradleCount * 30;
    
            while(goldRush.getFortyNiner().getMoney() < newMoney) {
                cradleCount--;
                newMoney = cradleCount * 30;
            }
    
            for(int item = 0; item < cradleCount; item++) {
                Cradle cradle = new Cradle();
                goldRush.getFortyNiner().setTools(cradle);
            }
    
            goldRush.getFortyNiner().setMoney(goldRush.getFortyNiner().getMoney() - newMoney);
    
            cradleCount = 0;

            msg = "You worked hard this week and some tools are now destroyed.";
            msg += "\n\nPress K to navigate";
        }

        cmpltAnimC = false;
        npc.getImageView().setImage(npc.getImageView().getImage());
        npc.go(0);
    }

    private void buyFood(Pane layout, Text textPopUp, Element popUp){
        foodPrice = goldRush.getFortyNiner().buyFood();

        msg = "Hi!\n";
        msg+= "Food for this week will cost you $";
        msg+= foodPrice;
        msg+= ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            fpm = false;
        }
        layout.getChildren().add(popUp.getImageView());
        textPopUp.setText(msg);
        layout.getChildren().add(textPopUp);
    }

    private void buyCradles(Pane layout, Text textPopUp, Element popUp){
        // cradle = fortyNiner.buyCradle();

        msg = "How many cradles do you want (M/N)?\n";
        msg += "No. of new cradles ";
        msg += cradleCount;
        msg += ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            fpm = false;
        }
        layout.getChildren().add(popUp.getImageView());
        textPopUp.setText(msg);
        layout.getChildren().add(textPopUp);
    }

    public void enter(String name, Pane layout, Text textPopUp, Element popUp){
        switch(name){
        case "saloon":
            if(!fps) return;
            textPopUp.setText("Welcome to the Saloon!\n\nPress K to navigate");
            fps = false;
            break;
        case "house":
            if(!fph) return;
            textPopUp.setText("Welcome Home!\n\nPress K to navigate");
            fph = false;
            break;
        case "workplace":
            if(!fpw) return;
            textPopUp.setText("This time next year, you'll be a millionaire!\n\nPress K to navigate");
            fpw = false;
            break;
        default:
            return;
        }
        layout.getChildren().add(popUp.getImageView());
        layout.getChildren().add(textPopUp);
        fp = false;
    }

    public void exit(Pane layout, Text textPopUp, Element popUp){
        layout.getChildren().remove(textPopUp);
        layout.getChildren().remove(popUp.getImageView());
        fps = true;
        fph = true;
        fpw = true;
        fp = true;
    }

    private void menuDisplay(Text textMenu){
        String msgs = "Week: " + week + "\n";
        msgs += "Stamina: " + goldRush.getFortyNiner().getEndurance() + "%\n";
        msgs += "Sluice health: " + goldRush.getFortyNiner().getTools().get(1).getDurability() + "%\n";
        msgs += "No. of cradles: " + (goldRush.getFortyNiner().getTools().size() - 2) + "\n";
        msgs += "Money: $" + goldRush.getFortyNiner().getMoney() + "\n";
        textMenu.setText(msgs);
    }

    public void changeCradle(int val, Text textPopUp){
        if(fbc){
            cradleCount += val;
            msg = "How many cradles do you want (M/N)?\n";
            msg += "No. of new cradles ";
            msg += cradleCount;
            msg += ".";
            if(cmpltAnim || cmpltAnimD) {
                msg += "\n\nPress K to navigate";
                fpm = false;
            }
            textPopUp.setText(msg);
        }
    }

    public void pressedK(Pane layout, NPCs npcs, Text textPopUp, Text textMenu, Element popUp){
        if(!fps) {
            exit(layout, textPopUp, popUp);
            if(gp_fsm == 4){
                location = "saloon";
                gameplay(layout, npcs, textPopUp, textMenu, popUp);
            }
        }
        else if(!fph) {
            exit(layout, textPopUp, popUp);
            if(gp_fsm == 4 || gp_fsm == 8){
                location = "house";
                gameplay(layout, npcs, textPopUp, textMenu, popUp);
            }
        }
        else if(!fpw) {
            exit(layout, textPopUp, popUp);
            if(gp_fsm == 4 || gp_fsm == 8 || gp_fsm == 12){
                location = "work";
                gameplay(layout, npcs, textPopUp, textMenu, popUp);
            }
        }
        else if(!fpm) {
            gameplay(layout, npcs, textPopUp, textMenu, popUp);
        }
    }
    
    public void exitGame(){
        goldRush.saveGame(week);
        Platform.exit();
    }

}
