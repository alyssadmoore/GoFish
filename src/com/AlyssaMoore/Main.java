package com.AlyssaMoore;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Creating Scanners
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Initializing while control loop goAgain
        int goAgain = 1;

        // Creating player's cards array; can hold 50 cards
        // TODO is it possible to not set size but add cards as game goes on?
        String playerHand[] = new String[50];

        // Creating computer's cards array; can hold 50 cards
        // TODO same as above
        String computerHand[] = new String[50];

        // Creating rank array, used with suit array to create deck array
        String rank[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        // Creating suit array, used with rank array to create deck array
        String suit[] = {"Clubs", "Diamonds", "Hearts", "Spades"};

        // Creating empty deck array
        String deck[] = new String[52];

        // Creating deck: first go through all ranks
        for (int x = 0; x < 13; x++) {

            // for each rank, go through all suits
            for (int y = 0; y < 4; y++) {

                    /* deck index: performs calculation on all suits of
                       this rank, then goes to the next rank, repeat
                       deck contents: comprised of a String combining rank and suit */
                deck[(4 * x) + y] = rank[x] + " of " + suit[y];
            }
        }
            /* Shuffling deck block modified from:
            http://www.cs.princeton.edu/courses/archive/fall10/cos126/lectures/04-14Array-2x2.pdf, page 15*/

            /* This loop takes a card at a random index in the deck, copies it, switches
               current card in loop for that card, then repeats for all cards in deck
             */

        // Shuffling the deck: iterate over the length of the deck (52)
        for (int a = 0; a < 52; a++) {

                /* finding a random number 0 - 52 and assigning it to variable b
                (I use a + ... 52 - a to not include cards already shuffled and put in index a
                in the Math.random calculation, and add the number back outside the calculation) */

            int b = a + (int) (Math.random() * (52 - a));

            // copying card at deck index b, assigning it to variable c
            String c = deck[b];

            // switching card at index b to current deck index in loop
            deck[b] = deck[a];

            // assigning current index in deck String variable c
            deck[a] = c;
        }

        // Uses first 7 cards in deck for player's cards
        for (int x = 0; x < 7; x++)
            playerHand[x] = deck[x];

        // Uses next 7 cards in deck for computer's cards
        for (int x = 7; x < 14; x++)
            computerHand[x - 7] = deck[x];

        // While loop encapsulates gameplay
        while (goAgain == 1) {
            System.out.println("Welcome to Go Fish! Here are the rules for this version:\n");
            System.out.println("1. If you find the requested card in the Go Fish pile, you may NOT ask for another card.");
            System.out.println("2. If you have more than one card of a rank when asked, you MUST hand over all cards of that rank.");
            System.out.println("3. You MUST lay down a 4-card book when you have all suits of a given rank.\n");

            // Showing the user their cards
            System.out.println("Here are your cards:");
            System.out.println(Arrays.toString(playerHand) + "\n");

            // Asking the user for a request to the computer player
            System.out.println("What rank will you request? Enter 2-10, Jack, Queen, King, or Ace");
            String request = stringScanner.nextLine();

            // for loop searches computer's hand
            for (int x = 0; x < 50; x++) {

                // if request is in computer's hand, copy to variable yesHand and replace with blank spot
                if (computerHand[x].contains(request)) {
                    System.out.println("Score! Adding card to hand...");
                    String yesCard = computerHand[x];
                    computerHand[x] = "";

                    // add yesHand to user's hand in next null entity
                    for (int y = 0; y < 50; y++) {
                        if (playerHand[y] == null)
                            playerHand[y] = yesCard;
                    }
                    // telling the user to go fish if request is not in computer's hand
                } else {
                    System.out.println("Sorry, go fish!");
                    for (int z = 0; z < 50; z++) {
                        if (playerHand[z] == null) {
                                if (deck[z] != null) {
                                    String goFishCard = deck[z];
                                    playerHand[z] = goFishCard;
                                    System.out.println("You fished a " + goFishCard + "\n");
                                    break;
                            }
                        }
                    }
                }
                // Computer states first card in hand as guess to ensure computer has at least one card of requested rank
                // TODO improve computer's request strategy
                String compGuess = computerHand[0];
                System.out.println("Computer's turn! Got any with this rank? " + compGuess);

                // for loop the same as for user's guess above
                // TODO combine these into one loop if possible
                for (int a = 0; a < 50; a++) {
                    // TODO: this is a very imperfect line!! This only matches exact cards which will never happen, I need to
                    // TODO: somehow extract just the rank. This just shows the method I will use once I figure that part out.

                    // code breaks at this point
                    if (playerHand[a].contains(compGuess)) {
                        System.out.println("Yes, handing card over...");
                        String yesCard = playerHand[a];
                        playerHand[a] = "";

                        for (int y = 0; y < 50; y++) {
                            if (computerHand[x] == null) {
                                computerHand[x] = yesCard;
                                break;
                            }
                        }
                        // also same as for player above
                    } else {
                        System.out.println("Sorry, go fish!");
                        for (int z = 0; z < 50; z++) {
                            if (computerHand[z] == null) {
                                    if (deck[z] != null) {
                                        String goFishCard = deck[z];
                                        computerHand[z] = goFishCard;
                                        break;
                                }
                            }
                        }
                    }
                }
                // TODO: active counter on number of cards in hand per rank, if
                // TODO: 4 then remove those from hand and add 1 to book counter
                // Added book count constants so the code runs, to be added later
                int userBookCount = 0;
                int computerBookCount = 0;

                // If the deck is empty, the game ends and tallies up books and states winner
                if (deck.length == 0) {
                    System.out.println("The deck is out of cards. Let's see who won!");
                    if (userBookCount > computerBookCount) {
                        System.out.println("Congratulations, you won! You had " + userBookCount +
                                " books, compared to the computer's " + computerBookCount);
                    } else if (computerBookCount > userBookCount) {
                        System.out.println("Sorry, you lose. You had " + userBookCount +
                                " books, compared to the computer's " + computerBookCount);
                    }
                    // Asking user if they wish to play again
                    System.out.println("Do you want to play again? Type 1 for yes or 0 for no");
                    goAgain = numberScanner.nextInt();
                }
                // If user has over half the books, it isn't possible to lose
                    else if (userBookCount == 7) {
                        System.out.println("You got over half the books. You win!");

                        // Asking user if they wish to play again
                        System.out.println("Do you want to play again? Type 1 for yes or 0 for no");
                        goAgain = numberScanner.nextInt();
                }

                    // If the computer has over half the books, it isn't possible to win
                    else if (computerBookCount == 7) {
                        System.out.println("Sorry, the computer got over half the books. You lose.");

                    // Asking user if they wish to play again
                    System.out.println("Do you want to play again? Type 1 for yes or 0 for no");
                    goAgain = numberScanner.nextInt();
                }
            }
        }
    }
}