package QuanLyCuaHangBanDienThoai;

import java.time.*;
import java.util.*;

class KhachHangVIP extends KhachHang {

    private double uuDai;

    public KhachHangVIP(String ma, String ten, String sdt, String email, LocalDate ngaySinh,
                        int diemTichLuy, String hangThanhVien,
                        List<String> lichSuMuaHang, double uuDai) {
        super(ma, ten, sdt, email, ngaySinh, diemTichLuy, hangThanhVien, lichSuMuaHang);
        this.uuDai = uuDai;
    }

    @Override
    public void xuat() {
        System.out.print("[VIP] ");
        super.xuat();
        System.out.println("Ưu đãi: " + uuDai * 100 + "%");
    }
}
