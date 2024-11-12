package View;
import Events.*;
import Listener.BoardView;
import Model.GamePlayer.AI;
import Model.BoardModel;
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
import java.net.URL;
import java.util.ArrayList;

/**
 * Lớp BoardGUI, còn được gọi là lớp boardView
 * @author Phuc Thanh
 */
public class BoardGUI extends JFrame implements BoardView{
    public final static int GAME_WIDTH = 985;
    public final static int GAME_HEIGHT = 807;
    private final static int[] DICE_DIM = new int[]{96, 96};
    private final int ORIGINAL_STARTING_MONEY = 1500;
    private final int UK_STARTING_MONEY = 15000000;

    private GameDisplayPanel gamePanel;
    private PlayerDisplayPanel sidePanel;
    private JPanel gameControlPanel;

    private final JButton turnPass, quit, roll, payOutOfJail, rollDouble, purchaseEstateHouses, sellHouses, save, save_quit;
    private final ArrayList<Image> diceImages;
    private JLabel dice1, dice2;

    /**
     * Hàm khởi tạo mặc định cho BoardGUI
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
     * đặt lại khung (frame)
     */
    private void resetFrame(){
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }


    /**
     * tạo bảng menu chính với các lựa chọn: trò chơi mới, tải trò chơi, thoát
     * @param model BoardModel
     */
    private void mainMenu(BoardModel model) {
        resetFrame();
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Latest Game");
        JButton quitMainMenu = new JButton("Quit Game");
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
     * cho người chơi lựa chọn giữa phiên bản trò chơi Mỹ và Anh
     * @param model BoardModel
     */
    private void playerBoardChoice(BoardModel model){
        resetFrame();
        JButton UK = new JButton("Vietnamese Version");
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
     * thêm các bảng vào menu
     * @param topButton nút trên cùng
     * @param middleButton nút ở giữa
     * @param bottomButton nút dưới cùng
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
     * tạo một trò chơi mới sau khi phiên bản được chọn
     * @param initialCost Số tiền bắt đầu dưới dạng số nguyên
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
     * Tải trò chơi từ một tệp đã lưu
     * @param model mô hình bảng
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
     * Thêm phiên bản và kết quả của xúc xắc vào trò chơi
     * @param version String, phiên bản của trò chơi
     * @param dice1 int, kết quả của xúc xắc 1
     * @param dice2 int, kết quả của xúc xắc 2
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
     * Phương thức ghi đè để xử lý một tài sản không có chủ sở hữu. Người chơi có thể chọn bỏ qua hoặc mua tài sản.
     * Nếu mua, cung cấp cho người chơi các lựa chọn để mua nhà.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public boolean propertyNoOwner(PropertyEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getProperty().getName(), e.getProperty().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Phương thức ghi đè để xử lý một tài sản đã có chủ sở hữu.
     * Người chơi có thể chọn mua nhà/khách sạn hoặc bỏ qua.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyOwned(PropertyEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + ", Property they own. Moving to the next player"
        +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã dừng tại " + e.getProperty().getName() + ", Tài sản thuộc sở hữu của họ. Chuyển sang người chơi tiếp theo."
        );
    }

    /**
     * Phương thức ghi đè để xử lý một tài sản đã có chủ sở hữu nhưng không phải lượt của người chơi đó.
     * Nếu người chơi không có đủ tiền để trả tiền thuê, họ sẽ bị phá sản.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyRent(PropertyEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + " Owned by " + e.getProperty().getOwner().getPlayerName()
                + " and rent is $" + e.getCost() + "\nThey pay now"
        + "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã dừng tại " + e.getProperty().getName() + " Thuộc sở hữu của " + e.getProperty().getOwner().getPlayerName()
                        + " và giá thuê là $" + e.getCost() + "\n Trả tiền ngay!)"
                );
    }

    /**
     * Thông báo rằng người chơi không thể mua tài sản.
     */
    @Override
    public void announceCannotBuy(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getProperty().getName() + " but does not have enough Money, moving to the next player"
        +
                        "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đang cố mua " + e.getProperty().getName() + " mà hỏng có ddue tiền, chuyển sang người chơi tiếp theo)"
        );
    }

    /**
     * Thông báo rằng người chơi đã mua tài sản
     * @param e PropertyEvent, sự kiện tài sản
     */
    @Override
    public void announcePurchaseProperty(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getProperty().getName()
        + "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã mua thành công " + e.getProperty().getName() +")"
        );
    }


    // ** ENDING OF PROPERTY IMPLEMENTATION


    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//

