

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Table {

    private static final int TOTAL_TURN = 12;

    private static final int TOTAL_TIDE_CARDS = TOTAL_TURN * 2;

    private static final int TOTAL_WEATHER_CARD = 60;

    private int[] tide = new int[TOTAL_TIDE_CARDS];

    private Weather[][] decks;

    private Player[] players;


    public static void main(String[] arg) {
        System.out.println("Welcome to the Turn the Tile game!");
        int player = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter the number of players you want to play:");
            player = scanner.nextInt();
        } while (player < 2 || player > 5);
        new Table(player).run();
    }


    public Table(int player) {
        // prepare players array
        // TODO
        players = new Player[player];
        players[0] = new Player("Anya Forger");
        for (int i = 1; i < players.length; i++) {
            players[i] = new Player();
        }

        // prepare tide cards, 1 to 12
        // TODO

        int[] tempTide = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12};
        tide = tempTide;

        int[][] MAGIC_NUMBERS = {
                {31, 26, 54, 19, 8, 45, 39, 16, 28, 42, 3, 38},  //deck1
                {30, 60, 58, 56, 51, 10, 41, 48, 14, 9, 32, 44},  //deck2
                {29, 35, 7, 49, 1, 21, 13, 46, 6, 18, 43, 24},   //deck3
                {27, 34, 33, 11, 12, 47, 23, 55, 40, 37, 22, 15}, //deck4, if any
                {57, 36, 50, 20, 53, 52, 59, 4, 25, 2, 17, 5}     //deck5, if any
        };

        // assign weather cards from the array weather to decks according to the
        // MAGIC_NUMBERS
        // TODO
        decks = new Weather[players.length][TOTAL_TURN];

        for (int i = 0; i < decks.length; i++)
            for (int j = 0; j < decks[i].length; j++) {
                decks[i][j] = new Weather(MAGIC_NUMBERS[i][j]);
            }


    }


    private void shuffleTideCard() {
        // A deterministic shuffle required by the program
        int[] order = {4, 23, 8, 11, 3, 16, 21, 14, 0, 6, 18, 15, 19, 9, 5, 12, 13, 2, 17, 7, 10, 20, 1, 22};
        int[] newTide = new int[TOTAL_TIDE_CARDS];
        for (int i = 0; i < TOTAL_TIDE_CARDS; i++) {
            newTide[i] = tide[order[i]];
        }
        tide = newTide;
    }


    public void run() {
        // TODO
        // The game should be running for N rounds.
        // We write the first round for you to get you started.

        for (int i = 0; i < players.length; i++) {
            System.out.println();
            System.out.println();
            int round = i + 1;
            System.out.println("Round " + round + " starts.");
            System.out.println("---------------------------------");
            System.out.println();
            int[] tempTide = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12};
            tide = tempTide;
            shuffleTideCard();
            oneRound(i);

        }
    }


    private void oneRound(int round) {
        // TODO
        // this method should do much more things and run at most 12 turns
        // unless there are less than 3 players remaining.
        // We write the first turn for you to get you started.
        //Assigning decks for

        //Shuffle decks




        for (int i = 0; i < decks.length; i++) {
            for (int j = 0; j < decks[i].length; j++) {
                players[i].addCard(decks[i][j]); // Assign the deck to the player
            }
        }

        Weather[] temp = decks[0];
        for (int i = 1; i < decks.length; i++) {
            decks[i - 1] = decks[i];
        }
        decks[decks.length - 1] = temp;

        for (int k = 0; k < round; k++) {
            shuffleTideCard();
        }


        for (int j = 0; j < players.length; j++)
            players[j].calcLifePreservers();

        //run turns


        for (int i = 0; i < 12; i++) {
            int turn = i + 1;
            System.out.println("Turn " + turn + " starts.");
            oneTurn();

            if (players[0].isEliminated()) {
                System.out.println("Player 0 is eliminated");
                break;
            }

            int alive = players.length;
            for (int j = 0; j < players.length; j++) {
                if (players[j].isEliminated()) {
                    System.out.println("Player " + j + " is eliminated.");
                    alive--;
                }
            }
            if (alive < 3) {
                System.out.println("Less than three players remain, this turn ends.");
                break;
            }
        }

        //Finding lowest tide number amongst
        int lowestTide = players[0].getTideLevel();
        for (int i = 1; i < players.length; i++)
            if (players[i].getTideLevel() < lowestTide)
                lowestTide = players[i].getTideLevel();


        //Tallying score
        for (int i = 0; i < players.length; i++) {
            if (players[i].getLifePreservers() > 0 && players[i].getTideLevel() == lowestTide) {
                int tempScore = players[i].getScore() + players[i].getLifePreservers() + 1;
                players[i].setScore(tempScore);
            } else if (players[i].getLifePreservers() > 0) {
                int tempScore = players[i].getScore() + players[i].getLifePreservers();
                players[i].setScore(tempScore);
            } else if (players[i].isEliminated()) {
                int tempScore = players[i].getScore() - 1;
                players[i].setScore(tempScore);
            }

        }
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName() + " has " + players[i].getScore() + " points.");
        }

        //Prep for new Round
        for (int i = 0; i < players.length; i++) {
            players[i].resetForNewRound();
        }


    }


    private int draw() {
        // TODO
        int output = tide[0];
        int[] newTide = new int[tide.length];
        for (int i = 0; i < 0; i++) {
            newTide[i] = tide[i];
        }
        for (int i = 0; i < TOTAL_TIDE_CARDS - 1; i++) {
            newTide[i] = tide[i + 1];
        }

        tide = newTide;
        return output;


    }


    private void oneTurn() {
        // TODO

        int[] choiceIndex = new int[players.length];
        Weather[] cardsPicked = new Weather[choiceIndex.length];

        //Getting Tide cards
        int tide1 = draw();
        int tide2 = draw();
        int higherTide = 0;
        int lowerTide = 0;
        if (tide1 > tide2) {
            higherTide = tide1;
            lowerTide = tide2;
        } else if (tide2 > tide1) {
            higherTide = tide2;
            lowerTide = tide1;
        } else {
            higherTide = tide1;
            lowerTide = tide2;
        }

        System.out.println("The higher tide is " + higherTide + " and the lower tide is " + lowerTide);
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isEliminated())
                System.out.println(players[i].getName() + " has tide level " + players[i].getTideLevel() + ", " + players[i].getLifePreservers() + " life preservers");
            else
                System.out.println(players[i].getName() + " has tide level " + players[i].getTideLevel() + ", " + 0 + " life preservers");
        }
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isEliminated()) {
                if (players[i].isHuman()) {
                    Scanner in = new Scanner(System.in);
                    players[i].printHand();
                    do {
                        System.out.println("Player " + players[i].getName() + ", you have " + players[i].getLifePreservers() + "  life preservers, please select a card to play:");
                        choiceIndex[i] = in.nextInt();
                        in.nextLine();
                    }
                    while (choiceIndex[i] >= players[i].getCardCount() || choiceIndex[i] < 0);
                    cardsPicked[i] = players[i].playCard(choiceIndex[i]);
                } else {
                    cardsPicked[i] = players[i].playRandomCard();
                }
            } else
                cardsPicked[i] = new Weather(0);
        }


        players[findIndexWithBiggestCard(cardsPicked)].setTideLevel(higherTide);
        players[findIndexWithSecondBiggestCard(cardsPicked)].setTideLevel(lowerTide);

        drownPlayer();
    }


    private int findIndexWithBiggestCard(Weather[] cards) {
        // TODO
        int biggestCard = cards[0].getValue();
        for (int i = 1; i < cards.length; i++) {

            if (biggestCard < cards[i].getValue())
                biggestCard = cards[i].getValue();
        }
        int indexBigCard = -1;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getValue() == biggestCard) {
                indexBigCard = i;
                break;
            }
        }
        return indexBigCard;
    }


    private int findIndexWithSecondBiggestCard(Weather[] cards) {
        // TODO
        int biggestCard = cards[0].getValue();
        int secondBiggestCard = 0;

        for (int i = 1; i < cards.length; i++) {
            if (cards[i].getValue() > biggestCard) {
                secondBiggestCard = biggestCard;
                biggestCard = cards[i].getValue();
            } else if (cards[i].getValue() < biggestCard && cards[i].getValue() > secondBiggestCard) {
                secondBiggestCard = cards[i].getValue();
            }
        }
        int secondBiggestCardIndex = -1;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getValue() == secondBiggestCard) {
                secondBiggestCardIndex = i;
                break;
            }
        }
        return secondBiggestCardIndex;
    }


    private void drownPlayer() {
        // TODO
        int highestTideLevel = players[0].getTideLevel();
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isEliminated()) {
                highestTideLevel = players[i].getTideLevel();
                break;
            }
        }
        for (int i = 1; i < players.length; i++) {
            if (!players[i].isEliminated() && players[i].getTideLevel() > highestTideLevel)
                highestTideLevel = players[i].getTideLevel();
        }

        for (int i = 0; i < players.length; i++) {
            if (!players[i].isEliminated() && players[i].getTideLevel() == highestTideLevel) {
                players[i].decreaseLifePreservers();
            }
        }
    }
}