package Model.BoardElements;

import Events.TaxEvent;
import Listener.BoardView;
import Listener.TaxListener;
import Model.BoardModel;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Lớp cho phần tử Thuế trên bảng
 * @author TQV aka QuangVx
 */
public class Tax extends Location{

    private final ArrayList<TaxListener> listeners;

    /**
     * Constructor cho phần tử Thuế
     * @param name String, tên của phần tử
     * @param cost Integer, chi phí
     */
    public Tax(String name, int cost){
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    /**
     * Chức năng khi người chơi dừng lại trên Thuế
     * @param p A MVC.Player p.
     * @param totalDiceRoll Tổng giá trị của các con xúc xắc.
     * @param currentTurn Integer, lượt chơi hiện tại
     * @return boolean, luôn trả về false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() <= this.getCost()){
            BoardModel.addToCenterMoney(p.getMoneyAmount());
        }
        else{
            BoardModel.addToCenterMoney(this.getCost());
            p.setMoneyAmount(p.getMoneyAmount() - this.getCost());
        }
        for (TaxListener listener : this.listeners){
            listener.payTax(new TaxEvent(this, p));
        }
        return false;
    }

    /**
     * Phương thức toString để hiển thị thông tin thuế
     * @param p A MVC.Player đối tượng p.
     * @return String, chuỗi thông tin
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " đã dừng lại trên " + this.getName() + ". Mất tiền là $" + this.getCost();
    }

    /**
     * Phương thức để mua Thuế (không thể mua nên luôn trả về false)
     * @param p A MVC.Player đối tượng p.
     * @return false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Không làm gì cả
     */
    @Override
    public void resetOwner() {
        // Không làm gì
    }

    /**
     * Thêm boardView vào danh sách lắng nghe
     * @param view A Listener.BoardView đối tượng view.
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

    /**
     * Tạo một phần tử Thuế mới từ dữ liệu trong node
     * @param node node chứa dữ liệu
     * @return Location, phần tử mới được tạo
     */
    public static Location createNewTax(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent(); // Phân tích dữ liệu tên
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent()); // Phân tích dữ liệu chi phí
        return new Tax(name, cost);
    }

}
