package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: GiaoDich.java

import java.util.Date;

/**
 * Lớp trừu tượng GiaoDich, làm lớp cha cho các loại giao dịch khác nhau.
 */
public abstract class GiaoDich {
    protected String maGD;
    protected Date ngayGD;
    protected String nguoiThucHien;
    protected double tongTien;

    public GiaoDich() {
    }

    public GiaoDich(String maGD, Date ngayGD, String nguoiThucHien, double tongTien) {
        this.maGD = maGD;
        this.ngayGD = ngayGD;
        this.nguoiThucHien = nguoiThucHien;
        this.tongTien = tongTien;
    }

    // --- Getters and Setters ---
    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public Date getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(Date ngayGD) {
        this.ngayGD = ngayGD;
    }

    public String getNguoiThucHien() {
        return nguoiThucHien;
    }

    public void setNguoiThucHien(String nguoiThucHien) {
        this.nguoiThucHien = nguoiThucHien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    /**
     * Phương thức trừu tượng để xuất thông tin chi tiết của giao dịch.
     * Các lớp con sẽ phải cài đặt (override) phương thức này.
     */
    public abstract void xuatThongTin();
}