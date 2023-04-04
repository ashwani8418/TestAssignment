package org.example;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("......Welcome to the Swiggy Card Game.......");
        System.out.println("To start the game, please enter player number between 2 to 4 ");
        int numPlayers = sc.nextInt();
        while (numPlayers < 2 || numPlayers > 4){
            System.out.println("Invalid numbers of players, please try again!");
            System.out.println("Enter number of players between 2 to 4");
            numPlayers = sc.nextInt();
        }
        Player[] playerHands = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++){
            System.out.println("Enter name for player " + (i + 1) + " : ");
            String playerName = sc.next();
            playerHands[i] = new Player(playerName);
        }

        MultiPlayerGame game = new MultiPlayerGame(playerHands);
        System.out.println("............Starting Game!!!...................");
        game.startGame();
    }
}