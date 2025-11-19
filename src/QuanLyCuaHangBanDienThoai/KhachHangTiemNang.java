package QuanLyCuaHangBanDienThoai;

import java.time.*;
import java.util.*;

class KhachHangTiemNang extends KhachHang {
    public KhachHangTiemNang(String ma, String ten, String sdt, String email, LocalDate ngaySinh) {
        super(ma, ten, sdt, email, ngaySinh, 0, "Tiềm năng", new ArrayList<>());
    }

    @Override
    public void xuat() {
        System.out.println("[Leads] " + ten + " - " + sdt + " - " + email);
    }
}
