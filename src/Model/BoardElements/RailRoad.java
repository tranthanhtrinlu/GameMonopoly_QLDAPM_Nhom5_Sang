package Model.BoardElements;

import Events.RailRoadEvent;
import Listener.BuyableLocation;
import Listener.RailRoadListener;
import Listener.BoardView;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Tran Quang Vu
 * Lớp Railroad định nghĩa một phần tử đường ray. Kế thừa từ MVC.Location
 */
public class RailRoad extends Location implements BuyableLocation {
    public final static String INVALID_PARSE = "B&O RailRoad";
    public final static String VALID_PARSE = "B&amp;O RailRoad";
    private final static int AI_RANDOM_CHOICE_BUY = 0;
    private List<Integer> payments;
    private Player owner;
    private final List<RailRoadListener> railRoadListener;

    /**
     * Hàm khởi tạo cho mô hình đường ray
     */
    public RailRoad(String name, int cost){
        super(cost, name);
        this.payments = new ArrayList<>(){{
            add(25);
            add(50);
            add(100);
            add(200);
        }};
        this.railRoadListener = new ArrayList<>();
        this.owner = null;
    }

    /**
     * Xử lý chức năng của vị trí nếu vị trí đã có chủ
     * @param p Người chơi, player
     * @param totalDiceRoll Số tổng của các lượt đổ xúc xắc
     * @param currentTurn Lượt hiện tại
     */
    @Override
    public void handleLocationOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
        int landedPlayerMoney = p.getMoneyAmount();
        int payment = this.payments.get(this.owner.getNumOfRailroads());
        if (landedPlayerMoney <= payment){
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
        }
        else{
            p.setMoneyAmount(p.getMoneyAmount() - payment);
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + payment);
        }

        for (RailRoadListener listener : this.railRoadListener){
            listener.railRoadRent(new RailRoadEvent(this, p, payment));
        }
    }

    /**
     * Xử lý chức năng của vị trí nếu vị trí chưa có chủ cho người chơi
     * @param p Người chơi, player
     * @param totalDiceRoll Số tổng của các lượt đổ xúc xắc
     * @param currentTurn Lượt hiện tại
     */
    @Override
    public void handleLocationNotOwnedFunctionalityUser(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() > this.getCost()){
            for (RailRoadListener listener : this.railRoadListener){
                if (listener.railRoadNoOwner(new RailRoadEvent(this, p, 0))){
                    if (!this.buy(p)) {
                        listener.announcePurchaseRailRoad(new RailRoadEvent(this, p, 0));
                    }
                }
            }
        }
    }

    /**
     * Xử lý chức năng của vị trí nếu vị trí đã có chủ và người chơi hiện tại đổ xúc xắc vào
     * @param p Người chơi, player
     * @param totalDiceRoll Số tổng của các lượt đổ xúc xắc
     * @param currentTurn Lượt hiện tại
     */
    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (RailRoadListener listener : this.railRoadListener){
            listener.railRoadOwned(new RailRoadEvent(this, p, 0));
        }
    }

    /**
     * Xử lý chức năng khi người chơi AI đổ xúc xắc vào một đường ray
     * @param p Người chơi, player
     * @param totalDiceRoll Tổng số xúc xắc
     * @param currentTurn Lượt hiện tại
     */
    @Override
    public void handleLocationNotOwnedFunctionalityAI(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            Random r = new Random();
            int choice = r.nextInt(2);
            if (choice == AI_RANDOM_CHOICE_BUY){
                this.buy(p);
                for (RailRoadListener listener : this.railRoadListener){
                    listener.announcePurchaseRailRoad(new RailRoadEvent(this, p, 0));
                }
            }
        }
    }

    /**
     * Xử lý chức năng khi một người chơi người chơi (User) đổ xúc xắc vào một đường ray
     * @param p Người chơi, player
     * @param totalDiceRoll Tổng số xúc xắc
     * @param currentTurn Lượt hiện tại
     */
    @Override
    public void handleLocationNotOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if(p instanceof AI){
            handleLocationNotOwnedFunctionalityAI(p,totalDiceRoll,currentTurn);
        }else{
            handleLocationNotOwnedFunctionalityUser(p,totalDiceRoll,currentTurn);
        }
    }

    /**
     * Chức năng của phần tử
     * @param p Người chơi, player
     * @param totalDiceRoll Tổng số của các lượt đổ xúc xắc
     * @param currentTurn Lượt hiện tại
     * @return true hoặc false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null){
            handleLocationNotOwnedFunctionality(p, totalDiceRoll, currentTurn);
            return true;
        }
        else {
            if (!this.owner.equals(p)) {
                handleLocationOwnedFunctionality(p, totalDiceRoll, currentTurn);
                return false;
            }
            handleLocationOwnedByPlayerFunctionality(p, totalDiceRoll, currentTurn);

        }
        return false;
    }

    /**
     * Cho phép người chơi mua một đường ray
     * @param p Người chơi, player
     * @return false hoặc true tùy thuộc vào việc họ có đủ tiền không
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() <= this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfRailroads();
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        return false;
    }

    /**
     * Đặt lại chủ sở hữu của tài sản đường ray
     */
    @Override
    public void resetOwner() {
        this.owner = null;
    }

    /**
     * Getter để lấy tiền thanh toán cho chủ sở hữu của tài sản
     * @return Một khoản thanh toán là số nguyên
     */
    public int getPayment(){
        return this.payments.get(this.owner.getNumOfRailroads());
    }

    /**
     * Phương thức thêm một view vào danh sách các railroadListeners
     * @param view Một đối tượng Listener.BoardView view.
     */
    @Override
    public void addListener(BoardView view) {
        this.railRoadListener.add(view);
    }

    /**
     * Lấy chủ sở hữu của đường ray
     * @return chủ sở hữu
     */
    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * In thông tin ra chuỗi
     * @param p Người chơi, player
     * @return tên tài sản, giá trị tài sản hoặc ai sở hữu nó và người chơi sẽ nợ bao nhiêu tiền
     */
    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Which you own this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.payments.get(this.owner.getNumOfRailroads()) + "} \n" + p.getPlayerName() + " will lose money now";
    }

    @Override
    /**
     * Trả về đại diện XML của RailRoad dưới dạng chuỗi
     */
    public String toXML(){
        String name = this.getName();
        if (name.equals(INVALID_PARSE))
            name =  VALID_PARSE;
        String str = "\t\t\t\t<RailRoad>\n";
        str += "\t\t\t\t\t<name>" + name + "</name>\n";
        str += "\t\t\t\t</RailRoad>\n";
        return str;
    }

    /**
     * Tạo một đường ray mới từ dữ liệu của node
     * @param node, node chứa dữ liệu
     * @return Tạo một Location mới
     */
    public static Location createNewRailRoad(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new RailRoad(name, cost);
    }

}
