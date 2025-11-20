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

    @Override
    public void xuat() {
        System.out.printf("%s | %s | %s | %s | %s | Điểm: %d | Hạng: %s%n",
                ma, ten, sdt, email,
                ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                diemTichLuy, hangThanhVien);
    }

    // Ghi file theo định dạng đẹp
    public String toData() {
        return ma + ";" + ten + ";" + sdt + ";" + email + ";" +
                ngaySinh + ";" + diemTichLuy + ";" + hangThanhVien;
    }

    // Load từ file
    public static KhachHang fromData(String data) {
        String[] a = data.split(";");
        return new KhachHang(
                a[0], a[1], a[2], a[3],
                LocalDate.parse(a[4]),
                Integer.parseInt(a[5]),
                a[6],
                new ArrayList<>()
        );
    }

    public int getDiemTichLuy() { return diemTichLuy; }
    public String getHangThanhVien() { return hangThanhVien; }
    public List<String> getLichSuMuaHang() { return lichSuMuaHang; }
    
    // Setter methods
    public void setDiemTichLuy(int diemTichLuy) { this.diemTichLuy = diemTichLuy; }
    public void setHangThanhVien(String hangThanhVien) { this.hangThanhVien = hangThanhVien; }
    public void setLichSuMuaHang(List<String> lichSuMuaHang) { this.lichSuMuaHang = lichSuMuaHang; }
}
