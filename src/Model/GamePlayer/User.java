package Model.GamePlayer;

public class User extends Player {


    public User(String name, int moneyAmount) {
        super(name, moneyAmount);
    }

    public User(String name, int moneyAmount, boolean out, int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities) {
        super(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }
}
