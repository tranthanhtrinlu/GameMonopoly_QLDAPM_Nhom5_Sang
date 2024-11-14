package View.Components.GamePhotosComponents;
import java.awt.*;
import java.util.ArrayList;

/**
 * Giao diện cho việc triển khai chung các phương thức getter cho hình ảnh của bảng
 * @author Tony Massaad
 */
public interface BoardGetterFunctionality {

    /**
     * Lấy các hình ảnh ở phía trên của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía trên
     */
    ArrayList<Image> getTopPhotos();

    /**
     * Lấy các hình ảnh ở phía bên trái của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía bên trái
     */
    ArrayList<Image> getLeftPhotos();

    /**
     * Lấy các hình ảnh ở phía dưới của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía dưới
     */
    ArrayList<Image> getBottomPhotos();

    /**
     * Lấy các hình ảnh ở phía bên phải của bảng tương ứng
     * @return ArrayList<Image>, danh sách các hình ảnh phía bên phải
     */
    ArrayList<Image> getRightPhotos();

    /**
     * Lấy thanh ngang của bảng phân tách hai hình ảnh
     * @return Image, thanh ngang
     */
    Image getHorizontalLine();

    /**
     * Lấy thanh dọc của bảng phân tách hai hình ảnh
     * @return Image, thanh dọc
     */
    Image getVerticalLine();
}
