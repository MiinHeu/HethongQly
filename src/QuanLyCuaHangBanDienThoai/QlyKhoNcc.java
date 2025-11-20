package QuanLyCuaHangBanDienThoai;

import java.util.*;

// Lớp quản lý module kho và nhà cung cấp
public class QlyKhoNcc {
    private DanhSachKho dsKho;
    private DanhSachNhaCungCap dsNcc;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner sc = new Scanner(System.in);

    // Hàm thiết lập
    public QlyKhoNcc() {
        qltt = QuanLyTrungTam.getInstance();
        dsKho = qltt.getDanhSachKho();
        dsNcc = qltt.getDanhSachNhaCungCap();
    }

    // Menu chính - ĐÃ ĐƠN GIẢN HÓA
    public void menu() {
        int luaChon;
        do {
            System.out.println("\n QUẢN LÝ KHO & NHÀ CUNG CẤP ");
            System.out.println("1. Quản lý kho");
            System.out.println("2. Quản lý nhà cung cấp");
            System.out.println("3. Tải dữ liệu từ file");
            System.out.println("4. Lưu dữ liệu ra file");
            System.out.println("5. Quay lại menu chính");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    menuKho();
                    break;
                case 2:
                    menuNhaCungCap();
                    break;
                case 3:
                    taiDuLieu();
                    break;
                case 4:
                    luuDuLieu();
                    break;
                case 5:
                    System.out.println("Quay lại menu chính...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 5);
    }

    // Menu quản lý kho - ĐÃ ĐƠN GIẢN HÓA
    private void menuKho() {
        int luaChon;
        do {
            System.out.println("\n========== QUẢN LÝ KHO ==========");
            System.out.println("1. Thêm sản phẩm vào kho");
            System.out.println("2. Sửa thông tin sản phẩm");
            System.out.println("3. Xóa sản phẩm khỏi kho");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Xem danh sách sản phẩm trong kho");
            System.out.println("6. Tải dữ liệu kho từ file");
            System.out.println("7. Xuất dữ liệu kho ra file");
            System.out.println("8. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    themSanPham();
                    break;
                case 2:
                    suaSanPham();
                    break;
                case 3:
                    xoaSanPham();
                    break;
                case 4:
                    timKiemSanPham();
                    break;
                case 5:
                    dsKho.xemTatCaSanPham();
                    break;
                case 6:
                    taiDuLieuKho();
                    break;
                case 7:
                    xuatDuLieuKho();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 8);
    }

    // Them san pham vao kho
    private void themSanPham() {
    try {
        System.out.println("\n--- THEM SAN PHAM ---");
        
        System.out.print("Ma san pham: ");
        String maSP = sc.nextLine().trim();
        if (maSP.isEmpty()) {
            System.out.println("Loi: Ma khong duoc de trong!");
            return;
        }
        
        if (dsKho.timTheoMaSanPham(maSP) != null) {
            System.out.println("Loi: Ma da ton tai!");
            return;
        }
        
        System.out.print("Ten san pham: ");
        String tenSP = sc.nextLine().trim();
        if (tenSP.isEmpty()) {
            System.out.println("Loi: Ten khong duoc de trong!");
            return;
        }
        
        System.out.print("Gia ban: ");
        double giaBan = sc.nextDouble();
        if (giaBan <= 0) {
            System.out.println("Loi: Gia phai lon hon 0!");
            sc.nextLine();
            return;
        }
        sc.nextLine();
        
        System.out.print("Hang san xuat: ");
        String hangSX = sc.nextLine().trim();
        
        System.out.print("So luong: ");
        int soLuongTon = sc.nextInt();
        if (soLuongTon <= 0) {
            System.out.println("Loi: So luong phai lon hon 0!");
            sc.nextLine();
            return;
        }
        sc.nextLine();
        
        System.out.print("Ngay nhap (dd/MM/yyyy): ");
        String ngayNhap = sc.nextLine().trim();
        
        System.out.print("Vi tri ke (VD: A1): ");
        String viTriKe = sc.nextLine().trim().toUpperCase();
        if (viTriKe.isEmpty()) viTriKe = "A1";
        
        System.out.println("\nChon loai:");
        System.out.println("1. Dien thoai");
        System.out.println("2. Phu kien");
        System.out.println("3. Linh kien");
        System.out.print("Chon: ");
        int loaiChon = sc.nextInt();
        sc.nextLine();
        
        SanPham sp;
        if (loaiChon == 1) {
            System.out.print("IMEI: ");
            String imei = sc.nextLine().trim();
            System.out.print("Cau hinh: ");
            String cauHinh = sc.nextLine().trim();
            sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
        } else if (loaiChon == 2) {
            System.out.print("Loai phu kien: ");
            String loaiPk = sc.nextLine().trim();
            System.out.print("Tuong thich: ");
            String tuongThich = sc.nextLine().trim();
            sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPk, tuongThich);
        } else if (loaiChon == 3) {
            System.out.print("Danh cho sua chua? (y/n): ");
            boolean danhChoSua = sc.nextLine().equalsIgnoreCase("y");
            System.out.print("Thuoc bao hanh? (y/n): ");
            boolean thuocBaoHanh = sc.nextLine().equalsIgnoreCase("y");
            sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSua, thuocBaoHanh);
        } else {
            System.out.println("Lua chon khong hop le!");
            return;
        }
        
        String serial = KhoHelper.sinhSerialNgauNhien();
        SanPhamTrongKho spk = new SanPhamTrongKho(sp, serial, new Date(), viTriKe, viTriKe, soLuongTon, "Moi nhap");
        dsKho.themSanPham(spk);
        
        System.out.println("\nThem thanh cong! Serial: " + serial);
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        sc.nextLine();
    }
}
    // Sua san pham
    private void suaSanPham() {
    try {
        System.out.println("\n--- SUA SAN PHAM ---");
        
        System.out.print("Nhap serial: ");
        String serial = sc.nextLine().trim();
        
        SanPhamTrongKho spk = dsKho.timTheoSerial(serial);
        if (spk == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin hien tai:");
        System.out.println(spk.layThongTinChiTiet());
        
        SanPham sp = spk.getSanPham();
        
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        System.out.print("Ten [" + sp.getTenSP() + "]: ");
        String tenMoi = sc.nextLine().trim();
        if (!tenMoi.isEmpty()) sp.setTenSP(tenMoi);
        
        System.out.print("Gia [" + sp.getGiaBan() + "]: ");
        String giaStr = sc.nextLine().trim();
        if (!giaStr.isEmpty()) {
            try {
                double giaMoi = Double.parseDouble(giaStr);
                if (giaMoi > 0) sp.setGiaBan(giaMoi);
            } catch (NumberFormatException e) {}
        }
        
        System.out.print("Hang [" + sp.getHangSX() + "]: ");
        String hangMoi = sc.nextLine().trim();
        if (!hangMoi.isEmpty()) sp.setHangSX(hangMoi);
        
        System.out.print("So luong [" + spk.getSoLuong() + "]: ");
        String slStr = sc.nextLine().trim();
        if (!slStr.isEmpty()) {
            try {
                int slMoi = Integer.parseInt(slStr);
                if (slMoi >= 0) {
                    spk.setSoLuong(slMoi);
                    sp.setSoLuongTon(slMoi);
                }
            } catch (NumberFormatException e) {}
        }
        
        System.out.print("Vi tri [" + spk.getViTriKe() + "]: ");
        String viTriMoi = sc.nextLine().trim().toUpperCase();
        if (!viTriMoi.isEmpty()) {
            spk.setViTriKe(viTriMoi);
            spk.setViTri(viTriMoi);
        }
        
        System.out.print("Trang thai [" + spk.getTrangThai() + "]: ");
        String ttMoi = sc.nextLine().trim();
        if (!ttMoi.isEmpty()) spk.setTrangThai(ttMoi);
        
        if (sp instanceof DienThoai) {
            DienThoai dt = (DienThoai) sp;
            System.out.print("IMEI [" + dt.getImei() + "]: ");
            String imeiMoi = sc.nextLine().trim();
            if (!imeiMoi.isEmpty()) dt.setImei(imeiMoi);
            
            System.out.print("Cau hinh [" + dt.getCauHinh() + "]: ");
            String cauHinhMoi = sc.nextLine().trim();
            if (!cauHinhMoi.isEmpty()) dt.setCauHinh(cauHinhMoi);
        } else if (sp instanceof PhuKien) {
            PhuKien pk = (PhuKien) sp;
            System.out.print("Loai [" + pk.getLoaiPhuKien() + "]: ");
            String loaiMoi = sc.nextLine().trim();
            if (!loaiMoi.isEmpty()) pk.setLoaiPhuKien(loaiMoi);
            
            System.out.print("Tuong thich [" + pk.getTuongThich() + "]: ");
            String ttMoi2 = sc.nextLine().trim();
            if (!ttMoi2.isEmpty()) pk.setTuongThich(ttMoi2);
        } else if (sp instanceof LinhKien) {
            LinhKien lk = (LinhKien) sp;
            System.out.print("Sua chua? (y/n) [" + (lk.isDanhChoSuaChua() ? "y" : "n") + "]: ");
            String scStr = sc.nextLine().trim();
            if (!scStr.isEmpty()) lk.setDanhChoSuaChua(scStr.equalsIgnoreCase("y"));
            
            System.out.print("Bao hanh? (y/n) [" + (lk.isThuocBaoHanh() ? "y" : "n") + "]: ");
            String bhStr = sc.nextLine().trim();
            if (!bhStr.isEmpty()) lk.setThuocBaoHanh(bhStr.equalsIgnoreCase("y"));
        }
        
        System.out.println("\nCap nhat thanh cong!");
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        sc.nextLine();
    }
}
    // Xoa san pham
    private void xoaSanPham() {
    try {
        System.out.println("\n--- XOA SAN PHAM ---");
        
        System.out.print("Nhap serial: ");
        String serial = sc.nextLine().trim();
        
        SanPhamTrongKho spk = dsKho.timTheoSerial(serial);
        if (spk == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin san pham:");
        System.out.println(spk.layThongTinChiTiet());
        
        System.out.print("\nXac nhan xoa? (y/n): ");
        String xacNhan = sc.nextLine().trim();
        
        if (xacNhan.equalsIgnoreCase("y")) {
            if (dsKho.xoaSanPham(serial)) {
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
    // Tìm kiếm sản phẩm - LOGIC CHUẨN NGHIỆP VỤ
    private void timKiemSanPham() {
    try {
        System.out.println("\n--- TIM KIEM SAN PHAM ---");
        System.out.println("1. Tim theo Serial");
        System.out.println("2. Tim theo Ma SP");
        System.out.println("3. Tim theo Ten");
        System.out.println("4. Tim theo Vi tri");
        System.out.println("5. Tim theo Trang thai");
        System.out.println("6. Tim theo Hang");
        System.out.print("Chon: ");
        int chon = sc.nextInt();
        sc.nextLine();

        if (chon == 1) {
            System.out.print("Nhap serial: ");
            String serial = sc.nextLine().trim();
            SanPhamTrongKho sp = dsKho.timTheoSerial(serial);
            if (sp != null) {
                System.out.println("\nTim thay:");
                System.out.println(sp.layThongTinChiTiet());
            } else {
                System.out.println("Khong tim thay!");
            }
        } else if (chon == 2) {
            System.out.print("Nhap ma SP: ");
            String maSP = sc.nextLine().trim();
            SanPhamTrongKho sp = dsKho.timTheoMaSanPham(maSP);
            if (sp != null) {
                System.out.println("\nTim thay:");
                System.out.println(sp.layThongTinChiTiet());
            } else {
                System.out.println("Khong tim thay!");
            }
        } else if (chon == 3) {
            System.out.print("Nhap ten: ");
            String ten = sc.nextLine().trim();
            List<SanPhamTrongKho> ds = dsKho.timTheoTen(ten);
            hienThiKetQua(ds);
        } else if (chon == 4) {
            System.out.print("Nhap vi tri: ");
            String viTri = sc.nextLine().trim().toUpperCase();
            List<SanPhamTrongKho> ds = dsKho.timTheoViTri(viTri);
            hienThiKetQua(ds);
        } else if (chon == 5) {
            System.out.print("Nhap trang thai: ");
            String trangThai = sc.nextLine().trim();
            List<SanPhamTrongKho> ds = dsKho.timTheoTrangThai(trangThai);
            hienThiKetQua(ds);
        } else if (chon == 6) {
            System.out.print("Nhap hang: ");
            String hang = sc.nextLine().trim();
            List<SanPhamTrongKho> ds = dsKho.timTheoHang(hang);
            hienThiKetQua(ds);
        } else {
            System.out.println("Lua chon khong hop le!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        sc.nextLine();
    }
}

private void hienThiKetQua(List<SanPhamTrongKho> ds) {
    if (ds.isEmpty()) {
        System.out.println("Khong tim thay!");
    } else {
        System.out.println("\nTim thay " + ds.size() + " san pham:");
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- San pham " + (i + 1) + " ---");
            System.out.println(ds.get(i).layThongTinChiTiet());
        }
    }
}

    // Menu nhà cung cấp - ĐÃ ĐƠN GIẢN HÓA
    private void menuNhaCungCap() {
        int luaChon;
        do {
            System.out.println("\n========== QUẢN LÝ NHÀ CUNG CẤP ==========");
            System.out.println("1. Thêm nhà cung cấp");
            System.out.println("2. Sửa thông tin nhà cung cấp");
            System.out.println("3. Xóa nhà cung cấp");
            System.out.println("4. Tìm kiếm nhà cung cấp");
            System.out.println("5. Xem danh sách nhà cung cấp");
            System.out.println("6. Tải dữ liệu nhà cung cấp từ file");
            System.out.println("7. Xuất dữ liệu nhà cung cấp ra file");
            System.out.println("8. Quay lại");
            System.out.print("Chọn: ");
            luaChon = sc.nextInt();
            sc.nextLine();

            switch (luaChon) {
                case 1:
                    themNhaCungCap();
                    break;
                case 2:
                    suaNhaCungCap();
                    break;
                case 3:
                    xoaNhaCungCap();
                    break;
                case 4:
                    timKiemNhaCungCap();
                    break;
                case 5:
                    dsNcc.xemTatCa();
                    System.out.println("Tổng số NCC: " + NhaCungCap.layTongSoNhaCungCap());
                    break;
                case 6:
                    taiDuLieuNhaCungCap();
                    break;
                case 7:
                    xuatDuLieuNhaCungCap();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 8);
    }

    // Thêm nhà cung cấp - LOGIC CHUẨN NGHIỆP VỤ
   private void themNhaCungCap() {
    try {
        System.out.println("\n--- THEM NHA CUNG CAP ---");
        
        System.out.print("Ma NCC: ");
        String ma = sc.nextLine().trim();
        if (ma.isEmpty()) {
            System.out.println("Loi: Ma khong duoc de trong!");
            return;
        }
        
        if (dsNcc.timTheoMa(ma) != null) {
            System.out.println("Loi: Ma da ton tai!");
            return;
        }
        
        System.out.print("Ten NCC: ");
        String ten = sc.nextLine().trim();
        if (ten.isEmpty()) {
            System.out.println("Loi: Ten khong duoc de trong!");
            return;
        }
        
        System.out.print("San pham cung cap: ");
        String sp = sc.nextLine().trim();
        
        System.out.print("So dien thoai: ");
        String sdt = sc.nextLine().trim();
        if (sdt.isEmpty()) {
            System.out.println("Loi: SDT khong duoc de trong!");
            return;
        }
        
        System.out.print("Dia chi: ");
        String dc = sc.nextLine().trim();
        
        System.out.print("Do tin cay (0-100, mac dinh 100): ");
        String dtcStr = sc.nextLine().trim();
        double doTinCay = 100;
        if (!dtcStr.isEmpty()) {
            try {
                doTinCay = Double.parseDouble(dtcStr);
                if (doTinCay < 0 || doTinCay > 100) doTinCay = 100;
            } catch (NumberFormatException e) {}
        }

        NhaCungCap ncc = new NhaCungCap(ma, ten, sp, doTinCay, sdt, dc);
        dsNcc.themNhaCungCap(ncc);
        
        System.out.println("\nThem thanh cong!");
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}
    // Sửa nhà cung cấp - LOGIC CHUẨN NGHIỆP VỤ
    private void suaNhaCungCap() {
    try {
        System.out.println("\n--- SUA NHA CUNG CAP ---");
        
        System.out.print("Nhap ma NCC: ");
        String ma = sc.nextLine().trim();
        
        NhaCungCap ncc = dsNcc.timTheoMa(ma);
        if (ncc == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ma: " + ncc.getMaNhaCungCap());
        System.out.println("Ten: " + ncc.getTenNhaCungCap());
        System.out.println("San pham: " + ncc.getSanPhamCungCap());
        System.out.println("Do tin cay: " + ncc.getDoTinCay());
        System.out.println("SDT: " + ncc.getSoDienThoai());
        System.out.println("Dia chi: " + ncc.getDiaChi());
        
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        System.out.print("Ten [" + ncc.getTenNhaCungCap() + "]: ");
        String tenMoi = sc.nextLine().trim();
        if (!tenMoi.isEmpty()) ncc.setTenNhaCungCap(tenMoi);
        
        System.out.print("San pham [" + ncc.getSanPhamCungCap() + "]: ");
        String spMoi = sc.nextLine().trim();
        if (!spMoi.isEmpty()) ncc.setSanPhamCungCap(spMoi);
        
        System.out.print("SDT [" + ncc.getSoDienThoai() + "]: ");
        String sdtMoi = sc.nextLine().trim();
        if (!sdtMoi.isEmpty()) ncc.setSoDienThoai(sdtMoi);
        
        System.out.print("Dia chi [" + ncc.getDiaChi() + "]: ");
        String dcMoi = sc.nextLine().trim();
        if (!dcMoi.isEmpty()) ncc.setDiaChi(dcMoi);
        
        System.out.print("Do tin cay [" + ncc.getDoTinCay() + "]: ");
        String dtcStr = sc.nextLine().trim();
        if (!dtcStr.isEmpty()) {
            try {
                double dtcMoi = Double.parseDouble(dtcStr);
                if (dtcMoi >= 0 && dtcMoi <= 100) ncc.setDoTinCay(dtcMoi);
            } catch (NumberFormatException e) {}
        }
        
        System.out.println("\nCap nhat thanh cong!");
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}

    // Xóa nhà cung cấp - LOGIC CHUẨN NGHIỆP VỤ
    private void xoaNhaCungCap() {
    try {
        System.out.println("\n--- XOA NHA CUNG CAP ---");
        
        System.out.print("Nhap ma NCC: ");
        String ma = sc.nextLine().trim();
        
        NhaCungCap ncc = dsNcc.timTheoMa(ma);
        if (ncc == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin NCC:");
        System.out.println("Ma: " + ncc.getMaNhaCungCap());
        System.out.println("Ten: " + ncc.getTenNhaCungCap());
        System.out.println("SDT: " + ncc.getSoDienThoai());
        
        System.out.print("\nXac nhan xoa? (y/n): ");
        String xacNhan = sc.nextLine().trim();
        
        if (xacNhan.equalsIgnoreCase("y")) {
            if (dsNcc.xoaNhaCungCap(ma)) {
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

    // Tìm kiếm nhà cung cấp - LOGIC CHUẨN NGHIỆP VỤ
    private void timKiemNhaCungCap() {
    try {
        System.out.println("\n--- TIM KIEM NHA CUNG CAP ---");
        System.out.println("1. Tim theo Ma");
        System.out.println("2. Tim theo Ten");
        System.out.println("3. Tim theo San pham");
        System.out.println("4. Tim theo SDT");
        System.out.print("Chon: ");
        int chon = sc.nextInt();
        sc.nextLine();

        if (chon == 1) {
            System.out.print("Nhap ma: ");
            String ma = sc.nextLine().trim();
            NhaCungCap ncc = dsNcc.timTheoMa(ma);
            if (ncc != null) {
                hienThiNCC(ncc);
            } else {
                System.out.println("Khong tim thay!");
            }
        } else if (chon == 2) {
            System.out.print("Nhap ten: ");
            String ten = sc.nextLine().trim();
            List<NhaCungCap> ds = dsNcc.timTheoTen(ten);
            hienThiDanhSachNCC(ds);
        } else if (chon == 3) {
            System.out.print("Nhap san pham: ");
            String sp = sc.nextLine().trim();
            List<NhaCungCap> ds = dsNcc.timTheoSanPham(sp);
            hienThiDanhSachNCC(ds);
        } else if (chon == 4) {
            System.out.print("Nhap SDT: ");
            String sdt = sc.nextLine().trim();
            List<NhaCungCap> ds = dsNcc.timTheoSDT(sdt);
            hienThiDanhSachNCC(ds);
        } else {
            System.out.println("Lua chon khong hop le!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        sc.nextLine();
    }
}

private void hienThiNCC(NhaCungCap ncc) {
    System.out.println("\nMa: " + ncc.getMaNhaCungCap());
    System.out.println("Ten: " + ncc.getTenNhaCungCap());
    System.out.println("San pham: " + ncc.getSanPhamCungCap());
    System.out.println("Do tin cay: " + ncc.getDoTinCay() + "%");
    System.out.println("SDT: " + ncc.getSoDienThoai());
    System.out.println("Dia chi: " + ncc.getDiaChi());
}

private void hienThiDanhSachNCC(List<NhaCungCap> ds) {
    if (ds.isEmpty()) {
        System.out.println("Khong tim thay!");
    } else {
        System.out.println("\nTim thay " + ds.size() + " NCC:");
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- NCC " + (i + 1) + " ---");
            hienThiNCC(ds.get(i));
        }
    }
}

    // Tải dữ liệu từ file (tất cả)
    private void taiDuLieu() {
        qltt.taiDuLieuTuFile();
    }

    // Lưu dữ liệu ra file (tất cả)
    private void luuDuLieu() {
        qltt.luuDuLieuRaFile();
    }

    // Tải dữ liệu kho từ file
    private void taiDuLieuKho() {
        System.out.print("Nhập tên file (mặc định: kho.txt): ");
        String tenFile = sc.nextLine();
        if (tenFile.isEmpty()) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/kho.txt";
        } else if (!tenFile.contains("/") && !tenFile.contains("\\")) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/" + tenFile;
        }
        dsKho.docFile(tenFile);
        System.out.println("Đã tải dữ liệu kho từ file: " + tenFile);
    }

    // Xuất dữ liệu kho ra file
    private void xuatDuLieuKho() {
        System.out.print("Nhập tên file (mặc định: kho.txt): ");
        String tenFile = sc.nextLine();
        if (tenFile.isEmpty()) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/kho.txt";
        } else if (!tenFile.contains("/") && !tenFile.contains("\\")) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/" + tenFile;
        }
        dsKho.ghiFile(tenFile);
        System.out.println("Đã xuất dữ liệu kho ra file: " + tenFile);
    }

    // Tải dữ liệu nhà cung cấp từ file
    private void taiDuLieuNhaCungCap() {
        System.out.print("Nhập tên file (mặc định: nhacungcap.txt): ");
        String tenFile = sc.nextLine();
        if (tenFile.isEmpty()) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/nhacungcap.txt";
        } else if (!tenFile.contains("/") && !tenFile.contains("\\")) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/" + tenFile;
        }
        dsNcc.docFile(tenFile);
        System.out.println("Đã tải dữ liệu nhà cung cấp từ file: " + tenFile);
    }

    // Xuất dữ liệu nhà cung cấp ra file
    private void xuatDuLieuNhaCungCap() {
        System.out.print("Nhập tên file (mặc định: nhacungcap.txt): ");
        String tenFile = sc.nextLine();
        if (tenFile.isEmpty()) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/nhacungcap.txt";
        } else if (!tenFile.contains("/") && !tenFile.contains("\\")) {
            tenFile = "src/QuanLyCuaHangBanDienThoai/" + tenFile;
        }
        dsNcc.ghiFile(tenFile);
        System.out.println("Đã xuất dữ liệu nhà cung cấp ra file: " + tenFile);
    }
}