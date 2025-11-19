package QuanLyCuaHangBanDienThoai;

public class DienThoai extends SanPham {
    private String imei;
    private String cauHinh;

    public DienThoai(String maSP, String tenSP, double giaBan, String hangSX, 
                     int soLuongTon, String ngayNhap, String imei, String cauHinh) {
        super(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap);
        this.imei = imei;
        this.cauHinh = cauHinh;
    }

    // Getters and Setters
    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public String getCauHinh() { return cauHinh; }
    public void setCauHinh(String cauHinh) { this.cauHinh = cauHinh; }

    @Override
    public String getLoaiSP() {
        return "Điện thoại";
    }

    @Override
    public String toString() {
        return String.format("DienThoai[maSP=%s, tenSP=%s, giaBan=%.2f, hangSX=%s, " +
                           "soLuongTon=%d, ngayNhap=%s, IMEI=%s, cauHinh=%s]",
                           maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
    }
}