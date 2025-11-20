package QuanLyCuaHangBanDienThoai;

import java.time.*;

abstract class ConNguoi {
    protected String ma;
    protected String ten;
    protected String sdt;
    protected String email;
    protected LocalDate ngaySinh;

    public ConNguoi() {}
    public ConNguoi(String ma, String ten, String sdt, String email, LocalDate ngaySinh) {
        this.ma = ma;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
    }

    public abstract void xuat();

    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public String getSdt() { return sdt; }
    public String getEmail() { return email; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    
    // Setter methods
    public void setMa(String ma) { this.ma = ma; }
    public void setTen(String ten) { this.ten = ten; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setEmail(String email) { this.email = email; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
}