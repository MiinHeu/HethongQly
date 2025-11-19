package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: IQuanLyGiaoDich.java

import java.util.Date;

/**
 * Interface định nghĩa các nghiệp vụ nâng cao cho việc quản lý giao dịch.
 */
public interface IQuanLyGiaoDich {
    /**
     * Tạo một hóa đơn mới.
     */
    void taoHoaDon();

    /**
     * Thống kê doanh thu trong một khoảng thời gian.
     * @return tổng doanh thu.
     */
    double thongKeDoanhThu(Date tuNgay, Date denNgay);

    /**
     * Lọc và hiển thị các giao dịch trong một ngày cụ thể.
     */
    void locTheoNgay(Date ngay);
}