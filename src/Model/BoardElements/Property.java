package Model.BoardElements;

import Events.PropertyEvent;
import Listener.BoardView;
import Listener.BuyableLocation;
import Listener.PropertyListener;
import Model.BoardModel;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Cory Helm
 * Class MVC.Property that defines a property. Extends MVC.Location
 */
public class Property extends Location implements BuyableLocation {
    private final static int AI_RANDOM_CHOICE_BUY = 0;

    private final List<Integer> rentCosts;
    private int numOfHouses;
    private final int maxNumberOfHouses;
    private final BoardModel.Color color;
    private Player owner;
    private final int numberOfColor;
    private final int costPerHouse;
    private final List<PropertyListener> propertyListeners;
    private int oldNumOfHouses;

    /**
     * Constructor for Property
     * @param name String, the name
     * @param cost Integer, the cost
     * @param costPerHouse Integer, the cost per house
     * @param initialRent Integer, the initial rent
     * @param house1Rent Integer, the rent with 1 house
     * @param house2Rent Integer, the rent with 2 houses
     * @param house3Rent Integer, the rent with 3 houses
     * @param house4Rent Integer, the rent with 4 houses
     * @param hotelRent Integer, the house with a hotel
     * @param color BoardModel.Color, the color of property
     * @param numOfColors Integer, then number of colors for this property
     */
    public Property(String name, int cost, int costPerHouse, int initialRent, int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, BoardModel.Color color, int numOfColors){
        super(cost, name);
        this.rentCosts = new ArrayList<>(){{
           add(initialRent);
           add(house1Rent);
           add(house2Rent);
           add(house3Rent);
           add(house4Rent);
           add(hotelRent);
        }};
        this.propertyListeners = new ArrayList<>();
        this.numOfHouses = 0;
        this.oldNumOfHouses = 0;
        this.maxNumberOfHouses = 5;
        this.color = color;
        this.owner = null;
        this.numberOfColor = numOfColors;
        this.costPerHouse = costPerHouse;
    }

    /**
     * set the owner attribute of a property
     * @param p Player, the player
     */
    public void setOwner(Player p){
        this.owner = p;
    }

    /**
     * allows players to buy property and adds property to owner
     * @param p MVC.Player
     * @return false or true
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() <= this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        this.owner.addColorToProperty(this.color, 1);
        return false;
    }

    /**
     * resets the owner on a property
     */
    @Override
    public void resetOwner() {
        this.numOfHouses = 0;
        this.owner = null;
    }

    /**
     * adds a listener to the property
     * @param view Listener.BoardView
     */
    @Override
    public void addListener(BoardView view) {
        this.propertyListeners.add(view);
    }

    /**
     * Setter for houses.
     * @param numOfHouses An integer number of houses
     */
    public void setNumOfHouses(int numOfHouses) {
        this.numOfHouses = numOfHouses;
    }

