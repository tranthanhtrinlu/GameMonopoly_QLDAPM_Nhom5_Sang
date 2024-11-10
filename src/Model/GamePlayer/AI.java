package Model.GamePlayer;

import Model.BoardModel;

/**
 * Lớp đại diện cho người chơi AI trong trò chơi
 */
public class AI extends Player {
    /**
     * Constructor mặc định cho AI
     * @param name Tên của người chơi AI
     * @param moneyAmount Số tiền ban đầu của AI
     */
    public AI(String name, int moneyAmount) {
        super(name, moneyAmount);
    }

    /**
     * Constructor cho AI với nhiều thông số chi tiết
     * @param name Tên của người chơi AI
     * @param moneyAmount Số tiền của AI
     * @param out Trạng thái "thoát" của người chơi
     * @param position Vị trí hiện tại của AI trên bàn cờ
     * @param inJail Trạng thái "trong tù" của AI
     * @param turnsInJail Số lượt còn lại AI phải ở trong tù
     * @param currLocation Vị trí hiện tại của AI
     * @param numOfRailroads Số lượng đường sắt mà AI sở hữu
     * @param numOfUtilities Số lượng tiện ích mà AI sở hữu
     */
    public AI(String name, int moneyAmount, boolean out, int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities) {
        super(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }

    /**
     * Phương thức thực hiện lượt chơi của AI dựa trên trạng thái hiện tại của AI
     * @return Integer, lựa chọn của AI
     */
    public int playAI(){
        if (!super.getInJail()) // Nếu AI không bị giam trong tù
            return BoardModel.PlayerChoice.ROLL.getChoice(); // AI sẽ chọn tung xúc xắc
        return BoardModel.PlayerChoice.ROLL_OUT.getChoice(); // Nếu AI ở trong tù, sẽ cố gắng tung xúc xắc đôi để ra tù
    }
}
