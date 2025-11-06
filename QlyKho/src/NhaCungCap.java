import java.io.Serializable;

public class NhaCungCap implements Serializable {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String sanPhamCungCap; // "Điện thoại Samsung", "Phụ kiện"
    private double doTinCay; // 0.0 - 10.0
    private int tongDonHang;
    private int soLuongHangLoi;
    private double tyLeHangLoi; // Tự động tính

    public NhaCungCap() {
        this.doTinCay = 5.0;
        this.tongDonHang = 0;
        this.soLuongHangLoi = 0;
    }

    public NhaCungCap(String maNCC, String tenNCC, String diaChi,
                      String soDienThoai, String email, String sanPhamCungCap) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.sanPhamCungCap = sanPhamCungCap;
        this.doTinCay = 5.0;
        this.tongDonHang = 0;
        this.soLuongHangLoi = 0;
        tinhTyLeHangLoi();
    }

    public void capNhatDanhGia(boolean coLoi) {
        tongDonHang++;
        if (coLoi) {
            soLuongHangLoi++;
            doTinCay = Math.max(0, doTinCay - 0.5); // Giảm độ tin cậy
        } else {
            doTinCay = Math.min(10, doTinCay + 0.1); // Tăng độ tin cậy
        }
        tinhTyLeHangLoi();
    }

    private void tinhTyLeHangLoi() {
        if (tongDonHang > 0) {
            tyLeHangLoi = (soLuongHangLoi * 100.0) / tongDonHang;
        } else {
            tyLeHangLoi = 0;
        }
    }

    public String getDanhGia() {
        if (doTinCay >= 8.0) return "Xuất sắc";
        if (doTinCay >= 6.0) return "Tốt";
        if (doTinCay >= 4.0) return "Trung bình";
        return "Không Tốt";
    }

    public String layThongTinChiTiet() {
        return String.format("NHÀ CUNG CẤP  %s - %s\n" +
                        " Địa chỉ: %s\n" +
                        " SĐT: %s | Email: %s\n" +
                        " Sản phẩm cung cấp: %s\n" +
                        " Độ tin cậy: %.1f/10 %s\n" +
                        " Tổng đơn hàng: %d\n" +
                        " Hàng lỗi: %d (%.2f%%)\n" +
                        " Đánh giá: %s",
                maNCC, tenNCC, diaChi, soDienThoai, email,
                sanPhamCungCap, doTinCay,
                tongDonHang, soLuongHangLoi, tyLeHangLoi, getDanhGia());
    }



    // Getters & Setters
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSanPhamCungCap() { return sanPhamCungCap; }
    public void setSanPhamCungCap(String sanPhamCungCap) { this.sanPhamCungCap = sanPhamCungCap; }

    public double getDoTinCay() { return doTinCay; }
    public int getTongDonHang() { return tongDonHang; }
    public int getSoLuongHangLoi() { return soLuongHangLoi; }
    public double getTyLeHangLoi() { return tyLeHangLoi; }

    @Override
    public String toString() {
        return String.format("%s - %s (%.1f | Lỗi: %.1f%%)",
                maNCC, tenNCC, doTinCay, tyLeHangLoi);
    }

    public String toFileFormat() {
        return String.format("%s|%s|%s|%s|%s|%s|%.1f|%d|%d|%.2f",
                maNCC, tenNCC, diaChi, soDienThoai, email,
                sanPhamCungCap, doTinCay, tongDonHang,
                soLuongHangLoi, tyLeHangLoi);
    }
}