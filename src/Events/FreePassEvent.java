package Events;
import Model.BoardElements.FreePass;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**
 * Class Events.FreePassEvent for describing a Events.FreePassEvent. Extends EventObject.
 */
public class FreePassEvent extends EventObject {


    private final Player player;

    public FreePassEvent(FreePass pass, Player p) {
        super(pass);
        this.player = p;
    }

    /**
     * Getter
     * @return A MVC.FreePass object.
     */
    public FreePass getPass(){
        return (FreePass) this.getSource();
    }

    /**
     * Overridden Java method lấy Object java
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Getter lấy người chơi
     * @return A Player player.
     */
    public Player getPlayer() {
        return this.player;
    }
}
