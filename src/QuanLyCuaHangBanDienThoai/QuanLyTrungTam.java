package QuanLyCuaHangBanDienThoai;

/**
 * Lớp quản lý trung tâm - liên kết tất cả các module với nhau
 * Đảm bảo logic nghiệp vụ hoạt động đúng và các module tương tác với nhau
 */
public class QuanLyTrungTam {
    private static QuanLyTrungTam instance;
    private DanhSachKhachHang danhSachKhachHang;
    private DanhSachSanPham danhSachSanPham;
    private DanhSachKho danhSachKho;
    private DanhSachNhaCungCap danhSachNhaCungCap;
    private DanhSachGiaoDich danhSachGiaoDich;

    // Constructor private để đảm bảo Singleton pattern
    private QuanLyTrungTam() {
        danhSachKhachHang = new DanhSachKhachHang();
        danhSachSanPham = new DanhSachSanPham();
        danhSachKho = new DanhSachKho();
        danhSachNhaCungCap = new DanhSachNhaCungCap();
        danhSachGiaoDich = new DanhSachGiaoDich();
    }

    // Lấy instance duy nhất (Singleton)
    public static QuanLyTrungTam getInstance() {
        if (instance == null) {
            instance = new QuanLyTrungTam();
        }
        return instance;
    }

    // Getter cho các danh sách
    public DanhSachKhachHang getDanhSachKhachHang() {
        return danhSachKhachHang;
    }

    public DanhSachSanPham getDanhSachSanPham() {
        return danhSachSanPham;
    }

    public DanhSachKho getDanhSachKho() {
        return danhSachKho;
    }

    public DanhSachNhaCungCap getDanhSachNhaCungCap() {
        return danhSachNhaCungCap;
    }

    public DanhSachGiaoDich getDanhSachGiaoDich() {
        return danhSachGiaoDich;
    }

    /**
     * Xử lý bán hàng - liên kết các module:
     * 1. Kiểm tra khách hàng có tồn tại
     * 2. Kiểm tra sản phẩm có tồn tại và đủ số lượng
     * 3. Cập nhật số lượng tồn kho
     * 4. Cập nhật điểm tích lũy cho khách hàng
     * 5. Cập nhật hạng khách hàng nếu cần
     */
    public boolean xuLyBanHang(HoaDonBan hoaDon) {
        // 1. Kiểm tra khách hàng có tồn tại
        KhachHang kh = danhSachKhachHang.timTheoMa(hoaDon.getMaKhachHang());
        if (kh == null) {
            System.out.println("Lỗi: Không tìm thấy khách hàng với mã: " + hoaDon.getMaKhachHang());
            return false;
        }

        // 2. Kiểm tra từng sản phẩm trong hóa đơn
        for (ChiTietGiaoDich ct : hoaDon.getDanhSachChiTiet()) {
            SanPham sp = danhSachSanPham.timTheoMa(ct.getMaSP());
            if (sp == null) {
                System.out.println("Lỗi: Không tìm thấy sản phẩm với mã: " + ct.getMaSP());
                return false;
            }

            // Kiểm tra số lượng tồn kho
            if (sp.getSoLuongTon() < ct.getSoLuong()) {
                System.out.println("Lỗi: Sản phẩm " + sp.getTenSP() + " chỉ còn " + 
                    sp.getSoLuongTon() + " sản phẩm, không đủ để bán " + ct.getSoLuong());
                return false;
            }
        }

        // 3. Cập nhật số lượng tồn kho và xuất kho
        for (ChiTietGiaoDich ct : hoaDon.getDanhSachChiTiet()) {
            SanPham sp = danhSachSanPham.timTheoMa(ct.getMaSP());
            // Cập nhật số lượng trong danh sách sản phẩm
            sp.setSoLuongTon(sp.getSoLuongTon() - ct.getSoLuong());
            // Xuất kho
            danhSachKho.xuatKho(ct.getMaSP(), ct.getSoLuong());
        }

        // 4. Tính chiết khấu và tổng tiền sau chiết khấu
        double tongTienTruocChietKhau = hoaDon.getTongTien();
        double chietKhau = danhSachKhachHang.tinhChietKhau(kh, tongTienTruocChietKhau);
        double tongTienSauChietKhau = tongTienTruocChietKhau - chietKhau;
        hoaDon.setTongTien(tongTienSauChietKhau);
        
        if (chietKhau > 0) {
            System.out.println("Chiết khấu: " + GiaoDichHelper.formatTien(chietKhau));
            System.out.println("Tổng tiền sau chiết khấu: " + GiaoDichHelper.formatTien(tongTienSauChietKhau));
        }
        
        // 5. Tính điểm tích lũy (1 điểm = 1000 VNĐ) dựa trên tổng tiền sau chiết khấu
        int diemTichLuy = (int)(tongTienSauChietKhau / 1000);
        
        // 6. Cập nhật điểm tích lũy và hạng khách hàng
        danhSachKhachHang.capNhatDiemTichLuy(hoaDon.getMaKhachHang(), diemTichLuy);
        String hangMoi = danhSachKhachHang.xepHang(kh);
        if (!hangMoi.equals(kh.getHangThanhVien())) {
            kh.hangThanhVien = hangMoi;
            System.out.println("Khách hàng " + kh.getTen() + " đã được nâng hạng lên: " + hangMoi);
        }

        // 6. Thêm vào lịch sử mua hàng
        kh.getLichSuMuaHang().add(hoaDon.getMaGD());

        // 7. Thêm giao dịch vào danh sách
        danhSachGiaoDich.themGiaoDich(hoaDon);

        System.out.println("Bán hàng thành công! Khách hàng nhận được " + diemTichLuy + " điểm tích lũy.");
        return true;
    }

