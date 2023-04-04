package org.example;

import org.example.Card.Card;

public class Game {
    private int numPlayers;
    private Player[] players;
    private Card[] deck;
    private Card[] discardPile;
    private int currentTurn;
    private int direction;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        this.players = new Player[numPlayers];
        this.deck = new Card[52];
        this.discardPile = new Card[52];
        this.currentTurn = 0;
        this.direction = 1;

        // Initialize players
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i + 1);
        }

        // Initialize deck
        int cardIndex = 0;
        for (int rank = 2; rank <= 14; rank++) {
            for (int suit = 1; suit <= 4; suit++) {
                deck[cardIndex] = new Card(rank, suit);
                cardIndex++;
            }
        }

        // Shuffle deck
        Random rnd = new Random();
        for (int i = 0; i < deck.length; i++) {
            int j = rnd.nextInt(deck.length);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }

        // Deal cards to players
        int cardCount = 0;
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < 5; j++) {
                players[i].addCardToHand(deck[cardCount]);
                cardCount++;
            }
        }

        // Place top card on discard pile
        discardPile[0] = deck[cardCount];
        cardCount++;
    }

    public void play() {
    while(true)

    {
        Player currentPlayer = players[currentTurn];

        System.out.println("Player " + currentPlayer.getId() + "'s turn.");

        // Display top card on discard pile
        System.out.println("Top card on discard pile: " + discardPile[0]);

        // Display current player's hand
        System.out.print("Your hand: ");
        for (Card card : currentPlayer.getHand()) {
            System.out.print(card + " ");
        }
        System.out.println();

        // Determine valid moves
        boolean hasValidMove = false;
        for (Card card : currentPlayer.getHand()) {
            if (card.matches(discardPile[0])) {
                hasValidMove = true;
                break;
            }
        }

        if (!hasValidMove) {
            System.out.println("No valid moves. Drawing a card.");
            Card drawnCard = deck[deck.length - 1];
            currentPlayer.addCardToHand(drawnCard);
            deck[deck.length - 1] = null;

            if (deck[deck.length - 1] == null) {
                System.out.println("Draw pile is empty. Game ends in a draw.");
                return;
            }
        } else {
            // Ask player to select a card to play
            boolean hasPlayedCard = false;
            while (!hasPlayedCard) {
                Card selectedCard = currentPlayer.selectCardToPlay();

                if (selectedCard == null) {
                    System.out.println("Drawing a card instead.");
                    Card drawnCard = deck[deck.length - 1];
                    currentPlayer.addCardToHand(drawnCard);
                    deck[deck.length - 1] = null;

                    if (deck[deck.length - 1] == null) {
                        System.out.println("Draw pile is empty. Game ends in a draw");
                    }
                }

                // Check if selected card is a valid move
                if (selectedCard.matches(discardPile[0])) {
                    System.out.println("Playing " + selectedCard + ".");
                    currentPlayer.playCard(selectedCard);
                    discardPile[0] = selectedCard;

                    if (selectedCard.isActionCard()) {
                        switch (selectedCard.getRank()) {
                            case 11:
                                System.out.println("Skipping next player.");
                                currentTurn += direction * 2;
                                break;
                            case 12:
                                System.out.println("Reversing direction.");
                                direction *= -1;
                                break;
                            case 13:
                                System.out.println("Adding 2 to next player's turn.");
                                currentTurn += direction;
                                Player nextPlayer = getNextPlayer();
                                nextPlayer.addCardToHand(deck[deck.length - 1]);
                                deck[deck.length - 1] = null;
                                nextPlayer.addCardToHand(deck[deck.length - 1]);
                                deck[deck.length - 1] = null;
                                break;
                            case 14:
                                System.out.println("Adding 4 to next player's turn.");
                                currentTurn += direction;
                                nextPlayer = getNextPlayer();
                                for (int i = 0; i < 4; i++) {
                                    nextPlayer.addCardToHand(deck[deck.length - 1]);
                                    deck[deck.length - 1] = null;
                                }
                                break;
                        }
                    }

                    hasPlayedCard = true;
                } else {
                    System.out.println(selectedCard + " is not a valid move.");
                }
            }
        }

        // Check if current player has won
        if (currentPlayer.getHandSize() == 0) {
            System.out.println("Player " + currentPlayer.getId() + " wins!");
            return;
        }

        // Move to next player
        currentTurn += direction;

        if (currentTurn < 0) {
            currentTurn = numPlayers - 1;
        } else if (currentTurn >= numPlayers) {
            currentTurn = 0;
        }
    }

    private Player getNextPlayer() {
        int nextTurn = currentTurn + direction;

        if (nextTurn < 0) {
            nextTurn = numPlayers - 1;
        } else if (nextTurn >= numPlayers) {
            nextTurn = 0;
        }
        return players[nextTurn];
    }
}
