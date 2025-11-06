import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Khởi tạo hệ thống
        DanhSachKho danhSachKho = new DanhSachKho();
        DanhSachNhaCungCap danhSachNCC = new DanhSachNhaCungCap();
        DuBaoNhapHang duBao = new DuBaoNhapHang(danhSachKho);

        // Tải dữ liệu từ file
        danhSachKho.docFile();
        danhSachNCC.docFile();

        // Nếu chưa có dữ liệu, tạo dữ liệu mẫu
        if (danhSachKho.getSoLuongMatHang() == 0) {
            taoDuLieuMau(danhSachKho, danhSachNCC, duBao);
        }

        boolean running = true;
        while (running) {
            hienThiMenuChinh();
            int choice = nhapSoNguyen(sc, "Chọn chức năng: ");

            switch (choice) {
                case 1:
                    menuQuanLyKho(sc, danhSachKho, danhSachNCC);
                    break;
                case 2:
                    menuQuanLyNCC(sc, danhSachNCC);
                    break;
                case 3:
                    menuTimKiem(sc, danhSachKho, danhSachNCC);
                    break;
                case 4:
                    menuThongKe(sc, danhSachKho, duBao);
                    break;
                case 5:
                    duBao.hienThiBaoCaoThongMinh();
                    break;
                case 6:
                    danhSachKho.ghiFile();
                    danhSachNCC.ghiFile();
                    break;
                case 0:
                    System.out.println("\n💾 Đang lưu dữ liệu...");
                    danhSachKho.ghiFile();
                    danhSachNCC.ghiFile();
                    System.out.println("👋 Tạm biệt!");
                    running = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        sc.close();
    }

    //  MENU

    private static void hienThiMenuChinh() {
        System.out.println("                                        \n");
        System.out.println("     HỆ THỐNG QUẢN LÝ KHO & NHÀ CUNG CẤP      ");
        System.out.println("                                                   ");
        System.out.println(" 1. Quản lý Kho hàng                           ");
        System.out.println(" 2. Quản lý Nhà cung cấp                       ");
        System.out.println(" 3. Tìm kiếm                                   ");
        System.out.println(" 4. Thống kê & Báo cáo                         ");
        System.out.println(" 5. Dự báo thông minh (AI)                 ");
        System.out.println(" 6. Lưu dữ liệu                             ");
        System.out.println(" 0. Thoát                                      ");
        System.out.println("                                               ");
    }

    private static void menuQuanLyKho(Scanner sc, DanhSachKho dsk, DanhSachNhaCungCap dsncc) {
        System.out.println("\n QUẢN LÝ KHO HÀNG ");
        System.out.println("1. Xem tất cả");
        System.out.println("2. Nhập kho");
        System.out.println("3. Xuất kho");
        System.out.println("4. Cập nhật số lượng");
        System.out.println("5. Xóa khỏi kho");
        System.out.println("6. Kiểm kho");
        System.out.println("0. Quay lại");

        int choice = nhapSoNguyen(sc, "Chọn: ");

        switch (choice) {
            case 1:
                dsk.hienThiTatCa();
                break;
            case 2:
                nhapKhoInteractive(sc, dsk, dsncc);
                break;
            case 3:
                System.out.print("Nhập mã kho: ");
                String maXuat = sc.nextLine();
                int slXuat = nhapSoNguyen(sc, "Số lượng xuất: ");
                dsk.xuatKho(maXuat, slXuat);
                break;
            case 4:
                System.out.print("Nhập mã kho: ");
                String maCapNhat = sc.nextLine();
                int slMoi = nhapSoNguyen(sc, "Số lượng mới: ");
                dsk.capNhatSoLuong(maCapNhat, slMoi);
                break;
            case 5:
                System.out.print("Nhập mã kho cần xóa: ");
                String maXoa = sc.nextLine();
                dsk.xoaKhoiKho(maXoa);
                break;
            case 6:
                dsk.kiemKho();
                break;
        }
    }

    private static void menuQuanLyNCC(Scanner sc, DanhSachNhaCungCap dsncc) {
        System.out.println("\n=== QUẢN LÝ NHÀ CUNG CẤP ===");
        System.out.println("1. Xem tất cả NCC");
        System.out.println("2. Thêm NCC mới");
        System.out.println("3. Cập nhật NCC");
        System.out.println("4. Xóa NCC");
        System.out.println("5. Xếp hạng NCC");
        System.out.println("6. Thống kê NCC");
        System.out.println("0. Quay lại");

        int choice = nhapSoNguyen(sc, "Chọn: ");

        switch (choice) {
            case 1:
                dsncc.hienThiTatCa();
                break;
            case 2:
                themNCCInteractive(sc, dsncc);
                break;
            case 5:
                List<NhaCungCap> topNCC = dsncc.xepHangTheoDoTinCay();
                System.out.println("\n🏆 XẾP HẠNG NHÀ CUNG CẤP:");
                for (int i = 0; i < topNCC.size(); i++) {
                    System.out.println((i + 1) + ". " + topNCC.get(i));
                }
                break;
            case 6:
                dsncc.hienThiThongKeNCC();
                break;
        }
    }

    private static void menuTimKiem(Scanner sc, DanhSachKho dsk, DanhSachNhaCungCap dsncc) {
        System.out.println("\n=== TÌM KIẾM ===");
        System.out.println("1. Tìm theo mã kho");
        System.out.println("2. Tìm theo serial/IMEI");
        System.out.println("3. Tìm theo vị trí");
        System.out.println("4. Tìm theo trạng thái");
        System.out.println("5. Tìm NCC theo tên");
        System.out.println("0. Quay lại");

        int choice = nhapSoNguyen(sc, "Chọn: ");

        switch (choice) {
            case 1:
                System.out.print("Nhập mã kho: ");
                String ma = sc.nextLine();
                ThucTheKho item = dsk.timTheoMa(ma);
                if (item != null) {
                    System.out.println(item.layThongTinChiTiet());
                } else {
                    System.out.println("Không tìm thấy!");
                }
                break;
            case 2:
                System.out.print("Nhập serial/IMEI: ");
                String serial = sc.nextLine();
                List<ThucTheKho> ketQuaSerial = dsk.timTheoSerial(serial);
                hienThiKetQuaTimKiem(ketQuaSerial);
                break;
            case 3:
                System.out.print("Nhập vị trí (vd: Kệ A): ");
                String viTri = sc.nextLine();
                List<ThucTheKho> ketQuaViTri = dsk.timTheoViTri(viTri);
                hienThiKetQuaTimKiem(ketQuaViTri);
                break;
            case 5:
                System.out.print("Nhập tên NCC: ");
                String tenNCC = sc.nextLine();
                List<NhaCungCap> nccList = dsncc.timTheoTen(tenNCC);
                if (nccList.isEmpty()) {
                    System.out.println("Không tìm thấy!");
                } else {
                    for (NhaCungCap ncc : nccList) {
                        System.out.println(ncc.layThongTinChiTiet());
                    }
                }
                break;
        }
    }

    private static void menuThongKe(Scanner sc, DanhSachKho dsk, DuBaoNhapHang duBao) {
        System.out.println("\n=== THỐNG KÊ & BÁO CÁO ===");
        System.out.println("1. Kiểm kho tổng thể");
        System.out.println("2. Hàng sắp hết (< 5)");
        System.out.println("3. Hàng tồn lâu (> 90 ngày)");
        System.out.println("4. Giá trị tồn kho");
        System.out.println("5. Báo cáo dự báo thông minh");
        System.out.println("0. Quay lại");

        int choice = nhapSoNguyen(sc, "Chọn: ");

        switch (choice) {
            case 1:
                dsk.kiemKho();
                break;
            case 2:
                List<ThucTheKho> sapHet = dsk.canhBaoHetHang(5);
                System.out.println("\n⚠️ HÀNG SẮP HẾT (" + sapHet.size() + " mặt hàng):");
                hienThiKetQuaTimKiem(sapHet);
                break;
            case 3:
                List<ThucTheKho> tonLau = dsk.canhBaoTonKhoLau(90);
                System.out.println("\n⚠️ HÀNG TỒN LÂU (" + tonLau.size() + " mặt hàng):");
                hienThiKetQuaTimKiem(tonLau);
                break;
            case 4:
                double giaTri = dsk.tinhGiaTriTonKho();
                System.out.println("\n💰 GIÁ TRỊ TỒN KHO: " + KhoHelper.formatTien(giaTri));
                break;
            case 5:
                duBao.hienThiBaoCaoThongMinh();
                break;
        }
    }

    // ===== HELPER METHODS =====

    private static void nhapKhoInteractive(Scanner sc, DanhSachKho dsk, DanhSachNhaCungCap dsncc) {
        System.out.println("\n=== NHẬP KHO ===");
        System.out.println("1. Sản phẩm");
        System.out.println("2. Linh kiện");
        System.out.println("3. Hàng lỗi");

        int loai = nhapSoNguyen(sc, "Chọn loại: ");

        System.out.print("Tên hàng hóa: ");
        String ten = sc.nextLine();

        int soLuong = nhapSoNguyen(sc, "Số lượng: ");

        System.out.print("Nhà cung cấp (mã NCC): ");
        String maNCC = sc.nextLine();
        NhaCungCap ncc = dsncc.timTheoMa(maNCC);

        ThucTheKho hangHoa = null;

        if (loai == 1) {
            String ma = KhoHelper.sinhMaKho("sản phẩm");
            String serial = KhoHelper.sinhIMEI();
            String viTri = KhoHelper.ganViTriKeThongMinh("Điện thoại", soLuong);
            double giaNhap = nhapSoThuc(sc, "Giá nhập: ");
            double giaBan = nhapSoThuc(sc, "Giá bán: ");

            hangHoa = new SanPhamTrongKho(ma, ten, serial, "Điện thoại",
                    viTri, soLuong, giaNhap, giaBan,
                    ncc != null ? ncc.getTenNCC() : "N/A");
        } else if (loai == 2) {
            String ma = KhoHelper.sinhMaKho("linh kiện");
            String viTri = KhoHelper.ganViTriKe("Linh kiện");
            double giaNhap = nhapSoThuc(sc, "Giá nhập: ");

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 12); // Hạn 1 năm
            Date hanSD = cal.getTime();

            hangHoa = new LinhKienTonKho(ma, ten, "Linh kiện chung",
                    viTri, soLuong, hanSD, giaNhap, "Sửa chữa");
        }

        if (hangHoa != null) {
            dsk.nhapKho(hangHoa, ncc);
        }
    }

    private static void themNCCInteractive(Scanner sc, DanhSachNhaCungCap dsncc) {
        System.out.println("\n=== THÊM NHÀ CUNG CẤP ===");

        String ma = "NCC" + String.format("%03d", dsncc.getSoLuongNCC() + 1);
        System.out.print("Tên NCC: ");
        String ten = sc.nextLine();
        System.out.print("Địa chỉ: ");
        String diaChi = sc.nextLine();
        System.out.print("Số điện thoại: ");
        String sdt = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Sản phẩm cung cấp: ");
        String sp = sc.nextLine();

        NhaCungCap ncc = new NhaCungCap(ma, ten, diaChi, sdt, email, sp);
        dsncc.themNCC(ncc);
    }

    private static void hienThiKetQuaTimKiem(List<ThucTheKho> ketQua) {
        if (ketQua.isEmpty()) {
            System.out.println("❌ Không tìm thấy kết quả nào!");
            return;
        }

        System.out.println("\n✓ Tìm thấy " + ketQua.size() + " kết quả:");
        for (int i = 0; i < ketQua.size(); i++) {
            System.out.println("\n[" + (i + 1) + "] " + ketQua.get(i).layThongTinChiTiet());
        }
    }

    private static int nhapSoNguyen(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số nguyên!");
            }
        }
    }

    private static double nhapSoThuc(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số hợp lệ!");
            }
        }
    }

    // ===== TẠO DỮ LIỆU MẪU =====

    private static void taoDuLieuMau(DanhSachKho dsk, DanhSachNhaCungCap dsncc, DuBaoNhapHang duBao) {
        System.out.println("📦 Đang tạo dữ liệu mẫu...");

        // Tạo NCC
        NhaCungCap ncc1 = new NhaCungCap("NCC001", "Samsung Vietnam",
                "Hà Nội", "0241234567", "samsung@vn.com", "Điện thoại Samsung");
        NhaCungCap ncc2 = new NhaCungCap("NCC002", "Apple Store VN",
                "TP.HCM", "0287654321", "apple@vn.com", "iPhone");
        NhaCungCap ncc3 = new NhaCungCap("NCC003", "Xiaomi Official",
                "Đà Nẵng", "0236111222", "xiaomi@vn.com", "Điện thoại Xiaomi");

        dsncc.themNCC(ncc1);
        dsncc.themNCC(ncc2);
        dsncc.themNCC(ncc3);

        // Tạo sản phẩm
        SanPhamTrongKho sp1 = new SanPhamTrongKho("SP00001", "iPhone 15 Pro Max",
                KhoHelper.sinhIMEI(), "Điện thoại", "Kệ A1", 3, 28000000, 33000000, "Apple Store VN");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        sp1.setNgayNhapKho(cal.getTime());

        SanPhamTrongKho sp2 = new SanPhamTrongKho("SP00002", "Samsung S24 Ultra",
                KhoHelper.sinhIMEI(), "Điện thoại", "Kệ A2", 15, 24000000, 28000000, "Samsung Vietnam");

        SanPhamTrongKho sp3 = new SanPhamTrongKho("SP00003", "Xiaomi 14 Pro",
                KhoHelper.sinhIMEI(), "Điện thoại", "Kệ A5", 8, 15000000, 18000000, "Xiaomi Official");

        dsk.themHangVaoKho(sp1);
        dsk.themHangVaoKho(sp2);
        dsk.themHangVaoKho(sp3);

        // Tạo linh kiện
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 8);
        LinhKienTonKho lk1 = new LinhKienTonKho("LK00001", "Màn hình iPhone 15",
                "Màn hình", "Kệ C3", 12, cal.getTime(), 3500000, "Sửa chữa");
        dsk.themHangVaoKho(lk1);

        // Tạo hàng lỗi
        HangLoiCanXuLy hl1 = new HangLoiCanXuLy("HL00001", "iPhone 14 Pro",
                KhoHelper.sinhIMEI(), "Khu D1", 2, "Lỗi màn hình",
                "Khách trả lại", 18000000);
        dsk.themHangVaoKho(hl1);

        // Tạo lịch sử bán hàng giả lập
        duBao.capNhatLichSuBan("SP00001", 5);
        duBao.capNhatLichSuBan("SP00001", 6);
        duBao.capNhatLichSuBan("SP00001", 4);
        duBao.capNhatLichSuBan("SP00002", 3);
        duBao.capNhatLichSuBan("SP00002", 4);

        System.out.println("✓ Đã tạo dữ liệu mẫu thành công!");
    }
}