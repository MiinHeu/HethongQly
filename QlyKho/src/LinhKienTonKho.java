import java.util.Date;

public class LinhKienTonKho extends ThucTheKho {
    private String tenLinhKien;
    private String loaiLinhKien; // "Màn hình", "Pin", "Camera"...
    private String moTa;
    private Date hanSuDung;
    private double giaNhap;
    private String mucDichSuDung; // "Sửa chữa", "Bảo hành"

    public LinhKienTonKho() {
        super();
    }

    public LinhKienTonKho(String maKho, String tenLinhKien, String loaiLinhKien,
                          String viTri, int soLuong, Date hanSuDung,
                          double giaNhap, String mucDichSuDung) {
        super(maKho, viTri, soLuong, "Tốt");
        this.tenLinhKien = tenLinhKien;
        this.loaiLinhKien = loaiLinhKien;
        this.hanSuDung = hanSuDung;
        this.giaNhap = giaNhap;
        this.mucDichSuDung = mucDichSuDung;
    }

    @Override
    public String layThongTinChiTiet() {
        String trangThaiHSD = kiemTraHanSuDung();
        return String.format("【LINH KIỆN】%s\n" +
                        " Loại: %s | Vị trí: %s\n" +
                        " Số lượng: %d | Trạng thái: %s\n" +
                        " Mục đích: %s\n" +
                        " Giá nhập: %,.0f₫\n" +
                        " Hạn SD: %s (%s)\n" +
                        " Tuổi tồn kho: %d ngày",
                tenLinhKien, loaiLinhKien, viTriKe, soLuong,
                trangThai, mucDichSuDung, giaNhap,
                KhoHelper.formatDate(hanSuDung), trangThaiHSD,
                KhoHelper.tinhTuoiTonKho(ngayNhapKho));
    }

    @Override
    public double tinhGiaTriTonKho() {
        // Giảm giá trị nếu gần hết hạn
        double heSo = kiemTraHanSuDung().contains("Hết hạn") ? 0.3 : 1.0;
        return giaNhap * soLuong * heSo;
    }

    @Override
    public boolean kiemTraCanCanhBao() {
        // Cần xử lý nếu gần hết hạn (< 30 ngày) hoặc đã hết hạn
        long ngayConLai = (hanSuDung.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24);
        return ngayConLai < 30 || soLuong < 3;
    }

    private String kiemTraHanSuDung() {
        long ngayConLai = (hanSuDung.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24);
        if (ngayConLai < 0) return "⚠️ Hết hạn";
        if (ngayConLai < 30) return "⚠️ Sắp hết hạn";
        return "✓ Còn hạn";
    }

    // Getters & Setters
    public String getTenLinhKien() { return tenLinhKien; }
    public void setTenLinhKien(String tenLinhKien) { this.tenLinhKien = tenLinhKien; }

    public String getLoaiLinhKien() { return loaiLinhKien; }
    public void setLoaiLinhKien(String loaiLinhKien) { this.loaiLinhKien = loaiLinhKien; }

    public Date getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(Date hanSuDung) { this.hanSuDung = hanSuDung; }

    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    public String toFileFormat() {
        return String.format("%s|%s|%s|%s|%d|%s|%d|%d|%.2f|%s",
                maThucThe, tenLinhKien, loaiLinhKien, viTriKe, soLuong,
                trangThai, ngayNhapKho.getTime(), hanSuDung.getTime(),
                giaNhap, mucDichSuDung);
    }
}