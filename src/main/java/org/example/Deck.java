package org.example;

import org.example.Card.Card;
import org.example.Card.Rank;
import org.example.Card.Suit;

import java.util.Random;

public class Deck {
    private final Card[] cards;
    private int topIndex;

    public Deck() {
        cards = new Card[52];
        topIndex = 0;
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[index] = new Card(rank, suit);
                index++;
            }
        }
    }

    public void shuffleCard() {
        Random random = new Random();
        for (int i = 0; i < cards.length; i++) {
            int j = random.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        topIndex = 0;
    }

    public Card drawCard() {
        if (topIndex >= cards.length) {
            return null;
        }
        Card card = cards[topIndex];
        topIndex++;
        return card;
    }


}

