package View.Controllers;
import Model.BoardModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * BoardController xử lý các lựa chọn đầu vào của người chơi hiện tại.
 * @author Phuc Thanh
 */
public class BoardController implements ActionListener {

    private final BoardModel model;

    /**
     * Constructor cho BoardController
     * @param model BoardModel
     */
    public BoardController(BoardModel model){
        this.model = model;
    }

    /**
     * Xử lý ActionEvent khi một nút nhất định được nhấn theo lệnh của nút
     * @param e ActionEvent, sự kiện ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] inputs = e.getActionCommand().split(" ");
        this.model.playCurrPlayerTurn(Integer.parseInt(inputs[0]));
    }
}
