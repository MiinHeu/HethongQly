package QuanLyCuaHangBanDienThoai;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

class KhachHang extends ConNguoi {
    protected int diemTichLuy;
    protected String hangThanhVien;
    protected List<String> lichSuMuaHang;

    public KhachHang(String ma, String ten, String sdt, String email, LocalDate ngaySinh,
                     int diemTichLuy, String hangThanhVien, List<String> lichSuMuaHang) {
        super(ma, ten, sdt, email, ngaySinh);
        this.diemTichLuy = diemTichLuy;
        this.hangThanhVien = hangThanhVien;
        this.lichSuMuaHang = lichSuMuaHang != null ? lichSuMuaHang : new ArrayList<>();
    }

    public void xuat() {
        System.out.printf("%s | %s | %s | %s | %s | Điểm: %d | Hạng: %s%n",
                ma, ten, sdt, email,
                ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                diemTichLuy, hangThanhVien);
    }

    public int getDiemTichLuy() { return diemTichLuy; }
    public String getHangThanhVien() { return hangThanhVien; }
    public List<String> getLichSuMuaHang() { return lichSuMuaHang; }
}