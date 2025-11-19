package QuanLyCuaHangBanDienThoai;

import java.util.Date;

// Lớp linh kiện tồn kho cho sửa chữa
public class LinhKienTonKho extends ThucTheKho {
    private String maLinhKien;
    private String tenLinhKien;
    private Date hanSuDung;
    private String mucDichSuDung;
    private double giaNhap;

    // Hàm thiết lập
    public LinhKienTonKho(String maLinhKien, String tenLinhKien, Date hanSuDung, String mucDichSuDung, double giaNhap, String viTri, int soLuong, String trangThai) {
        super(viTri, soLuong, trangThai);
        this.maLinhKien = maLinhKien;
        this.tenLinhKien = tenLinhKien;
        this.hanSuDung = hanSuDung;
        this.mucDichSuDung = mucDichSuDung;
        this.giaNhap = giaNhap;
    }

    @Override
    public String layThongTinChiTiet() {
        return "Linh kiện: " + tenLinhKien +
                ", Mã: " + maLinhKien +
                ", Hạn SD: " + hanSuDung +
                ", Số lượng: " + soLuong;
    }

    @Override
    public double tinhGiaTri() {
        return giaNhap * soLuong;
    }

    // Getter và Setter
    public String getMaLinhKien() {
        return maLinhKien;
    }

    public void setMaLinhKien(String maLinhKien) {
        this.maLinhKien = maLinhKien;
    }

    public String getTenLinhKien() {
        return tenLinhKien;
    }

    public void setTenLinhKien(String tenLinhKien) {
        this.tenLinhKien = tenLinhKien;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getMucDichSuDung() {
        return mucDichSuDung;
    }

    public void setMucDichSuDung(String mucDichSuDung) {
        this.mucDichSuDung = mucDichSuDung;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    @Override
    public String toString() {
        return maLinhKien + "|" + tenLinhKien + "|" + hanSuDung.getTime() + "|" + mucDichSuDung + "|" + giaNhap + "|" + viTri + "|" + soLuong + "|" + trangThai;
    }
}