    /**
     * adds house to a property based on how many the player wants
     * @param add Integer number of houses added
     * @return boolean, true if house added otherwise false
     */
    public boolean addHouse(int add){
        if (this.numOfHouses+add <= this.maxNumberOfHouses && this.owner.getMoneyAmount() >= add*this.costPerHouse) {
            this.oldNumOfHouses = this.numOfHouses;
            this.numOfHouses += add;
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() - (add*this.costPerHouse));
            return true;
        }
        return false;
    }

    /**
     * Sell houses on current property
     * @param val Integer, number of houses
     */
    public void sellHouse(int val){
        this.numOfHouses -= val;
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() + val*this.costPerHouse);
    }

    /**
     * find the rent of the current property based off houses and hotels
     * @return the rent of a property
     */
    public int getRent(){
        if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
            return this.rentCosts.get(this.numOfHouses)*2;
        return this.rentCosts.get(this.numOfHouses);
    }

    /**
     * handle location owned by another user
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
        int doubleAmount = 1;
        if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
            doubleAmount = 2;
        int landedPlayerMoney = p.getMoneyAmount();
        int rentCost = this.getRentCost(this.numOfHouses) * doubleAmount;
        if (landedPlayerMoney <= rentCost) {
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
        } else {
            p.setMoneyAmount(p.getMoneyAmount() - rentCost);
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + rentCost);
        }

        for (PropertyListener listener : this.propertyListeners) {
            listener.propertyRent(new PropertyEvent(this, p, rentCost));
        }
    }

    /**
     * handle Location not owned functionality for the user
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityUser(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            for (PropertyListener listener : this.propertyListeners) {
                if (listener.propertyNoOwner(new PropertyEvent(this, p, 0))) {
                    if (!this.buy(p)) {
                        listener.announcePurchaseProperty(new PropertyEvent(this, p, 0));
                    }
                }
            }
        }
    }

    /**
     * handle location owned by the current player landing on it
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn){
        for (PropertyListener listener : this.propertyListeners) {
            listener.propertyOwned(new PropertyEvent(this, p, 0));
        }
    }


    /**
     * handles functionality for when an AI player lands on a property
     * @param p Player, the current player
     * @param totalDiceRoll Integer, the total Dice Roll
     * @param currentTurn Integer, the current player turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityAI(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            if (p.hasColor(this.color)){
                this.buy(p);
                for (PropertyListener listener : this.propertyListeners){
                    listener.announcePurchaseProperty(new PropertyEvent(this, p, 0));
                }
            }
            else{
                Random r = new Random();
                int choice = r.nextInt(2);
                if (choice == AI_RANDOM_CHOICE_BUY){
                    this.buy(p);
                    for (PropertyListener listener : this.propertyListeners){
                        listener.announcePurchaseProperty(new PropertyEvent(this, p, 0));
                    }
                }
            }
        }
    }

    /**
     * handles functionality for when an human player(User) lands on a property
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current player turn
     */
    @Override
    public void handleLocationNotOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
        if (p instanceof AI) {
            handleLocationNotOwnedFunctionalityAI(p,totalDiceRoll,currentTurn);
        } else {
            handleLocationNotOwnedFunctionalityUser(p, totalDiceRoll, currentTurn);
        }
    }

    /**
     * Method for property functionality
     * @return A boolean
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null) {
            handleLocationNotOwnedFunctionality(p, totalDiceRoll, currentTurn);
            return true;
        } else {
            if (this.owner.equals(p)) {
                handleLocationOwnedByPlayerFunctionality(p, totalDiceRoll, currentTurn);
                return true;
            }
            handleLocationOwnedFunctionality(p, totalDiceRoll, currentTurn);
        }
        return false;
    }

    /**
     * Getter for getting cost per house
     * @return An integer cost per house
     */
    public int getCostPerHouse() {
        return this.costPerHouse;
    }

    /**
     * Getter for max number of houses
     * @return An integer max number of houses
     */
    public int getMaxNumberOfHouses() {
        return this.maxNumberOfHouses;
    }

    /**
     * Getter for number of houses
     * @return An integer number of houses
     */
    public int getNumOfHouses() {
        return this.numOfHouses;
    }

    /**
     * Getter for a color
     * @return A MVC.BoardModel color
     */
    public BoardModel.Color getColor() {
        return this.color;
    }

    /**
     * Getter for getting the number of colours on the board
     * @return An integer numberOfColor
     */
    public int getNumberOfColor() {
        return this.numberOfColor;
    }

    /**
     * Getter for getting the property owner
     * @return A MVC.Player owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Method for getting the rent cost from a list of rent costs
     * @param index An integer index
     * @return An integer rent cost
     */
    public int getRentCost(int index){
        if (index < 0 || index >= this.rentCosts.size()) return -1;
        return this.rentCosts.get(index);
    }

    /**
     * Getter for getting the old number of houses prior to buying a house
     * @return An integer oldNumOfHouses
     */
    public int getOldNumOfHouses() {
        return this.oldNumOfHouses;
    }

    /**
     * send info to a string
     * @param p MVC.Player
     * @return property name, the cost or how who owns it and how much the player owes them
     */
    public String toString(Player p){
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.rentCosts.get(numOfHouses) + "} \n" + p.getPlayerName() + " will lose money now";
    }

    /**
     * get the number of houses a current property can be bought by the Player owner
     * @return Integer, the total number of houses
     */
    public int numberOfHousesCanBuy() {
        int total = 0;
        int totalHousesToBuy = this.maxNumberOfHouses - this.numOfHouses;
        for (int i = 0; i < totalHousesToBuy; i++){
            if ((this.owner.getMoneyAmount() - (i+1)*this.costPerHouse) >= 0){
                total++;
            }
        }
        return total;
    }

    /**
     * setter for oldNumofHouses
     * @param oldNumOfHouses, the old num of houses
     */
    public void setOldNumOfHouses(int oldNumOfHouses) {
        this.oldNumOfHouses = oldNumOfHouses;
    }

    @Override
    /**
     * returns a xml representation of property as a string
     */
    public String toXML(){
        String str = "\t\t\t\t<Property>\n";
        str += "\t\t\t\t\t<name>" + this.getName() + "</name>\n";
        str += "\t\t\t\t\t<numberOfHouses>" + this.numOfHouses + "</numberOfHouses>\n";
        str += "\t\t\t\t\t<oldNumOfHouses>" + this.oldNumOfHouses + "</oldNumOfHouses>\n";
        str += "\t\t\t\t</Property>\n";
        return str;
    }

    /**
     * Create new property from node data
     * @param node, node containing the data
     * @return the newly created location
     */
    public static Location createNewProperty(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        int costPerHouse = Integer.parseInt(e.getElementsByTagName("costPerHouse").item(0).getTextContent());
        int initialRent = Integer.parseInt(e.getElementsByTagName("initialRent").item(0).getTextContent());
        int house1Rent = Integer.parseInt(e.getElementsByTagName("house1Rent").item(0).getTextContent());
        int house2Rent = Integer.parseInt(e.getElementsByTagName("house2Rent").item(0).getTextContent());
        int house3Rent = Integer.parseInt(e.getElementsByTagName("house3Rent").item(0).getTextContent());
        int house4Rent = Integer.parseInt(e.getElementsByTagName("house4Rent").item(0).getTextContent());
        int hotelRent = Integer.parseInt(e.getElementsByTagName("hotelRent").item(0).getTextContent());
        BoardModel.Color color = BoardModel.Color.valueOf(e.getElementsByTagName("color").item(0).getTextContent());
        int numOfColors = Integer.parseInt(e.getElementsByTagName("numOfColors").item(0).getTextContent());
        return new Property(name, cost, costPerHouse, initialRent, house1Rent, house2Rent, house3Rent, house4Rent, hotelRent, color, numOfColors);
    }

    /**
     * get the List of rent cost
     * @return List<Integer>
     */
    public List<Integer> getRentCosts() {
        return this.rentCosts;
    }

    /**
     * getter for property listeners
     * @return List<PropertyListener>
     */
    public List<PropertyListener> getPropertyListeners() {
        return this.propertyListeners;
    }

    /**
     * Overrides java object equals() method
     * @param obj Object, the object to check
     * @return boolean, true iff the comparison and the object is identical, otherwise false
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }
        Property place = (Property) obj;
        return this.getName().equals(place.getName()) && this.rentCosts == place.getRentCosts()
                && this.numOfHouses == place.getNumOfHouses()
                && this.maxNumberOfHouses == place.getMaxNumberOfHouses() && this.color == place.getColor()
                && this.owner == place.getOwner() && this.numberOfColor == place.getNumberOfColor() && this.costPerHouse == place.getCostPerHouse()
                && this.oldNumOfHouses == place.getOldNumOfHouses() && this.propertyListeners == place.getPropertyListeners();
    }
}
