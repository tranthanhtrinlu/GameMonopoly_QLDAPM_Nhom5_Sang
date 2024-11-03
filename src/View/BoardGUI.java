package View;

import Events.*;
import Listener.BoardView;
import Model.BoardModel;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import Model.GamePlayer.User;
import View.Components.GameDisplayPanel;
import View.Components.PlayerDisplayPanel;
import View.Controllers.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * BoardGUI Class, also known as the boardView class
 * @author Tony Massaad, Max Curkovic, Kareem, Cory Helm
 */
public class BoardGUI extends JFrame implements BoardView{
    public final static int GAME_WIDTH = 985;
    public final static int GAME_HEIGHT = 807;
    private final static int[] DICE_DIM = new int[]{96, 96};
    private final int ORIGINAL_STARTING_MONEY = 1500;
    private final int UK_STARTING_MONEY = 15000000;

    private GameDisplayPanel gamePanel;
    private PlayerDisplayPanel sidePanel;
//    private PlayerDisplayPanel sidePanel;
    private JPanel gameControlPanel;

    private final JButton turnPass, quit, roll, payOutOfJail, rollDouble, purchaseEstateHouses, sellHouses, save, save_quit;
    private final ArrayList<Image> diceImages;
    private JLabel dice1, dice2;

    /**
     * default constructor for BoardGUI
     */
    public BoardGUI(){
        super("Monopoly");
        BoardModel model = new BoardModel();
        mainMenu(model);

        this.turnPass = new JButton("Pass");
        this.quit = new JButton("Quit");
        this.roll = new JButton("Roll");
        this.payOutOfJail = new JButton("Pay out of jail");
        this.rollDouble = new JButton("Roll Double");
        this.purchaseEstateHouses = new JButton("Purchase Houses");
        this.sellHouses = new JButton("Sell Houses");
        this.save = new JButton("Save");
        this.save_quit = new JButton("Save and Quit");

        BoardController controller = new BoardController(model);
        this.roll.setActionCommand(BoardModel.PlayerChoice.ROLL.getChoice() + " ");
        this.roll.addActionListener(controller);
        this.quit.setActionCommand(BoardModel.PlayerChoice.QUIT.getChoice() + " ");
        this.quit.addActionListener(controller);
        this.turnPass.setActionCommand(BoardModel.PlayerChoice.PASS.getChoice() + " ");
        this.turnPass.addActionListener(controller);
        this.payOutOfJail.setActionCommand(BoardModel.PlayerChoice.PAY_OUT.getChoice() + " ");
        this.payOutOfJail.addActionListener(controller);
        this.rollDouble.setActionCommand(BoardModel.PlayerChoice.ROLL_OUT.getChoice() + " ");
        this.rollDouble.addActionListener(controller);
        this.purchaseEstateHouses.setActionCommand(BoardModel.PlayerChoice.BUY_HOUSE.getChoice() + " ");
        this.purchaseEstateHouses.addActionListener(controller);
        this.sellHouses.setActionCommand(BoardModel.PlayerChoice.SELL_HOUSE.getChoice() + " ");
        this.sellHouses.addActionListener(controller);
        this.save.setActionCommand(BoardModel.PlayerChoice.SAVE.getChoice() + " ");
        this.save.addActionListener(controller);
        this.save_quit.setActionCommand(BoardModel.PlayerChoice.SAVE_QUIT.getChoice() + " ");
        this.save_quit.addActionListener(controller);

        this.diceImages = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice1.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice2.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice3.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice4.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice5.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice6.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
        }};
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }


    /**
     * reset the frame
     */
    private void resetFrame(){
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }


    /**
     * creates the main menu panel with new game, load game, quit
     * @param model BoardModel
     */
    private void mainMenu(BoardModel model) {
        resetFrame();
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Load Recent Game");
        JButton quitMainMenu = new JButton("Quit");
        addToMenuPanels(newGame, loadGame, quitMainMenu);
        newGame.addActionListener(e -> {playerBoardChoice(model);});
        quitMainMenu.addActionListener(e -> {System.exit(0);});
        loadGame.addActionListener(e -> {
            try {
                loadSavedGame(model);
            } catch (IOException | ParserConfigurationException | SAXException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * gives the player choice between US and UK versions of the game
     * @param model BoardModel
     */
    private void playerBoardChoice(BoardModel model){
        resetFrame();
        JButton UK = new JButton("UK Version");
        JButton US = new JButton("US Version");
        JButton back = new JButton("Back");
        addToMenuPanels(UK, US, back);
        UK.addActionListener(e -> {
            try {
                initializeNewGame(BoardModel.TypeOfBoards.UK.getVersion(), UK_STARTING_MONEY, model);
            } catch (ParserConfigurationException | SAXException | IOException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        US.addActionListener(e -> {
            try {
                initializeNewGame(BoardModel.TypeOfBoards.US.getVersion(), ORIGINAL_STARTING_MONEY, model);
            } catch (ParserConfigurationException | SAXException | IOException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        back.addActionListener(e -> {mainMenu(model);});
    }

    /**
     * adds panels to the menu
     * @param topButton top button
     * @param middleButton middle button
     * @param bottomButton bottom button
     */
    private void addToMenuPanels(JButton topButton, JButton middleButton, JButton bottomButton) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel blank = new JPanel();
        JPanel blankTwo = new JPanel();
        JLabel mainTitle = new JLabel("     Monopoly");
        JLabel bottomBlank = new JLabel("");
        mainTitle.setFont(new Font("Arial", Font.BOLD, 40));
        buttonPanel.setPreferredSize(new Dimension(200, 400));
        blank.setPreferredSize(new Dimension(50, 400));
        blankTwo.setPreferredSize(new Dimension(50, 400));
        topButton.setPreferredSize(new Dimension(200, 85));
        middleButton.setPreferredSize(new Dimension(200, 125));
        bottomButton.setPreferredSize(new Dimension(200, 85));
        mainTitle.setPreferredSize(new Dimension(300, 50));
        bottomBlank.setPreferredSize(new Dimension(300, 50));
        this.add(blank, BorderLayout.LINE_START);
        this.add(buttonPanel, BorderLayout.CENTER);
        this.add(blankTwo, BorderLayout.LINE_END);
        this.add(mainTitle, BorderLayout.PAGE_START);
        this.add(bottomBlank, BorderLayout.PAGE_END);
        buttonPanel.add(topButton, BorderLayout.PAGE_START);
        buttonPanel.add(middleButton, BorderLayout.CENTER);
        buttonPanel.add(bottomButton, BorderLayout.PAGE_END);
        this.pack();
        this.setSize(300, 400);
    }

    /**
     * creates a new game after the version is selected
     * @param initialCost Int of starting money
     * @param model BoardModel
     */
    private void initializeNewGame(String version, int initialCost, BoardModel model) throws ParserConfigurationException, SAXException, IOException {
        model.setGameVersion(version);
        model.createBoard();
        resetFrame();
        addToGame(version, 0,0);
        StartGameController start = new StartGameController();
        int numberOfPlayers = start.getNumOfPlayers(this);
        int numberOfAIs = 0;
        if (numberOfPlayers != BoardModel.MAX_PLAYERS)
            numberOfAIs = start.getNumOfAIs(this, numberOfPlayers);
        ArrayList<String> names = start.getNameOfPlayers(numberOfPlayers, this);
        for (int i = 0; i < numberOfPlayers + numberOfAIs; i++){
            if (i < numberOfPlayers){
                model.addGamePlayers(new User(names.get(i), initialCost));
            }else{
                model.addGamePlayers(new AI("AI" + (i - numberOfPlayers+1), initialCost));
            }
            this.sidePanel.addNewPlayerViewButton(model.getPlayersByIndex(i), i);
            this.gamePanel.addInitialPlayers(i, numberOfPlayers);
        }
        model.setNumberOfPlayers(numberOfPlayers+numberOfAIs);
        this.updateChoicePanel(model.getPlayersByIndex(0));
        model.addView(this);
        model.addViewToListener(this);
    }

    /**
     * Load a game from a save file
     * @param model the board model
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private void loadSavedGame(BoardModel model) throws IOException, SAXException, ParserConfigurationException {
        if (!model.loadSavedXML(BoardModel.StringRepresentationOfXML.JAR_SAVED.getPath()))
            model.loadSavedXML(BoardModel.StringRepresentationOfXML.SAVED.getPath());
        resetFrame();
        addToGame(model.getGameVersion(), model.getRoll1()-1, model.getRoll2()-1);
        for (int i = 0; i<model.getNumberOfPlayers(); i++){
            Player p = model.getPlayersByIndex(i);
            this.gamePanel.loadInPlayers(p, i, model.getNumberOfPlayers());
            this.sidePanel.addNewPlayerViewButton(p, i);
            if (p.getOut()) {
                this.sidePanel.removePlayerView(i, p);
                this.gamePanel.removePieceFromBoard(i, p.getPosition());
            }
        }
        for (int i = 0; i<model.getNumOfPlayers(); i++){
            this.sidePanel.updateCurrentTurn(model.getCurrentTurn(), i, model.getPlayersByIndex(i));
        }
        this.updateChoicePanel(model.getPlayersByIndex(model.getCurrentTurn()));
        model.addView(this);
        model.addViewToListener(this);
    }

    /**
     * add version and rolls to game
     * @param version String, the version of the game
     * @param dice1 int, dice 1
     * @param dice2 int, dice 2
     */
    private void addToGame(String version, int dice1, int dice2){
        this.gamePanel = new GameDisplayPanel(version);
        this.sidePanel = new PlayerDisplayPanel();
        this.gameControlPanel = new JPanel();
        this.gameControlPanel.setLayout(new GridLayout(10,1));
        this.gameControlPanel.setBounds(520,315, 150,200);
        this.gameControlPanel.setBackground(new Color(255,255,255));
        this.setLayout(null);
        this.sidePanel.setBounds(0,0,200, GAME_HEIGHT);
        this.gamePanel.setBounds(200,0,GAME_WIDTH,GAME_HEIGHT);
        this.dice1 = new JLabel(new ImageIcon(this.diceImages.get(dice1)));
        this.dice2 = new JLabel(new ImageIcon(this.diceImages.get(dice2)));
        this.dice1.setBounds(450,150,96,96);
        this.dice2.setBounds(650,150,96,96);
        this.add(this.dice1);
        this.add(this.dice2);
        this.add(this.gameControlPanel);
        this.add(this.gamePanel);
        JScrollPane scroll = new JScrollPane(this.sidePanel);
        scroll.setBounds(0,0,200, GAME_HEIGHT);
        this.add(scroll);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }


    //****BEGINNING OF PROPERTY FUNCTIONS**//

    /**
     * Overridden method to handle a property with no owner. A player can either pass on or buy a property.
     * If bought, give player choices to purchase houses.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public boolean propertyNoOwner(PropertyEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getProperty().getName(), e.getProperty().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Overridden method to handle a property already owned by a player.
     * Can either buy houses/hotels or pass.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyOwned(PropertyEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + ", Property they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a property owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyRent(PropertyEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + " Owned by " + e.getProperty().getOwner().getPlayerName()
                + " and rent is $" + e.getCost() + "\nThey pay now");
    }

    /**
     * announcement that the player cannot buy a property
     */
    @Override
    public void announceCannotBuy(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getProperty().getName() + " but does not have enough Money, moving to the next player");
    }

    /**
     * announcement that the player purchased the property
     * @param e PropertyEvent, the property event
     */
    @Override
    public void announcePurchaseProperty(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getProperty().getName());
    }


    // ** ENDING OF PROPERTY IMPLEMENTATION


    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//

    /**
     * Overridden method to handle a railroad with no owner. A player can either pass on or buy a property.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public boolean railRoadNoOwner(RailRoadEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getRailRoad().getName(), e.getRailRoad().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Method to handle a railroad that a player who owns it lands on.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadOwned(RailRoadEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + ", Property they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a railroad owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadRent(RailRoadEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + " Owned by " + e.getRailRoad().getOwner().getPlayerName()
                + " and rent is $" + e.getRentCost() + "\nThey pay now");
    }

    /**
     * Overridden method to announce that the player cannot afford a railroad
     * @param e RailRoadEvent, the event occurring in the RailRoad
     */
    @Override
    public void announceCannotBuyRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getRailRoad().getName() + " but does not have enough Money, moving to the next player");
    }

    /**
     * Overridden method to announce that the player purchased a railroad
     * @param e RailRoadEvent, the event occurring in the RailRoad
     */
    @Override
    public void announcePurchaseRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getRailRoad().getName());
    }

    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    /**
     * Overridden method to handle a utility with no owner. A player can either pass on or buy a property.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public boolean UtilityNoOwner(UtilityEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getUtility().getName(), e.getUtility().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Method to handle a utility that a player who owns it lands on.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityOwned(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "player:  " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " a Utility they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a utility owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityPay(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " Owned by " + e.getUtility().getOwner().getPlayerName() + ".\n" +
                "Number of utilities owned by owner is " + e.getUtility().getOwner().getNumOfUtilities() + ". so payment (dice roll * (10 if 2 utilities else 4)) is $" + e.getPayment());
    }

    /**
     * Overridden method to announce that a player cannot afford a utility
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void announceCannotBuyUtility(UtilityEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getUtility().getName() + " but does not have enough Money, moving to the next player");
    }

    /**
     * Overridden method to announce that a player has purchased a utility
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void announcePurchaseOfUtility(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getUtility().getName());
    }


    // ** END OF UTILITY IMPLEMENTATION **

    /**
     * Overridden method for handling free parking.
     * If a player lands here they get the money in the "center" of the board.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void FreeParking(FreeParkingEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on free parking, they receive $" + e.getCenterMoney());
    }

    /**
     * Overridden method for handling the payment of tax.
     * If player runs out of money, they will go bankrupt.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void payTax(TaxEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on " + e.getLocation().getName() + ", they lose $" + e.getLocation().getCost() + " which goes into the center money");
    }

    /**
     * Overridden method for handling "Just Visiting".
     * @param e A Events.LandOnJailEvent event.
     */
    @Override
    public void visiting(LandOnJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed " + e.getLandOnJail().getName());
    }


    /**
     * Overridden method for sending a player to jail.
     * @param e Events.GoToJailEvent e.
     */
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on Go To Jail, they go to jail.\nMoving to the next player");
        this.handlePlayerPieceMovement(e.getCurrentTurn(), e.getOldPos(), e.getNewPos());
    }


    /**
     * Overridden method for handling a free pass. These replace the Chance + Community Chest as placeholders on the board.
     * @param e Events.FreePassEvent event.
     */
    @Override
    public void FreePass(FreePassEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " Landed on a free pass, moving to the next player");
    }


    /* HANDLES */

    /**
     * Announces a players bankruptcy and removal form the game
     * @param p Player, the player who is bankrupt
     */
    @Override
    public void handleAnnounceBankruptedPlayer(Player p){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + p.getPlayerName() + " has no more money. Removing player from game\nTheir properties are now back in estate!");
    }


    /**
     * Overridden method for handling the player quit.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerQuit(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has quit the game. Removing player from game\nTheir properties are now back in estate!");
    }

    /**
     * Overridden method for handling the announcement of a player passing the turn.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announcePlayerPass(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " passed the turn. Moving to the next player.");
    }

    /**
     * Overridden boolean method for handling the payment in jail.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void payJail(boolean payed, BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (payed){
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " payed out of jail.");
        }
        else{
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " attempted to pay out of jail but could not due to financial issues.");
        }
    }

    /**
     * Button handle for rolling doubles to get out of jail
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleResultsOfRollingInJail(BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (e.getDoubles()){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " rolled a double! they are now out of jail and on the move!");
            return;
        }
        if (e.getPlayer().getTurnsInJail() != 0){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " attempted to roll out of jail. They failed and now have " + e.getPlayer().getTurnsInJail() + " left in jail.\nMoving tothe next player");
        }
        else{
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " are now out of turns in jail, and now have to pay the $50 fine to get out.\nMoving to the next player");
        }
    }

    /**
     * handles the choice to buy houses
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    @Override
    public void handlePlayerChoiceToPurchaseHouses(BoardEvent e) {
        HouseController controller = new HouseController();
        controller.buyHouses(this, e.getPlayer());
    }

    /**
     * handle the choice to sell houses
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    @Override
    public void handlePlayerChoiceToSellHouses(BoardEvent e) {
        HouseController controller = new HouseController();
        controller.sellHouses(this, e.getPlayer());
    }

    /**
     * disable or enable the buttons
     * @param b boolean, true to enable otherwise false
     */
    @Override
    public void buttonEnableCondition(boolean b){
        this.turnPass.setEnabled(b);
        this.quit.setEnabled(b);
        this.roll.setEnabled(b);
        this.payOutOfJail.setEnabled(b);
        this.rollDouble.setEnabled(b);
        this.purchaseEstateHouses.setEnabled(b);
        this.sellHouses.setEnabled(b);
        this.save.setEnabled(b);
        this.save_quit.setEnabled(b);
        this.gameControlPanel.revalidate();
    }

    /**
     * Overridden method that announces when a player has reached GO!.
     */
    @Override
    public void announceReachingGo(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " received $" + BoardModel.GO_MONEY + " for reaching GO");
        this.handleUpdateSidePanelDisplay(e);
    }


    /**
     * handle the next turn of the player according to the view
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleNextTurnDisplay(BoardEvent e, int updatedTurn){
        for (int i = 0; i<e.getNumOfPlayers(); i++){
            this.sidePanel.updateCurrentTurn(updatedTurn, i, e.getPlayerByIndex(i));
        }
    }

    /**
     * handle the update of the side panel for each player in a view
     * @param e BoardEvent, the board event
     */
    @Override
    public void handleUpdateSidePanelDisplay(BoardEvent e){
        for (int i = 0; i<e.getNumOfPlayers(); i++) {
            if (e.getPlayerByIndex(i).getOut()){
                this.sidePanel.removePlayerView(i, e.getPlayerByIndex(i));
            }
            this.sidePanel.updatePlayerDisplay(i, e.getPlayerByIndex(i));
        }
    }

    /**
     * handle the piece display on the board for each player in a view
     * @param currentTurn Integer, the current turn
     * @param oldPos Integer, the old position
     * @param position Integer, the new position
     */
    @Override
    public void handlePlayerPieceMovement(int currentTurn, int oldPos, int position) {
        this.gamePanel.movePieceImage(currentTurn, oldPos, position);

    }

    /**
     * Handle the announcement of the winner if there is a winner
     * if winner, send message and end game.
     */
    @Override
    public void handleAnnounceWinner(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " wins the game\nThank you Playing\nExiting Program");
        System.exit(0);
    }

    /**
     * announce that the current player rolled a double to every view
     */
    @Override
    public void handleAnnounceRollingAgain(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " Rolled a double. They get to roll again!");
    }

    /**
     * update the dice rolls on the board
     * @param roll1 Integer, the first roll
     * @param roll2 Integer, the second roll
     */
    @Override
    public void handleUpdateRoll(int roll1, int roll2) {
        dice1.setIcon(new ImageIcon(diceImages.get(roll1-1)));
        dice2.setIcon(new ImageIcon(diceImages.get(roll2-1)));
    }

    /**
     * Handles the removal of a player piece from the board
     * @param e BoardEvent, the event occurring in the board
     */
    @Override
    public void handleRemoveOfPlayerPiece(BoardEvent e){
        this.gamePanel.removePieceFromBoard(e.getTurn(),e.getPlayer().getPosition());
    }

    /**
     * Handles the removal of a player view from the board
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    @Override
    public void handleRemoveOfPlayerView(BoardEvent e){
        this.sidePanel.removePlayerView(e.getTurn(), e.getPlayer());
    }


    /**
     * Update panel according to the views
     * @param player Player, the current player
     */
    @Override
    public void updateChoicePanel(Player player) {
        this.gameControlPanel.removeAll();
        boolean inJail = player.getInJail();
        boolean canPurchase = player.numberOfEstatePropertiesThatPlayerCanBuyHousesFor() != 0;
        boolean canSell = player.numberOfEstatePropertiesWithHouses() != 0;

        if (!inJail) {
            if (canPurchase && canSell) {
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.purchaseEstateHouses);
                this.gameControlPanel.add(this.sellHouses);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
            else if (canPurchase){
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.purchaseEstateHouses);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
            else if (canSell){
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.sellHouses);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
            else {
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
        } else {
            this.gameControlPanel.add(this.payOutOfJail);
            this.gameControlPanel.add(this.rollDouble);
            this.gameControlPanel.add(this.quit);
        }
        this.gameControlPanel.add(this.save);
        this.gameControlPanel.add(this.save_quit);
        this.gameControlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.gameControlPanel.revalidate();

    }

    //main
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        new BoardGUI();
    }

}
