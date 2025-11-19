package QuanLyCuaHangBanDienThoai;

import java.util.Date;

// Lớp kế thừa từ ThucTheKho và SanPham
public class SanPhamTrongKho extends ThucTheKho {
    private SanPham sanPham;
    private String serialIMEI;
    private Date ngayNhap;
    private String viTriKe;

    // Hàm thiết lập đầy đủ
    public SanPhamTrongKho(SanPham sanPham, String serialIMEI, Date ngayNhap, String viTriKe, String viTri, int soLuong, String trangThai) {
        super(viTri, soLuong, trangThai);
        this.sanPham = sanPham;
        this.serialIMEI = serialIMEI;
        this.ngayNhap = ngayNhap;
        this.viTriKe = viTriKe;
    }

    // Cài đặt hàm trừu tượng từ lớp cha
    @Override
    public String layThongTinChiTiet() {
        return "Sản phẩm: " + sanPham.getTenSP() +
                ", Serial: " + serialIMEI +
                ", Vị trí kệ: " + viTriKe +
                ", Số lượng: " + soLuong;
    }

    @Override
    public double tinhGiaTri() {
        return sanPham.getGiaBan() * soLuong;
    }

    // Getter và Setter
    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getSerialIMEI() {
        return serialIMEI;
    }

    public void setSerialIMEI(String serialIMEI) {
        this.serialIMEI = serialIMEI;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getViTriKe() {
        return viTriKe;
    }

    public void setViTriKe(String viTriKe) {
        this.viTriKe = viTriKe;
    }

    @Override
    public String toString() {
        return sanPham.getMaSP() + "|" + serialIMEI + "|" + ngayNhap.getTime() + "|" + viTriKe + "|" + viTri + "|" + soLuong + "|" + trangThai;
    }
}