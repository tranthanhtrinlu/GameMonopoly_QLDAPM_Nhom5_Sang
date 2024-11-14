package Model.BoardElements;
import Events.GoToJailEvent;
import Listener.BoardView;
import Listener.GoToJailListener;
import Model.BoardModel;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tran Quang Vu
 * Lớp MVC.GoToJail để định nghĩa một phần tử MVC.GoToJail. Kế thừa từ MVC.Location.
 */
public class GoToJail extends Location{

    private final List<GoToJailListener> goToJailListener;

    /**
     * Constructor cho phần tử Ô Tù
     * @param name String, tên
     * @param cost Integer, chi phí
     */
    public GoToJail(String name, int cost) {
        super(cost, name);
        this.goToJailListener = new ArrayList<>();
    }

    /**
     * Mô tả chức năng của phần tử MVC.GoToJail
     * @param p Đối tượng MVC.Player
     * @param totalDiceRoll Tổng giá trị xúc xắc
     * @param currentTurn Integer, lượt hiện tại
     * @return Một giá trị boolean, luôn trả về false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        int oldPos = p.getPosition();
        p.setPosition(BoardModel.JAIL_POSITION);
        p.setInJail(true);
        for (GoToJailListener listener : this.goToJailListener){
            listener.SendPlayerToJail(new GoToJailEvent(this, p, currentTurn, oldPos, p.getPosition()));
        }
        return false;
    }

    /**
     * Phương thức toString thông báo 1 người dùng chuyển sang ô tù
     * @param p Một người chơi p
     * @return Một câu chuỗi mô tả người chơi đang dừng tại MVC.GoToJail
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " đã dừng ở " + this.getName() + ". Đang bị gửi vào tù.";
    }

    /**
     * Trả về false không mua
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Reset lại ô này
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Thêm view vào ArrayList của goToJailListeners
     * @param view Một đối tượng Listener.BoardView
     */
    @Override
    public void addListener(BoardView view) {
        this.goToJailListener.add(view);
    }

    /**
     * Tạo mới một phần tử đi tù từ dữ liệu tải về
     * @param node node chứa thông tin đi tù
     * @return Location, vị trí mới được tạo
     */
    public static Location createNewGoToJail(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new GoToJail(name, cost);
    }

}
