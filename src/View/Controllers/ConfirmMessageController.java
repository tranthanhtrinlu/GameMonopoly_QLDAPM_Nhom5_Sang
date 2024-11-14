package View.Controllers;
import javax.swing.*;

/**
 * Controller cho đầu vào của người dùng để xác nhận một thông báo đối thoại
 * @author Phuc Thanh
 */
public class ConfirmMessageController {

    /**
     * Gửi một thông báo tới người dùng với chuỗi bất kỳ `s`
     * @param frame JFrame, khung được liên kết
     * @param s String, thông báo
     */
    public void sendMessage(JFrame frame, String s) {
        JOptionPane.showMessageDialog(frame,
                s);
    }
}
