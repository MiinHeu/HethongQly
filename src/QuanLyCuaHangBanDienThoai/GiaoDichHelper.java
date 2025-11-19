package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: GiaoDichHelper.java

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lớp tiện ích (static) cung cấp các hàm hỗ trợ cho nghiệp vụ giao dịch.
 */
public class GiaoDichHelper {
    private static final AtomicInteger counter = new AtomicInteger(1);

    /**
     * Sinh mã hóa đơn tự động tăng dần (HD001, HD002, ...).
     * @return Một chuỗi mã hóa đơn mới.
     */
    public static String sinhMaHoaDon() {
        return String.format("HD%03d", counter.getAndIncrement());
    }

    /**
     * Định dạng một số double thành chuỗi tiền tệ theo kiểu #,### VNĐ.
     * @param soTien Số tiền cần định dạng.
     * @return Chuỗi đã định dạng.
     */
    public static String formatTien(double soTien) {
        DecimalFormat formatter = new DecimalFormat("#,### VNĐ");
        return formatter.format(soTien);
    }

    /**
     * Tính thuế VAT (10%) của một khoản tiền.
     * @param tongTien Tổng tiền trước thuế.
     * @return Số tiền thuế VAT.
     */
    public static double tinhVAT(double tongTien) {
        return tongTien * 0.10;
    }
}