package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: HoaDonBan.java

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Lớp HoaDonBan kế thừa từ GiaoDich, đại diện cho giao dịch bán hàng.
 */
public class HoaDonBan extends GiaoDich {
    private String maKhachHang;
    private List<ChiTietGiaoDich> danhSachChiTiet;

    public HoaDonBan(String maGD, Date ngayGD, String nguoiThucHien, String maKhachHang) {
        super(maGD, ngayGD, nguoiThucHien, 0); // Tổng tiền ban đầu là 0
        this.maKhachHang = maKhachHang;
        this.danhSachChiTiet = new ArrayList<>();
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public List<ChiTietGiaoDich> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    /**
     * Thêm một chi tiết (sản phẩm) vào hóa đơn và cập nhật lại tổng tiền.
     * @param chiTiet Chi tiết giao dịch cần thêm.
     */
    public void themChiTiet(ChiTietGiaoDich chiTiet) {
        this.danhSachChiTiet.add(chiTiet);
        this.tongTien += chiTiet.tinhThanhTien();
    }

    @Override
    public void xuatThongTin() {
        System.out.println("--- HÓA ĐƠN BÁN HÀNG ---");
        System.out.println("Mã giao dịch: " + maGD);
        System.out.println("Ngày giao dịch: " + ngayGD);
        System.out.println("Nhân viên thực hiện: " + nguoiThucHien);
        System.out.println("Mã khách hàng: " + maKhachHang);
        System.out.println("Chi tiết:");
        for (ChiTietGiaoDich ct : danhSachChiTiet) {
            System.out.printf("  + SP: %s, Số lượng: %d, Đơn giá: %s, Thành tiền: %s%n",
                ct.getMaSP(),
                ct.getSoLuong(),
                GiaoDichHelper.formatTien(ct.getDonGia()),
                GiaoDichHelper.formatTien(ct.tinhThanhTien())
            );
        }
        System.out.println("--------------------------");
        System.out.println("Tổng tiền: " + GiaoDichHelper.formatTien(tongTien));
        System.out.println("VAT (10%): " + GiaoDichHelper.formatTien(GiaoDichHelper.tinhVAT(tongTien)));
        System.out.println("TỔNG CỘNG: " + GiaoDichHelper.formatTien(tongTien + GiaoDichHelper.tinhVAT(tongTien)));
        System.out.println("==========================");
    }
}