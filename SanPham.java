import java.io.Serializable;

public abstract class SanPham implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String maSP;
    protected String tenSP;
    protected double giaBan;
    protected String hangSX;
    protected int soLuongTon;
    protected String ngayNhap;

    public SanPham(String maSP, String tenSP, double giaBan, String hangSX, int soLuongTon, String ngayNhap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.hangSX = hangSX;
        this.soLuongTon = soLuongTon;
        this.ngayNhap = ngayNhap;
    }

    // Getters and Setters
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    
    public String getHangSX() { return hangSX; }
    public void setHangSX(String hangSX) { this.hangSX = hangSX; }
    
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
    
    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }

    // Abstract methods
    public abstract String getLoaiSP();
    public abstract String toString();
}