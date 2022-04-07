// package com.goldrush;

// public class Game {
    

//     private void makeFortyNiner(){
//         week = game.loadGame();
//         week = game.survive();
//     }


//     private void gameplay(){
//         switch(gp_fsm){
//         case 0:
//             makeFortyNiner();
//             msg = "GOLD RUSH\n";
//             msg += "In this game, you are an ol' timer - 49er!";
//             msg += "\n\nPress K to navigate";
//             textPopUp.setText(msg);
//             layout.getChildren().add(popUpImage);
//             layout.getChildren().add(textPopUp);
//             fpm = false;
//             fp = false;
//             break;
//         case 1:
//             msg = "Every weekend you can go to the saloon, rest at home or fix the broken sluice.";
//             msg += "\n\nPress K to navigate";
//             textPopUp.setText(msg);
//             break;
//         case 2:
//             msg = "You will also have to buy food, but you may buy cradles as well.";
//             msg += "\n\nPress K to navigate";
//             textPopUp.setText(msg);
//             break;
//         case 3:
//             layout.getChildren().remove(popUpImage);
//             layout.getChildren().remove(textPopUp);
//             fpm = true;
//             fp = true;
//             break;
//         case 4:       
//             switch(location) {
//                 case "house":
//                     msg = "Even God rested on Sunday!";
//                     msg+= "\n\nPress K to navigate";
//                     break;
//                 case "work":
//                     msg = "Work is priority number one!";
//                     msg+= "\n\nPress K to navigate";
//                     break;
//                 case "saloon":
//                     msg = "Enjoy your night's out!\n";
//                     msg+= "Have one for me!";
//                     msg+= "\n\nPress K to navigate";
//                     break;
//                 default:
//                     msg = "";
//                     break;
//             }
//             game.getFortyNiner().itIsSundayAgain(location);
//             textPopUp.setText(msg);
//             layout.getChildren().add(popUpImage);
//             layout.getChildren().add(textPopUp);
//             fpm = false;
//             fp = false;
//             break;
//         case 5:
//             layout.getChildren().remove(popUpImage);
//             layout.getChildren().remove(textPopUp);
//             fpm = true;
//             fp = true;
//             foodComes();
//             break;
//         case 6:
//             buyFood();
//             break;
//         case 7:
//             foodGoes();
//             break;
//         case 8:
//             cmpltAnimC = true;
//             msg = "You can now buy some more of those sweet cradles.";
//             if(cmpltAnimB) {
//                 msg += "\n\nPress K to navigate";
//                 fpm = false;
//                 fp = false;
//             }
//             else {
//                 fpm = true;
//                 fp = false;
//             }
//             textPopUp.setText(msg);
//             layout.getChildren().add(popUpImage);
//             layout.getChildren().add(textPopUp);
//             break;
//         case 9:
//             layout.getChildren().remove(popUpImage);
//             layout.getChildren().remove(textPopUp);
//             fpm = true;
//             fp = true;
//             cradleComes();
//             break;
//         case 10:
//             buyCradles();
//             break;
//         case 11:
//             cradleGoes();
//             break;
//         case 12:
//             cmpltAnimC = true;
//             msg = "You worked hard this week and some tools are now destroyed.";
//             if(cmpltAnimB) {
//                 msg += "\n\nPress K to navigate";
//                 fpm = false;
//                 fp = false;
//             }
//             else {
//                 fpm = true;
//                 fp = false;
//             }
//             textPopUp.setText(msg);
//             layout.getChildren().add(popUpImage);
//             layout.getChildren().add(textPopUp);
//             game.getFortyNiner().useTools();
//             game.getFortyNiner().loseEndurance();
//             break;
//         case 13:
//             layout.getChildren().remove(popUpImage);
//             layout.getChildren().remove(textPopUp);
//             fpm = true;
//             fp = true;
//             week++;
//             msg = "It's weekend again!";
//             msg += "\n\nPress K to navigate";
//             textPopUp.setText(msg);
//             layout.getChildren().add(popUpImage);
//             layout.getChildren().add(textPopUp);
//             fpm = false;
//             fp = false;

//             if(week == 20) {
//                 endgame();
//             }
//             break;
//         default:
//             return;
//         }
//         menuDisplay();
//         gp_fsm++;
//         if(gp_fsm >= 14){
//             gp_fsm = 3;
//         }
//     }


//     private void foodComes(){
//         fsf = true;
//         faf = true;
//         countAI = 0;
//         fad = true;
//         fsg = true;
//         cmpltAnim = false;
//         sellerFoodImage.setImage(sellerFoodRight);

//         msg = "Food for this week will cost you $";
//         msg += foodPrice;
//         msg += ".";
//         msg += "\n\nPress K to navigate";

//         cmpltAnimD = false;
//         animAIF("sellerFood", sellerFoodImage, pathTransitionFood, animPointsSellerFood);
//     }

//     private void buyFood(){
//         foodPrice = game.getFortyNiner().buyFood();

