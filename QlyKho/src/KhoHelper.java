import java.text.SimpleDateFormat;
import java.util.*;

public class KhoHelper {
    private static int seriaDem = 1000;
    private static final Map<String, String> viTriMap = new HashMap<>();

    static {
        // Khởi tạo mapping vị trí kệ theo loại sản phẩm
        viTriMap.put("Điện thoại", "Kệ A");
        viTriMap.put("Phụ kiện", "Kệ B");
        viTriMap.put("Linh kiện", "Kệ C");
        viTriMap.put("Hàng lỗi", "Khu D");
    }

    //  Serial/IMEI Generation

    public static String sinhSerialNgauNhien() {
        Random rand = new Random();
        StringBuilder serial = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            serial.append(rand.nextInt(10));
        }
        return serial.toString();
    }

    public static String sinhIMEI() {
        // IMEI format: 15 digits với checksum Luhn algorithm
        Random rand = new Random();
        StringBuilder imei = new StringBuilder();

        // 14 số đầu
        for (int i = 0; i < 14; i++) {
            imei.append(rand.nextInt(10));
        }

        // Tính chữ số kiểm tra (Luhn algorithm)
        int checksum = tinhLuhnChecksum(imei.toString());
        imei.append(checksum);

        return imei.toString();
    }

    private static int tinhLuhnChecksum(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }

    public static String sinhMaKho(String loai) {
        String prefix = "";
        switch (loai.toLowerCase()) {
            case "sản phẩm":
            case "điện thoại":
                prefix = "SP";
                break;
            case "linh kiện":
                prefix = "LK";
                break;
            case "hàng lỗi":
                prefix = "HL";
                break;
            default:
                prefix = "KH";
        }
        return prefix + String.format("%05d", seriaDem++);
    }

    // Vị trí kệ

    public static String ganViTriKe(String loaiSP) {
        String keChung = viTriMap.getOrDefault(loaiSP, "Kệ X");
        Random rand = new Random();
        int soKe = rand.nextInt(10) + 1; // 1-10
        return keChung + soKe;
    }

    public static String ganViTriKeThongMinh(String loaiSP, int soLuong) {
        // Hàng nhiều -> Kệ gần cửa (số nhỏ)
        // Hàng ít -> Kệ xa (số lớn)
        String keChung = viTriMap.getOrDefault(loaiSP, "Kệ X");
        int soKe = soLuong > 20 ? (new Random().nextInt(3) + 1) :
                (new Random().nextInt(7) + 4);
        return keChung + soKe;
    }

    // ===== Tính toán thời gian =====

    public static int tinhTuoiTonKho(Date ngayNhap) {
        long diff = new Date().getTime() - ngayNhap.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static int tinhSoNgayDenHan(Date hanSuDung) {
        long diff = hanSuDung.getTime() - new Date().getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static boolean hetHanSuDung(Date hanSuDung) {
        return hanSuDung.before(new Date());
    }

    // ===== Format output =====

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatTien(double soTien) {
        return String.format("%,.0f₫", soTien);
    }

    // ===== Validation =====

    public static boolean kiemTraSerial(String serial) {
        return serial != null && serial.matches("\\d{15}");
    }

    public static boolean kiemTraSoDienThoai(String sdt) {
        return sdt != null && sdt.matches("0\\d{9,10}");
    }

    public static boolean kiemTraEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // ===== Thống kê =====

    public static Map<String, Integer> thongKeSoLuongTheoLoai(List<ThucTheKho> danhSach) {
        Map<String, Integer> thongKe = new HashMap<>();
        for (ThucTheKho item : danhSach) {
            String loai = item.getClass().getSimpleName();
            thongKe.put(loai, thongKe.getOrDefault(loai, 0) + item.getSoLuong());
        }
        return thongKe;
    }

    public static double tinhTyLeHangLoi(List<ThucTheKho> danhSach) {
        int tongSoLuong = 0;
        int soLuongLoi = 0;

        for (ThucTheKho item : danhSach) {
            tongSoLuong += item.getSoLuong();
            if (item instanceof HangLoiCanXuLy) {
                soLuongLoi += item.getSoLuong();
            }
        }

        return tongSoLuong > 0 ? (soLuongLoi * 100.0 / tongSoLuong) : 0;
    }
}