package View.Controllers;
import Model.GamePlayer.Player;
import Model.BoardElements.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Max, Kareem, Tony và Cory
 * Lớp HouseController để kiểm soát việc mua và bán nhà.
 */
public class HouseController {
    private final static boolean SELL_HOUSE = false;
    private final static boolean BUY_HOUSE = true;

    /**
     * Phương thức mua nhà.
     * @param frame JFrame.
     * @param p Player, người chơi hiện tại
     */
    public void buyHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // luôn có ít nhất một
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != pr.getMaxNumberOfHouses()){
                options.add(pr.getName());
            }
        }
        handlePanelOfBuyOrSellHouses(BUY_HOUSE, listProperties, options, p, frame);
    }

    /**
     * Phương thức bán nhà.
     * @param frame JFrame.
     * @param p Player, người chơi hiện tại
     */
    public void sellHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // luôn có ít nhất một
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != 0){
                options.add(pr.getName());
            }
        }
        handlePanelOfBuyOrSellHouses(SELL_HOUSE, listProperties, options, p, frame);
    }

    /**
     * Tạo bảng điều khiển để mua hoặc bán nhà tùy thuộc vào lựa chọn.
     * @param choice boolean, true nếu mua nhà, ngược lại false
     * @param listProperties List<Property>, danh sách tài sản của người chơi
     * @param options List<String> danh sách tên tài sản tương ứng với listProperties
     * @param p Player, người chơi
     * @param frame JFrame, khung
     */
    private void handlePanelOfBuyOrSellHouses(boolean choice, List<Property> listProperties, List<String> options, Player p, JFrame frame){
        JPanel panel = new JPanel(new GridLayout(2,2));
        AtomicReference<Property> place = new AtomicReference<>(p.getPropertyByName(options.get(0)));
        JComboBox houses = new JComboBox(options.toArray());
        AtomicReference<JComboBox> num = new AtomicReference<>(new JComboBox(getBuyOrSellChoices(choice, place)));
        houses.addActionListener(e->{
            updatePanel(panel, (String) houses.getSelectedItem(), place, num, choice, p, houses);
        });
        addToPanel(panel, place, num, houses, choice);
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            getResultOfChoice(choice, place, num, frame, p);
        }
    }

    /**
     * Phương thức để lấy kết quả của lựa chọn
     * @param choice boolean, true nếu mua nhà, ngược lại false
     * @param place AtomicReference, tài sản của người chơi
     * @param num AtomicInteger, giá trị lựa chọn của người chơi
     * @param p Player, người chơi
     * @param frame JFrame, khung
     */
    private void getResultOfChoice(boolean choice, AtomicReference<Property> place, AtomicReference<JComboBox> num, JFrame frame, Player p){
        if (choice == BUY_HOUSE){
            place.get().addHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Người chơi: " + p.getPlayerName() + " đã mua " + num.get().getSelectedItem() + " nhà cho " + place.get().getName());
            return;
        }
        place.get().sellHouse((int) num.get().getSelectedItem());
        JOptionPane.showMessageDialog(frame, "Người chơi: " + p.getPlayerName() + " đã bán " + num.get().getSelectedItem() + " nhà trên " + place.get().getName());
    }

    /**
     * Phương thức lấy danh sách các lựa chọn mua hoặc bán nhà.
     * @return Danh sách các số nguyên lst.
     */
    private List<Integer> getLst(int val){
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < val; i++) {
            lst.add(i + 1);
        }
        return lst;
    }

    /**
     * Phương thức cập nhật bảng điều khiển.
     * @param panel JPanel.
     * @param selectedItem Chuỗi đã chọn.
     * @param place Tham chiếu đến tài sản.
     * @param num Tham chiếu đến JComboBox num.
     * @param choice Boolean lựa chọn.
     * @param houses JComboBox houses.
     */
    private void updatePanel (JPanel panel, String selectedItem, AtomicReference<Property> place, AtomicReference<JComboBox> num, boolean choice, Player p, JComboBox houses){
        place.set(p.getPropertyByName(selectedItem));
        num.set(new JComboBox(getBuyOrSellChoices(choice, place)));
        panel.removeAll();
        panel.add(new JLabel("Tài sản: "));
        panel.add(houses);
        panel.add(new JLabel(getText(choice, place)));
        panel.add(num.get());
        panel.revalidate();
    }

    /**
     * Phương thức để lấy các lựa chọn mua hoặc bán của người chơi.
     * @param choice Boolean lựa chọn.
     * @param place Tham chiếu đến tài sản.
     * @return Danh sách các lựa chọn.
     */
    private Object[] getBuyOrSellChoices(boolean choice, AtomicReference<Property> place){
        if(choice == BUY_HOUSE){
            return getLst(place.get().numberOfHousesCanBuy()).toArray();
        }
        return getLst(place.get().getNumOfHouses()).toArray();
    }

    /**
     * Phương thức trả về chuỗi thông báo.
     * @param choice Boolean lựa chọn.
     * @param place Tham chiếu đến tài sản.
     * @return Chuỗi thông báo.
     */
    private String getText(boolean choice, AtomicReference<Property> place){
        if(choice == BUY_HOUSE){
            return "<html>Số nhà được thêm vào hiện tại (" + place.get().getNumOfHouses() +" của " + place.get().getMaxNumberOfHouses() + "): <br>(Chi phí mỗi nhà là $" + place.get().getCostPerHouse() + ")</html>";
        }
        return "<html>Số nhà hiện có " + place.get().getNumOfHouses() + ": <br>(lợi nhuận mỗi nhà là $" + place.get().getCostPerHouse() + ")</html>";
    }

    /**
     * Phương thức để thêm vào bảng điều khiển.
     * @param panel JPanel.
     * @param place Tham chiếu đến tài sản.
     * @param num Tham chiếu đến JComboBox num.
     * @param houses JComboBox houses.
     * @param choice Boolean lựa chọn.
     */
    private void addToPanel(JPanel panel, AtomicReference<Property> place, AtomicReference<JComboBox> num, JComboBox houses, boolean choice){
        panel.add(new JLabel("Tài sản: "));
        panel.add(houses);
        panel.add(new JLabel(getText(choice, place)));
        panel.add(num.get());
    }
}
