package QuanLyCuaHangBanDienThoai;

import java.util.*;

// Lớp quản lý module kho và nhà cung cấp
public class QlyKhoNcc {
    private DanhSachKho dsKho;
    private DanhSachNhaCungCap dsNcc;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner sc = new Scanner(System.in);

    // Hàm thiết lập
    public QlyKhoNcc() {
        qltt = QuanLyTrungTam.getInstance();
        dsKho = qltt.getDanhSachKho();
        dsNcc = qltt.getDanhSachNhaCungCap();
    }

    // Menu chính
    public void menu() {
        int luaChon;
        do {
            System.out.println("\n QUẢN LÝ KHO & NHÀ CUNG CẤP ");
            System.out.println("1. Quản lý sản phẩm trong kho");
            System.out.println("2. Quản lý linh kiện tồn kho");
            System.out.println("3. Quản lý hàng lỗi cần xử lý");
            System.out.println("4. Quản lý nhà cung cấp");
            System.out.println("5. Nghiệp vụ kho");
            System.out.println("6. Thống kê & báo cáo");
            System.out.println("7. Tải dữ liệu từ file");
            System.out.println("8. Lưu dữ liệu ra file");
            System.out.println("9. Quay lại menu chính");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    menuSanPhamTrongKho();
                    break;
                case 2:
                    menuLinhKien();
                    break;
                case 3:
                    menuHangLoi();
                    break;
                case 4:
                    menuNhaCungCap();
                    break;
                case 5:
                    menuNghiepVu();
                    break;
                case 6:
                    menuThongKe();
                    break;
                case 7:
                    taiDuLieu();
                    break;
                case 8:
                    luuDuLieu();
                    break;
                case 9:
                    System.out.println("Quay lại menu chính...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 9);
    }

    // Menu sản phẩm
    private void menuSanPhamTrongKho() {
        int luaChon;
        do {
            System.out.println("\n--- QUẢN LÝ SẢN PHẨM TRONG KHO ---");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Xem danh sách sản phẩm");
            System.out.println("6. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
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
                    dsKho.xemTatCaSanPham();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 6);
    }

    // Thêm sản phẩm
    private void themSanPham() {
        System.out.print("Mã sản phẩm: ");
        String maSP = sc.nextLine();
        System.out.print("Tên sản phẩm: ");
        String tenSP = sc.nextLine();
        System.out.print("Loại sản phẩm: ");
        String loai = sc.nextLine();
        System.out.print("Giá bán: ");
        double giaBan = sc.nextDouble();
        sc.nextLine();
        System.out.print("Hãng sản xuất: ");
        String hangSX = sc.nextLine();
        System.out.print("Số lượng tồn: ");
        int soLuongTon = sc.nextInt();
        sc.nextLine();
        System.out.print("Ngày nhập (dd/MM/yyyy): ");
        String ngayNhap = sc.nextLine();

        SanPham sp;
        String l = loai == null ? "" : loai.toLowerCase();
        if (l.contains("điện") || l.contains("dien") || l.contains("đt") ) {
            System.out.print("Nhập IMEI: ");
            String imei = sc.nextLine();
            System.out.print("Nhập cấu hình: ");
            String cauHinh = sc.nextLine();
            sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
        } else if (l.contains("phụ") || l.contains("phu") || l.contains("phu kien") || l.contains("phukien")) {
            System.out.print("Loại phụ kiện: ");
            String loaiPk = sc.nextLine();
            System.out.print("Tương thích: ");
            String tuongThich = sc.nextLine();
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPk, tuongThich);
        } else if (l.contains("linh")) {
            System.out.print("Dành cho sửa chữa? (y/n): ");
            boolean danhChoSua = sc.nextLine().equalsIgnoreCase("y");
            System.out.print("Thuộc bảo hành? (y/n): ");
            boolean thuocBaoHanh = sc.nextLine().equalsIgnoreCase("y");
            sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSua, thuocBaoHanh);
        } else {
            // default to PhuKien if type not recognized
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, "", "");
        }

        String serial = KhoHelper.sinhSerialNgauNhien();
        String viTriKe = KhoHelper.ganViTriKe(loai);
        SanPhamTrongKho spk = new SanPhamTrongKho(sp, serial, new Date(), viTriKe, viTriKe, soLuongTon, "Mới nhập");
        dsKho.themSanPham(spk);
        System.out.println("Đã thêm! Serial: " + serial);
    }