//         msg = "Hi!\n";
//         msg+= "Food for this week will cost you $";
//         msg+= foodPrice;
//         msg+= ".";
//         if(cmpltAnim) {
//             msg += "\n\nPress K to navigate";
//             fpm = false;
//         }
//         layout.getChildren().add(popUpImage);
//         textPopUp.setText(msg);
//         layout.getChildren().add(textPopUp);
//     }

//     private void foodGoes(){
//         cmpltAnimB = false;
//         layout.getChildren().remove(popUpImage);
//         layout.getChildren().remove(textPopUp);
//         fpm = true;
//         fp = true;

//         fsf = false;
//         faf = true;
//         fad = false;

//         msg = "You worked hard this week and some tools are now destroyed.";
//         msg += "\n\nPress K to navigate";

//         cmpltAnimC = false;
//         sellerFoodImage.setImage(sellerFoodLeft);
//         animAIR("sellerFood", sellerFoodImage, pathTransitionFood, animPointsSellerFood);
//     }

//     private void cradleComes(){
//         fsc = true;
//         fac = true;
//         countAI = 0;
//         fad = true;
//         fsg = true;
//         cmpltAnim = false;
//         sellerCradleImage.setImage(sellerCradleRight);

//         msg = "How many cradles do you want?\n";
//         msg += "No. of new cradles ";
//         msg += cradlePrice;
//         msg += ".";
//         msg += "\n\nPress K to navigate";

//         fbc = true;

//         cmpltAnimD = false;
//         animAIF("sellerCradle", sellerCradleImage, pathTransitionCradle, animPointsSellerCradle);
//     }

//     private void buyCradles(){
//         // cradle = fortyNiner.buyCradle();

//         msg = "How many cradles do you want (M/N)?\n";
//         msg += "No. of new cradles ";
//         msg += cradlePrice;
//         msg += ".";
//         if(cmpltAnim) {
//             msg += "\n\nPress K to navigate";
//             fpm = false;
//         }
//         layout.getChildren().add(popUpImage);
//         textPopUp.setText(msg);
//         layout.getChildren().add(textPopUp);
//     }

//     private void cradleGoes(){
//         cmpltAnimB = false;
//         layout.getChildren().remove(popUpImage);
//         layout.getChildren().remove(textPopUp);
//         fpm = true;
//         fp = true;

//         fsc = false;
//         fac = true;
//         fad = false;

//         fbc = false;

//         int newMoney = cradlePrice * 30;

//         while(game.getFortyNiner().getMoney() < newMoney) {
//             cradlePrice--;
//             newMoney = cradlePrice * 30;
//         }

//         for(int item = 0; item < cradlePrice; item++) {
//             Cradle cradle = new Cradle();
//             game.getFortyNiner().setTools(cradle);
//         }

//         game.getFortyNiner().setMoney(game.getFortyNiner().getMoney() - newMoney);

//         cradlePrice = 0;

//         msg = "You worked hard this week and some tools are now destroyed.";
//         msg += "\n\nPress K to navigate";

//         cmpltAnimC = false;
//         sellerCradleImage.setImage(sellerCradleLeft);
//         animAIR("sellerCradle", sellerCradleImage, pathTransitionCradle, animPointsSellerCradle);
//     }

//     private void enterSaloon(){
//         layout.getChildren().add(popUpImage);
//         textPopUp.setText("Welcome to the Saloon!\n\nPress K to navigate");
//         layout.getChildren().add(textPopUp);
//         fps = false;
//         fp = false;
//     }

//     private void exitSaloon(){
//         layout.getChildren().remove(textPopUp);
//         layout.getChildren().remove(popUpImage);
//         playerImage.setImage(playerDown); // turn down when exiting house
//         fps = true;
//         fp = true;
//     }

//     private void enterHouse(){
//         layout.getChildren().add(popUpImage);
//         textPopUp.setText("Welcome Home!\n\nPress K to navigate");
//         layout.getChildren().add(textPopUp);
//         fph = false;
//         fp = false;
//     }

//     private void exitHouse(){
//         layout.getChildren().remove(textPopUp);
//         layout.getChildren().remove(popUpImage);
//         playerImage.setImage(playerLeft); // turn left when exiting house
//         fph = true;
//         fp = true;
//     }

//     private void enterWork(){
//         layout.getChildren().add(popUpImage);
//         textPopUp.setText("This time next year, you'll be a millionaire!\n\nPress K to navigate");
//         layout.getChildren().add(textPopUp);
//         fpw = false;
//         fp = false;
//     }

//     private void exitWork(){
//         layout.getChildren().remove(textPopUp);
//         layout.getChildren().remove(popUpImage);
//         fpw = true;
//         fp = true;
//     }

//     private void menuDisplay(){
//         String msgs = "Week: " + week + "\n";
//         msgs += "Stamina: " + game.getFortyNiner().getEndurance() + "%\n";
//         msgs += "Sluice health: " + game.getFortyNiner().getTools().get(1).getDurability() + "%\n";
//         msgs += "No. of cradles: " + (game.getFortyNiner().getTools().size() - 2) + "\n";
//         msgs += "Money: $" + game.getFortyNiner().getMoney() + "\n";
//         textMenu.setText(msgs);
//     }


// }
