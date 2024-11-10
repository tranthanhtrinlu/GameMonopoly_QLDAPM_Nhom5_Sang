package Model.GamePlayer;
import Model.BoardElements.RailRoad;
import Model.BoardElements.Utility;
import Model.BoardModel;
import Model.BoardElements.Location;
import Model.BoardElements.Property;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Lớp Player định nghĩa các thuộc tính của một người chơi trong trò chơi Monopoly.
 */
public abstract class Player{
    private final String playerName;
    private int moneyAmount;
    private boolean inJail;
    private int turnsInJail;
    private int position;
    private String currLocation;
    private int numOfRailroads;
    private int numOfUtilities;
    private boolean out;
    private HashMap<BoardModel.Color, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;


    public Player(String name, int moneyAmount){
        this(name, moneyAmount, false, 0, false, 0, BoardModel.STARTING_POSITION, 0,0);
    }

    public Player(String playerName, int moneyAmount, boolean out,  int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities){
        this.playerName = playerName;
        this.moneyAmount = moneyAmount;
        this.out = out;
        this.position = position;
        this.inJail = inJail;
        this.turnsInJail = turnsInJail;
        this.currLocation = currLocation;
        this.numOfRailroads = numOfRailroads;
        this.numOfUtilities = numOfUtilities;
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
    }


    public void setOut(boolean out) {
        this.out = out;
    }


    public boolean getOut(){
        return this.out;
    }


    public void setPosition(int position){
        this.position = position;
    }


    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    /**
     * Di chuyển người chơi trên bàn cờ
     * @param combinedRolls Tổng số xúc xắc đã tung
     * @return True nếu người chơi được di chuyển, ngược lại là false
     */
    public boolean movePlayer(int combinedRolls){
        this.position += combinedRolls;
        if (this.position > BoardModel.SIZE_OF_BOARD){
            this.moneyAmount += BoardModel.GO_MONEY;
            this.position -= BoardModel.SIZE_OF_BOARD;
            return true;
        }
        return false;
    }

    /**
     * Tính toán vị trí mới của người chơi dựa trên vị trí hiện tại và tổng số xúc xắc
     * @param pos Integer, vị trí giả định
     * @param sum Integer, tổng số xúc xắc
     * @return Integer, vị trí mới
     */
    public int getSumOfMovement(int pos, int sum){
        pos += sum;
        if (pos > BoardModel.SIZE_OF_BOARD){
            pos -= BoardModel.SIZE_OF_BOARD;
        }
        return pos;
    }

     /**
     * Đặt trạng thái của người chơi có vào tù không
     * @param inJail boolean có vào tù hay không
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        if (inJail)
            this.turnsInJail = 3;
        else
            this.turnsInJail = 0;
    }

    /**
     * Lấy số lượt còn lại trong tù của người chơi
     * @return Integer, số lượt
     */
    public int getTurnsInJail(){
        return this.turnsInJail;
    }

      /**
     * Đặt số lượt còn lại trong tù của người chơi
     * @param val Integer, số lượt
     */
    public void setTurnsInJail(int val){
        this.turnsInJail = val;
    }

   /**
     * Kiểm tra xem người chơi có ở trong tù không
     * @return boolean, trạng thái có ở tù
     */
    public boolean getInJail(){
        return this.inJail;
    }


