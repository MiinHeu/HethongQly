// Thành viên 3 - Quản lý Giao Dịch
// File: IQuanLyGiaoDich.java
import java.util.Date;
public interface IQuanLyGiaoDich {
    void taoHoaDon();
    double thongKeDoanhThu(Date tuNgay, Date denNgay);
    void locTheoNgay(Date ngay);
}