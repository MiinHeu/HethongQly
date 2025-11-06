

public class SanPhamTrongKho extends ThucTheKho {
    private String tenSanPham;
    private String serial; // Hoặc IMEI
    private String loaiSanPham; // "Điện thoại", "Phụ kiện"
    private double giaNhap;
    private double giaBan;
    private String nhaCungCap;

    public SanPhamTrongKho() {
        super();
    }

    public SanPhamTrongKho(String maKho, String tenSanPham, String serial,
                           String loaiSanPham, String viTriKe, int soLuong,
                           double giaNhap, double giaBan, String nhaCungCap) {
        super(maKho, viTriKe, soLuong, "Tốt");
        this.tenSanPham = tenSanPham;
        this.serial = serial;
        this.loaiSanPham = loaiSanPham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public String layThongTinChiTiet() {
        return String.format("SẢN PHẨM %s\n" +
                        " Serial/IMEI: %s\n" +
                        " Loại: %s | Vị trí: %s\n" +
                        " Số lượng: %d | Trạng thái: %s\n" +
                        " Giá nhập: %,.0f₫ | Giá bán: %,.0f₫\n" +
                        " NCC: %s | Ngày nhập: %s\n" +
                        " Tuổi tồn kho: %d ngày",
                tenSanPham, serial, loaiSanPham, viTriKe, soLuong,
                trangThai, giaNhap, giaBan, nhaCungCap,
                KhoHelper.formatDate(ngayNhapKho),
                KhoHelper.tinhTuoiTonKho(ngayNhapKho));
    }

    @Override
    public double tinhGiaTriTonKho() {
        return giaNhap * soLuong;
    }

    @Override
    public boolean kiemTraCanCanhBao() {
        // Cần xử lý nếu tồn kho quá 90 ngày hoặc số lượng < 5
        return KhoHelper.tinhTuoiTonKho(ngayNhapKho) > 90 || soLuong < 5;
    }

    // Getters & Setters
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }

    public String getLoaiSanPham() { return loaiSanPham; }
    public void setLoaiSanPham(String loaiSanPham) { this.loaiSanPham = loaiSanPham; }

    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }

    public String getNhaCungCap() { return nhaCungCap; }
    public void setNhaCungCap(String nhaCungCap) { this.nhaCungCap = nhaCungCap; }

    public String toFileFormat() {
        return String.format("%s|%s|%s|%s|%s|%d|%s|%.2f|%.2f|%s|%d",
                maThucThe, tenSanPham, serial, loaiSanPham, viTriKe,
                soLuong, trangThai, giaNhap, giaBan, nhaCungCap,
                ngayNhapKho.getTime());
    }
}