    public String getPlayerName() {
        return this.playerName;
    }


    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    
    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }


    public void addNumOfRailroads(){
        this.numOfRailroads++;
    }


    public int getNumOfRailroads() {
        return this.numOfRailroads;
    }


    public int getNumOfUtilities(){
        return this.numOfUtilities;
    }

    /**
     * Tăng số tiện ích mà người chơi sở hữu
     */
    public void addNumOfUtilities(){
        this.numOfUtilities++;
    }


    public int getPosition() {
        return this.position;
    }

     /**
     * Ghi đè phương thức equals() của Java để kiểm tra hai đối tượng Player có giống nhau không
     * @param obj Đối tượng để so sánh
     * @return boolean, true nếu đối tượng giống nhau, ngược lại là false
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }
        User player = (User) obj;
        return this.playerName.equals(player.getPlayerName()) && this.moneyAmount == player.getMoneyAmount()
                && this.ownedPropertiesBasedOnColors == player.getOwnedPropertiesBasedOnColors()
                && this.position == player.getPosition() && this.ownedProperties == player.getOwnedProperties()
                && this.inJail == player.getInJail() && this.currLocation.equals(player.getCurrLocation()) && this.numOfRailroads == player.getNumOfRailroads()
                && this.numOfUtilities == player.getNumOfUtilities() && this.turnsInJail == player.getTurnsInJail() && this.out == player.getOut();
    }

    /**
     * Lấy vị trí hiện tại của người chơi (tên vị trí)
     * @return Chuỗi, vị trí hiện tại
     */
    public String getCurrLocation() {
        return this.currLocation;
    }


    public String toString(){
        return "Player: " + this.playerName + "\n{\n" +
                "Money: $" + this.moneyAmount + "\nLocation: " + this.currLocation + "\nOwned Properties: " + this.printOwnedProperties() + "\n}";
    }

     /**
     * Kiểm tra xem người chơi có đủ tiền để trả phí ra tù không
     * @return True nếu tiền của người chơi lớn hơn 50, ngược lại là false
     */
    public boolean payJail() {
        return this.moneyAmount - 50 > 0;

    }

   
   
    public String printOwnedProperties(){
        StringBuilder s = new StringBuilder();
        for(Location location : this.ownedProperties){
            s.append(location.getName()).append(", ");
        }
        return s.toString();
    }

     /**
     * Lấy danh sách các tài sản là bất động sản mà người chơi sở hữu
     * @return Danh sách các đối tượng Property
     */
    public List<Property> getEstatePropertiesOfPlayer(){
        List<Property> properties = new ArrayList<>();
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                properties.add((Property) ownedProperty);
            }
        }
        return properties;
    }

    /**
     * Lấy tài sản dựa trên tên của nó
     * @param name Tên của tài sản
     * @return Đối tượng Property nếu người chơi sở hữu, ngược lại là null
     */
    public Property getPropertyByName(String name){
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty.getName().equals(name)){
                return (Property) ownedProperty;
            }
        }
        return null;
    }

   /**
     * Kiểm tra người chơi có sở hữu tài sản với màu nhất định không
     * @param color Màu của tài sản cần kiểm tra
     * @return True nếu người chơi sở hữu tài sản màu đó, ngược lại là false
     */
    public boolean hasColor(BoardModel.Color color){
        return this.ownedPropertiesBasedOnColors.containsKey(color);
    }


    /**
     * Đếm số lượng tài sản mà người chơi có thể mua nhà
     * @return Số tài sản có thể mua nhà
     */
    public int numberOfEstatePropertiesThatPlayerCanBuyHousesFor(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                //if (this.ownedPropertiesBasedOnColors.get(((Property) ownedProperty).getColor()) == ((Property) ownedProperty).getNumberOfColor())
                if (((Property) ownedProperty).getNumOfHouses() != ((Property) ownedProperty).getMaxNumberOfHouses()){
                    if (((Property) ownedProperty).numberOfHousesCanBuy() > 0){
                        k++;
                    }
                }
            }
        }
        return k;
    }


    /**
     * Đếm số lượng tài sản mà người chơi đã xây nhà trên đó
     * @return Số tài sản có nhà
     */
    public int numberOfEstatePropertiesWithHouses(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                if (((Property) ownedProperty).getNumOfHouses() > 0)
                    k++;
            }
        }
        return k;
    }

    /**
     * Lấy số lượng tài sản mà người chơi sở hữu
     * @return Integer, số lượng tài sản
     */
    public int getNumOfProperties(){
        return this.ownedProperties.size();
    }

    /**
     * Lấy tài sản dựa trên chỉ số trong danh sách
     * @param i Chỉ số của tài sản
     * @return Đối tượng Location, tài sản tại chỉ số đó
     */
    public Location getPropertyByIndex(int i){
        return this.ownedProperties.get(i);
    }

    /**
     * Kiểm tra người chơi sở hữu bao nhiêu tài sản với một màu nhất định
     * @param color Màu của tài sản cần kiểm tra
     * @param numOfColor Số lượng tài sản màu đó
     * @return True nếu số lượng tài sản màu đó đúng với yêu cầu, ngược lại là false
     */
    public boolean numberOfColoredPropertiesOwned(BoardModel.Color color, int numOfColor){
        return this.ownedPropertiesBasedOnColors.get(color) == numOfColor;
    }

    /**
     * Lấy bản đồ các tài sản sở hữu theo màu
     * @return HashMap màu và số lượng tài sản sở hữu
     */
    public HashMap<BoardModel.Color, Integer> getOwnedPropertiesBasedOnColors() {
        return ownedPropertiesBasedOnColors;
    }

    
    /**
     * Thêm tài sản vào danh sách tài sản sở hữu của người chơi
     * @param p Đối tượng Location, tài sản cần thêm
     */
    public void addProperty(Location p){
        this.ownedProperties.add(p);
    }


     /**
     * Thêm mã màu cho tài sản nếu người chơi sở hữu tất cả các tài sản màu đó (để tăng tiền thuê)
     * @param color Màu của tài sản
     * @param add Số lượng tài sản màu đó cần thêm
     */
    public void addColorToProperty(BoardModel.Color color, int add){
        if (this.ownedPropertiesBasedOnColors.containsKey(color)){
            int oldVal = this.ownedPropertiesBasedOnColors.get(color);
            this.ownedPropertiesBasedOnColors.replace(color, oldVal + add);
            return;
        }
        this.ownedPropertiesBasedOnColors.put(color, add);
    }

   /**
     * Nếu người chơi phá sản, đặt lại tất cả tài sản mà họ sở hữu
     */
    public void bankrupted(){
        for (Location location : this.ownedProperties){
            location.resetOwner();
        }
        this.ownedProperties.clear();
        this.ownedPropertiesBasedOnColors.clear();
    }

      /**
     * Lấy danh sách các tài sản mà người chơi sở hữu
     * @return Danh sách các đối tượng Location
     */
    public List<Location> getOwnedProperties() {
        return this.ownedProperties;
    }

      /**
     * Trả về biểu diễn XML của người chơi dưới dạng chuỗi
     * @return Chuỗi biểu diễn XML của người chơi
     */
    public String toXML(String kindOfPlayer){
        String currLocation = this.currLocation;
        if (currLocation.equals(RailRoad.INVALID_PARSE))
            currLocation = RailRoad.VALID_PARSE;
        String str = "";
        str += "\t<player>\n";
        str += "\t\t<typeOfPlayer>" + kindOfPlayer + "</typeOfPlayer>\n";
        str += "\t\t<moneyAmount>" + this.moneyAmount + "</moneyAmount>\n";
        str += "\t\t<playerName>" + this.playerName + "</playerName>\n";
        str += "\t\t<inJail>" + this.inJail + "</inJail>\n";
        str += "\t\t<turnsInJail>" + this.turnsInJail + "</turnsInJail>\n";
        str += "\t\t<position>" + this.position + "</position>\n";
        str += "\t\t<currLocation>" + currLocation + "</currLocation>\n";
        str += "\t\t<numOfRailroads>" + this.numOfRailroads + "</numOfRailroads>\n";
        str += "\t\t<numOfUtilities>" + this.numOfUtilities + "</numOfUtilities>\n";
        str += "\t\t<out>" + this.out + "</out>\n";
        // Properties
        str += "\t\t<ownedProperties>\n";
        for(Location l : this.ownedProperties){
            str += "\t\t\t<Location>\n";
            if (l instanceof Property){
                str += ((Property) l).toXML();
            }else if (l instanceof Utility){
                str += ((Utility) l).toXML();
            }else if (l instanceof RailRoad){
                str += ((RailRoad) l).toXML();
            }
            str += "\t\t\t</Location>\n";
        }
        str += "\t\t</ownedProperties>\n";
                // Các tài sản có màu sắc

        str += "\t\t<coloredOwnedProperties>\n";
        for(BoardModel.Color color : this.ownedPropertiesBasedOnColors.keySet()){
            str += "\t\t\t<index>\n";
            str += "\t\t\t\t<color>" + color + "</color>\n";
            str += "\t\t\t\t<number>" + this.ownedPropertiesBasedOnColors.get(color) + "</number>\n";
            str += "\t\t\t</index>\n";
        }
        str += "\t\t</coloredOwnedProperties>\n";
        str += "\t</player>\n";
        return str;
    }

      /**
     * Tạo một đối tượng Player từ dữ liệu đã lưu
     * @param playerElement Đối tượng Element chứa thông tin của người chơi
     * @return Đối tượng Player được tạo
     */
    public static Player createPlayer(Element playerElement){
        String kindOfPlayer = playerElement.getElementsByTagName("typeOfPlayer").item(0).getTextContent();
        int moneyAmount = Integer.parseInt(playerElement.getElementsByTagName("moneyAmount").item(0).getTextContent());
        String name = playerElement.getElementsByTagName("playerName").item(0).getTextContent();
        boolean inJail = Boolean.parseBoolean(playerElement.getElementsByTagName("inJail").item(0).getTextContent());
        int turnsInJail = Integer.parseInt(playerElement.getElementsByTagName("turnsInJail").item(0).getTextContent());
        int position = Integer.parseInt(playerElement.getElementsByTagName("position").item(0).getTextContent());
        String currLocation = playerElement.getElementsByTagName("currLocation").item(0).getTextContent();
        int numOfRailroads = Integer.parseInt(playerElement.getElementsByTagName("numOfRailroads").item(0).getTextContent());
        int numOfUtilities = Integer.parseInt(playerElement.getElementsByTagName("numOfUtilities").item(0).getTextContent());
        boolean out = Boolean.parseBoolean(playerElement.getElementsByTagName("out").item(0).getTextContent());
        if (kindOfPlayer.equals("User"))
            return new User(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
        return new AI(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }

    /**
     * Load properties to player
     * @param nodeProperty, the node containing the properties
     * @param board the list of locations on the board
     */
    private void loadPropertyToPlayer(Node nodeProperty, List<Location> board){
        Element property = (Element) nodeProperty;
        String propertyName = property.getElementsByTagName("name").item(0).getTextContent();
        int numberOfHouses = Integer.parseInt(property.getElementsByTagName("numberOfHouses").item(0).getTextContent());
        int oldNumOfHouses = Integer.parseInt(property.getElementsByTagName("oldNumOfHouses").item(0).getTextContent());
        for (Location l : board){
            if (l instanceof Property){
                if (l.getName().equals(propertyName)){
                    ((Property) l).setNumOfHouses(numberOfHouses);
                    ((Property) l).setOldNumOfHouses(oldNumOfHouses);
                    ((Property) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }
        }
    }

   /**
     * Tải các tài sản của người chơi từ dữ liệu lưu trữ
     * @param nodeProperty Nút chứa thông tin về các tài sản
     * @param board Danh sách các vị trí (Location) trên bảng
     */
    private void loadNonPropertyOwnerShipToPlayer(Node location, List<Location> board){
        String locationName = ((Element) location).getElementsByTagName("name").item(0).getTextContent();
        for (Location l : board) {
            if (l instanceof RailRoad) {
                if (l.getName().equals(locationName)) {
                    ((RailRoad) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }else if (l instanceof Utility) {
                if (l.getName().equals(locationName)) {
                    ((Utility) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }
        }
    }

    /**
     * Tải các tài sản không phải bất động sản mà người chơi sở hữu
     * @param location Nút chứa thông tin về các vị trí không phải bất động sản
     * @param board Danh sách các vị trí trên bảng
     */
    public void parseAddPlayerProperties(Element playerElement, List<Location> board) {
        Element ownedLocations = (Element) playerElement.getElementsByTagName("ownedProperties").item(0);
        NodeList locations = ownedLocations.getElementsByTagName("Location");
        for (int itr = 0; itr < locations.getLength(); itr++) {
            Node node = locations.item(itr).getFirstChild().getNextSibling();
            if (node.getNodeType() == Node.ELEMENT_NODE){
                if (node.getNodeName().equals("Property")){
                    this.loadPropertyToPlayer(node, board);
                }else{
                    this.loadNonPropertyOwnerShipToPlayer(node, board);
                }
            }
        }
    }

      /**
     * Phân tích và thêm các tài sản mà người chơi sở hữu từ dữ liệu lưu trữ
     * @param playerElement Đối tượng Element chứa thông tin về người chơi
     * @param board Danh sách các vị trí trên bảng
     */
    public void parseAddPlayerOwnedColors(Element playerElement) {
        Element ownedColors = (Element) playerElement.getElementsByTagName("coloredOwnedProperties").item(0);
        NodeList colorsOwned = ownedColors.getElementsByTagName("index");
        for (int itr = 0; itr<colorsOwned.getLength(); itr++) {
            Node colorIndex = colorsOwned.item(itr);
            if (colorIndex.getNodeType() == Node.ELEMENT_NODE){
                Element value = (Element) colorIndex;
                BoardModel.Color color = BoardModel.Color.valueOf(value.getElementsByTagName("color").item(0).getTextContent());
                int num = Integer.parseInt(value.getElementsByTagName("number").item(0).getTextContent());
                this.addColorToProperty(color, num);
            }
        }
    }
}