    /**
     * Phương thức ghi đè để xử lý một tuyến đường sắt không có chủ sở hữu. Người chơi có thể bỏ qua hoặc mua tài sản.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public boolean railRoadNoOwner(RailRoadEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getRailRoad().getName(), e.getRailRoad().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Phương thức để xử lý một tuyến đường sắt mà người chơi sở hữu khi họ dừng lại trên đó.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadOwned(RailRoadEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + ", Property they own. Moving to the next player" +
                "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã dừng tại" + e.getRailRoad().getName() + ", Tài sản họ sở hữu. Chuyển sang người chơi tiếp theo )");
    }

    /**
     * Phương thức ghi đè để xử lý một tuyến đường sắt đã được sở hữu bởi người chơi không phải lượt của họ.
     * Nếu người chơi không đủ tiền để trả tiền thuê, họ sẽ bị phá sản.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadRent(RailRoadEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + " Owned by " + e.getRailRoad().getOwner().getPlayerName()
                + " and rent is $" + e.getRentCost() + "\nThey pay now"
                +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã dừng tại " + e.getRailRoad().getName() + " Được sở hữu bởi " + e.getRailRoad().getOwner().getPlayerName()
                + " và giá thuể là $" + e.getRentCost() + "\n Trả tiền cho họ ngay!)"
        );
    }

    /**
     * Phương thức ghi đè để thông báo rằng người chơi không thể đủ tiền để mua một tuyến đường sắt
     * @param e RailRoadEvent, sự kiện xảy ra trong RailRoad
     */
    @Override
    public void announceCannotBuyRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getRailRoad().getName() + " but does not have enough Money, moving to the next player"
        + "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đang cố mua " + e.getRailRoad().getName() + " mà hỏng có đủ tiền, Chuyển sang ngươi chơi tiếp theo)"
        );
    }

    /**
     * Phương thức ghi đè để thông báo rằng người chơi đã mua một tuyến đường sắt
     * @param e RailRoadEvent, sự kiện xảy ra trong RailRoad
     */
    @Override
    public void announcePurchaseRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getRailRoad().getName()
        +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã mua thành công " + e.getRailRoad().getName()+ ")"
        );
    }

    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    /**
     * Phương thức ghi đè để xử lý một tiện ích không có chủ sở hữu. Người chơi có thể bỏ qua hoặc mua tài sản.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public boolean UtilityNoOwner(UtilityEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getUtility().getName(), e.getUtility().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Phương thức để xử lý một tiện ích mà người chơi sở hữu khi họ hạ cánh lên đó.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityOwned(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player:  " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " a Utility they own. Moving to the next player"
        + "\n(Người chơi:  " + e.getPlayer().getPlayerName() + " đã dùng tại " + e.getUtility().getName() + " 1 tiện ích mà họ sở hữu. Chuyển sang người chơi tiếp theo)"
        );
    }

    /**
     * Phương thức ghi đè để xử lý một tiện ích đã được sở hữu bởi người chơi không phải lượt của họ.
     * Nếu người chơi không có đủ tiền để trả tiền thuê, họ sẽ bị phá sản.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityPay(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " Owned by " + e.getUtility().getOwner().getPlayerName() + ".\n" +
                "Number of utilities owned by owner is " + e.getUtility().getOwner().getNumOfUtilities() + ". so payment (dice roll * (10 if 2 utilities else 4)) is $" + e.getPayment()

                +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã dừng tại " + e.getUtility().getName() + " Thuộc sở hữu của " + e.getUtility().getOwner().getPlayerName() + ".\n" +
                        "Số tiện ích thuộc chủ sở hữu là " + e.getUtility().getOwner().getNumOfUtilities() + ". Vì vậy thanh toán (lăn xúc xắc * (10 nếu 2 tiện ích khác thì 4)) là $" + e.getPayment() +")"
        );
    }

    /**
     * Phương thức ghi đè để thông báo rằng người chơi không đủ tiền để chi trả cho một tiện ích.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void announceCannotBuyUtility(UtilityEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getUtility().getName() + " but does not have enough money, Moving to the next player"
        +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " đang cố mua " + e.getUtility().getName() + " nhưng mà hong có đủ tiền, Chuyển sang người chơi tiếp theo)"
        );
    }

    /**
     * Phương thức ghi đè để thông báo rằng người chơi đã mua một tiện ích.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void announcePurchaseOfUtility(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getUtility().getName() +
                        "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã mua thành công " + e.getUtility().getName() + ")"
                );
    }


    // ** END OF UTILITY IMPLEMENTATION **

    /**
     * Phương thức ghi đè để xử lý ô "Đỗ xe miễn phí".
     * Nếu người chơi dừng ở đây, họ sẽ nhận được số tiền ở "trung tâm" của bàn chơi.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void FreeParking(FreeParkingEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on free parking, they receive $" + e.getCenterMoney()
        +"\n ("+ e.getPlayer().getPlayerName() + " đã được miễn phí đậu xe , nhận được $" + e.getCenterMoney() +")");
    }

    /**
     * Phương thức ghi đè để xử lý việc thanh toán thuế.
     * Nếu người chơi hết tiền, họ sẽ bị phá sản.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void payTax(TaxEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on " + e.getLocation().getName() + ", they lose $" + e.getLocation().getCost() + " which goes into the center money"
         + "\n (" + e.getPlayer().getPlayerName() + " đã dừng tại " + e.getLocation().getName() + ", phải trả $" + e.getLocation().getCost() + " để xung vào công quỹ thành phố)"
        );
    }

    /**
     * Phương thức ghi đè để xử lý "Chỉ Thăm".
     * @param e A Events.LandOnJailEvent event.
     */
    @Override
    public void visiting(LandOnJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed " + e.getLandOnJail().getName()
        + "\n(" + e.getPlayer().getPlayerName() + " đã vô tù " + e.getLandOnJail().getName() + ")"
        );
    }


    /**
     * Phương thức ghi đè để đưa người chơi vào tù.
     * @param e Events.GoToJailEvent e.
     */
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on Go To Jail, they go to jail. Moving to the next player"
        + "\n(phải vào tù thôi ,họ sẽ phải đi tù. Chuyển sang người chơi tiếp theo)"
        );
        this.handlePlayerPieceMovement(e.getCurrentTurn(), e.getOldPos(), e.getNewPos());
    }


    /**
     * Phương thức ghi đè để xử lý thẻ miễn phí. Những thẻ này thay thế các thẻ Chance và Community Chest làm các thẻ giữ chỗ trên bàn chơi.
     * @param e Events.FreePassEvent event.
     */
    @Override
    public void FreePass(FreePassEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on a free pass, moving to the next player"
        +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " được lượt miễn phí, Chuyển sang người chơi tiếp theo)"
        );
    }


    /* HANDLES */

    /**
     * Thông báo việc một người chơi phá sản và bị loại khỏi trò chơi.
     * @param p Player, the player who is bankrupt
     */
    @Override
    public void handleAnnounceBankruptedPlayer(Player p){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + p.getPlayerName() + " has no more money. Removing player from game. Their properties are now back in estate!"
        +  "\n(Người chơi: " + p.getPlayerName() + " không đủ khả năng chi trả. Xóa người chơi khỏi trò chơi Tài sản của họ hiện đã trở lại bất động sản!) "
        );
    }


    /**
     * Phương thức ghi đè để xử lý khi người chơi thoát game.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerQuit(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " has quit the game. Removing player from game \n Their properties are now back in estate!"
        +"\n(Người chơi " + e.getPlayer().getPlayerName() + " đã thoát. Xóa người chơi khỏi trò chơi \nTài sản của họ hiện đã trở lại bất động sản)"
        );
    }

    /**
     * Phương thức ghi đè để xử lý thông báo người chơi qua lượt.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announcePlayerPass(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " passed the turn. Moving to the next player."
        + " \n(Người chơi: " + e.getPlayer().getPlayerName() + " đã bỏ qua lượt này. Mời người chơi tiếp theo.)"
        );
    }

    /**
     * Phương thức boolean ghi đè để xử lý việc thanh toán khi ở tù.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void payJail(boolean payed, BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (payed){
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " payed out of jail."
            + "\n(Người chơi: " + e.getPlayer().getPlayerName() + " đã mua chuộc cảnh sát để ra tù sớm .)"
            );
        }
        else{
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " attempted to pay out of jail but could not due to financial issues."
            +"\n(Người chơi: " + e.getPlayer().getPlayerName() + " cố gắng mua chuộc cảnh sát nhưng không thể vì vấn đề tài chính.)"
            );
        }
    }

    /**
     * Xử lý nút để tung xúc xắc đôi nhằm ra khỏi tù.
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleResultsOfRollingInJail(BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (e.getDoubles()){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " rolled a double! they are now out of jail and on the move!"
            + "\n("+e.getPlayer().getPlayerName() + " đã tung được cú đúp! Bây giờ họ có thể ra tù và di chuyển)"
            );
            return;
        }
        if (e.getPlayer().getTurnsInJail() != 0){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " attempted to roll out of jail. They failed and now have " + e.getPlayer().getTurnsInJail() + " left in jail. Moving tothe next player"
            + "\n(" +e.getPlayer().getPlayerName() + " đang cố gắng trốn thoát khỏi tù. Thất bại" + e.getPlayer().getTurnsInJail() + ". Chuyển sang người chơi tiếp theo)"
            );
        }
        else{
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " are now out of turns in jail, and now have to pay the $50 fine to get out. Moving to the next player"
            +"\n("+ e.getPlayer().getPlayerName() + "Hiện tại đã hết lượt trong tù, phải nộp phạt $50 để ra tù.  Chuyển sang người chơi tiếp theo)"
            );
        }
    }

    /**
     * Xử lý lựa chọn mua nhà.

     * @param e BoardEvent, the event occurring in the BoardModel
     */
    @Override
    public void handlePlayerChoiceToPurchaseHouses(BoardEvent e) {
        HouseController controller = new HouseController();
        controller.buyHouses(this, e.getPlayer());
    }

    /**
     * Xử lý lựa chọn bán nhà.

     * @param e BoardEvent, the event occurring in the BoardModel
     */
    @Override
    public void handlePlayerChoiceToSellHouses(BoardEvent e) {
        HouseController controller = new HouseController();
        controller.sellHouses(this, e.getPlayer());
    }

    /**
     * Vô hiệu hóa hoặc kích hoạt các nút.

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
     * Phương thức ghi đè thông báo khi một người chơi đã đến GO!.

     */
    @Override
    public void announceReachingGo(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " received $" + BoardModel.GO_MONEY + " for reaching GO"
        + "\n("+e.getPlayer().getPlayerName() + " nhận $" + BoardModel.GO_MONEY + " để đạt GO )"
        );
        this.handleUpdateSidePanelDisplay(e);
    }


    /**
     * Xử lý lượt tiếp theo của người chơi theo giao diện.
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleNextTurnDisplay(BoardEvent e, int updatedTurn){
        for (int i = 0; i<e.getNumOfPlayers(); i++){
            this.sidePanel.updateCurrentTurn(updatedTurn, i, e.getPlayerByIndex(i));
        }
    }

    /**
     * Xử lý việc cập nhật bảng điều khiển bên cho mỗi người chơi trong giao diện.

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
     * Xử lý việc hiển thị quân cờ trên bảng cho mỗi người chơi trong giao diện.

     * @param currentTurn Integer, the current turn
     * @param oldPos Integer, the old position
     * @param position Integer, the new position
     */
    @Override
    public void handlePlayerPieceMovement(int currentTurn, int oldPos, int position) {
        this.gamePanel.movePieceImage(currentTurn, oldPos, position);

    }

    /**
     * Xử lý việc thông báo người chiến thắng nếu có người chiến thắng.
     * Nếu có người chiến thắng, gửi thông báo và kết thúc trò chơi.
     */
    @Override
    public void handleAnnounceWinner(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " wins the game! Congrats"
            + "\n(" +e.getPlayer().getPlayerName() + " đã thắng trò chơi! Chúc mừng bạn !)");
        System.exit(0);
    }

    /**
     * Thông báo rằng người chơi hiện tại đã tung được đôi cho tất cả các view.

     */
    @Override
    public void handleAnnounceRollingAgain(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " Rolled a double. They get to roll again!"
        + "\n("+ e.getPlayer().getPlayerName() + " Tung được cú đúp. Họ sẽ nhận thêm lượt tung ! )"
        );
    }

    /**
     * Cập nhật kết quả gieo xúc xắc trên bảng.
     * @param roll1 Integer, kết quả gieo xúc xắc thứ nhất
     * @param roll2 Integer, kết quả gieo xúc xắc thứ hai
     */
    @Override
    public void handleUpdateRoll(int roll1, int roll2) {
        dice1.setIcon(new ImageIcon(diceImages.get(roll1-1)));
        dice2.setIcon(new ImageIcon(diceImages.get(roll2-1)));
    }

    /**
     * Xử lý việc loại bỏ quân cờ của người chơi khỏi bảng.
     * @param e BoardEvent, sự kiện xảy ra trên bảng
     */
    @Override
    public void handleRemoveOfPlayerPiece(BoardEvent e){
        this.gamePanel.removePieceFromBoard(e.getTurn(),e.getPlayer().getPosition());
    }

    /**
     * Xử lý việc loại bỏ view của người chơi khỏi bảng.
     * @param e BoardEvent, sự kiện xảy ra trong BoardModel
     */
    @Override
    public void handleRemoveOfPlayerView(BoardEvent e){
        this.sidePanel.removePlayerView(e.getTurn(), e.getPlayer());
    }


    /**
     * Cập nhật bảng điều khiển theo các view.
     * @param player Player, người chơi hiện tại
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
