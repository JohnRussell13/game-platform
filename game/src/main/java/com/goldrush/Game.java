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

    private Menu menu;
    private PopUp popUp;

    public Game(Menu mn, PopUp pu) {
        menu = mn;
        popUp = pu;
    }
    
    private void makeFortyNiner(){
        week = goldRush.loadGame();
        week = goldRush.survive();
    }


    public void gameplay(Pane layout, NPCs npcs){
        switch(gp_fsm){
        case 0: // START SCREEN
            makeFortyNiner();
            msg = "GOLD RUSH\n";
            msg += "In this game, you are an ol' timer - 49er!";
            msg += "\n\nPress K to navigate";

            popUp.setMessage(msg);
            popUp.show(layout);
            popUp.setFlag(false);

            fp = false;
            break;
        case 1: // START SCREEN
            msg = "Every weekend you can go to the saloon, rest at home or fix the broken sluice.";
            msg += "\n\nPress K to navigate";

            popUp.setMessage(msg);
            break;
        case 2: // START SCREEN
            msg = "You will also have to buy food, but you may buy cradles as well.";
            msg += "\n\nPress K to navigate";

            popUp.setMessage(msg);
            break;
        case 3: // EXIT START SCREENS
            popUp.hide(layout);
            popUp.setFlag(true);

            fp = true;
            break;
        case 4: // ENTER LOCATION
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
            popUp.setMessage(msg);
            popUp.show(layout);
            popUp.setFlag(false);
            fp = false;
            break;
        case 5: // FOOD SELLEER COMES
            popUp.hide(layout);
            popUp.setFlag(true);
            fp = true;
            sellerComes(npcs.getNPCs()[0], 0);
                
            msg = "\n\nPress K to navigate";
            popUp.appendMessage(msg);


            popUp.show(layout);
            popUp.setFlag(false);
            fp = false;
            break;
        case 6: // BUYING FOOD
            popUp.hide(layout);
            popUp.setFlag(true);
            fp = true;
            buyFood(layout);
            break;
        case 7: // FOOD SELLER GOES
            sellerGoes(layout, npcs.getNPCs()[0], 0);
            break;
        case 8: // ENTER LOCATION
            cmpltAnimC = true;
            msg = "You can now buy some more of those sweet cradles.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                popUp.setFlag(false);
                fp = false;
            }
            else {
                popUp.setFlag(true);
                fp = false;
            }
            popUp.setMessage(msg);
            popUp.show(layout);
            break;
        case 9: // CRADLES SELLER COMES
            popUp.hide(layout);
            popUp.setFlag(true);
            fp = true;

            sellerComes(npcs.getNPCs()[1], 1);
               
            msg = "\n\nPress K to navigate";
            popUp.appendMessage(msg);

            popUp.setMessage(msg);
            popUp.show(layout);
            popUp.setFlag(false);
            fp = false;
            break;
        case 10: // BUYING CRADLES
            popUp.hide(layout);
            popUp.setFlag(true);
            fp = true;
            buyCradles(layout);
            break;
        case 11:// CRADLES SELLER GOES
            sellerGoes(layout, npcs.getNPCs()[1], 1);
            break;
        case 12: // END WEEK
            cmpltAnimC = true;
            msg = "You worked hard this week and some tools are now destroyed.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                popUp.setFlag(false);
                fp = false;
            }
            else {
                popUp.setFlag(true);
                fp = false;
            }
            popUp.setMessage(msg);
            popUp.show(layout);

            goldRush.getFortyNiner().useTools();
            goldRush.getFortyNiner().loseEndurance();
            break;
        case 13:// NEW WEEK STARTS
            popUp.hide(layout);
            popUp.setFlag(true);
            fp = true;

            week++;

            msg = "It's weekend again!";
            msg += "\n\nPress K to navigate";
            popUp.setMessage(msg);
            popUp.show(layout);
            popUp.setFlag(false);
            fp = false;

            if(week == 20) {
                endgame(layout);
            }
            break;
        default:
            return;
        }
        menuDisplay();
        gp_fsm++;
        if(gp_fsm >= 14){
            gp_fsm = 3;
        }
    }

    private void endgame(Pane layout){
        popUp.hide(layout);
        
        msg = "Congratulations!\n";
        msg += "You WON!\n";
        msg += "You survived 20 weeks in the Wild West!\n";
        msg += "Press Q to leave or\n";
        msg += "press K to continue..."; // not sure why, but K deals with it without custom flag
        
        popUp.setMessage(msg);
        popUp.show(layout);
    }

    private void sellerComes(NPC npc, int id){
        fss[id] = true;
        fsa[id] = true;
        countAI = 0;
        fad = true;
        fsg = true;
        cmpltAnim = false;
        npc.getImageView().setImage(npc.getImageView().getImage());

        cmpltAnimD = false;
        npc.come(0);
    }

    private void sellerGoes(Pane layout, NPC npc, int id){
        cmpltAnimB = false;
        popUp.hide(layout);
        popUp.setFlag(true);
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

    private void buyFood(Pane layout){
        foodPrice = goldRush.getFortyNiner().buyFood();

        msg = "Hi!\n";
        msg+= "Food for this week will cost you $";
        msg+= foodPrice;
        msg+= ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            popUp.setFlag(false);
        }
        popUp.setMessage(msg);
        popUp.show(layout);
    }

    private void buyCradles(Pane layout){
        // cradle = fortyNiner.buyCradle();

        msg = "How many cradles do you want (M/N)?\n";
        msg += "No. of new cradles ";
        msg += cradleCount;
        msg += ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            popUp.setFlag(false);
        }
        popUp.setMessage(msg);
        popUp.show(layout);
    }

    public void enter(String name, Pane layout){
        switch(name){
        case "saloon":
            if(!fps) return;
            msg = "Welcome to the Saloon!\n\nPress K to navigate";
            fps = false;
            break;
        case "house":
            if(!fph) return;
            msg = "Welcome Home!\n\nPress K to navigate";
            fph = false;
            break;
        case "workplace":
            if(!fpw) return;
            msg = "This time next year, you'll be a millionaire!\n\nPress K to navigate";
            fpw = false;
            break;
        default:
            return;
        }
        popUp.setMessage(msg);
        popUp.show(layout);
        fp = false;
    }

    public void exit(Pane layout){
        popUp.hide(layout);
        fps = true;
        fph = true;
        fpw = true;
        fp = true;
    }

    private void menuDisplay(){
        String msgs = "Week: " + week + "\n";
        msgs += "Stamina: " + goldRush.getFortyNiner().getEndurance() + "%\n";
        msgs += "Sluice health: " + goldRush.getFortyNiner().getTools().get(1).getDurability() + "%\n";
        msgs += "No. of cradles: " + (goldRush.getFortyNiner().getTools().size() - 2) + "\n";
        msgs += "Money: $" + goldRush.getFortyNiner().getMoney() + "\n";

        menu.setMessage(msgs);
    }

    public void changeCradle(int val){
        if(fbc){
            cradleCount += val;
            msg = "How many cradles do you want (M/N)?\n";
            msg += "No. of new cradles ";
            msg += cradleCount;
            msg += ".";
            if(cmpltAnim || cmpltAnimD) {
                msg += "\n\nPress K to navigate";
                popUp.setFlag(false);
            }
            popUp.setMessage(msg);
        }
    }

    public void pressedK(Pane layout, NPCs npcs){
        if(!fps) {
            exit(layout);
            if(gp_fsm == 4){
                location = "saloon";
                gameplay(layout, npcs);
            }
        }
        else if(!fph) {
            exit(layout);
            if(gp_fsm == 4 || gp_fsm == 8){
                location = "house";
                gameplay(layout, npcs);
            }
        }
        else if(!fpw) {
            exit(layout);
            if(gp_fsm == 4 || gp_fsm == 8 || gp_fsm == 12){
                location = "work";
                gameplay(layout, npcs);
            }
        }
        else if(!popUp.getFlag()) {
            gameplay(layout, npcs);
        }
    }
    
    public void exitGame(){
        goldRush.saveGame(week);
        Platform.exit();
    }

    public PopUp getPopUp(){ return popUp; }

}
