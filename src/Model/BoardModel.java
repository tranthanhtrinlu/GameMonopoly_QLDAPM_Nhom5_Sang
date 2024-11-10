package Model;
import Events.BoardEvent;
import Listener.BoardView;
import Model.BoardElements.*;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import Model.GamePlayer.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Lớp đại diện cho mô hình trò chơi Monopoly. Thiết lập trò chơi và bàn cờ.
 */
public class BoardModel {
    public static final int MAX_PLAYERS = 4;
    public static final int SIZE_OF_BOARD = 39; // từ 0 đến 39
    public static final int GO_MONEY = 200;
    public static final int JAIL_POSITION = 10; // vị trí nhà tù là 11 - 1
    public static final int TOTAL_UTILITIES = 2;
    private static final int ROLLING_DICE_DELAY = 10;
    public static final String STARTING_POSITION = "Go";
    public static int centerMoney = 0;

    private final List<Location> board;
    private final List<BoardView> views;
    private int currentTurn;
    private int numberOfPlayers;
    private Status status;
    private int roll1;
    private int roll2;
    private final ArrayList<Player> gamePlayers;
    private boolean payed;
    private String version;

    /**
     * Định nghĩa các màu cho bàn cờ
     */
    public enum Color{
        BROWN, LIGHTBLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARKBLUE, NONE
    }

    
    /**
     * Enum cho các chuỗi đại diện trong XML khi tải và lưu
     */
    public enum StringRepresentationOfXML{
        JAR_SAVED("./savedFile.xml"), JAR_LOAD_UK("./UKBoardModel.xml"), JAR_LOAD_US("./NewBoardModel.xml"),
        LOAD_US("src/LoadXML/NewBoardModel.xml"), LOAD_UK("src/LoadXML/UKBoardModel.xml"), SAVED("src/SaveXML/savedFile.xml");

        private final String path;

        /**
         * Constructor for String Representation
         * @param path String, the path
         */
        StringRepresentationOfXML(String path){
            this.path = path;
        }

        /**
         * Get the path of the Given XML file
         * @return String, the path
         */
        public String getPath(){
            return this.path;
        }
    }

  /**
     * Trạng thái của trò chơi
     */
    public enum Status{
        FINISHED, UNFINISHED
    }

       /**
     * Các loại bàn cờ mà BoardModel có thể chơi
     */
    public enum TypeOfBoards{
        UK("UK"), US("US");

        private final String version;
        TypeOfBoards(String version){
            this.version = version;
        }

        /**
         * Lấy phiên bản của trò chơi dưới dạng chuỗi
         */
        public String getVersion() {
            return this.version;
        }
    }

     /**
     * Các lựa chọn của người chơi trong suốt trò chơi
     */
    public enum PlayerChoice{
        ROLL(1), QUIT(2), PASS(3), PAY_OUT(4), ROLL_OUT(5), BUY_HOUSE(6), SELL_HOUSE(7), SAVE(8), SAVE_QUIT(9);

        private final int choice;

         /**
         * Constructor cho các lựa chọn của người chơi
         * @param choice Số nguyên, lựa chọn
         */
        PlayerChoice(int choice){
            this.choice = choice;
        }


        public int getChoice() {
            return choice;
        }
    }

    /**
     * Các thông báo khi chuyển lượt cho người chơi tiếp theo
     */
    public enum nextPlayerTurnAnnouncements{
        ROLL_AGAIN, ROLL_OUT, PAY_OUT, PASS, NONE
    }


    public BoardModel(){
         this(0,0,0);
    }

     /**
     * Constructor cho BoardModel
     * @param currentTurn Lượt hiện tại
     * @param roll1 Kết quả lần tung xúc xắc thứ nhất
     * @param roll2 Kết quả lần tung xúc xắc thứ hai
     */
    public BoardModel(int currentTurn, int roll1, int roll2) {
        this.gamePlayers = new ArrayList<>();
        this.board = new ArrayList<>();
        this.views = new ArrayList<>();
        this.currentTurn = currentTurn;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.status = Status.UNFINISHED;
        this.payed = false;
        this.version = "";
    }



    public void setGameVersion(String version){
        this.version = version;
    }


