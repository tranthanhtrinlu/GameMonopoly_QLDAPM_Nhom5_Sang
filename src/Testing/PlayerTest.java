package Testing;

import Model.BoardElements.Property;
import Model.BoardElements.RailRoad;
import Model.BoardModel;
import Model.GamePlayer.User;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * Tất cả các kiểm thử cần thiết cho lớp trừu tượng Player.
 * Lưu ý rằng User và AI được kế thừa từ lớp này.
 */
public class PlayerTest {

    /**
     * Kiểm tra lấy tài sản theo tên.
     */
    @Test
    public void testGetPropertyByName() {
        Property property = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max",500);
        property.buy(player);
        assertNotNull(player.getPropertyByName("Mediterranean Avenue"));
    }

    /**
     * Kiểm tra lấy số lượng tài sản của người chơi.
     */
    @Test
    public void testGetNumOfProperties() {
        RailRoad railRoad = new RailRoad("Max Line", 200);
        User player = new User("Max", 500);
        railRoad.buy(player);
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Kiểm tra lấy tài sản theo chỉ số trong danh sách.
     */
    @Test
    public void testGetPropertyByIndex() {
        RailRoad railRoad = new RailRoad("Max Line", 200);
        User player = new User("Max", 500);
        railRoad.buy(player);
        assertEquals(railRoad, player.getPropertyByIndex(0));
    }

    /**
     * Kiểm tra số lượng tài sản màu sắc mà người chơi sở hữu.
     */
    @Test
    public void testNumberOfColoredPropertiesOwned() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        Property property2 = new Property("BALTIC AVENUE", 60,50,4,20,60,180,320,450, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        property1.buy(player);
        property2.buy(player);
        assertTrue(player.numberOfColoredPropertiesOwned(BoardModel.Color.BROWN, 2));
    }

    /**
     * Kiểm tra việc đưa người chơi vào tù.
     */
    @Test
    public void testSetInJail() {
        User player = new User("Max", 500);
        player.setInJail(true);
        assertTrue(player.getInJail());
    }

    /**
     * Kiểm tra việc thêm tài sản vào danh sách tài sản của người chơi.
     */
    @Test
    public void testAddProperty() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        player.addProperty(property1);
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Kiểm tra việc thêm màu vào tài sản.
     */
    @Test
    public void testAddColorToProperty() {
        User player = new User("Max", 500);
        player.addColorToProperty(BoardModel.Color.GREEN, 3);
        assertNotNull(player.getOwnedPropertiesBasedOnColors());
    }

    /**
     * Kiểm tra xem người chơi có bị phá sản hay không.
     */
    @Test
    public void testBankrupted() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        property1.buy(player);
        player.bankrupted();
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Kiểm tra việc thêm đường sắt vào danh sách tài sản của người chơi.
     */
    @Test
    public void testAddNumOfRailroads() {
        User player = new User("Max", 500);
        player.addNumOfRailroads();
        assertEquals(1, player.getNumOfRailroads());
    }

    /**
     * Kiểm tra việc thêm tiện ích vào danh sách tài sản của người chơi.
     */
    @Test
    public void testAddNumOfUtilities() {
        User player = new User("Max", 500);
        player.addNumOfUtilities();
        assertEquals(1, player.getNumOfUtilities());
    }

    /**
     * Kiểm tra việc trả tiền để ra khỏi tù của người chơi.
     */
    @Test
    public void testPayJail() {
        User player = new User("Max", 500);
        player.setMoneyAmount(70);
        assertTrue(player.payJail());
    }
}
