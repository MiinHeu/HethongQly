public class PhuKien extends SanPham {
    private String loaiPhuKien;
    private String tuongThich;

    public PhuKien(String maSP, String tenSP, double giaBan, String hangSX,
                   int soLuongTon, String ngayNhap, String loaiPhuKien, String tuongThich) {
        super(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap);
        this.loaiPhuKien = loaiPhuKien;
        this.tuongThich = tuongThich;
    }

    // Getters and Setters
    public String getLoaiPhuKien() { return loaiPhuKien; }
    public void setLoaiPhuKien(String loaiPhuKien) { this.loaiPhuKien = loaiPhuKien; }

    public String getTuongThich() { return tuongThich; }
    public void setTuongThich(String tuongThich) { this.tuongThich = tuongThich; }

    @Override
    public String getLoaiSP() {
        return "Phụ kiện";
    }

    @Override
    public String toString() {
        return String.format("PhuKien[maSP=%s, tenSP=%s, giaBan=%.2f, hangSX=%s, " +
                           "soLuongTon=%d, ngayNhap=%s, loaiPhuKien=%s, tuongThich=%s]",
                           maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPhuKien, tuongThich);
    }
}