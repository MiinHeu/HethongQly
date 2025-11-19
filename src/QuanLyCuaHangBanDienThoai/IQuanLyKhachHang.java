package QuanLyCuaHangBanDienThoai;

import java.util.*;

interface IQuanLyKhachHang {
    void capNhatDiemTichLuy(String maKH, int diem);
    String xepHang(KhachHang kh);
    double tinhChietKhau(KhachHang kh, double tongTien);
    List<KhachHang> locKhachSapSinhNhat(int soNgay);
}