    /**
     * Xử lý nhập hàng - liên kết các module:
     * 1. Kiểm tra nhà cung cấp có tồn tại
     * 2. Nhập sản phẩm vào kho
     * 3. Cập nhật danh sách sản phẩm nếu chưa có
     */
    public boolean xuLyNhapHang(PhieuNhapHang phieuNhap, String maSP, String tenSP, 
                                double giaBan, String hangSX, String ngayNhap, String loaiSP) {
        // 1. Kiểm tra nhà cung cấp có tồn tại
        NhaCungCap ncc = danhSachNhaCungCap.timTheoMa(phieuNhap.getNhaCungCap());
        if (ncc == null) {
            // Tìm theo tên
            var dsNcc = danhSachNhaCungCap.timTheoTen(phieuNhap.getNhaCungCap());
            if (dsNcc.isEmpty()) {
                System.out.println("Cảnh báo: Không tìm thấy nhà cung cấp: " + phieuNhap.getNhaCungCap());
                System.out.println("Vui lòng thêm nhà cung cấp trước khi nhập hàng.");
                return false;
            }
            ncc = dsNcc.get(0);
        }

        // 2. Kiểm tra sản phẩm đã tồn tại chưa
        SanPham sp = danhSachSanPham.timTheoMa(maSP);
        if (sp == null) {
            // Tạo sản phẩm mới dựa vào loại
            String loai = loaiSP.toLowerCase();
            if (loai.contains("điện") || loai.contains("dien") || loai.contains("đt")) {
                sp = new DienThoai(maSP, tenSP, giaBan, hangSX, phieuNhap.getSoLuong(), 
                    ngayNhap, KhoHelper.sinhSerialNgauNhien(), "Chưa cập nhật");
            } else if (loai.contains("phụ") || loai.contains("phu")) {
                sp = new PhuKien(maSP, tenSP, giaBan, hangSX, phieuNhap.getSoLuong(), 
                    ngayNhap, "Phụ kiện", hangSX);
            } else if (loai.contains("linh")) {
                sp = new LinhKien(maSP, tenSP, giaBan, hangSX, phieuNhap.getSoLuong(), 
                    ngayNhap, true, false);
            } else {
                sp = new PhuKien(maSP, tenSP, giaBan, hangSX, phieuNhap.getSoLuong(), 
                    ngayNhap, "Khác", hangSX);
            }
            danhSachSanPham.themSanPham(sp);
        } else {
            // Cập nhật số lượng tồn kho
            sp.setSoLuongTon(sp.getSoLuongTon() + phieuNhap.getSoLuong());
        }

        // 3. Nhập kho
        danhSachKho.nhapKho(sp, phieuNhap.getSoLuong(), ncc);

        // 4. Thêm giao dịch vào danh sách
        danhSachGiaoDich.themGiaoDich(phieuNhap);

        System.out.println("Nhập hàng thành công!");
        return true;
    }

