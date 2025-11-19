package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: PhieuNhapHang.java

import java.util.Date;

/**
 * Lớp PhieuNhapHang kế thừa từ GiaoDich, đại diện cho giao dịch nhập hàng từ nhà cung cấp.
 */
public class PhieuNhapHang extends GiaoDich {
    private String nhaCungCap;
    private int soLuong;
    private double giaNhap;

    public PhieuNhapHang(String maGD, Date ngayGD, String nguoiThucHien, String nhaCungCap, int soLuong, double giaNhap) {
        super(maGD, ngayGD, nguoiThucHien, soLuong * giaNhap);
        this.nhaCungCap = nhaCungCap;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
    }

    // --- Getters ---
    public String getNhaCungCap() { return nhaCungCap; }
    public int getSoLuong() { return soLuong; }
    public double getGiaNhap() { return giaNhap; }

    @Override
    public void xuatThongTin() {
        System.out.println("--- PHIẾU NHẬP HÀNG ---");
        System.out.println("Mã giao dịch: " + maGD);
        System.out.println("Ngày nhập: " + ngayGD);
        System.out.println("Người nhập: " + nguoiThucHien);
        System.out.println("Nhà cung cấp: " + nhaCungCap);
        System.out.println("Số lượng: " + soLuong);
        System.out.println("Giá nhập: " + GiaoDichHelper.formatTien(giaNhap));
        System.out.println("Tổng tiền: " + GiaoDichHelper.formatTien(tongTien));
        System.out.println("========================");
    }
}