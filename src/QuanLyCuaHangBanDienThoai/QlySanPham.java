package QuanLyCuaHangBanDienThoai;

import java.util.List;
import java.util.Scanner;

class QlySanPham {
    private DanhSachSanPham danhSachSP;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "src/QuanLyCuaHangBanDienThoai/sanpham.txt";

    public QlySanPham() {
        qltt = QuanLyTrungTam.getInstance();
        danhSachSP = qltt.getDanhSachSanPham();
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n===== QUAN LY SAN PHAM (MODULE) =====");
            System.out.println("1. Them san pham");
            System.out.println("2. Sua thong tin san pham");
            System.out.println("3. Xoa san pham");
            System.out.println("4. Tim kiem san pham");
            System.out.println("5. Xem danh sach san pham");
            System.out.println("6. Tai danh sach san pham tu file");
            System.out.println("7. Xuat danh sach san pham ra file");
            System.out.println("8. Thoat khoi module quan ly san pham");
            System.out.print("Chon: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    themSanPham();
                    break;
                case 2:
                    suaSanPham();
                    break;
                case 3:
                    xoaSanPham();
                    break;
                case 4:
                    timKiemSanPham();
                    break;
                case 5:
                    xemDanhSach();
                    break;
                case 6:
                    danhSachSP.docFile(fileName);
                    System.out.println("Da tai danh sach san pham tu file.");
                    break;
                case 7:
                    danhSachSP.ghiFile(fileName);
                    System.out.println("Da xuat danh sach san pham ra file.");
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        } while (choice != 8);
    }

    private void themSanPham() {
        System.out.println("\n----- THEM SAN PHAM (DON GIAN) -----");
        System.out.print("Ten san pham: ");
        String tenSP = scanner.nextLine();
        System.out.print("Gia ban: ");
        double giaBan = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Hang san xuat: ");
        String hangSX = scanner.nextLine();
        System.out.print("So luong ton: ");
        int soLuongTon = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ngay nhap (dd/MM/yyyy): ");
        String ngayNhap = scanner.nextLine();

        String maSP = SanPhamHelper.sinhMaSanPham();

        // Mac dinh them vao danh sach la phu kien chung chung
        PhuKien sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap,
                "Phu kien", hangSX);
        danhSachSP.themSanPham(sp);
        System.out.println("Them san pham thanh cong!");
    }

    private void suaSanPham() {
        System.out.print("Nhap ma san pham can sua: ");
        String maSP = scanner.nextLine();
        SanPham sp = danhSachSP.timTheoMa(maSP);
        if (sp == null) {
            System.out.println("Khong tim thay san pham!");
            return;
        }

        System.out.println("Thong tin hien tai: " + sp);

        System.out.print("Ten moi (Enter de giu nguyen): ");
        String tenMoi = scanner.nextLine();
        if (!tenMoi.isEmpty()) {
            sp.setTenSP(tenMoi);
        }

        System.out.print("Gia moi (nhap -1 de giu nguyen): ");
        double giaMoi = scanner.nextDouble();
        if (giaMoi >= 0) {
            sp.setGiaBan(giaMoi);
        }

        System.out.print("So luong ton moi (nhap -1 de giu nguyen): ");
        int slMoi = scanner.nextInt();
        scanner.nextLine();
        if (slMoi >= 0) {
            sp.setSoLuongTon(slMoi);
        }

        danhSachSP.suaSanPham(sp);
        System.out.println("Cap nhat san pham thanh cong!");
    }

    private void xoaSanPham() {
        System.out.print("Nhap ma san pham can xoa: ");
        String maSP = scanner.nextLine();
        if (danhSachSP.xoaSanPham(maSP)) {
            System.out.println("Xoa san pham thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private void timKiemSanPham() {
        System.out.println("\n----- TIM KIEM SAN PHAM (DON GIAN) -----");
        System.out.println("1. Tim theo ma");
        System.out.println("2. Tim theo ten");
        System.out.print("Chon: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        switch (c) {
            case 1:
                System.out.print("Nhap ma san pham: ");
                String ma = scanner.nextLine();
                SanPham sp = danhSachSP.timTheoMa(ma);
                if (sp != null) {
                    System.out.println(sp);
                } else {
                    System.out.println("Khong tim thay san pham!");
                }
                break;
            case 2:
                System.out.print("Nhap ten san pham: ");
                String ten = scanner.nextLine();
                List<SanPham> kq = danhSachSP.timTheoTen(ten);
                if (kq.isEmpty()) {
                    System.out.println("Khong tim thay san pham nao!");
                } else {
                    kq.forEach(System.out::println);
                }
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    private void xemDanhSach() {
        List<SanPham> ds = danhSachSP.getDanhSach();
        if (ds.isEmpty()) {
            System.out.println("Danh sach rong!");
        } else {
            ds.forEach(System.out::println);
        }
    }
}
