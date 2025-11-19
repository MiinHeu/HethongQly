package QuanLyCuaHangBanDienThoai;

import java.util.List;
import java.util.Map;

public interface IQuanLySanPham {
    // Thêm, xóa, sửa sản phẩm
    void themSanPham(SanPham sp);
    boolean xoaSanPham(String maSP);
    boolean suaSanPham(SanPham sp);

    // Tìm kiếm sản phẩm
    SanPham timTheoMa(String maSP);
    List<SanPham> timTheoTen(String tenSP);
    SanPham timTheoIMEI(String imei);  // Riêng điện thoại
    List<SanPham> timTheoHang(String hangSX);
    List<SanPham> locTheoGia(double min, double max);

    // Tìm kiếm gần đúng
    List<SanPham> timGanDung(String tuKhoa);

    // Thống kê
    Map<String, Long> thongKeTheoHang();
    List<SanPham> getTopBanChay(int top);
    List<SanPham> getSanPhamTonKhoCao();

    // Sắp xếp
    void sapXepTheoGia(boolean tangDan);
    void sapXepTheoTen();
    void sapXepTheoNgayNhap();

    // Đọc/ghi file
    void ghiFile(String tenFile);
    void docFile(String tenFile);

    // Tính năng sáng tạo
    List<SanPham> goiYThayThe(SanPham sp);
    List<PhuKien> goiYPhuKienTuongThich(DienThoai dt);
}