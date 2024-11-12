package Events;
import Model.BoardElements.GoToJail;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**
 * Class Events.GoToJailEvent that describes a go to jail event. Extends EventObject.
 */
public class GoToJailEvent extends EventObject {

    private final Player player;
    private final int currentTurn;
    private final int oldPos;
    private final int newPos;

    public GoToJailEvent(GoToJail goToJail, Player p, int currentTurn, int oldPos, int newPos) {
        super(goToJail);
        this.player = p;
        this.currentTurn = currentTurn;
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    /**
     * Getter lấy người chơi
     * @return A MVC.Player object player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter lấy sự kiện đi từ
     * @return A MVC.GoToJail event.
     */
    public GoToJail getGoToJail(){
        return (GoToJail) this.getSource();
    }

    /**
     * Overridden Java method Lấy object java
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    public int getCurrentTurn(){
        return this.currentTurn;
    }

    public int getOldPos(){
        return this.oldPos;
    }

    public int getNewPos(){
        return this.newPos;
    }


}
