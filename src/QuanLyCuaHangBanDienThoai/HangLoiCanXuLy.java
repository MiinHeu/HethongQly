package QuanLyCuaHangBanDienThoai;

import java.util.Date;

// Lớp hàng lỗi cần xử lý
public class HangLoiCanXuLy extends ThucTheKho {
    private String maSanPham;
    private String tenSanPham;
    private String loaiLoi;
    private Date ngayPhatHien;
    private String nguonGoc;
    private String cachXuLy;
    private double giaTriThietHai;

    // Hàm thiết lập
    public HangLoiCanXuLy(String maSanPham, String tenSanPham, String loaiLoi, Date ngayPhatHien, String nguonGoc, String cachXuLy, double giaTriThietHai, String viTri, int soLuong, String trangThai) {
        super(viTri, soLuong, trangThai);
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiLoi = loaiLoi;
        this.ngayPhatHien = ngayPhatHien;
        this.nguonGoc = nguonGoc;
        this.cachXuLy = cachXuLy;
        this.giaTriThietHai = giaTriThietHai;
    }

    @Override
    public String layThongTinChiTiet() {
        return "Hàng lỗi: " + tenSanPham +
                ", Loại lỗi: " + loaiLoi +
                ", Nguồn gốc: " + nguonGoc +
                ", Số lượng: " + soLuong;
    }

    @Override
    public double tinhGiaTri() {
        return giaTriThietHai * soLuong;
    }

    // Getter và Setter
    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiLoi() {
        return loaiLoi;
    }

    public void setLoaiLoi(String loaiLoi) {
        this.loaiLoi = loaiLoi;
    }

    public Date getNgayPhatHien() {
        return ngayPhatHien;
    }

    public void setNgayPhatHien(Date ngayPhatHien) {
        this.ngayPhatHien = ngayPhatHien;
    }

    public String getNguonGoc() {
        return nguonGoc;
    }

    public void setNguonGoc(String nguonGoc) {
        this.nguonGoc = nguonGoc;
    }

    public String getCachXuLy() {
        return cachXuLy;
    }

    public void setCachXuLy(String cachXuLy) {
        this.cachXuLy = cachXuLy;
    }

    public double getGiaTriThietHai() {
        return giaTriThietHai;
    }

    public void setGiaTriThietHai(double giaTriThietHai) {
        this.giaTriThietHai = giaTriThietHai;
    }

    @Override
    public String toString() {
        return maSanPham + "|" + tenSanPham + "|" + loaiLoi + "|" + ngayPhatHien.getTime() + "|" + nguonGoc + "|" + cachXuLy + "|" + giaTriThietHai + "|" + viTri + "|" + soLuong + "|" + trangThai;
    }
}