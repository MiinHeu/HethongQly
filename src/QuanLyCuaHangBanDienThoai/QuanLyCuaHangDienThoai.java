package QuanLyCuaHangBanDienThoai;

import java.util.Scanner;

public class QuanLyCuaHangDienThoai {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        
        // Khởi tạo quản lý trung tâm và tải dữ liệu
        QuanLyTrungTam qltt = QuanLyTrungTam.getInstance();
        System.out.println("=== HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN ĐIỆN THOẠI ===");
        System.out.println("Đang tải dữ liệu từ file...");
        qltt.taiDuLieuTuFile();
        System.out.println("Tải dữ liệu hoàn tất!\n");
        
        int select;
        do {
            System.out.println("\n========== MENU CHÍNH ==========");
            System.out.println("1. Quản lý Kho & Nhà cung cấp");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Sản phẩm");
            System.out.println("4. Quản lý Giao Dịch");
            System.out.println("5. Lưu tất cả dữ liệu");
            System.out.println("6. Thoát chương trình");
            System.out.print("Chọn: ");
            
            try {
                select = in.nextInt();
                in.nextLine();
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                in.nextLine();
                select = 0;
            }

            switch (select) {
                case 1:
                    QlyKhoNcc ql1 = new QlyKhoNcc();
                    ql1.menu();
                    break;
                case 2:
                    QlyKhachHang ql2 = new QlyKhachHang();
                    ql2.menu();
                    break;
                case 3:
                    QlySanPham ql3 = new QlySanPham();
                    ql3.menu();
                    break;
                case 4:
                    QlyGiaoDich ql4 = new QlyGiaoDich();
                    ql4.menu();
                    break;
                case 5:
                    System.out.println("Đang lưu tất cả dữ liệu...");
                    qltt.luuDuLieuRaFile();
                    System.out.println("Lưu dữ liệu hoàn tất!");
                    break;
                case 6:
                    System.out.println("Đang lưu dữ liệu trước khi thoát...");
                    qltt.luuDuLieuRaFile();
                    System.out.println("Thoát chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (select != 6);
    }
}

