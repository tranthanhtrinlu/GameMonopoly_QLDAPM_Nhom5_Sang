package Testing;

import Model.BoardModel;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the BoardModel class.
 */
public class BoardModelTest {

    /**
     * Kiểm tra để đảm bảo tiền được thêm vào trung tâm của bàn cờ.
     */
    @Test
    public void addToCenterMoney() {
        BoardModel.addToCenterMoney(100);
        assertEquals(100, BoardModel.getCenterMoney());
    }

    /**
     * Kiểm tra cập nhật trạng thái.
     */
    @Test
    public void testStatusUpdate() {
        BoardModel bm = new BoardModel();
        bm.setNumberOfPlayers(1);
        bm.updateStatus();
        assertEquals(bm.getStatus(), BoardModel.Status.FINISHED);
    }

    /**
     * Kiểm tra việc tải phiên bản Mỹ.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void testLoadUSVersion() throws IOException, ParserConfigurationException, SAXException {
        BoardModel bm = new BoardModel();
        bm.initializeBoard("src/LoadXML/NewBoardModel.xml");
        assertEquals(40, bm.getSizeOfBoard());
    }

    /**
     * Kiểm tra việc tải phiên bản Anh.
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void testLoadUKVersion() throws IOException, ParserConfigurationException, SAXException {
        BoardModel bm = new BoardModel();
        bm.initializeBoard("src/LoadXML/UKBoardModel.xml");
        assertEquals(40, bm.getSizeOfBoard());
    }

    /**
     * Kiểm tra việc tải file đã lưu.
     * Việc tải file đã lưu có thể áp dụng cho bất kỳ file nào và phiên bản nào.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void testLoadSavedFile() throws ParserConfigurationException, IOException, SAXException {
        BoardModel bm = new BoardModel();
        bm.loadSavedXML("src/SaveXML/testSavedFile.xml");
        assertEquals(40, bm.getSizeOfBoard());
        assertEquals(5, bm.getNumOfPlayers());
        assertEquals(3, bm.getCurrentTurn());
        assertEquals(2, bm.getRoll1());
        assertEquals(5, bm.getRoll2());
        assertEquals(0, BoardModel.getCenterMoney());
        assertEquals(BoardModel.TypeOfBoards.US.getVersion(), bm.getGameVersion());
    }




}