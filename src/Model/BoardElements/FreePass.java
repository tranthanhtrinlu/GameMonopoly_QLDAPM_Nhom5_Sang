package Model.BoardElements;
import Events.FreePassEvent;
import Listener.BoardView;
import Listener.FreePassListener;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TQV aka QuangVx
 * Lớp MVC.FreePass để mô tả một phần tử Free Pass.
 */
public class FreePass extends Location {

    private final List<FreePassListener> listeners;

    /**
     * Constructor cho FreePass
     * @param name String, tên
     * @param cost Integer, chi phí
     */
    public FreePass(String name, int cost) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    /**
     * Phương thức boolean để lắng nghe phần tử Free Pass.
     * @param p Một đối tượng MVC.Player.
     * @param totalDiceRoll Một số nguyên, tổng giá trị tung xúc xắc.
     * @param currentTurn Integer, lượt hiện tại
     * @return Luôn trả về false.
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (FreePassListener listener : this.listeners) {
            listener.FreePass(new FreePassEvent(this, p));
        }
        return false;
    }

    /**
     * Phương thức toString cho Free Pass.
     * @param p Một đối tượng MVC.Player.
     * @return Một chuỗi.
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Đã dừng ở một Free Pass";
    }

    /**
     * Phương thức boolean được ghi đè để xử lý việc mua một Free Pass.
     * @param p Một đối tượng MVC.Player.
     * @return Luôn trả về false vì không thể mua một Free Pass.
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Không làm gì cho lớp này.
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Thêm view vào danh sách các listener.
     * @param view Một đối tượng Listener.BoardView.
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

    /**
     * Tạo một Free Pass mới từ dữ liệu của node.
     * @param node Node, dữ liệu
     * @return FreePass, đối tượng FreePass mới
     */
    public static Location createNewFreePass(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new FreePass(name, cost);
    }
}
