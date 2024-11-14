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
 * @author Tran Quang Vu
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
     * Hàm khởi tạo cho Tài sản
     * @param name String, tên tài sản
     * @param cost Integer, giá trị tài sản
     * @param costPerHouse Integer, chi phí mỗi ngôi nhà
     * @param initialRent Integer, tiền thuê ban đầu
     * @param house1Rent Integer, tiền thuê với 1 ngôi nhà
     * @param house2Rent Integer, tiền thuê với 2 ngôi nhà
     * @param house3Rent Integer, tiền thuê với 3 ngôi nhà
     * @param house4Rent Integer, tiền thuê với 4 ngôi nhà
     * @param hotelRent Integer, tiền thuê với 1 khách sạn
     * @param color BoardModel.Color, màu sắc của tài sản
     * @param numOfColors Integer, số lượng màu sắc của tài sản này
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
     * Thiết lập thuộc tính chủ sở hữu của tài sản
     * @param p Player, người chơi
     */

    public void setOwner(Player p){
        this.owner = p;
    }

    /**
     * Cho phép người chơi mua tài sản và thêm tài sản vào chủ sở hữu
     * @param p MVC.Player
     * @return false hoặc true
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
     * Đặt lại chủ sở hữu của tài sản
     */

    @Override
    public void resetOwner() {
        this.numOfHouses = 0;
        this.owner = null;
    }

    /**
     * Thêm một listener vào tài sản
     * @param view Listener.BoardView
     */

    @Override
    public void addListener(BoardView view) {
        this.propertyListeners.add(view);
    }

    /**
     * Phương thức setter cho số lượng nhà.
     * @param numOfHouses Số lượng nhà (kiểu integer)
     */

    public void setNumOfHouses(int numOfHouses) {
        this.numOfHouses = numOfHouses;
    }

    /**
     * Thêm nhà vào tài sản dựa trên số lượng mà người chơi muốn
     * @param add Số lượng nhà thêm vào (kiểu integer)
     * @return boolean, trả về true nếu nhà đã được thêm, nếu không trả về false
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
     * Bán nhà trên tài sản hiện tại
     * @param val Số lượng nhà (kiểu integer)
     */
    public void sellHouse(int val){
        this.numOfHouses -= val;
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() + val*this.costPerHouse);
    }

    /**
     * Tìm kiếm tiền thuê của tài sản hiện tại dựa trên số lượng nhà và khách sạn
     * @return tiền thuê của tài sản
     */

    public int getRent(){
        if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
            return this.rentCosts.get(this.numOfHouses)*2;
        return this.rentCosts.get(this.numOfHouses);
    }

    /**
     * Xử lý khi vị trí thuộc sở hữu của người chơi khác
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số điểm gieo xúc xắc
     * @param currentTurn Integer, lượt chơi hiện tại
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
     * Xử lý chức năng khi vị trí chưa được sở hữu đối với người chơi
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số điểm gieo xúc xắc
     * @param currentTurn Integer, lượt chơi hiện tại
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
     * Xử lý chức năng khi vị trí đã được sở hữu bởi người chơi hiện tại và người chơi đó hạ cánh lên
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số điểm gieo xúc xắc
     * @param currentTurn Integer, lượt chơi hiện tại
     */

    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn){
        for (PropertyListener listener : this.propertyListeners) {
            listener.propertyOwned(new PropertyEvent(this, p, 0));
        }
    }


    /**
     * Xử lý chức năng khi một người chơi AI hạ cánh lên tài sản
     * @param p Player, người chơi hiện tại
     * @param totalDiceRoll Integer, tổng số điểm gieo xúc xắc
     * @param currentTurn Integer, lượt chơi hiện tại
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
     * Xử lý chức năng khi một người chơi (Người dùng) hạ cánh lên tài sản
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số điểm gieo xúc xắc
     * @param currentTurn Integer, lượt chơi hiện tại
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
     * Phương thức cho chức năng của tài sản
     * @return Một giá trị boolean
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
     * Phương thức lấy giá của mỗi ngôi nhà
     * @return Một số nguyên là giá của mỗi ngôi nhà
     */

    public int getCostPerHouse() {
        return this.costPerHouse;
    }

    /**
     * Phương thức lấy số lượng tối đa của các ngôi nhà
     * @return Một số nguyên là số lượng tối đa các ngôi nhà
     */

    public int getMaxNumberOfHouses() {
        return this.maxNumberOfHouses;
    }

    /**
     * Phương thức lấy số lượng ngôi nhà
     * @return Một số nguyên là số lượng ngôi nhà
     */

    public int getNumOfHouses() {
        return this.numOfHouses;
    }

    /**
     * Phương thức lấy màu sắc
     * @return Một màu sắc thuộc MVC.BoardModel
     */

    public BoardModel.Color getColor() {
        return this.color;
    }

    /**
     * Phương thức lấy số lượng màu sắc trên bảng
     * @return Một số nguyên numberOfColor
     */

    public int getNumberOfColor() {
        return this.numberOfColor;
    }

    /**
     * Phương thức lấy chủ sở hữu của tài sản
     * @return Một đối tượng MVC.Player owner
     */

    public Player getOwner() {
        return this.owner;
    }

    /**
     * Phương thức để lấy chi phí thuê từ danh sách các chi phí thuê
     * @param index Một chỉ số kiểu integer
     * @return Một chi phí thuê kiểu integer
     */

    public int getRentCost(int index){
        if (index < 0 || index >= this.rentCosts.size()) return -1;
        return this.rentCosts.get(index);
    }

    /**
     * Phương thức lấy số lượng nhà cũ trước khi mua nhà
     * @return Một số nguyên oldNumOfHouses
     */

    public int getOldNumOfHouses() {
        return this.oldNumOfHouses;
    }

    /**
     * Gửi thông tin dưới dạng chuỗi
     * @param p MVC.Player
     * @return tên tài sản, chi phí hoặc ai sở hữu nó và người chơi nợ bao nhiêu
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
     * Lấy số lượng nhà mà tài sản hiện tại có thể được mua bởi chủ sở hữu người chơi
     * @return Integer, tổng số lượng nhà
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
     * setter cho oldNumOfHouses
     * @param oldNumOfHouses, số lượng nhà cũ
     */
    public void setOldNumOfHouses(int oldNumOfHouses) {
        this.oldNumOfHouses = oldNumOfHouses;
    }

    @Override
    /**
     * trả về biểu diễn XML của tài sản dưới dạng chuỗi
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
     * Tạo tài sản mới từ dữ liệu của node
     * @param node, node chứa dữ liệu
     * @return vị trí mới được tạo
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
     * Lấy danh sách chi phí thuê
     * @return List<Integer>
     */

    public List<Integer> getRentCosts() {
        return this.rentCosts;
    }

    /**
     * Lấy danh sách các listener của tài sản
     * @return List<PropertyListener>
     */

    public List<PropertyListener> getPropertyListeners() {
        return this.propertyListeners;
    }

    /**
     * Ghi đè phương thức equals() của đối tượng java
     * @param obj Object, đối tượng cần kiểm tra
     * @return boolean, trả về true nếu đối tượng và phép so sánh là giống nhau, ngược lại trả về false
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
