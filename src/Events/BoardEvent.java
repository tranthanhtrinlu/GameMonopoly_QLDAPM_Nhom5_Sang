package Events;
import Model.BoardElements.Location;
import Model.BoardModel;
import Model.GamePlayer.Player;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class BoardEvent extends EventObject {

    private final List<Location> board;
    private final boolean doubles;
    private final int roll1;
    private final int roll2;
    private final Player player;
    private final int turn;
    private final int numOfPlayers;
    private final ArrayList<Player> players;

    public BoardEvent(BoardModel boardModel, List<Location> board, boolean doubles, int roll1, int roll2, Player p, int turn, ArrayList<Player> players) {
        super(boardModel);
        this.board = board;
        this.doubles = doubles;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.player = p;
        this.turn = turn;
        this.players = players;
        this.numOfPlayers = players.size();
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getTurn() {
        return this.turn;
    }

    public Player getPlayerByIndex(int i){
        return this.players.get(i);
    }

    /**
     * Getter: lấy xúc xắc 1
     */
    public int getRoll1() {
        return this.roll1;
    }

    /**
     * Getter lấy xúc xắc 2
     */
    public int getRoll2() {
        return this.roll2;
    }

    /**
     * Xác định tổng của 2 xúc xắc
     */
    public int diceSum(){
        return this.roll1 + this.roll2;
    }

    /**
     * Kiểm tra roll có phải kiểu double
     * @return A boolean doubles.
     */
    public boolean getDoubles(){
        return this.doubles;
    }

    /**
     * Xác định tên của một phần tử trên bảng
     * @param index An integer index.
     * @return A string name from the board element.
     */
    public String boardElementName(int index){
        if (index < 0 || index > this.board.size()){
            return null;
        }
        return this.board.get(index).getName();
    }

    /**
     * Lấy phần tử vị trí trong bảng thông qua index
     * @param index An integer index.
     * @return A MVC.Location object of an element on the board.
     */
    public Location boardElementByIndex(int index){
        return this.board.get(index);
    }

    /**
     * Getter lấy Board Model
     * @return A MVC.BoardModel object model.
     */
    public BoardModel getModel(){
        return (BoardModel) this.getSource();
    }

    /**
     * Overriden Java method để Object
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}
