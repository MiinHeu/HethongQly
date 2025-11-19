package QuanLyCuaHangBanDienThoai;

import java.util.concurrent.atomic.AtomicInteger;

public class SanPhamHelper {
    private static final AtomicInteger counter = new AtomicInteger(1);

    // Sinh mã sản phẩm tự động (SP001, SP002,...)
    public static String sinhMaSanPham() {
        return String.format("SP%03d", counter.getAndIncrement());
    }

    // Kiểm tra IMEI hợp lệ (15 số)
    public static boolean validateIMEI(String imei) {
        if (imei == null || imei.length() != 15) {
            return false;
        }
        return imei.matches("\\d{15}");
    }

    // So sánh sản phẩm theo nhiều tiêu chí
    public static int soSanhSanPham(SanPham a, SanPham b, String... tieuChi) {
        for (String tc : tieuChi) {
            int ketQua = 0;
            switch (tc.toLowerCase()) {
                case "gia":
                    ketQua = Double.compare(a.getGiaBan(), b.getGiaBan());
                    break;
                case "ten":
                    ketQua = a.getTenSP().compareToIgnoreCase(b.getTenSP());
                    break;
                case "hang":
                    ketQua = a.getHangSX().compareToIgnoreCase(b.getHangSX());
                    break;
                case "ngay":
                    ketQua = a.getNgayNhap().compareTo(b.getNgayNhap());
                    break;
                case "tonkho":
                    ketQua = Integer.compare(a.getSoLuongTon(), b.getSoLuongTon());
                    break;
            }
            if (ketQua != 0) {
                return ketQua;
            }
        }
        return 0;
    }

    // Định dạng tiền tệ
    public static String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }

    // Kiểm tra giá trị hợp lệ
    public static boolean isValidGia(double gia) {
        return gia >= 0;
    }

    public static boolean isValidSoLuong(int soLuong) {
        return soLuong >= 0;
    }

    // Reset bộ đếm mã sản phẩm (sử dụng khi cần)
    public static void resetCounter() {
        counter.set(1);
    }
}