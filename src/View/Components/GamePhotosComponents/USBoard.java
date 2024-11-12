package View.Components.GamePhotosComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * lớp để lấy hình ảnh cho phiên bản bảng Mỹ
 * @author Phuc Thanh
 */
public class USBoard implements BoardGetterFunctionality{

    /**
     * lấy các hình ảnh phía trên của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía trên
     */
    @Override
    public ArrayList<Image> getTopPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/FREE_PARKING.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/RED1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/CHANCE2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/RED2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/RED3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/RAILROAD3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/WATERWORKS.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/TOP/GO_TO_JAIL.png"))).getImage());
        }};
    }

    /**
     * lấy các hình ảnh bên trái của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh bên trái
     */
    @Override
    public ArrayList<Image> getLeftPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/COMMUNITY_CHEST2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/RAILROAD2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ELECTRIC1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK1.png")).getImage());
        }};
    }

    /**
     * lấy các hình ảnh bên phải của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh bên phải
     */
    @Override
    public ArrayList<Image> getRightPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN2.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/COMMUNITY_CHEST3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/RAILROAD4.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/CHANCE3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/DARK_BLUE1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/LUXURY_TAX.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/MonopolyBoardImages/RIGHT/DARK_BLUE2.png"))).getImage());
        }};
    }

    /**
     * lấy các hình ảnh dưới cùng của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh dưới cùng
     */
    @Override
    public ArrayList<Image> getBottomPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/JAIL.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/CHANCE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/RAILROAD1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/INCOME_TAX.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/BROWN2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/COMMUNITY_CHEST1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/BROWN1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BOTTOM/GO.png"))).getImage());
        }};
    }

    /**
     * lấy thanh ngang của bảng phân cách hai hình ảnh
     * @return Image, thanh ngang
     */
    @Override
    public Image getHorizontalLine(){
        return new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BlackBorders/HORIZONTAL_BAR.png"))).getImage();
    }

    /**
     * lấy thanh dọc của bảng phân cách hai hình ảnh
     * @return Image, thanh dọc
     */
    @Override
    public Image getVerticalLine(){
        return new ImageIcon((this.getClass().getResource("/MonopolyBoardImages/BlackBorders/VERTICAL_BAR.png"))).getImage();
    }

}
