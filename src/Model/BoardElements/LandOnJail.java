package Model.BoardElements;
import Events.LandOnJailEvent;
import Listener.BoardView;
import Listener.LandOnJailListener;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TQV aka QuangVx
 * Lớp MVC.LandOnJail để định nghĩa một phần tử MVC.LandOnJail
 */
public class LandOnJail extends Location {

    private final List<LandOnJailListener> landOnJailListenerList;

    /**
     * Constructor cho LandOnJail
     * @param name String, tên
     * @param cost Integer, chi phí
     */
    public LandOnJail(String name, int cost) {
        super(cost, name);
        this.landOnJailListenerList = new ArrayList<>();
    }

    /**
     *  Chức năng của vị trí
     * @param p Đối tượng MVC.Player
     * @param totalDiceRoll Tổng giá trị của xúc xắc
     * @param currentTurn Int, lượt hiện tại
     * @return listener cho việc dừng ở ô nhà tù
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (LandOnJailListener listener : this.landOnJailListenerList){
            listener.visiting(new LandOnJailEvent(this, p));
        }
        return false;
    }

    /**
     * Gửi thông tin thành chuỗi
     * @param p Đối tượng MVC.Player
     * @return Tên người chơi và thông báo họ đang chỉ thăm tù
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " DỪNG Ở Ô NHÀ TÙ. Chỉ ghé thăm";
    }

    /**
     * Người chơi không thể mua ô nhà tù
     * @param p Đối tượng MVC.Player
     * @return Luôn trả về false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Thiết lập lại chủ sở hữu
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Thêm listener cho ô nhà tù
     * @param view BoardView, khung nhìn
     */
    @Override
    public void addListener(BoardView view) {
        this.landOnJailListenerList.add(view);
    }

    /**
     * Tạo một ô "dừng ở nhà tù" mới từ dữ liệu tải về
     * @param node node chứa thông tin dừng ở nhà tù
     * @return Location, vị trí mới được tạo
     */
    public static Location createNewLandOnJail(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new LandOnJail(name, cost);
    }

}
