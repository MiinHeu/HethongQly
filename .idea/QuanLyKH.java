package qlkh;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DanhSachKhachHang ds = new DanhSachKhachHang();

        // ======== 1. ĐỌC DỮ LIỆU TỪ FILE ========
        try {
            ds.docFile("khachhang.txt");   // hoặc "kh.txt" nếu bạn đặt tên vậy
            System.out.println(" Đọc dữ liệu từ file thành công!");
        } catch (IOException e) {
            System.out.println(" Không tìm thấy file khachhang.txt, tạo dữ liệu mẫu...");
            
            // ======== 2. TẠO DỮ LIỆU MẪU ========
            KhachHang kh1 = new KhachHang(KhachHangHelper.sinhMaKhachHang(), "Nguyen Van A", "0901234567", "a@gmail.com",
                    LocalDate.of(1995, 12, 20), 800, "Vàng", Arrays.asList("DH001", "DH002"));
            KhachHangVIP kh2 = new KhachHangVIP(KhachHangHelper.sinhMaKhachHang(), "Tran Thi B", "0912345678", "b@gmail.com",
                    LocalDate.of(1990, 11, 5), 1500, "VIP", Arrays.asList("DH003", "DH004"), 0.15);
            KhachHangTiemNang kh3 = new KhachHangTiemNang(KhachHangHelper.sinhMaKhachHang(), "Le Van C", "0987654321",
                    "c@gmail.com", LocalDate.of(2000, 10, 30));

            ds.them(kh1);
            ds.them(kh2);
            ds.them(kh3);

            // Ghi lại dữ liệu mẫu vào file để lần sau đọc
            try {
                ds.ghiFile("khachhang.txt");
                System.out.println(" Đã tạo file khachhang.txt với dữ liệu mẫu!");
            } catch (IOException ex) {
                System.out.println(" Lỗi ghi file: " + ex.getMessage());
            }
        }

        // ======== 3. HIỂN THỊ DANH SÁCH ========
        System.out.println("\n=== DANH SÁCH KHÁCH HÀNG ===");
        ds.xuat();

        // ======== 4. THỐNG KÊ KHÁCH VIP ========
        System.out.println("\n=== KHÁCH HÀNG VIP ===");
        ds.thongKeVIP().forEach(KhachHang::xuat);

        // ======== 5. TÌM KHÁCH GẦN ĐÚNG TÊN ========
        System.out.println("\n=== TÌM TÊN GẦN ĐÚNG (vd: 'Nguyen Van A') ===");
        ds.timGanDungTen("Nguyen Van A").forEach(KhachHang::xuat);

        // ======== 6. KHÁCH MUA NHIỀU NHẤT ========
        System.out.println("\n=== KHÁCH MUA NHIỀU NHẤT ===");
        KhachHang khMax = ds.khachMuaNhieuNhat();
        if (khMax != null) khMax.xuat();

        System.out.println("\n Chương trình chạy hoàn tất!");
    }
}
