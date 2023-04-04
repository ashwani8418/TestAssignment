package org.example;

import org.example.Card.Card;

public class Player {
    private final String name;
    private final Card[] hand;
    public int handSize;

    public Player(String name) {
        this.name = name;
        this.hand = new Card[5];
        handSize = 0;
    }

    public String getName() {
        return name;
    }

    public Card[] getHand() {
        return hand;
    }

    public void addCardToPlayer(Card card){
        for(int i = 0; i < hand.length; i++){
            if(hand[i] == null){
                hand[i] = card;
                return;
            }
        }
        throw new RuntimeException("Cannot draw card, hand if full");
    }

    public Card playCard(int index) {
        if (index < 0 || index > hand.length) {
            return null;
        }
        Card card = hand[index];
        for (int i = index; i < hand.length - 1; i++) {
            hand[i] = hand[i + 1];
        }
        hand[hand.length - 1] = null;
        handSize = hand.length - 1;
        return card;
    }

    public int getHandSize() {
        return handSize;
    }

    public void printHand() {
        System.out.println("Cards in hand of player " + name + ":");
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                System.out.println(i + " : " + hand[i].getRank() + " : " + hand[i].getSuit());
            }
        }
        System.out.println();
    }
}
