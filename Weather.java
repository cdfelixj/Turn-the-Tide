/**
 * A class that represents the weather card.
 * A weather card has a value from 1 to 60 and a life preserver value.
 * No life preserver if the weather value is less than 13 or greater than 48.
 * There is a full life preserver if the weather value is between 25 and 36 (inclusive).
 * There is a half life preserver if the weather value is not the above cases.
 */
public class Weather {

    private static final int NO_LIFE_PRESERVER = 0;


    private static final int HALF_LIFE_PRESERVER = 1;

    private static final int FULL_LIFE_PRESERVER = 2;
    private final int lifePreserver;
    private final int value;


    public Weather(int value) {
        //TODO
        this.value = value;
        if (this.value >= 13 && this.value <= 48) {
            if (this.value >= 25 && this.value <= 36)
                lifePreserver = FULL_LIFE_PRESERVER;
            else
                lifePreserver = HALF_LIFE_PRESERVER;
        } else
            lifePreserver = NO_LIFE_PRESERVER;

    }


    public int getValue() {
        //TODO
        return value;
    }


    public int getLifePreserver() {
        //TODO
        return lifePreserver;
    }


    public String toString() {
        //TODO
        String output = "";
        output += getValue();
        output += "(";
        if (lifePreserver == FULL_LIFE_PRESERVER)
            output += "o";
        else if (lifePreserver == HALF_LIFE_PRESERVER)
            output += "u";
        else
            output += ".";
        output += ")";

        return output;
    }
}