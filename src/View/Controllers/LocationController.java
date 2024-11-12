package View.Controllers;
import javax.swing.*;

/**
 * Điều khiển đầu vào của người dùng khi họ vào một vị trí
 * @author Phuc Thanh
 */
public class LocationController {

    /**
     * Thông báo cho người chơi biết họ đã dừng lại ở tài sản nào và chi phí
     * @param frame JFrame
     * @param name Tên của tài sản
     * @param cost Giá trị của tài sản
     * @return frame
     */
    public int LocationNoOwnerController(JFrame frame, String name, int cost){
        return JOptionPane.showConfirmDialog(
                frame,
                "You landed on " + name + ", cost is $" + cost + ". Would you like to purchase this property?" +
                        "\n(Bạn đã dừng ở " + name + ", giá là $" + cost + ". Bạn có muốn mua tài sản này không?)",
                "Purchase?",
                JOptionPane.YES_NO_OPTION);
    }



}
