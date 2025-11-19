// Thành viên 3 - Quản lý Giao Dịch
// File: PhieuBaoHanh.java

import java.util.Date;
import java.text.SimpleDateFormat; // <-- ĐÃ THÊM

/**
 * Lớp PhieuBaoHanh kế thừa từ GiaoDich, đại diện cho giao dịch bảo hành/sửa chữa.
 */
public class PhieuBaoHanh extends GiaoDich {
    private String maSanPham;
    private String loiHong;
    private double phiSuaChua;

    public PhieuBaoHanh(String maGD, Date ngayGD, String nguoiThucHien, String maSanPham, String loiHong, double phiSuaChua) {
        super(maGD, ngayGD, nguoiThucHien, phiSuaChua);
        this.maSanPham = maSanPham;
        this.loiHong = loiHong;
        this.phiSuaChua = phiSuaChua;
    }
    
    // --- Getters ---
    public String getMaSanPham() { return maSanPham; }
    public String getLoiHong() { return loiHong; }
    public double getPhiSuaChua() { return phiSuaChua; }

    @Override
    public void xuatThongTin() {
        // --- BẮT ĐẦU SỬA ---
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("--- PHIẾU BẢO HÀNH ---");
        System.out.println("Mã giao dịch: " + maGD);
        System.out.println("Ngày bảo hành: " + sdf.format(ngayGD)); // <-- SỬA DÒNG NÀY
        // --- KẾT THÚC SỬA ---
        System.out.println("Kỹ thuật viên: " + nguoiThucHien);
        System.out.println("Mã sản phẩm: " + maSanPham);
        System.out.println("Mô tả lỗi: " + loiHong);
        System.out.println("Phí sửa chữa: " + GiaoDichHelper.formatTien(phiSuaChua));
        System.out.println("=======================");
    }
}