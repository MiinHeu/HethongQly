import java.util.Date;
import java.util.*;
public class HangLoiCanXuLy extends ThucTheKho {
    private String tenSanPham;
    private String serial;
    private String loiPhatHien; // Mô tả lỗi
    private String nguonGoc; // "Nhập về bị lỗi", "Khách trả lại", "Hỏng trong kho"
    private String phuongAnXuLy; // "Trả NCC", "Sửa chữa", "Thanh lý"
    private double giaTriUocTinh;
    private boolean daXuLy;

    public HangLoiCanXuLy() {
        super();
        this.trangThai = "LOI";
        this.daXuLy = false;
    }

    public HangLoiCanXuLy(String maKho, String tenSanPham, String serial,
                          String viTri, int soLuong, String loiPhatHien,
                          String nguonGoc, double giaTriUocTinh) {
        super(maKho, viTri, soLuong, "LOI");
        this.tenSanPham = tenSanPham;
        this.serial = serial;
        this.loiPhatHien = loiPhatHien;
        this.nguonGoc = nguonGoc;
        this.giaTriUocTinh = giaTriUocTinh;
        this.daXuLy = false;
        this.phuongAnXuLy = "Chưa xác định";
    }

    @Override
    public String layThongTinChiTiet() {
        return String.format("HÀNG LỖI %s\n" +
                        " Serial: %s | Vị trí: %s\n" +
                        " Số lượng: %d | Trạng thái: %s\n" +
                        " Lỗi: %s\n" +
                        " Nguồn gốc: %s\n" +
                        " Phương án: %s\n" +
                        " Giá trị ước tính: %,.0f₫\n" +
                        " Đã xử lý: %s\n" +
                        " Thời gian tồn: %d ngày",
                tenSanPham, serial, viTriKe, soLuong, trangThai,
                loiPhatHien, nguonGoc, phuongAnXuLy, giaTriUocTinh,
                (daXuLy ? "✓" : "✗"),
                KhoHelper.tinhTuoiTonKho(ngayNhapKho));
    }

    @Override
    public double tinhGiaTriTonKho() {
        // Hàng lỗi chỉ còn 20-50% giá trị
        return daXuLy ? 0 : giaTriUocTinh * 0.3;
    }

    @Override
    public boolean kiemTraCanCanhBao() {
        // Luôn cần xử lý nếu chưa xử lý
        return !daXuLy;
    }

    public void xuLy(String phuongAn) {
        this.phuongAnXuLy = phuongAn;
        this.daXuLy = true;
        this.trangThai = "Đã xử lý - " + phuongAn;
    }

    // Getters & Setters
    public String getTenSanPham() { return tenSanPham; }
    public String getSerial() { return serial; }
    public String getLoiPhatHien() { return loiPhatHien; }
    public String getNguonGoc() { return nguonGoc; }
    public String getPhuongAnXuLy() { return phuongAnXuLy; }
    public double getGiaTriUocTinh() { return giaTriUocTinh; }
    public boolean isDaXuLy() { return daXuLy; }

    public void setLoiPhatHien(String loiPhatHien) { this.loiPhatHien = loiPhatHien; }
    public void setPhuongAnXuLy(String phuongAnXuLy) { this.phuongAnXuLy = phuongAnXuLy; }

    public String toFileFormat() {
        return String.format("%s|%s|%s|%s|%d|%s|%s|%s|%s|%.2f|%b|%d",
                maThucThe, tenSanPham, serial, viTriKe, soLuong,
                trangThai, loiPhatHien, nguonGoc, phuongAnXuLy,
                giaTriUocTinh, daXuLy, ngayNhapKho.getTime());
    }
}
