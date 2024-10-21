package Model.BoardElements;
import Events.LandOnJailEvent;
import Listener.BoardView;
import Listener.LandOnJailListener;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TQV aka QuangVx
 * siuuuuuuuuuuuuuuuuuuuuuuuuuu
 * Class MVC.LandOnJail that defines a MVC.LandOnJail element
 */
public class LandOnJail extends Location {

    private final List<LandOnJailListener> landOnJailListenerList;
    /**
     * Constructor for LandOnJail
     * @param name String, the name
     * @param cost Integer, the cost
     */
    public LandOnJail(String name, int cost) {
        super(cost, name);
        this.landOnJailListenerList = new ArrayList<>();
    }

    /**
     *  location functionality
     * @param p MVC.Player
     * @param totalDiceRoll Integer sum of dice roll
     * @param currentTurn Int, the current turn
     * @return land on jail listener
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (LandOnJailListener listener : this.landOnJailListenerList){
            listener.visiting(new LandOnJailEvent(this, p));
        }
        return false;
    }

    /**
     * send info to a string
     * @param p MVC.Player
     * @return player name and landed on just visiting
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " LANDED ON JAIL. Just Visiting";
    }

    /**
     * the play can not buy jail
     * @param p MVC.Player
     * @return false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * reset the owner
     */
    @Override
    public void resetOwner() {

    }

    /**
     * adds the listener for jail
     * @param view BoardView, the view
     */
    @Override
    public void addListener(BoardView view) {
        this.landOnJailListenerList.add(view);
    }

    /**
     * create new land on jail form load
     * @param node node containing land on jail info
     * @return Location, the new location created
     */
    public static Location createNewLandOnJail(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new LandOnJail(name, cost);
    }

}
