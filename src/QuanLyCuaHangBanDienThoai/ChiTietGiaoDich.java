package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: ChiTietGiaoDich.java

/**
 * Lớp ChiTietGiaoDich chứa thông tin về một sản phẩm trong hóa đơn.
 */
public class ChiTietGiaoDich {
    private String maSP;
    private int soLuong;
    private double donGia;

    public ChiTietGiaoDich(String maSP, int soLuong, double donGia) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // --- Getters ---
    public String getMaSP() {
        return maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    /**
     * Tính tổng tiền cho chi tiết này (số lượng * đơn giá).
     * @return thành tiền.
     */
    public double tinhThanhTien() {
        return soLuong * donGia;
    }
}