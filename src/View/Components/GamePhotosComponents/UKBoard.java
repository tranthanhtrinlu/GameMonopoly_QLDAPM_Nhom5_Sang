package View.Components.GamePhotosComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * class for getting the images of board version UK
 * 
 * @author Tony Massaad
 */
public class UKBoard implements BoardGetterFunctionality {

    /**
     * get the top images of the respective board
     * 
     * @return ArrayList<Image>, a list of top images
     */
    @Override
    public ArrayList<Image> getTopPhotos() {
        return new ArrayList<>() {
            {
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/FREE_PARKING.png")))
                        .getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED1.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/CHANCE2.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED2.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED3.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RAILROAD3.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW1.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW2.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/WATERWORKS.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW3.png"))).getImage());
                add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/GO_TO_JAIL.png"))).getImage());
            }
        };
    }
    /**
     * get the left images of the respective board
     * 
     * @return ArrayList<Image>, a list of left images
     */

    // 1
    /**
     * get the bottom images of the respective board
     * 
     * @return ArrayList<Image>, a list of bottom images
     */
    // 2
    /**
     * get the right images of the respective board
     * 
     * @return ArrayList<Image>, a list of right images
     */

    // 3
    /**
     * get the horizontal bar of the board that separates two images
     * 
     * @return Image, the horizontal bar
     */
    @Override
    public Image getHorizontalLine() {
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/HORIZONTAL_BAR.png")))
                .getImage();
    }

    /**
     * get the vertical bar of the board that separates two images
     * 
     * @return Image, the vertical bar
     */
    /**
     * get the vertical bar of the board that separates two images
     * 
     * @return Image, the vertical bar
     */
    @Override
    public Image getVerticalLine() {
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/VERTICAL_BAR.png"))).getImage();
    }
}