    /**
     * Tìm đường dẫn file - thử nhiều vị trí
     */
    private String timDuongDanFile(String tenFile) {
        // Thử các đường dẫn có thể
        String[] duongDan = {
            "src/QuanLyCuaHangBanDienThoai/" + tenFile,
            "QuanLyCuaHangBanDienThoai/" + tenFile,
            tenFile
        };
        
        for (String path : duongDan) {
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                return path;
            }
        }
        
        // Nếu không tìm thấy, trả về đường dẫn trong src (sẽ tạo file mới)
        return "src/QuanLyCuaHangBanDienThoai/" + tenFile;
    }

    /**
     * Tải dữ liệu từ tất cả các file
     */
    public void taiDuLieuTuFile() {
        try {
            danhSachKhachHang.docFile(timDuongDanFile("danhsachKH.txt"));
            System.out.println("Đã tải danh sách khách hàng.");
        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách khách hàng: " + e.getMessage());
        }

        try {
            danhSachNhaCungCap.docFile(timDuongDanFile("nhacungcap.txt"));
            System.out.println("Đã tải danh sách nhà cung cấp.");
        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách nhà cung cấp: " + e.getMessage());
        }

        try {
            danhSachKho.docFile(timDuongDanFile("kho.txt"));
            System.out.println("Đã tải danh sách kho.");
        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách kho: " + e.getMessage());
        }

        try {
            danhSachGiaoDich.docFile(timDuongDanFile("giaodich.txt"));
            System.out.println("Đã tải danh sách giao dịch.");
        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách giao dịch: " + e.getMessage());
        }

        try {
            danhSachSanPham.docFile(timDuongDanFile("sanpham.txt"));
            System.out.println("Đã tải danh sách sản phẩm.");
        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách sản phẩm: " + e.getMessage());
        }
    }

    /**
     * Lưu dữ liệu ra tất cả các file
     */
    public void luuDuLieuRaFile() {
        // Luôn lưu vào thư mục src để dễ quản lý
        String thuMuc = "src/QuanLyCuaHangBanDienThoai/";
        
        try {
            danhSachKhachHang.ghiFile(thuMuc + "danhsachKH.txt");
            System.out.println("Đã lưu danh sách khách hàng.");
        } catch (Exception e) {
            System.out.println("Lỗi lưu danh sách khách hàng: " + e.getMessage());
        }

        try {
            danhSachNhaCungCap.ghiFile(thuMuc + "nhacungcap.txt");
            System.out.println("Đã lưu danh sách nhà cung cấp.");
        } catch (Exception e) {
            System.out.println("Lỗi lưu danh sách nhà cung cấp: " + e.getMessage());
        }

        try {
            danhSachKho.ghiFile(thuMuc + "kho.txt");
            System.out.println("Đã lưu danh sách kho.");
        } catch (Exception e) {
            System.out.println("Lỗi lưu danh sách kho: " + e.getMessage());
        }

        try {
            danhSachGiaoDich.ghiFile(thuMuc + "giaodich.txt");
            System.out.println("Đã lưu danh sách giao dịch.");
        } catch (Exception e) {
            System.out.println("Lỗi lưu danh sách giao dịch: " + e.getMessage());
        }

        try {
            danhSachSanPham.ghiFile(thuMuc + "sanpham.txt");
            System.out.println("Đã lưu danh sách sản phẩm.");
        } catch (Exception e) {
            System.out.println("Lỗi lưu danh sách sản phẩm: " + e.getMessage());
        }
    }
}