    // Sửa sản phẩm
    private void suaSanPham() {
        System.out.print("Nhập serial cần sửa: ");
        String serial = sc.nextLine();
        System.out.print("Số lượng mới: ");
        int sl = sc.nextInt();
        sc.nextLine();
        System.out.print("Trạng thái mới: ");
        String tt = sc.nextLine();

        if (dsKho.suaSanPham(serial, sl, tt)) {
            System.out.println("Đã cập nhật!");
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    // Xóa sản phẩm
    private void xoaSanPham() {
        System.out.print("Nhập serial cần xóa: ");
        String serial = sc.nextLine();
        if (dsKho.xoaSanPham(serial)) {
            System.out.println("Đã xóa!");
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    // Tìm kiếm sản phẩm
    private void timKiemSanPham() {
        System.out.println("1. Tìm theo Serial/IMEI");
        System.out.println("2. Tìm theo vị trí kệ");
        System.out.println("3. Tìm theo trạng thái");
        System.out.print("Chọn: ");
        int chon = sc.nextInt();
        sc.nextLine();

        switch (chon) {
            case 1:
                System.out.print("Nhập serial: ");
                String serial = sc.nextLine();
                SanPhamTrongKho sp = dsKho.timTheoSerial(serial);
                if (sp != null) {
                    System.out.println(sp.layThongTinChiTiet());
                } else {
                    System.out.println("Không tìm thấy!");
                }
                break;
            case 2:
                System.out.print("Nhập vị trí: ");
                String viTri = sc.nextLine();
                List<SanPhamTrongKho> dsViTri = dsKho.timTheoViTri(viTri);
                if (dsViTri.isEmpty()) {
                    System.out.println("Không có sản phẩm!");
                } else {
                    for (SanPhamTrongKho s : dsViTri) {
                        System.out.println(s.layThongTinChiTiet());
                    }
                }
                break;
            case 3:
                System.out.print("Nhập trạng thái: ");
                String trangThai = sc.nextLine();
                List<SanPhamTrongKho> dsTrangThai = dsKho.timTheoTrangThai(trangThai);
                if (dsTrangThai.isEmpty()) {
                    System.out.println("Không có sản phẩm!");
                } else {
                    for (SanPhamTrongKho s : dsTrangThai) {
                        System.out.println(s.layThongTinChiTiet());
                    }
                }
                break;
        }
    }

    // Menu linh kiện
    private void menuLinhKien() {
        int luaChon;
        do {
            System.out.println("\n--- QUẢN LÝ LINH KIỆN TỒN KHO ---");
            System.out.println("1. Thêm linh kiện");
            System.out.println("2. Xem danh sách linh kiện");
            System.out.println("3. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    themLinhKien();
                    break;
                case 2:
                    dsKho.xemTatCaLinhKien();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 3);
    }

    // Thêm linh kiện
    private void themLinhKien() {
        System.out.print("Mã linh kiện: ");
        String ma = sc.nextLine();
        System.out.print("Tên linh kiện: ");
        String ten = sc.nextLine();
        System.out.print("Giá nhập: ");
        double gia = sc.nextDouble();
        System.out.print("Số lượng: ");
        int sl = sc.nextInt();
        sc.nextLine();
        System.out.print("Mục đích sử dụng: ");
        String mucDich = sc.nextLine();

        LinhKienTonKho lk = new LinhKienTonKho(ma, ten, new Date(), mucDich, gia, "Kệ C1", sl, "Tốt");
        dsKho.themLinhKien(lk);
        System.out.println("Đã thêm linh kiện!");
    }

    // Menu hàng lỗi
    private void menuHangLoi() {
        int luaChon;
        do {
            System.out.println("\n--- QUẢN LÝ HÀNG LỖI CẦN XỬ LÝ ---");
            System.out.println("1. Thêm hàng lỗi");
            System.out.println("2. Xem danh sách hàng lỗi");
            System.out.println("3. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    themHangLoi();
                    break;
                case 2:
                    dsKho.xemTatCaHangLoi();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 3);
    }

    // Thêm hàng lỗi
    private void themHangLoi() {
        System.out.print("Mã sản phẩm lỗi: ");
        String ma = sc.nextLine();
        System.out.print("Tên sản phẩm: ");
        String ten = sc.nextLine();
        System.out.print("Loại lỗi: ");
        String loaiLoi = sc.nextLine();
        System.out.print("Nguồn gốc (Nhà cung cấp/Khách hàng): ");
        String nguonGoc = sc.nextLine();
        System.out.print("Cách xử lý: ");
        String cachXuLy = sc.nextLine();
        System.out.print("Giá trị thiệt hại: ");
        double giaTriThietHai = sc.nextDouble();
        System.out.print("Số lượng: ");
        int sl = sc.nextInt();
        sc.nextLine();

        HangLoiCanXuLy hl = new HangLoiCanXuLy(ma, ten, loaiLoi, new Date(), nguonGoc, cachXuLy, giaTriThietHai, "Kệ D1", sl, "Chờ xử lý");
        dsKho.themHangLoi(hl);
        System.out.println("Đã thêm hàng lỗi!");
    }

    // Menu nhà cung cấp
    private void menuNhaCungCap() {
        int luaChon;
        do {
            System.out.println("\n--- QUẢN LÝ NHÀ CUNG CẤP ---");
            System.out.println("1. Thêm nhà cung cấp");
            System.out.println("2. Sửa thông tin nhà cung cấp");
            System.out.println("3. Xóa nhà cung cấp");
            System.out.println("4. Tìm kiếm nhà cung cấp");
            System.out.println("5. Xem danh sách nhà cung cấp");
            System.out.println("6. Đánh giá chất lượng nhà cung cấp");
            System.out.println("7. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    themNhaCungCap();
                    break;
                case 2:
                    suaNhaCungCap();
                    break;
                case 3:
                    xoaNhaCungCap();
                    break;
                case 4:
                    timKiemNhaCungCap();
                    break;
                case 5:
                    dsNcc.xemTatCa();
                    System.out.println("Tổng số NCC: " + NhaCungCap.layTongSoNhaCungCap());
                    break;
                case 6:
                    danhGiaNhaCungCap();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 7);
    }

    // Thêm nhà cung cấp
    private void themNhaCungCap() {
        System.out.print("Mã NCC: ");
        String ma = sc.nextLine();
        System.out.print("Tên NCC: ");
        String ten = sc.nextLine();
        System.out.print("Sản phẩm cung cấp: ");
        String sp = sc.nextLine();
        System.out.print("Số điện thoại: ");
        String sdt = sc.nextLine();
        System.out.print("Địa chỉ: ");
        String dc = sc.nextLine();

        NhaCungCap ncc = new NhaCungCap(ma, ten, sp, 100, sdt, dc);
        dsNcc.themNhaCungCap(ncc);
        System.out.println("Đã thêm!");
    }

    // Sửa nhà cung cấp
    private void suaNhaCungCap() {
        System.out.print("Mã NCC cần sửa: ");
        String ma = sc.nextLine();
        System.out.print("Tên mới: ");
        String ten = sc.nextLine();
        System.out.print("Sản phẩm mới: ");
        String sp = sc.nextLine();
        System.out.print("Độ tin cậy mới (0-100): ");
        double dtc = sc.nextDouble();

        if (dsNcc.suaNhaCungCap(ma, ten, sp, dtc)) {
            System.out.println("Đã cập nhật!");
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    // Xóa nhà cung cấp
    private void xoaNhaCungCap() {
        System.out.print("Mã NCC cần xóa: ");
        String ma = sc.nextLine();
        if (dsNcc.xoaNhaCungCap(ma)) {
            System.out.println("Đã xóa");
        } else {
            System.out.println("Không tìm thấy");
        }
    }

    // Tìm kiếm nhà cung cấp
    private void timKiemNhaCungCap() {
        System.out.print("Nhập tên cần tìm: ");
        String ten = sc.nextLine();
        List<NhaCungCap> kq = dsNcc.timTheoTen(ten);
        if (kq.isEmpty()) {
            System.out.println("Không tìm thấy!");
        } else {
            for (NhaCungCap ncc : kq) {
                System.out.println("Mã: " + ncc.getMaNhaCungCap() + " - Tên: " + ncc.getTenNhaCungCap() + " - Độ tin cậy: " + ncc.getDoTinCay());
            }
        }
    }

    // Đánh giá nhà cung cấp
    private void danhGiaNhaCungCap() {
        System.out.print("Mã NCC: ");
        String ma = sc.nextLine();
        System.out.print("Tổng hàng nhập: ");
        int tong = sc.nextInt();
        System.out.print("Số hàng lỗi: ");
        int loi = sc.nextInt();
        dsNcc.danhGiaChatLuong(ma, tong, loi);
    }

    // Menu nghiệp vụ kho
    private void menuNghiepVu() {
        int luaChon;
        do {
            System.out.println("\n--- NGHIỆP VỤ KHO ---");
            System.out.println("1. Nhập kho");
            System.out.println("2. Xuất kho");
            System.out.println("3. Kiểm kho");
            System.out.println("4. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    nhapKho();
                    break;
                case 2:
                    xuatKho();
                    break;
                case 3:
                    kiemKho();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 4);
    }

    // Nhập kho
    private void nhapKho() {
        System.out.print("Mã NCC: ");
        String maNCC = sc.nextLine();
        NhaCungCap ncc = dsNcc.timTheoMa(maNCC);
        if (ncc == null) {
            System.out.println("Không tìm thấy NCC!");
            return;
        }

        System.out.print("Mã sản phẩm: ");
        String maSP = sc.nextLine();
        System.out.print("Tên sản phẩm: ");
        String tenSP = sc.nextLine();
        System.out.print("Hãng sản xuất: ");
        String hangSX = sc.nextLine();
        System.out.print("Giá bán: ");
        double giaBan = sc.nextDouble();
        System.out.print("Số lượng tồn: ");
        int soLuongTon = sc.nextInt();
        sc.nextLine();
        System.out.print("Ngày nhập (dd/MM/yyyy): ");
        String ngayNhap = sc.nextLine();
        System.out.print("Loại sản phẩm: ");
        String loai = sc.nextLine();

        SanPham sp;
        String l = loai == null ? "" : loai.toLowerCase();
        if (l.contains("điện") || l.contains("dien") || l.contains("đt")) {
            System.out.print("Nhập IMEI: ");
            String imei = sc.nextLine();
            System.out.print("Nhập cấu hình: ");
            String cauHinh = sc.nextLine();
            sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
        } else if (l.contains("phụ") || l.contains("phu") || l.contains("phu kien") || l.contains("phukien")) {
            System.out.print("Loại phụ kiện: ");
            String loaiPk = sc.nextLine();
            System.out.print("Tương thích: ");
            String tuongThich = sc.nextLine();
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPk, tuongThich);
        } else if (l.contains("linh")) {
            System.out.print("Dành cho sửa chữa? (y/n): ");
            boolean danhChoSua = sc.nextLine().equalsIgnoreCase("y");
            System.out.print("Thuộc bảo hành? (y/n): ");
            boolean thuocBaoHanh = sc.nextLine().equalsIgnoreCase("y");
            sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSua, thuocBaoHanh);
        } else {
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, "", "");
        }

        dsKho.nhapKho(sp, soLuongTon, ncc);
        System.out.println("Đã nhập kho!");
    }

    // Xuất kho
    private void xuatKho() {
        System.out.print("Mã sản phẩm: ");
        String ma = sc.nextLine();
        System.out.print("Số lượng xuất: ");
        int sl = sc.nextInt();
        dsKho.xuatKho(ma, sl);
        System.out.println("Đã xuất kho!");
    }

    // Kiểm kho
    private void kiemKho() {
        System.out.print("Serial cần kiểm: ");
        String serial = sc.nextLine();
        System.out.print("Số lượng thực tế: ");
        int sl = sc.nextInt();
        dsKho.kiemKho(serial, sl);
    }

    // Menu thống kê
    private void menuThongKe() {
        int luaChon;
        do {
            System.out.println("\n--- THỐNG KÊ & BÁO CÁO ---");
            System.out.println("1. Hàng tồn kho lâu (>3 tháng)");
            System.out.println("2. Hàng sắp hết (<5 cái)");
            System.out.println("3. Giá trị tồn kho");
            System.out.println("4. Cảnh báo hết hàng");
            System.out.println("5. Lọc NCC theo độ tin cậy");
            System.out.println("6. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    List<SanPhamTrongKho> tonLau = dsKho.thongKeHangTonLau();
                    System.out.println("Có " + tonLau.size() + " sản phẩm tồn lâu:");
                    for (SanPhamTrongKho sp : tonLau) {
                        System.out.println("- " + sp.layThongTinChiTiet() + " | Tuổi: " + KhoHelper.tinhTuoiTonKho(sp.getNgayNhap()) + " ngày");
                    }
                    break;
                case 2:
                    List<SanPhamTrongKho> sapHet = dsKho.thongKeHangSapHet();
                    System.out.println("Có " + sapHet.size() + " sản phẩm sắp hết:");
                    for (SanPhamTrongKho sp : sapHet) {
                        System.out.println("- " + sp.layThongTinChiTiet());
                    }
                    break;
                case 3:
                    double giaTri = dsKho.tinhGiaTriTonKho();
                    System.out.println("Giá trị tồn kho: " + String.format("%.2f", giaTri) + " VNĐ");
                    break;
                case 4:
                    System.out.print("Ngưỡng tối thiểu: ");
                    int nguong = sc.nextInt();
                    List<SanPham> hetHang = dsKho.canhBaoHetHang(nguong);
                    System.out.println("Có " + hetHang.size() + " sản phẩm cần nhập thêm");
                    break;
                case 5:
                    System.out.print("Độ tin cậy tối thiểu: ");
                    double dtc = sc.nextDouble();
                    List<NhaCungCap> nccTot = dsNcc.locTheoDoTinCay(dtc);
                    System.out.println("Có " + nccTot.size() + " NCC đạt yêu cầu:");
                    for (NhaCungCap ncc : nccTot) {
                        System.out.println("- " + ncc.getTenNhaCungCap() + " (" + ncc.getDoTinCay() + "%)");
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 6);
    }

    // Tải dữ liệu từ file
    private void taiDuLieu() {
        qltt.taiDuLieuTuFile();
    }

    // Lưu dữ liệu ra file
    private void luuDuLieu() {
        qltt.luuDuLieuRaFile();
    }
}