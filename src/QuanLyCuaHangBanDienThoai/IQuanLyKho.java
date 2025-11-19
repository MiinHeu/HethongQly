package QuanLyCuaHangBanDienThoai;

import java.util.List;

// Interface quản lý kho
public interface IQuanLyKho {
    void nhapKho(SanPham sp, int soLuong, NhaCungCap ncc);
    void xuatKho(String maSP, int soLuong);
    List<SanPham> canhBaoHetHang(int nguongToiThieu);
    double tinhGiaTriTonKho();
}