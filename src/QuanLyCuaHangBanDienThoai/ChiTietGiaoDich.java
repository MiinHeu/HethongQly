package QuanLyCuaHangBanDienThoai;

// Thành viên 3 - Quản lý Giao Dịch
// File: ChiTietGiaoDich.java
public class ChiTietGiaoDich {
    private String maSP;
    private int soLuong;
    private double donGia;
    public ChiTietGiaoDich(String maSP, int soLuong, double donGia) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    public String getMaSP() { return maSP; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
    public double tinhThanhTien() {
        return soLuong * donGia;
    }
}