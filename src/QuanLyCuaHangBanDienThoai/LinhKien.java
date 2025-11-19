package QuanLyCuaHangBanDienThoai;

public class LinhKien extends SanPham {
    private boolean danhChoSuaChua;
    private boolean thuocBaoHanh;

    public LinhKien(String maSP, String tenSP, double giaBan, String hangSX,
                    int soLuongTon, String ngayNhap, boolean danhChoSuaChua, boolean thuocBaoHanh) {
        super(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap);
        this.danhChoSuaChua = danhChoSuaChua;
        this.thuocBaoHanh = thuocBaoHanh;
    }

    // Getters and Setters
    public boolean isDanhChoSuaChua() { return danhChoSuaChua; }
    public void setDanhChoSuaChua(boolean danhChoSuaChua) { this.danhChoSuaChua = danhChoSuaChua; }

    public boolean isThuocBaoHanh() { return thuocBaoHanh; }
    public void setThuocBaoHanh(boolean thuocBaoHanh) { this.thuocBaoHanh = thuocBaoHanh; }

    @Override
    public String getLoaiSP() {
        return "Linh kiện";
    }

    @Override
    public String toString() {
        return String.format("LinhKien[maSP=%s, tenSP=%s, giaBan=%.2f, hangSX=%s, " +
                           "soLuongTon=%d, ngayNhap=%s, danhChoSuaChua=%b, thuocBaoHanh=%b]",
                           maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSuaChua, thuocBaoHanh);
    }
}