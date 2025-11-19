package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: GiaoDichHelper.java
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class GiaoDichHelper {
    private static final AtomicInteger counter = new AtomicInteger(1);
    public static String sinhMaHoaDon() {
        return String.format("HD%03d", counter.getAndIncrement());
    }
    public static String formatTien(double soTien) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(soTien) + " VNĐ";
    }
    public static double tinhVAT(double tongTien) {
        return tongTien * 0.10;
    }
}