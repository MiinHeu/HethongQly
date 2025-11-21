package QuanLyCuaHangBanDienThoai;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class QlyGiaoDich {
    private DanhSachGiaoDich dsgd;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "src/QuanLyCuaHangBanDienThoai/giaodich.txt";

    public QlyGiaoDich() {
        qltt = QuanLyTrungTam.getInstance();
        dsgd = qltt.getDanhSachGiaoDich();
    }

//menu chính

    public void menu() {
        int choice;
        do {
            System.out.println("\n--- QUAN LY GIAO DICH ---");
            System.out.println("1. Them giao dich");
            System.out.println("2. Sua thong tin giao dich");
            System.out.println("3. Xoa giao dich");
            System.out.println("4. Tim kiem giao dich");
            System.out.println("5. Xem danh sach giao dich");
            System.out.println("6. Tai du lieu tu file");
            System.out.println("7. Xuat du lieu ra file");
            System.out.println("8. Quay lai");
            System.out.print("Chon: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    themGiaoDich();
                    break;
                case 2:
                    suaGiaoDich();
                    break;
                case 3:
                    xoaGiaoDich();
                    break;
                case 4:
                    timKiemGiaoDich();
                    break;
                case 5:
                    xemDanhSach();
                    break;
                case 6:
                    taiDuLieu();
                    break;
                case 7:
                    xuatDuLieu();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 8);
    }

// ===== 1. THEM GIAO DICH =====

    private void themGiaoDich() {
        try {
            System.out.println("\n--- THEM GIAO DICH ---");

            System.out.println("Chon loai giao dich:");
            System.out.println("1. Hoa don ban hang");
            System.out.println("2. Phieu nhap hang");
            System.out.println("3. Phieu bao hanh");
            System.out.print("Chon: ");
            int loai = scanner.nextInt();
            scanner.nextLine();

            String maGD = GiaoDichHelper.sinhMaHoaDon();
            Date ngayGD = new Date();

            System.out.print("Nguoi thuc hien: ");
            String nguoiThucHien = scanner.nextLine().trim();

            GiaoDich gd = null;

            if (loai == 1) {
                // Hoa don ban
                // Hien thi danh sach khach hang de chon
                List<KhachHang> dsKH = qltt.getDanhSachKhachHang().layDanhSach();
                if (dsKH.isEmpty()) {
                    System.out.println("Loi: Chua co khach hang nao trong he thong!");
                    System.out.println("Vui long them khach hang truoc (Menu 2)");
                    return;
                }
                
                System.out.println("\n--- Danh sach khach hang ---");
                for (int i = 0; i < Math.min(5, dsKH.size()); i++) {
                    KhachHang k = dsKH.get(i);
                    System.out.println(k.getMa() + " - " + k.getTen() + " - " + k.getSdt());
                }
                if (dsKH.size() > 5) {
                    System.out.println("... va " + (dsKH.size() - 5) + " khach hang khac");
                }
                
                System.out.print("\nMa khach hang: ");
                String maKH = scanner.nextLine().trim();

                KhachHang kh = qltt.getDanhSachKhachHang().timTheoMa(maKH);
                if (kh == null) {
                    System.out.println("Loi: Khong tim thay khach hang voi ma: " + maKH);
                    System.out.println("Vui long kiem tra lai ma khach hang!");
                    return;
                }

                gd = new HoaDonBan(maGD, ngayGD, nguoiThucHien, maKH);

            } else if (loai == 2) {
                // Phieu nhap
                // Hien thi danh sach nha cung cap de chon
                List<NhaCungCap> dsNCC = qltt.getDanhSachNhaCungCap().getDanhSach();
                if (dsNCC.isEmpty()) {
                    System.out.println("Loi: Chua co nha cung cap nao trong he thong!");
                    System.out.println("Vui long them nha cung cap truoc (Menu 1 -> Quan ly NCC)");
                    return;
                }
                
                System.out.println("\n--- Danh sach nha cung cap ---");
                for (int i = 0; i < Math.min(5, dsNCC.size()); i++) {
                    NhaCungCap n = dsNCC.get(i);
                    System.out.println(n.getMaNhaCungCap() + " - " + n.getTenNhaCungCap());
                }
                if (dsNCC.size() > 5) {
                    System.out.println("... va " + (dsNCC.size() - 5) + " nha cung cap khac");
                }
                
                System.out.print("\nMa nha cung cap: ");
                String maNCC = scanner.nextLine().trim();

                NhaCungCap ncc = qltt.getDanhSachNhaCungCap().timTheoMa(maNCC);
                if (ncc == null) {
                    System.out.println("Loi: Khong tim thay nha cung cap voi ma: " + maNCC);
                    System.out.println("Vui long kiem tra lai ma nha cung cap!");
                    return;
                }

                System.out.print("So luong: ");
                int soLuong = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Gia nhap: ");
                double giaNhap = scanner.nextDouble();
                scanner.nextLine();

                gd = new PhieuNhapHang(maGD, ngayGD, nguoiThucHien, maNCC, soLuong, giaNhap);

            } else if (loai == 3) {
                // Phieu bao hanh
                // Hien thi danh sach san pham de chon
                List<SanPham> dsSP = qltt.getDanhSachSanPham().layDanhSach();
                if (dsSP.isEmpty()) {
                    System.out.println("Loi: Chua co san pham nao trong he thong!");
                    System.out.println("Vui long them san pham truoc (Menu 3)");
                    return;
                }
                
                System.out.println("\n--- Danh sach san pham ---");
                for (int i = 0; i < Math.min(5, dsSP.size()); i++) {
                    SanPham s = dsSP.get(i);
                    System.out.println(s.getMaSP() + " - " + s.getTenSP());
                }
                if (dsSP.size() > 5) {
                    System.out.println("... va " + (dsSP.size() - 5) + " san pham khac");
                }
                
                System.out.print("\nMa san pham: ");
                String maSP = scanner.nextLine().trim();

                System.out.print("Loi hong: ");
                String loiHong = scanner.nextLine().trim();

                System.out.print("Phi sua chua: ");
                double phiSuaChua = scanner.nextDouble();
                scanner.nextLine();

                gd = new PhieuBaoHanh(maGD, ngayGD, nguoiThucHien, maSP, loiHong, phiSuaChua);

            } else {
                System.out.println("Lua chon khong hop le!");
                return;
            }

            dsgd.themGiaoDich(gd);
            System.out.println("\nThem thanh cong! Ma GD: " + maGD);

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            scanner.nextLine();
        }
    }

// ===== 2. SUA GIAO DICH =====

    private void suaGiaoDich() {
        try {
            System.out.println("\n--- SUA GIAO DICH ---");

            System.out.print("Nhap ma giao dich: ");
            String maGD = scanner.nextLine().trim();

            GiaoDich gd = dsgd.timTheoMa(maGD);
            if (gd == null) {
                System.out.println("Khong tim thay!");
                return;
            }

            System.out.println("\nThong tin hien tai:");
            System.out.println("Ma: " + gd.getMaGD());
            System.out.println("Ngay: " + gd.getNgayGD());
            System.out.println("Nguoi thuc hien: " + gd.getNguoiThucHien());
            System.out.println("Tong tien: " + gd.getTongTien());

            System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");

            System.out.print("Nguoi thuc hien [" + gd.getNguoiThucHien() + "]: ");
            String nguoi = scanner.nextLine().trim();
            if (!nguoi.isEmpty()) gd.setNguoiThucHien(nguoi);

            System.out.print("Tong tien [" + gd.getTongTien() + "]: ");
            String tienStr = scanner.nextLine().trim();
            if (!tienStr.isEmpty()) {
                try {
                    double tien = Double.parseDouble(tienStr);
                    if (tien >= 0) gd.setTongTien(tien);
                } catch (NumberFormatException e) {
                }
            }

            // Sua thuoc tinh rieng
            if (gd instanceof HoaDonBan) {
                System.out.println("(Khong the sua thuoc tinh rieng cua Hoa don ban)");
            } else if (gd instanceof PhieuNhapHang) {
                System.out.println("(Khong the sua thuoc tinh rieng cua Phieu nhap hang)");
            } else if (gd instanceof PhieuBaoHanh) {
                System.out.println("(Khong the sua thuoc tinh rieng cua Phieu bao hanh)");
            }

            System.out.println("\nCap nhat thanh cong!");

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

// ===== 3. XOA GIAO DICH =====

    private void xoaGiaoDich() {
        try {
            System.out.println("\n--- XOA GIAO DICH ---");

            System.out.print("Nhap ma giao dich: ");
            String maGD = scanner.nextLine().trim();

            GiaoDich gd = dsgd.timTheoMa(maGD);
            if (gd == null) {
                System.out.println("Khong tim thay!");
                return;
            }

            System.out.println("\nThong tin giao dich:");
            System.out.println("Ma: " + gd.getMaGD());
            System.out.println("Ngay: " + gd.getNgayGD());
            System.out.println("Tong tien: " + gd.getTongTien());

            System.out.print("\nXac nhan xoa? (y/n): ");
            String xacNhan = scanner.nextLine().trim();

            if (xacNhan.equalsIgnoreCase("y")) {
                if (dsgd.xoaGiaoDich(maGD)) {
                    System.out.println("Xoa thanh cong!");
                } else {
                    System.out.println("Loi khi xoa!");
                }
            } else {
                System.out.println("Da huy!");
            }

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

// ===== 4. TIM KIEM GIAO DICH =====

    private void timKiemGiaoDich() {
        try {
            System.out.println("\n--- TIM KIEM GIAO DICH ---");
            System.out.println("1. Tim theo Ma");
            System.out.println("2. Tim theo Ngay");
            System.out.println("3. Tim theo Nguoi thuc hien");
            System.out.println("4. Tim theo Loai");
            System.out.print("Chon: ");
            int chon = scanner.nextInt();
            scanner.nextLine();

            if (chon == 1) {
                System.out.print("Nhap ma: ");
                String ma = scanner.nextLine().trim();
                GiaoDich gd = dsgd.timTheoMa(ma);
                if (gd != null) {
                    System.out.println("\nTim thay:");
                    hienThiGD(gd);
                } else {
                    System.out.println("Khong tim thay!");
                }
            } else if (chon == 2) {
                System.out.print("Nhap ngay (dd/MM/yyyy): ");
                String ngay = scanner.nextLine().trim();
                List<GiaoDich> ds = dsgd.timTheoNgay(ngay);
                hienThiDanhSachGD(ds);
            } else if (chon == 3) {
                System.out.print("Nhap nguoi thuc hien: ");
                String nguoi = scanner.nextLine().trim();
                List<GiaoDich> ds = dsgd.timTheoNguoi(nguoi);
                hienThiDanhSachGD(ds);
            } else if (chon == 4) {
                System.out.print("Nhap loai (HoaDonBan/PhieuNhapHang/PhieuBaoHanh): ");
                String loai = scanner.nextLine().trim();
                List<GiaoDich> ds = dsgd.timTheoLoai(loai);
                hienThiDanhSachGD(ds);
            } else {
                System.out.println("Lua chon khong hop le!");
            }

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void hienThiGD(GiaoDich gd) {
        System.out.println("Ma: " + gd.getMaGD());
        System.out.println("Ngay: " + gd.getNgayGD());
        System.out.println("Nguoi thuc hien: " + gd.getNguoiThucHien());
        System.out.println("Tong tien: " + gd.getTongTien());
        System.out.println("Loai: " + gd.getClass().getSimpleName());
    }

    private void hienThiDanhSachGD(List<GiaoDich> ds) {
        if (ds.isEmpty()) {
            System.out.println("Khong tim thay!");
        } else {
            System.out.println("\nTim thay " + ds.size() + " giao dich:");
            for (int i = 0; i < ds.size(); i++) {
                System.out.println("\n--- Giao dich " + (i + 1) + " ---");
                hienThiGD(ds.get(i));
            }
        }
    }

// ===== 5. XEM DANH SACH =====

    private void xemDanhSach() {
        System.out.println("\n--- DANH SACH GIAO DICH ---");
        List<GiaoDich> ds = dsgd.layDanhSach();
        if (ds.isEmpty()) {
            System.out.println("Danh sach trong!");
        } else {
            for (int i = 0; i < ds.size(); i++) {
                System.out.println("\n--- Giao dich " + (i + 1) + " ---");
                hienThiGD(ds.get(i));
            }
            System.out.println("\nTong so: " + ds.size());
        }
    }

// ===== 6. TAI/XUAT FILE =====

    private void taiDuLieu() {
        try {
            dsgd.docFile(fileName);
            System.out.println("Da tai du lieu tu file!");
        } catch (Exception e) {
            System.out.println("Loi khi tai file: " + e.getMessage());
        }
    }

    private void xuatDuLieu() {
        try {
            dsgd.ghiFile(fileName);
            System.out.println("Da xuat du lieu ra file!");
        } catch (Exception e) {
            System.out.println("Loi khi xuat file: " + e.getMessage());
        }
    }
}
