package Model.BoardElements;

import Events.UtilityEvent;
import Listener.BuyableLocation;
import Listener.UtilityListener;
import Listener.BoardView;
import Model.BoardModel;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kareem El-Hajjar, Max Curkovic
 * Lớp MVC.Utility cho phần tử tiện ích
 */
public class Utility extends Location implements BuyableLocation {
    private final static int AI_RANDOM_CHOICE_BUY = 0;
    private Player owner;
    private final List<UtilityListener> utilityListenerList;

    /**
     * Constructor cho Utility
     * @param name String, tên của tài sản
     * @param cost Integer, chi phí
     */
    public Utility(String name, int cost) {
        super(cost, name);
        this.owner = null;
        this.utilityListenerList = new ArrayList<>();
    }

    /**
     * Cho phép người chơi mua tài sản tiện ích
     * @param p MVC.Player
     * @return true nếu không đủ tiền, false nếu mua thành công
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfUtilities();
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        return false;
    }

    /**
     * Đặt lại chủ sở hữu của tài sản tiện ích
     */
    @Override
    public void resetOwner() {
        this.owner = null;
    }

    /**
     * Thêm người nghe cho bảng
     * @param view  view của bảng
     */
    @Override
    public void addListener(BoardView view) {
        this.utilityListenerList.add(view);
    }

    /**
     * Lấy chủ sở hữu của tài sản tiện ích
     * @return MVC.Player, chủ sở hữu
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Xử lý chức năng khi tài sản đã được sở hữu
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     */
    @Override
    public void handleLocationOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
        int landedPlayerMoney = p.getMoneyAmount();
        int payment = this.payment(totalDiceRoll);
        if (landedPlayerMoney <= payment){
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
        }
        else{
            p.setMoneyAmount(p.getMoneyAmount() - payment);
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() + payment);
        }

        for (UtilityListener listener : this.utilityListenerList){
            listener.UtilityPay(new UtilityEvent(this, p, totalDiceRoll, payment));
        }
    }

    /**
     * Xử lý chức năng khi tài sản chưa được sở hữu cho người chơi
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     */
    @Override
    public void handleLocationNotOwnedFunctionalityUser(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() > this.getCost()){
            for (UtilityListener listener : this.utilityListenerList) {
                if (listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll, 0))) {
                    if (this.buy(p)) {
                        listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    }
                }
            }
        }
    }

    /**
     * Xử lý chức năng khi tài sản được sở hữu bởi người chơi khác
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     */
    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (UtilityListener listener : this.utilityListenerList) {
            listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll, 0));
        }
    }

    /**
     * Xử lý chức năng khi một AI chơi game đi qua tiện ích
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     */
    @Override
    public void handleLocationNotOwnedFunctionalityAI(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            Random r = new Random();
            int choice = r.nextInt(2);
            if (choice == AI_RANDOM_CHOICE_BUY){
                this.buy(p);
                for (UtilityListener listener : this.utilityListenerList) {
                    listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                }
            }
        }
    }

    /**
     * Xử lý chức năng khi người chơi đi qua một tài sản chưa sở hữu
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
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
     * Chức năng của vị trí trên bảng đối với người chơi
     * @param p Player, người chơi
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     * @return Boolean, true nếu chưa có chủ, ngược lại false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null) {
            handleLocationNotOwnedFunctionality(p, totalDiceRoll, currentTurn);
            return true;
        } else {
            if (!this.owner.equals(p)) { // nếu đã có chủ
                handleLocationOwnedFunctionality(p,totalDiceRoll, currentTurn);
                return false;
            }
            handleLocationOwnedByPlayerFunctionality(p,totalDiceRoll, currentTurn);
            return false;
        }
    }

    /**
     * Tính số tiền cần phải trả khi người chơi dừng lại tại tiện ích
     * @param totalDiceRoll Integer, tổng số xúc xắc
     * @return Integer, số tiền cần trả
     */
    public int payment(int totalDiceRoll){
        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return totalDiceRoll*amount;
    }

    /**
     * Chuyển thông tin thành chuỗi
     * @param p MVC.Player
     * @return tên tài sản, ai sở hữu nó và số tiền phải trả nếu dừng lại tại đó
     */
    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Tên tài sản: " + this.getName() + " {Mua tài sản: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Tên tài sản: " + this.getName() + " Bạn là người sở hữu tài sản này";
        }
        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return "Tên tài sản: " + this.getName() + " {Sở hữu: " + this.owner.getPlayerName() + ", Tiền phải trả: xúc xắc * "
                + amount + "} \n" + p.getPlayerName() + " sẽ mất tiền khi dừng lại tại đây";
    }

    @Override
    /**
     * Trả về biểu diễn XML của Utility dưới dạng chuỗi
     */
    public String toXML(){
        String str = "\t\t\t\t<Utility>\n";
        str += "\t\t\t\t\t<name>" + this.getName() + "</name>\n";
        str += "\t\t\t\t</Utility>\n";
        return str;
    }

    /**
     * Thiết lập chủ sở hữu của tài sản tiện ích này
     * @param owner Player, người chơi là chủ sở hữu mới
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Tạo mới một tiện ích từ dữ liệu của node
     * @param node, node chứa dữ liệu
     * @return đối tượng Location vừa được tạo
     */
    public static Location createNewUtility(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new Utility(name, cost);
    }
}
