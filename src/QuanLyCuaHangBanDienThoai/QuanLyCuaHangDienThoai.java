package QuanLyCuaHangBanDienThoai;

import java.util.Scanner;

public class QuanLyCuaHangDienThoai {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        int select;
        do {
            System.out.println("Menu:");
            System.out.println("1. Quản lý Kho Nhà cung cấp");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Sản phẩm");
            System.out.println("4. Quản lý Giao Dịch");
            System.out.println("5. Thoát chương trình");
            System.out.print("Chọn: ");
            select = in.nextInt();
            in.nextLine();

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
                    System.out.println("Thoát hẳn chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (select != 5);
        }
    }

