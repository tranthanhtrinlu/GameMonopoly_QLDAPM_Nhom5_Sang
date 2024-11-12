package Testing;

import Model.BoardElements.Property;
import Model.BoardModel;
import Model.GamePlayer.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Kiểm thử JUnit của lớp Property
 * Kiểm thử tổng quát bằng cách sử dụng BoardWalk làm ví dụ từ bảng
 * @tác giả Tony Massaad, Max Curkovic
 */
public class PropertyTest {

    /**
     * Kiểm tra rằng tất cả tiền thuê ban đầu được thiết lập theo constructor
     */
    @Test
    public void testInsuranceAllRentsAreSetAccordinglyForAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(50, testPropertyBoardWalk.getRentCost(0));
        assertEquals(200, testPropertyBoardWalk.getRentCost(1));
        assertEquals(600, testPropertyBoardWalk.getRentCost(2));
        assertEquals(1400, testPropertyBoardWalk.getRentCost(3));
        assertEquals(1700, testPropertyBoardWalk.getRentCost(4));
        assertEquals(2000, testPropertyBoardWalk.getRentCost(5));
    }

    /**
     * Kiểm tra giá trị của tài sản để đảm bảo rằng nó được thiết lập theo constructor
     */
    @Test
    public void testGetCostOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(400, testPropertyBoardWalk.getCost());
    }

    /**
     * Kiểm tra phương thức lấy số màu của tài sản được thiết lập theo constructor
     */
    @Test
    public void testNumberOfColorForAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(2, testPropertyBoardWalk.getNumberOfColor());
    }

    /**
     * Kiểm tra rằng mặc định của một tài sản là không có chủ sở hữu
     */
    @Test
    public void testDefaultOwnerOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertNull(testPropertyBoardWalk.getOwner());
    }

    /**
     * Kiểm tra rằng chi phí cho mỗi ngôi nhà được thiết lập theo constructor
     */
    @Test
    public void testGetCostPerHouseOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(200, testPropertyBoardWalk.getCostPerHouse());
    }

    /**
     * Kiểm tra rằng màu của tài sản được thiết lập theo constructor
     */
    @Test
    public void testGetColorOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(BoardModel.Color.DARKBLUE, testPropertyBoardWalk.getColor());
    }

    /**
     * Kiểm tra rằng số lượng nhà tối đa mặc định là 5 khi khởi tạo tài sản
     */
    @Test
    public void testDefaultMaxNumberOfHousesOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(5, testPropertyBoardWalk.getMaxNumberOfHouses());
    }

    /**
     * Đảm bảo rằng người chơi có thể mua tài sản nếu có đủ tiền cho phép.
     * Trả về false nếu người chơi có thể
     */
    @Test
    public void testValidBuyOfAPropertyByAPlayer(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing", 500);
        assertFalse(testPropertyBoardWalk.buy(testPlayer));
    }

    /**
     * Đảm bảo rằng người chơi không thể mua tài sản nếu không đủ tiền.
     * Trả về true nếu người chơi không thể
     */
    @Test
    public void testInvalidBuyOfAPropertyByAPlayer(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing", 500);
        testPlayer.setMoneyAmount(200);
        assertTrue(testPropertyBoardWalk.buy(testPlayer));
    }

    /**
     * Kiểm tra chủ sở hữu của tài sản bằng cách sử dụng phương thức set owner
     */
    @Test
    public void testSetOfOwnerWithGetOwnerOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing", 500);
        testPropertyBoardWalk.setOwner(testPlayer);
        assertEquals(testPlayer, testPropertyBoardWalk.getOwner());
    }

    /**
     * Kiểm tra rằng thêm nhà vào tài sản trả về true nếu người chơi có đủ tiền
     */
    @Test
    public void testingAddHouseWithPlayerValidAmount() {
        User testPlayer = new User("Testing", 500);
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(5000);
        assertTrue(testPropertyBoardWalk.addHouse(5));
    }

    /**
     * Kiểm tra nếu thêm nhà vào tài sản trả về false nếu người chơi không có đủ tiền
     */
    @Test
    public void testingAddHouseWithPlayerInvalidAmount() {
        User testPlayer = new User("Testing", 500);
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400, 200, 50, 200, 600, 1400, 1700, 2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(200);
        assertFalse(testPropertyBoardWalk.addHouse(5));
    }

    // Các chú thích khác tiếp tục dịch theo mẫu trên...
}
