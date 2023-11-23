import java.util.concurrent.ThreadLocalRandom;


public class Player {

    private static int aiCount = 0;

    private static final int HAND_SIZE = 12;

    private Weather[] cards = new Weather[HAND_SIZE];

    private int remainingLifePreservers;

    private boolean eliminated;

    private int tideLevel;

    private int score;

    private boolean isHuman;

    private String name;


    public String getName() {
        //TODO
        return name;
    }


    public int getTideLevel() {
        //TODO
        return tideLevel;
    }


    public Player(String name) {
        //TODO
        this.name = name;
        isHuman = true;
        eliminated = false;
        remainingLifePreservers = 0;
    }


    public Player() {
        //TODO
        int aiNumber = aiCount + 1;
        this.name = "AI " + aiNumber;
        eliminated = false;
        isHuman = false;
        aiCount++;
        remainingLifePreservers = 0;
    }


    public boolean isHuman() {
        //TODO
        return isHuman;
    }


    public void resetForNewRound() {
        //TODO
        tideLevel = 0;
        eliminated = false;
        cards = new Weather[HAND_SIZE];

    }


    public void setTideLevel(int tideLevel) {
        //TODO
        this.tideLevel = tideLevel;
    }


    public void addCard(Weather c) {
        //TODO
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = c;
                break;
            }
        }
    }


    public Weather playCard(int index) {
        //TODO
        Weather temporaryReturn = cards[index];
        Weather[] tempCards = new Weather[cards.length];
        for (int i = 0; i < index; i++) {
            tempCards[i] = cards[i];
        }
        for (int i = index; i < getCardCount() - 1; i++) {
            tempCards[i] = cards[i + 1];
        }
        cards = tempCards;
        return temporaryReturn;
    }


    public int getCardCount() {
        //TODO
        int count = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null)
                count++;
        }
        return count;
    }


    public Weather playRandomCard() {
        //TODO

        int indexForBot = ThreadLocalRandom.current().nextInt(0, getCardCount());
        Weather temporaryReturn = cards[indexForBot];
        Weather[] tempCards = new Weather[cards.length];
        for (int i = 0; i < indexForBot; i++) {
            tempCards[i] = cards[i];
        }
        for (int i = indexForBot; i < getCardCount() - 1; i++) {
            tempCards[i] = cards[i + 1];
        }
        cards = tempCards;
        return temporaryReturn;
    }


    public void calcLifePreservers() {
        //TODO
        int sum = 0;
        for (int i = 0; i < getCardCount(); i++) {
            if (cards[i].getLifePreserver() > 0)
                sum += cards[i].getLifePreserver();
        }

        remainingLifePreservers = sum / 2;
    }


    public int getLifePreservers() {
        //TODO
        return remainingLifePreservers;
    }


    public void decreaseLifePreservers() {
        //TODO
        if (remainingLifePreservers > -1)
            remainingLifePreservers -= 1;
    }


    public boolean isEliminated() {
        //TODO

        if (remainingLifePreservers < 0) {
            eliminated = true;
        } else
            eliminated = false;
        return eliminated;

    }


    public int getScore() {
        //TODO
        return score;
    }


    public void setScore(int score) {
        //TODO
        this.score = score;
    }


    public void printHand() {
        //TODO
        for (int i = 0; i < getCardCount(); i++) {
            System.out.print(cards[i].toString() + " ");
        }
        System.out.println();
    }
}