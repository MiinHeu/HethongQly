package QuanLyCuaHangBanDienThoai;

import java.util.Date;
import java.util.Random;

// Lớp tiện ích với các phương thức static
public class KhoHelper {

    // Sinh serial ngẫu nhiên 15 số
    public static String sinhSerialNgauNhien() {
        Random rd = new Random();
        String serial = "";
        for (int i = 0; i < 15; i++) {
            serial += rd.nextInt(10);
        }
        return serial;
    }

    // Tính tuổi tồn kho theo số ngày
    public static long tinhTuoiTonKho(Date ngayNhap) {
        Date ngayHienTai = new Date();
        long chenhLech = ngayHienTai.getTime() - ngayNhap.getTime();
        return chenhLech / (1000 * 60 * 60 * 24);
    }

    // Gán vị trí kệ theo loại sản phẩm
    public static String ganViTriKe(String loaiSP) {
        switch (loaiSP.toLowerCase()) {
            case "laptop":
                return "Kệ A1";
            case "điện thoại":
            case "dien thoai":
                return "Kệ A2";
            case "phụ kiện":
            case "phu kien":
                return "Kệ B1";
            case "linh kiện":
            case "linh kien":
                return "Kệ C1";
            case "tablet":
                return "Kệ A3";
            default:
                return "Kệ D1";
        }
    }

    // Kiểm tra hàng tồn kho lâu (> 90 ngày)
    public static boolean laTonKhoLau(Date ngayNhap) {
        return tinhTuoiTonKho(ngayNhap) > 90;
    }

    // Kiểm tra hàng sắp hết (< 5 cái)
    public static boolean laSapHetHang(int soLuong) {
        return soLuong < 5;
    }
}