    public String getGameVersion(){
        return this.version;
    }

    /**
     * nhận được bao nhiêu tiền ở trung tâm
     * @return Integer centerMoney
     */
    public static int getCenterMoney() {
        return BoardModel.centerMoney;
    }

    /**
     * đặt bao nhiêu tiền
     * @param centerMoney 
     */
    public static void setCenterMoney(int centerMoney) {
        BoardModel.centerMoney = centerMoney;
    }


    public static void addToCenterMoney(int add){
        BoardModel.centerMoney += add;
    }


    /**
     * add người chơi
     * @param player Player, the player
     */
    public void addGamePlayers(Player player) {
        this.gamePlayers.add(player);
    }

    public int getNumberOfPlayers() {
        return this.gamePlayers.size();
    }


    public int getSizeOfBoard(){return this.board.size();}

    /**
     * lấy giá trị của lần tung xúc xắc đầu tiên
     * @return Integer
     */
    public int getRoll1() {
        return this.roll1;
    }

    /**
     * lấy giá trị của lần tung xúc xắc lần thứ 2
     * @return Integer,
     */
    public int getRoll2() {
        return this.roll2;
    }

    /**
     * lấy số lượng người chơi thực sự trong trò chơi 
     * @return Integer
     */
    public int getNumOfPlayers() {
        return this.numberOfPlayers;
    }


    public Player getPlayersByIndex(int i) {
        return this.gamePlayers.get(i);
    }

    public void setNumberOfPlayers(int num){
        this.numberOfPlayers = num;
    }


    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * xác định xem người chơi đã nhân đôi hay chưa
     * @return Đúng nếu nhân đôi được tung ra, ngược lại là sai.
     */
    private boolean rollDiceOfTwo(){
        Random r = new Random();
        this.roll1 = r.nextInt(6) +  1;
        this.roll2 = r.nextInt(6) + 1;
        return this.roll1 == this.roll2;
    }

    /**
     * Phương thức thêm đối tượng Listener.BoardView vào ArrayList của BoardViews.
     * @param view 
     */
    public void addView(BoardView view){
        this.views.add(view);
    }

