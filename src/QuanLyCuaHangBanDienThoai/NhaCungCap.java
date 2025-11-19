package QuanLyCuaHangBanDienThoai;

// Lớp nhà cung cấp
public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String sanPhamCungCap;
    private double doTinCay;
    private String soDienThoai;
    private String diaChi;

    // Biến static đếm số nhà cung cấp
    private static int tongSoNhaCungCap = 0;

    // Hàm thiết lập
    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String sanPhamCungCap, double doTinCay, String soDienThoai, String diaChi) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sanPhamCungCap = sanPhamCungCap;
        this.doTinCay = doTinCay;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        tongSoNhaCungCap++;
    }

    // Phương thức static
    public static int layTongSoNhaCungCap() {
        return tongSoNhaCungCap;
    }

    // Getter và Setter
    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSanPhamCungCap() {
        return sanPhamCungCap;
    }

    public void setSanPhamCungCap(String sanPhamCungCap) {
        this.sanPhamCungCap = sanPhamCungCap;
    }

    public double getDoTinCay() {
        return doTinCay;
    }

    public void setDoTinCay(double doTinCay) {
        this.doTinCay = doTinCay;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return maNhaCungCap + "|" + tenNhaCungCap + "|" + sanPhamCungCap + "|" + doTinCay + "|" + soDienThoai + "|" + diaChi;
    }
}