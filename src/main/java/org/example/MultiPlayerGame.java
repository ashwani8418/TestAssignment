package org.example;

import org.example.Card.Card;

import java.util.Scanner;

public class MultiPlayerGame {
    private final Deck deck;
    private final Player[] players;
    private final Card[] discardPile;
    private int topDiscard;
    private int currentPlayer;
    private boolean reversed;
    private int drawCount;

    public MultiPlayerGame(Player[] players){
        deck = new Deck();
        deck.shuffleCard();
        this.players = players;
        discardPile = new Card[52];
        topDiscard = 0;
        currentPlayer = 0;
        reversed = false;
        drawCount = 0;
        dealCards();
        discardPile[topDiscard++] = deck.drawCard();
    }

    private void dealCards(){
        for (Player player : players){
            for (int i = 0; i < 5; i++){
                player.addCardToPlayer(deck.drawCard());
            }
        }
    }

    public void startGame(){
        while (!isGameOver()){
            playTurn();
            nextPlayer();
        }
        endGame();
    }

    private boolean isGameOver(){
        for(Player player : players){
            if(player.getHand()[0] == null){
                return true;
            }
        }
        return false;
    }

    private void playTurn(){
        System.out.println("Current Player: " + players[currentPlayer].getName());
        players[currentPlayer].printHand();
        System.out.println("Top card on discard Pile: " + discardPile[topDiscard - 1]);
        if(drawCount > 0){
            System.out.println("Draw count: " + drawCount);
            for(int i = 0; i < drawCount; i++){
                players[currentPlayer].addCardToPlayer(deck.drawCard());
            }
            drawCount = 0;
        }

        boolean validPlay = false;
        while (!validPlay){
            int cardIndex = getCardIndex();
            Card card = players[currentPlayer].playCard(cardIndex);
            if(isValidPlay(card)){
                discardPile[topDiscard++] = card;
                performAction(card);
                validPlay = true;
            }
            else {
                System.out.println("Invalid play, please try again !");
                players[currentPlayer].addCardToPlayer(card);
            }
        }
    }

    private int getCardIndex() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the index (0-" + (players[currentPlayer].getHandSize()) + ") of the card you want to play: ");
            if (scanner.hasNextInt()) {
                int cardIndex = scanner.nextInt();
                if (cardIndex >= 0 && cardIndex < players[currentPlayer].getHand().length - 1) {
                    Card card = players[currentPlayer].getHand()[cardIndex];
                    if (card != null) {
                        return cardIndex;
                    } else {
                        System.out.println("You do not have a card at index " + cardIndex + ", please try again.");
                    }
                } else {
                    System.out.println("Invalid index, please enter a number between 0 and " + (players[currentPlayer].getHand().length - 1) + ".");
                }
            } else {
                System.out.println("Invalid input, please enter a number between 0 and " + (players[currentPlayer].getHand().length - 1) + ".");
                scanner.next();
            }
        }
    }

    private boolean isValidPlay(Card card){
        Card topCard = discardPile[topDiscard - 1];
        return  card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank();
    }
    private void performAction(Card card){
        switch (card.getRank()){
            case ACE -> {
                nextPlayer();
                break;
            }
            case KING -> {
                reversed = !reversed;
                break;
            }
            case QUEEN -> {
                drawCount += 2;
                break;
            }
            case JACK -> {
                drawCount += 4;
                break;
            }
        }
    }

    private void nextPlayer(){
        if(reversed){
            currentPlayer--;
            if(currentPlayer < 0){
                currentPlayer = players.length - 1;
            }
        }
        else {
            currentPlayer++;
            if(currentPlayer >= players.length - 1){
                currentPlayer = 0;
            }
        }
    }

    private void endGame(){
        for (Player player : players){
            if(player.getHand()[0] == null){
                System.out.println(player.getName() + " has won the game!!");
                return;
            }
        }
        System.out.println("The game end in a draw!!");
    }
}
