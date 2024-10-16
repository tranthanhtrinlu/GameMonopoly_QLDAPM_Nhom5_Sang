package Model.BoardElements;
import Events.GoToJailEvent;
import Listener.BoardView;
import Listener.GoToJailListener;
import Model.BoardModel;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TQV aka QuangVx
 * Siuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
 * Class MVC.GoToJail that defines a MVC.GoToJail element. Extends MVC.Location
 */
public class GoToJail extends Location{

    private final List<GoToJailListener> goToJailListener;

    /**
     * Constructor for Go To Jail Board Element
     * @param name String, the name
     * @param cost Integer, the cost
     */
    public GoToJail(String name, int cost) {
        super(cost, name);
        this.goToJailListener= new ArrayList<>();
    }

    /**
     * Describes functionality of the MVC.GoToJail element
     * @param p MVC.Player object p
     * @param totalDiceRoll Integer sum of dice roll
     * @param currentTurn Integer, the current turn
     * @return A boolean, always returns false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        int oldPos = p.getPosition();
        p.setPosition(BoardModel.JAIL_POSITION);
        p.setInJail(true);
        for (GoToJailListener listener : this.goToJailListener){
            listener.SendPlayerToJail(new GoToJailEvent(this, p, currentTurn, oldPos, p.getPosition()));
        }
        return false;
    }

    /**
     * Java toString method
     * @param p A player p
     * @return A string sentence of someone landing on MVC.GoToJail
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " landed on " + this.getName() + ". Being Sent to Jail.";
    }

    /**
     * Does nothing for this class
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Does nothing for this class
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Adds the view to the ArrayList of goToJailListeners
     * @param view A Listener.BoardView view
     */
    @Override
    public void addListener(BoardView view) {
        this.goToJailListener.add(view);
    }

    /**
     * create new go to jail form load
     * @param node node containing go to jail info
     * @return Location, the new location created
     */
    public static Location createNewGoToJail(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new GoToJail(name, cost);
    }


}
