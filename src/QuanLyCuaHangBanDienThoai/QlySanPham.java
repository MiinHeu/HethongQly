package QuanLyCuaHangBanDienThoai;

import java.util.List;
import java.util.Scanner;

class QlySanPham {
    private DanhSachSanPham danhSachSP;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "src/QuanLyCuaHangBanDienThoai/sanpham.txt";

    public QlySanPham() {
        qltt = QuanLyTrungTam.getInstance();
        danhSachSP = qltt.getDanhSachSanPham();
    }

// menu chính

public void menu() {
    int choice;
    do {
        System.out.println("\n--- QUAN LY SAN PHAM ---");
        System.out.println("1. Them san pham");
        System.out.println("2. Sua thong tin san pham");
        System.out.println("3. Xoa san pham");
        System.out.println("4. Tim kiem san pham");
        System.out.println("5. Xem danh sach san pham");
        System.out.println("6. Tai du lieu tu file");
        System.out.println("7. Xuat du lieu ra file");
        System.out.println("8. Quay lai");
        System.out.print("Chon: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: themSanPham(); break;
            case 2: suaSanPham(); break;
            case 3: xoaSanPham(); break;
            case 4: timKiemSanPham(); break;
            case 5: xemDanhSach(); break;
            case 6: taiDuLieu(); break;
            case 7: xuatDuLieu(); break;
            case 8: break;
            default: System.out.println("Lua chon khong hop le!");
        }
    } while (choice != 8);
}

// ===== 1. THEM SAN PHAM 

private void themSanPham() {
    try {
        System.out.println("\n--- THEM SAN PHAM ---");
        
        String maSP = SanPhamHelper.sinhMaSanPham();
        
        System.out.print("Ten san pham: ");
        String tenSP = scanner.nextLine().trim();
        if (tenSP.isEmpty()) {
            System.out.println("Loi: Ten khong duoc de trong!");
            return;
        }
        
        System.out.print("Gia ban: ");
        double giaBan = scanner.nextDouble();
        if (giaBan <= 0) {
            System.out.println("Loi: Gia phai lon hon 0!");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        System.out.print("Hang san xuat: ");
        String hangSX = scanner.nextLine().trim();
        
        System.out.print("So luong ton: ");
        int soLuongTon = scanner.nextInt();
        if (soLuongTon < 0) {
            System.out.println("Loi: So luong khong hop le!");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        System.out.print("Ngay nhap (dd/MM/yyyy): ");
        String ngayNhap = scanner.nextLine().trim();
        
        System.out.println("\nChon loai san pham:");
        System.out.println("1. Dien thoai");
        System.out.println("2. Phu kien");
        System.out.println("3. Linh kien");
        System.out.print("Chon: ");
        int loai = scanner.nextInt();
        scanner.nextLine();
        
        SanPham sp;
        if (loai == 1) {
            System.out.print("IMEI: ");
            String imei = scanner.nextLine().trim();
            System.out.print("Cau hinh: ");
            String cauHinh = scanner.nextLine().trim();
            sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
        } else if (loai == 2) {
            System.out.print("Loai phu kien: ");
            String loaiPK = scanner.nextLine().trim();
            System.out.print("Tuong thich: ");
            String tuongThich = scanner.nextLine().trim();
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPK, tuongThich);
        } else if (loai == 3) {
            System.out.print("Danh cho sua chua? (y/n): ");
            boolean danhChoSua = scanner.nextLine().equalsIgnoreCase("y");
            System.out.print("Thuoc bao hanh? (y/n): ");
            boolean thuocBaoHanh = scanner.nextLine().equalsIgnoreCase("y");
            sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSua, thuocBaoHanh);
        } else {
            System.out.println("Lua chon khong hop le!");
            return;
        }
        
        danhSachSP.themSanPham(sp);
        System.out.println("\nThem thanh cong! Ma SP: " + maSP);
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        scanner.nextLine();
    }
}

// ===== 2. SUA SAN PHAM =====

private void suaSanPham() {
    try {
        System.out.println("\n--- SUA SAN PHAM ---");
        
        System.out.print("Nhap ma san pham: ");
        String maSP = scanner.nextLine().trim();
        
        SanPham sp = danhSachSP.timTheoMa(maSP);
        if (sp == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ma: " + sp.getMaSP());
        System.out.println("Ten: " + sp.getTenSP());
        System.out.println("Gia: " + sp.getGiaBan());
        System.out.println("Hang: " + sp.getHangSX());
        System.out.println("So luong: " + sp.getSoLuongTon());
        
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        System.out.print("Ten [" + sp.getTenSP() + "]: ");
        String ten = scanner.nextLine().trim();
        if (!ten.isEmpty()) sp.setTenSP(ten);
        
        System.out.print("Gia [" + sp.getGiaBan() + "]: ");
        String giaStr = scanner.nextLine().trim();
        if (!giaStr.isEmpty()) {
            try {
                double gia = Double.parseDouble(giaStr);
                if (gia > 0) sp.setGiaBan(gia);
            } catch (NumberFormatException e) {}
        }
        
        System.out.print("Hang [" + sp.getHangSX() + "]: ");
        String hang = scanner.nextLine().trim();
        if (!hang.isEmpty()) sp.setHangSX(hang);
        
        System.out.print("So luong [" + sp.getSoLuongTon() + "]: ");
        String slStr = scanner.nextLine().trim();
        if (!slStr.isEmpty()) {
            try {
                int sl = Integer.parseInt(slStr);
                if (sl >= 0) sp.setSoLuongTon(sl);
            } catch (NumberFormatException e) {}
        }
        
        // Sua thuoc tinh rieng
        if (sp instanceof DienThoai) {
            DienThoai dt = (DienThoai) sp;
            System.out.print("IMEI [" + dt.getImei() + "]: ");
            String imei = scanner.nextLine().trim();
            if (!imei.isEmpty()) dt.setImei(imei);
            
            System.out.print("Cau hinh [" + dt.getCauHinh() + "]: ");
            String cauHinh = scanner.nextLine().trim();
            if (!cauHinh.isEmpty()) dt.setCauHinh(cauHinh);
        } else if (sp instanceof PhuKien) {
            PhuKien pk = (PhuKien) sp;
            System.out.print("Loai [" + pk.getLoaiPhuKien() + "]: ");
            String loai = scanner.nextLine().trim();
            if (!loai.isEmpty()) pk.setLoaiPhuKien(loai);
            
            System.out.print("Tuong thich [" + pk.getTuongThich() + "]: ");
            String tt = scanner.nextLine().trim();
            if (!tt.isEmpty()) pk.setTuongThich(tt);
        } else if (sp instanceof LinhKien) {
            LinhKien lk = (LinhKien) sp;
            System.out.print("Sua chua? (y/n) [" + (lk.isDanhChoSuaChua() ? "y" : "n") + "]: ");
            String sc = scanner.nextLine().trim();
            if (!sc.isEmpty()) lk.setDanhChoSuaChua(sc.equalsIgnoreCase("y"));
            
            System.out.print("Bao hanh? (y/n) [" + (lk.isThuocBaoHanh() ? "y" : "n") + "]: ");
            String bh = scanner.nextLine().trim();
            if (!bh.isEmpty()) lk.setThuocBaoHanh(bh.equalsIgnoreCase("y"));
        }
        
        System.out.println("\nCap nhat thanh cong!");
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}

// ===== 3. XOA SAN PHAM =====

private void xoaSanPham() {
    try {
        System.out.println("\n--- XOA SAN PHAM ---");
        
        System.out.print("Nhap ma san pham: ");
        String maSP = scanner.nextLine().trim();
        
        SanPham sp = danhSachSP.timTheoMa(maSP);
        if (sp == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin san pham:");
        System.out.println("Ma: " + sp.getMaSP());
        System.out.println("Ten: " + sp.getTenSP());
        System.out.println("Gia: " + sp.getGiaBan());
        
        System.out.print("\nXac nhan xoa? (y/n): ");
        String xacNhan = scanner.nextLine().trim();
        
        if (xacNhan.equalsIgnoreCase("y")) {
            if (danhSachSP.xoaSanPham(maSP)) {
                System.out.println("Xoa thanh cong!");
            } else {
                System.out.println("Loi khi xoa!");
            }
        } else {
            System.out.println("Da huy!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}

// ===== 4. TIM KIEM SAN PHAM =====

private void timKiemSanPham() {
    try {
        System.out.println("\n--- TIM KIEM SAN PHAM ---");
        System.out.println("1. Tim theo Ma");
        System.out.println("2. Tim theo Ten");
        System.out.println("3. Tim theo Hang");
        System.out.println("4. Tim theo Loai");
        System.out.print("Chon: ");
        int chon = scanner.nextInt();
        scanner.nextLine();

        if (chon == 1) {
            System.out.print("Nhap ma: ");
            String ma = scanner.nextLine().trim();
            SanPham sp = danhSachSP.timTheoMa(ma);
            if (sp != null) {
                System.out.println("\nTim thay:");
                hienThiSP(sp);
            } else {
                System.out.println("Khong tim thay!");
            }
        } else if (chon == 2) {
            System.out.print("Nhap ten: ");
            String ten = scanner.nextLine().trim();
            List<SanPham> ds = danhSachSP.timTheoTen(ten);
            hienThiDanhSachSP(ds);
        } else if (chon == 3) {
            System.out.print("Nhap hang: ");
            String hang = scanner.nextLine().trim();
            List<SanPham> ds = danhSachSP.timTheoHang(hang);
            hienThiDanhSachSP(ds);
        } else if (chon == 4) {
            System.out.print("Nhap loai: ");
            String loai = scanner.nextLine().trim();
            List<SanPham> ds = danhSachSP.timTheoLoai(loai);
            hienThiDanhSachSP(ds);
        } else {
            System.out.println("Lua chon khong hop le!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        scanner.nextLine();
    }
}

private void hienThiSP(SanPham sp) {
    System.out.println("Ma: " + sp.getMaSP());
    System.out.println("Ten: " + sp.getTenSP());
    System.out.println("Gia: " + sp.getGiaBan());
    System.out.println("Hang: " + sp.getHangSX());
    System.out.println("So luong: " + sp.getSoLuongTon());
    System.out.println("Loai: " + sp.getLoaiSP());
}

private void hienThiDanhSachSP(List<SanPham> ds) {
    if (ds.isEmpty()) {
        System.out.println("Khong tim thay!");
    } else {
        System.out.println("\nTim thay " + ds.size() + " san pham:");
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- San pham " + (i + 1) + " ---");
            hienThiSP(ds.get(i));
        }
    }
}

// ===== 5. XEM DANH SACH =====

private void xemDanhSach() {
    System.out.println("\n--- DANH SACH SAN PHAM ---");
    List<SanPham> ds = danhSachSP.layDanhSach();
    if (ds.isEmpty()) {
        System.out.println("Danh sach trong!");
    } else {
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- San pham " + (i + 1) + " ---");
            hienThiSP(ds.get(i));
        }
        System.out.println("\nTong so: " + ds.size());
    }
}

// ===== 6. TAI/XUAT FILE =====

private void taiDuLieu() {
    try {
        danhSachSP.docFile(fileName);
        System.out.println("Da tai du lieu tu file!");
    } catch (Exception e) {
        System.out.println("Loi khi tai file: " + e.getMessage());
    }
}

private void xuatDuLieu() {
    try {
        danhSachSP.ghiFile(fileName);
        System.out.println("Da xuat du lieu ra file!");
    } catch (Exception e) {
        System.out.println("Loi khi xuat file: " + e.getMessage());
    }
}

}
