package Model.BoardElements;

import Listener.BoardView;
import Model.GamePlayer.Player;

/**
 * @author Tran Quang Vu
 * Lớp trừu tượng MVC.Location mô tả chung về một vị trí trên bảng.
 * Lưu ý rằng một số phương thức trừu tượng này sẽ không thực hiện trong các phần tử cụ thể, vì chúng không phải là tài sản "thực" có thể sở hữu hoặc mua.
 */
public abstract class Location {
    private final int cost;
    private final String name;

    /**
     * Constructor cho MVC.Location
     * @param cost Chi phí dưới dạng số nguyên
     * @param name Tên dưới dạng chuỗi.
     */
    public Location(int cost, String name){
        this.cost = cost;
        this.name = name;
    }

    /**
     * Phương thức getter cho tên.
     * @return Một chuỗi tên.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Phương thức getter cho chi phí.
     * @return Chi phí dưới dạng số nguyên.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Phương thức trừu tượng trả về boolean cho bất kỳ chức năng nào của một vị trí trên bảng.
     * @param p Đối tượng MVC.Player p.
     * @param totalDiceRoll Tổng giá trị của xúc xắc.
     * @param currentTurn Số nguyên, lượt hiện tại
     * @return Giá trị boolean, phụ thuộc vào lớp con.
     */
    public abstract boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn);

    /**
     * Phương thức Java đã được ghi đè để chuyển thành chuỗi.
     * @param p Đối tượng MVC.Player p.
     * @return Một chuỗi.
     */
    public abstract String toString(Player p);

    /**
     * Phương thức trừu tượng trả về boolean cho việc mua một phần tử.
     * @param p Đối tượng MVC.Player p.
     * @return True nếu đã mua, false nếu không.
     */
    public abstract boolean buy(Player p);

    /**
     * Phương thức trừu tượng void để thiết lập lại chủ sở hữu của một phần tử.
     */
    public abstract void resetOwner();


    /**
     * Phương thức trừu tượng để thêm một view vào mảng listener.
     * @param view Đối tượng Listener.BoardView view.
     */
    public abstract void addListener(BoardView view);

}
