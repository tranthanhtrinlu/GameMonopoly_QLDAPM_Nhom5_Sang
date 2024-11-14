package View.Components.GamePhotosComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Lớp để lấy các hình ảnh của bảng phiên bản UK
 * @author Phuc Thanh
 */
public class VietnameseBoard implements BoardGetterFunctionality{

    /**
     * Lấy các hình ảnh ở phía trên của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía trên
     */
    @Override
    public ArrayList<Image> getTopPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/FREE_PARKING.png"))).getImage());
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
        }};
    }
    /**
     * Lấy các hình ảnh ở phía bên trái của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía bên trái
     */
    @Override
    public ArrayList<Image> getLeftPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/COMMUNITY_CHEST2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/RAILROAD2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ELECTRIC1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK1.png")).getImage());
        }};
    }
    /**
     * Lấy các hình ảnh ở phía dưới của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía dưới
     */
    @Override
    public ArrayList<Image> getBottomPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/JAIL.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/CHANCE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/RAILROAD1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/INCOME_TAX.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/BROWN2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/COMMUNITY_CHEST1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/BROWN1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/GO.png"))).getImage());
        }};
    }
    /**
     * Lấy các hình ảnh ở phía bên phải của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía bên phải
     */
    @Override
    public ArrayList<Image> getRightPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN2.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/COMMUNITY_CHEST3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/RAILROAD4.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/CHANCE3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/DARK_BLUE1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/LUXURY_TAX.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/DARK_BLUE2.png"))).getImage());
        }};
    }
    /**
     * Lấy thanh ngang của bảng phân tách hai hình ảnh
     * @return Image, thanh ngang
     */
    @Override
    public Image getHorizontalLine(){
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/HORIZONTAL_BAR.png"))).getImage();
    }

    /**
     * Lấy thanh dọc của bảng phân tách hai hình ảnh
     * @return Image, thanh dọc
     */
    @Override
    public Image getVerticalLine() {
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/VERTICAL_BAR.png"))).getImage();
    }
}
