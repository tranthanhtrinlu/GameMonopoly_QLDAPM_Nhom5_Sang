package Events;


import Model.BoardElements.LandOnJail;
import Model.GamePlayer.Player;

import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
=======
import Model.BoardElements.LandOnJail;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**

 * Class Events.LandOnJailEvent cho sự kiện người chơi đi vô ô tù
 */
public class LandOnJailEvent extends EventObject {

    private final Player player;

    public LandOnJailEvent(LandOnJail landOnJail, Player p) {
        super(landOnJail);
        this.player = p;
    }

    /**
     * Getter đối tượng
     * @return A MVC.LandOnJail element
     */
    public LandOnJail getLandOnJail(){
        return (LandOnJail) this.getSource();
    }

    /**
     * Override Java method lấy Object java
     * @return A Java source
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
