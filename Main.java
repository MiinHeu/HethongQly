import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        try {
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("console.encoding", "UTF-8");
            MenuSanPham menu = new MenuSanPham();
            menu.hienThiMenu();
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}