     /**
     * Phương thức khởi tạo bàn cờ. Thêm tất cả các phần tử cần thiết bao gồm các tài sản, đường sắt và tiện ích.
     * @param path Chuỗi, đường dẫn tệp
     */
    public boolean initializeBoard(String path){
        try{
            File load = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(load);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Location");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr).getFirstChild().getNextSibling();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    this.parseAndAddDataLocationToBoard(node);
                }
            }
            return true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Didn't load path " + path);
            return false;
        }
    }

    /**
     * Dựa vào phần tử node, thêm Location vào bảng cờ dựa trên thông tin của node
     * @param node Node, phần tử node của Location
     */
    private void parseAndAddDataLocationToBoard(Node node){
        switch (node.getNodeName()) {
            case "FreePass": {
                this.board.add(FreePass.createNewFreePass(node));
                break;
            }
            case "Property": {
                this.board.add(Property.createNewProperty(node));
                break;
            }
            case "Tax": {
                this.board.add(Tax.createNewTax(node));
                break;
            }
            case "RailRoad": {
                this.board.add(RailRoad.createNewRailRoad(node));
                break;
            }
            case "LandOnJail": {
                this.board.add(LandOnJail.createNewLandOnJail(node));
                break;
            }
            case "Utility": {
                this.board.add(Utility.createNewUtility(node));
                break;
            }
            case "FreeParking": {
                this.board.add(FreeParking.createNewFreeParking(node));
                break;
            }
            case "GoToJail": {
                this.board.add(GoToJail.createNewGoToJail(node));
                break;
            }
        }
    }


    /**
     * Phương thức duyệt qua danh sách board và thêm tất cả listener vào mỗi vị trí.
     * @param view Đối tượng Listener.BoardView
     */
    public void addViewToListener(BoardView view){
        for (Location location : this.board){
            location.addListener(view);
        }
    }

   /**
 * Phương thức xử lý lượt tiếp theo của người chơi.
 */
    public void nextTurn() {
        this.currentTurn++;
        while (true){
            if (this.currentTurn == this.gamePlayers.size())
                this.currentTurn = 0;

            if (this.gamePlayers.get(this.currentTurn).getOut()){
                this.currentTurn++;
                continue;
            }
            break;
        }
    }

   /**
 * Phương thức cập nhật danh sách người chơi nếu một người chơi thua hoặc bỏ cuộc.
 * @return True nếu danh sách người chơi được cập nhật, ngược lại là False.
 */
    public boolean checkBankrupt() {
        Player p = this.gamePlayers.get(this.currentTurn);
        if (p.getMoneyAmount() == 0){
            this.removePlayer();
            return true;
        }
        return false;
    }

  /**
 * Xóa người chơi hiện tại khỏi trò chơi, đặt thuộc tính "out" cho người chơi,
 * chuyển sang lượt tiếp theo, nếu chỉ còn một người chơi thì thông báo người thắng.
 */
    private void removePlayer(){
        Player p = this.gamePlayers.get(this.currentTurn);
        p.setOut(true);
        p.bankrupted();
        nextTurn();
        this.numberOfPlayers -= 1;
        updateStatus();
    }


    public Status getStatus() {
        return this.status;
    }

    /**
 * Kiểm tra xem có người thắng hay chưa. Nếu có, chuyển trạng thái thành "FINISHED".
 */
    public void updateStatus(){
        if (this.numberOfPlayers == 1){
            this.status = Status.FINISHED;
        }
    }

  /**
 * Nếu người chơi hiện tại là AI, AI sẽ tung xúc xắc nếu không bị giam,
 * ngược lại sẽ thử tung xúc xắc đôi.
 * @return boolean, true nếu là AI, ngược lại là false
 */
    private boolean playAI(){
        Player p = this.gamePlayers.get(this.currentTurn);
        if(p instanceof AI){
            playCurrPlayerTurn(((AI) p).playAI());
            return true;
        }
        return false;
    }

   /**
 * Xử lý xem lượt hiện tại có phải của AI không. Nếu là AI thì vô hiệu hóa nút chơi,
 * ngược lại kích hoạt và cập nhật.
 */
    private void handleIfAITurn(){
        if(!playAI()){
            for (BoardView view : views){
                view.buttonEnableCondition(true);
                view.updateChoicePanel(gamePlayers.get(currentTurn));
            }
        }
    }

  /**
 * Lấy loại thông báo hiển thị cho người xem dựa trên sự kiện
 * @param view BoardView, người xem hiện tại
 * @param announcement nextPlayerTurnAnnouncements, trình xử lý thông báo
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void getTypeOfViewAnnouncement(BoardView view, nextPlayerTurnAnnouncements announcement, BoardEvent e){
        switch(announcement){
            case ROLL_AGAIN:
                view.handleAnnounceRollingAgain(e);
                break;
            case PASS:
                view.announcePlayerPass(e);
                break;
            case ROLL_OUT:
                view.handleResultsOfRollingInJail(e);
                break;
            case PAY_OUT:
                view.payJail(payed, e);
                break;
            default:
                break;
        }
    }

  /**
 * Phương thức xử lý khi người chơi bị phá sản.
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handleBankruptcy(BoardEvent e){
        for (BoardView view : views){
            view.handleAnnounceBankruptedPlayer(e.getPlayer());
            view.handleRemoveOfPlayerPiece(e);
            view.handleRemoveOfPlayerView(e);

            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
            if (status != Status.UNFINISHED){
                view.handleAnnounceWinner(e);
            }
        }
        handleIfAITurn();
    }

    /**
 * Xử lý chuyển sang lượt người chơi tiếp theo và thông báo kết quả của người chơi hiện tại.
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 * @param announcements nextPlayerTurnAnnouncements, thông báo về kết quả của người chơi hiện tại
 */
    private void handleTransitionToNextPlayerTurn(BoardEvent e, nextPlayerTurnAnnouncements announcements){
        nextTurn();
        for (BoardView view : views){
            getTypeOfViewAnnouncement(view, announcements, e);
            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
        }
        payed = false;
        handleIfAITurn();
    }

    /**
 * Xử lý khi người chơi tung xúc xắc đôi và có thể chơi lại
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handlePlayerPlayAgainAnnouncement(BoardEvent e){
        for (BoardView view : views){
            getTypeOfViewAnnouncement(view, nextPlayerTurnAnnouncements.ROLL_AGAIN, e);
            view.handleUpdateSidePanelDisplay(e);
        }
        handleIfAITurn();
    }

   /**
 * Xử lý khi người chơi bỏ cuộc
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handlePlayerQuit(BoardEvent e){
        removePlayer();
        for (BoardView view : this.views){
            view.handlePlayerQuit(e);
            if (status != Status.UNFINISHED){
                view.handleAnnounceWinner(e);
            }
            view.handleRemoveOfPlayerPiece(e);
            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
        }
        handleIfAITurn();
    }

 /**
 * Xử lý khi người chơi trả tiền để ra khỏi tù
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handlePlayerPayingOutOfJail(BoardEvent e){
        Player p = e.getPlayer();
        Location place = e.boardElementByIndex(p.getPosition());
        if (p.payJail()){
            p.setCurrLocation(place.getName());
            p.setInJail(false);
            payed = true;
        }
        handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.PAY_OUT);
    }

   /**
 * Xử lý khi người chơi chọn mua nhà
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handleBuyingOfHouses(BoardEvent e){
        for (BoardView view : this.views){
            view.handlePlayerChoiceToPurchaseHouses(e);
            view.handleUpdateSidePanelDisplay(e);
            view.updateChoicePanel(e.getPlayer());
        }
    }

    /**
 * Xử lý khi người chơi chọn bán nhà
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void handleSellingOfHouses(BoardEvent e){
        for (BoardView view : this.views){
            view.handlePlayerChoiceToSellHouses(e);
            view.handleUpdateSidePanelDisplay(e);
            view.updateChoicePanel(e.getPlayer());
        }
    }

   /**
 * Xử lý khi người chơi tung xúc xắc, hiển thị chuyển động của người chơi, 
 * vận hành chức năng của vị trí đến, và chạy trạng thái của trò chơi tương ứng.
 * Nếu người chơi bị phá sản, xử lý bằng phương thức phá sản,
 * nếu không thì chuyển lượt sang người tiếp theo hoặc cho phép chơi lại nếu không ở trong tù.
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void movePlayerFunctionality(BoardEvent e){
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            private final Player p = e.getPlayer();
            private int pos = p.getPosition();
            private final int sum = roll1 + roll2;
            @Override
            public void run() {
                if (p.getSumOfMovement(p.getPosition(), sum) != pos){
                    int oldPos = pos;
                    boolean go = false;
                    pos++;
                    if (pos > SIZE_OF_BOARD){
                        go = true;
                        pos = 0;
                    }
                    for (BoardView view : views){
                        view.handlePlayerPieceMovement(currentTurn, oldPos, pos);
                        if (go){
                            view.announceReachingGo(e);
                        }
                    }
                }
                else{
                    p.movePlayer(sum);
                    Location place = e.boardElementByIndex(p.getPosition());
                    p.setCurrLocation(place.getName());
                    place.locationElementFunctionality(p, sum, currentTurn);

                    if (checkBankrupt()){
                        handleBankruptcy(e);
                    }else{
                        if (!e.getDoubles()){
                            handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.NONE);
                        }
                        else{
                            if (p.getInJail()){
                                handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.NONE);
                            }
                            else{
                                handlePlayerPlayAgainAnnouncement(e);
                            }
                        }
                    }
                    timer2.cancel();
                }
            }
        }, 0, 200);
    }
/**
 * Xử lý khi người chơi cố gắng tung xúc xắc để ra khỏi tù.
 * Nếu người chơi tung xúc xắc đôi, thông báo và thực hiện di chuyển, ngược lại xử lý ở tù.
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 */
    private void rollingOutOfJailFunctionality(BoardEvent e){
        if (e.getDoubles()){
            e.getPlayer().setInJail(false);
            for (BoardView view : views){
                view.handleResultsOfRollingInJail(e);
            }
            movePlayerFunctionality(e);
        }
        else{
            e.getPlayer().setTurnsInJail(e.getPlayer().getTurnsInJail() - 1);
            if (e.getPlayer().getTurnsInJail() == 0){
                e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - 50);
            }
            if (checkBankrupt()){
                handleBankruptcy(e);
            }else{
                handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.ROLL_OUT);
            }
        }
    }

   /**
 * Xử lý khi người chơi tung xúc xắc chung
 * Hiển thị hoạt ảnh tung xúc xắc
 * Nếu người chơi tung xúc xắc, chạy phương thức movePlayerFunctionality. Ngược lại, chạy rollingOutOfJailFunctionality
 * @param e BoardEvent, sự kiện xảy ra trong BoardModel
 * @param choice Integer, lựa chọn của người chơi
 */
    private void handleRollingDice(BoardEvent e, int choice){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int counter = 0;
            private final Random r = new Random();
            @Override
            public void run() {
                if (counter < ROLLING_DICE_DELAY) {
                    counter++;
                    int lastRoll1 = r.nextInt(6) + 1;
                    int lastRoll2 = r.nextInt(6) + 1;
                    for (BoardView view : views){
                        view.buttonEnableCondition(false);
                        if (counter == ROLLING_DICE_DELAY)
                            view.handleUpdateRoll(roll1, roll2);
                        else
                            view.handleUpdateRoll(lastRoll1, lastRoll2);
                    }
                } else {
                    if (choice == PlayerChoice.ROLL.getChoice()){
                        movePlayerFunctionality(e);
                    }
                    else{
                        rollingOutOfJailFunctionality(e);
                    }
                    timer.cancel();
                }
            }
        }, 0, 200);
    }

  /**
 * Xử lý khi người chơi quyết định lưu trò chơi.
 * @param filename String, tên của tệp để lưu thông tin
 */
    private boolean handleSaveToXML(String filename){
        try{
            File save = new File(filename);
            BufferedWriter out = new BufferedWriter(new FileWriter(save));
            out.write(this.toXML());
            out.close();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

  /**
 * Trả về biểu diễn dưới dạng XML của mô hình bàn cờ dưới dạng chuỗi duy nhất
 * @return str, chuỗi chứa biểu diễn XML của mô hình bàn cờ này
 */
    private String toXML(){
        String str = "<boardModel>\n";
        str += "\t<version>" + this.version + "</version>\n";
        str += "\t<centerMoney>" + BoardModel.centerMoney + "</centerMoney>\n";
        str += "\t<numberOfPlayers>" + this.numberOfPlayers + "</numberOfPlayers>\n";
        str += "\t<currentTurn>" + this.currentTurn + "</currentTurn>\n";
        str += "\t<roll1>" + this.roll1 + "</roll1>\n";
        str += "\t<roll2>" + this.roll2 + "</roll2>\n";

        for(Player p : this.gamePlayers){
            if (p instanceof User){
                str += p.toXML("User");
                continue;
            }
            str += p.toXML("AI");
        }
        str += "</boardModel>\n";
        return str;
    }

    /**
 * Tạo bàn cờ dựa trên phiên bản mô hình bàn cờ đã được chỉ định
 */
    public void createBoard() {
        if (!initializeBoard(getVersionTypeJar()))
            initializeBoard(getVersionType());
    }

    /**
 * Lấy đường dẫn phiên bản trong tệp JAR
 * @return String, đường dẫn
 */
    private String getVersionTypeJar(){
        if (this.version.equals(TypeOfBoards.US.getVersion())){
            return StringRepresentationOfXML.JAR_LOAD_US.getPath();
        }
        return StringRepresentationOfXML.JAR_LOAD_UK.getPath();
    }

  /**
 * Lấy đường dẫn phiên bản trong IDE
 * @return String, đường dẫn
 */
    private String getVersionType(){
        if (this.version.equals(TypeOfBoards.US.getVersion())){
            return StringRepresentationOfXML.LOAD_US.getPath();
        }
        return StringRepresentationOfXML.LOAD_UK.getPath();
    }

    /**
     * Khởi tạo mô hình bàn cờ với tài liệu XML đã phân tích cú pháp
     * @param doc Document, tài liệu XML đã phân tích cú pháp
     * @throws ParserConfigurationException Ném ngoại lệ nếu có lỗi phân tích cú pháp
     * @throws SAXException Ném ngoại lệ nếu có lỗi SAX
     * @throws IOException Ném ngoại lệ nếu có lỗi IO
     */
    private void initializeLoadedBoardModel(Document doc) throws IOException, SAXException, ParserConfigurationException {
        String version = doc.getElementsByTagName("version").item(0).getTextContent();
        int diceRoll1 = Integer.parseInt(doc.getElementsByTagName("roll1").item(0).getTextContent());
        int diceRoll2 = Integer.parseInt(doc.getElementsByTagName("roll2").item(0).getTextContent());
        int currentTurn = Integer.parseInt(doc.getElementsByTagName("currentTurn").item(0).getTextContent());
        int centerMoney = Integer.parseInt(doc.getElementsByTagName("centerMoney").item(0).getTextContent());
        int numOfPlayers = Integer.parseInt(doc.getElementsByTagName("numberOfPlayers").item(0).getTextContent());
        this.roll1 = diceRoll1;
        this.roll2 = diceRoll2;
        this.currentTurn = currentTurn;
        this.numberOfPlayers = numOfPlayers;
        BoardModel.centerMoney = centerMoney;
        this.version = version;
        createBoard();
    }

   /**
 * Tải một tệp XML đã lưu được chỉ định bởi người chơi trong menu trò chơi
 * @param path String, đường dẫn tệp
 */
    public boolean loadSavedXML(String path) {
        try{
            Player p;
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodePlayerList = doc.getElementsByTagName("player");
            initializeLoadedBoardModel(doc);
            for (int i = 0; i < nodePlayerList.getLength(); i++){
                Node player = nodePlayerList.item(i);
                if (player.getNodeType() == Node.ELEMENT_NODE){
                    Element playerElement = (Element) player;
                    p = Player.createPlayer(playerElement);
                    this.addGamePlayers(p);
                    p.parseAddPlayerProperties(playerElement, this.board);
                    p.parseAddPlayerOwnedColors(playerElement);
                }
            }
            return true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Failed to Load " + path);
            return false;
        }
    }
/**
 * Xử lý lưu trò chơi vào tệp XML. Nếu không lưu được tại đường dẫn mặc định, sẽ lưu vào đường dẫn dự phòng.
 */
    private void handleSavedFunctionality(){
        if (!handleSaveToXML(StringRepresentationOfXML.SAVED.getPath()))
            handleSaveToXML(StringRepresentationOfXML.JAR_SAVED.getPath());
    }

   /**
 * Phương thức mô phỏng lượt chơi của người chơi dựa trên nhiều tình huống. Tung xúc xắc và xác định xem người chơi có ở trong tù không.
 * Đưa ra các lựa chọn cho người chơi như di chuyển, bỏ lượt hoặc thoát trò chơi.
 * @param choice Lựa chọn của người chơi
 */
    public void playCurrPlayerTurn(int choice){
        boolean doubles = rollDiceOfTwo();
        BoardEvent e = new BoardEvent(this, this.board, doubles, this.roll1, this.roll2, this.gamePlayers.get(this.currentTurn), this.currentTurn, this.gamePlayers);

        if (choice == PlayerChoice.ROLL.getChoice()){ // roll
            handleRollingDice(e, choice);
        }
        else if (choice == PlayerChoice.QUIT.getChoice()){ // quit
            handlePlayerQuit(e);
        }
        else if (choice == PlayerChoice.PASS.getChoice()){ // pass
            handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.PASS);
        }
        else if (choice == PlayerChoice.PAY_OUT.getChoice()){ // pay out of jail
            handlePlayerPayingOutOfJail(e);
        }
        else if (choice == PlayerChoice.ROLL_OUT.getChoice()){ // roll double out of jail
            handleRollingDice(e, choice);
        }
        else if (choice == PlayerChoice.BUY_HOUSE.getChoice()){ // purchase house
            handleBuyingOfHouses(e);
        }
        else if (choice == PlayerChoice.SELL_HOUSE.getChoice()){ // sell house
            handleSellingOfHouses(e);
        }else if (choice == PlayerChoice.SAVE.getChoice()){
            handleSavedFunctionality();
        }else if (choice == PlayerChoice.SAVE_QUIT.getChoice()){
            handleSavedFunctionality();
            System.exit(0);
        }
    }
}
