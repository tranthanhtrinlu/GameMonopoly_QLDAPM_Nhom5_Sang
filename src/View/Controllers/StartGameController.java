package View.Controllers;
import Model.BoardModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bộ điều khiển cho phần giới thiệu trò chơi
 * chủ yếu để lấy số lượng người chơi và tên của họ
 * Tác giả: Phuc Thanh
 */
public class StartGameController {


    private final static String PLAYER = " Người chơi";
    private final static String AI_PLAYER = " AI";

    /**
     * lấy số lượng người chơi hiện tại trong trò chơi
     * @param frame JFrame
     * @return Số nguyên của số lượng người chơi
     */
    public int getNumOfPlayers(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(4,1));
        AtomicInteger num = new AtomicInteger(2);
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < BoardModel.MAX_PLAYERS-1; i++){
            addingToPanel(panel, num, group, i, i+2, PLAYER);
        }
        JOptionPane.showConfirmDialog(frame, panel,
                "Số lượng người chơi", JOptionPane.DEFAULT_OPTION);
        return num.get();
    }

    /**
     * lấy tên của tất cả người chơi trong trò chơi
     * Không thoát ra cho đến khi tất cả tên theo số lượng người chơi được điền
     * @param num Số nguyên của số lượng người chơi
     * @param frame JFrame
     * @return mảng tên
     */
    public ArrayList<String> getNameOfPlayers(int num, JFrame frame) {
        ArrayList<JTextField> names = new ArrayList<>();
        ArrayList<String> nameText = new ArrayList<>();
        JPanel myPanel = new JPanel(new GridLayout(num,2));
        for (int i = 0; i<num; i++){
            myPanel.add(new JLabel("Tên " + (i+1) + ":"));
            names.add(new JTextField());
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(names.get(i));
        }
        while (true){
            boolean stay = true;
            int result = JOptionPane.showConfirmDialog(frame, myPanel,
                    "Nhập thông tin", JOptionPane.DEFAULT_OPTION);
            if (result == 0){
                for (int i = 0; i < num; i++) {
                    if (names.get(i).getText().equals("")){
                        nameText.clear();
                        break;
                    }
                    nameText.add(names.get(i).getText());
                }
                if (nameText.size() == num)
                    stay = false;
                if (!stay)
                    break;
            }
        }
        return nameText;
    }

    /**
     * lấy lựa chọn về số lượng AI
     * @param frame JFrame, khung
     * @param numberOfPlayers Số nguyên, số lượng người chơi
     * @return Số nguyên, số lượng AI
     */
    public int getNumOfAIs(JFrame frame, int numberOfPlayers) {
        int numOfAIs = (BoardModel.MAX_PLAYERS) - numberOfPlayers;
        JPanel panel = new JPanel(new GridLayout(numOfAIs+2,1));
        AtomicInteger num = new AtomicInteger(0);
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i<numOfAIs+1; i++){
            addingToPanel(panel, num, group, i, i, AI_PLAYER);
        }
        JOptionPane.showConfirmDialog(frame, panel,
                "Nhập số lượng AI", JOptionPane.DEFAULT_OPTION);
        return num.get();
    }

    /**
     * Thêm vào bảng tùy chọn để người xem lựa chọn
     * @param panel JPanel, bảng
     * @param num AtomicInteger, giá trị để thiết lập lựa chọn
     * @param group ButtonGroup, nhóm các nút radio
     * @param actualVal Số nguyên, giá trị chỉ số mà nút nằm ở
     * @param theoreticalVal Số nguyên, giá trị chỉ số để thiết lập num và văn bản nút
     * @param name Chuỗi, tên. AI nếu là AI, nếu không là NGƯỜI CHƠI
     */
    private void addingToPanel(JPanel panel, AtomicInteger num, ButtonGroup group, int actualVal, int theoreticalVal, String name){
        JRadioButton button = new JRadioButton(theoreticalVal + name);
        if (actualVal == 0)
            button.setSelected(true);
        button.addActionListener(e -> num.set(theoreticalVal));
        group.add(button);
        panel.add(button);
    }

}
