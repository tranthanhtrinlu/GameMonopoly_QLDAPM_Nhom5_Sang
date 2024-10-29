package View.Components;

import Model.BoardModel;
import Model.GamePlayer.Player;
import Model.GamePlayer.User;
import View.Components.GamePhotosComponents.BoardGetterFunctionality;
import View.Components.GamePhotosComponents.UKBoard;
import View.Components.GamePhotosComponents.USBoard;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GameDisplayPanel for the BoardGUI
 * 
 * @author Tony Massaad
 */
public class GameDisplayPanel extends JPanel {
    private final static int DIM1 = 96;
    private final static int DIM2 = 64;
    private final static int DIM3 = 3;

    private final static int BOARD_START_TOP_X_POS = 0; // after the side panel
    private final static int BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS = DIM1; // after the top right corner image
    private final static int BOARD_START_RIGHT_X_POS = (DIM2 * 9) + DIM1;
    private final static int BOARD_START_BOTTOM_Y_POS = (DIM2 * 9) + DIM1; // 8 middle small properties width + the
                                                                           // corner width
    private final static int[] CORNER_WIDTH_HEIGHT = new int[] { DIM1, DIM1 };
    private final static int[] TOP_BOTTOM_WIDTH_HEIGHT = new int[] { DIM2, DIM1 };
    private final static int[] MIDDLE_WIDTH_HEIGHT = new int[] { DIM1, DIM2 };
    private final static int[] H_LINE_WIDTH_HEIGHT = new int[] { DIM1, DIM3 };
    private final static int[] V_LINE_WIDTH_HEIGHT = new int[] { DIM3, DIM1 };

    private final ImageIcon verticalBorder, horizontalBorder;

    private final ArrayList<JLabel> playerPieces;
    private final ArrayList<JPanel> playerPiecesDisplay;
    private final ArrayList<Image> topPhotos;
    private final ArrayList<Image> bottomPhotos;
    private final ArrayList<Image> leftPhotos;
    private final ArrayList<Image> rightPhotos;

    // bua sau tiep tuc o cho nay nhe

}
