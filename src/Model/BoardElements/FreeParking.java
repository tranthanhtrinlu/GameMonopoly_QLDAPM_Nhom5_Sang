package Model.BoardElements;

import Events.FreeParkingEvent;
import Listener.BoardView;
import Listener.FreeParkingListener;
import Model.BoardModel;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * @author Tran Quang Vu
 * Lớp MVC.FreeParking để mô tả một phần tử Free Parking (Ô dừng của trò chơi).
 */
public class FreeParking extends Location{


    private final ArrayList<FreeParkingListener> listeners;
    /**
     * Constructor cho FreeParking
     * @param cost Integer, chi phí của 1 ô
     * @param name String, tên của 1 ô
     */
    public FreeParking(int cost, String name){
        super(cost, name);
        this.listeners = new ArrayList<>();

    }

    /**
     * Phương thức locationElementFunctionality để lắng nghe phần tử Free Pass.
     * @param p Một đối tượng MVC.Player.
     * @param totalDiceRoll Một số nguyên totalDiceRoll.
     * @param currentTurn Một số nguyên currentTurn.
     * @return Luôn trả về false.
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (BoardModel.centerMoney == 0)
            BoardModel.setCenterMoney(100);
        p.setMoneyAmount(p.getMoneyAmount() + BoardModel.getCenterMoney());
        for (FreeParkingListener listener : this.listeners){
            listener.FreeParking(new FreeParkingEvent(this, p, BoardModel.getCenterMoney()));
        }
        BoardModel.setCenterMoney(0);
        return false;
    }

    /**
     * Phương thức toString cho Free Parking.
     * @param p Một đối tượng MVC.Player.
     * @return Một chuỗi.
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Đã dừng ở bãi đỗ xe miễn phí. Số tiền kiếm được là $";
    }

    /**
     * trả về false nếu không mua ô
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Thiết lập lại chủ sở hữu của 1 ô
     */
    @Override
    public void resetOwner() {}

    /**
     * Thêm view vào ArrayList của các listener Free Parking
     * @param view Một đối tượng Listener.BoardView
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

    /**
     * Tạo mới Free Parking từ dữ liệu node
     * @param node node chứa dữ liệu
     * @return Location, vị trí mới được tạo ra
     */
    public static Location createNewFreeParking(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new FreeParking(cost, name);
    }

}
