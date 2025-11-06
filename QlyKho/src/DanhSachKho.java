import java.util.*;
import java.io.*;

public class DanhSachKho implements IQuanLyKho {
    private List<ThucTheKho> danhSachKho;
    private String tenFile = "kho.txt";
    private String logFile = "lichsu_nhapxuat.txt";

    public DanhSachKho() {
        this.danhSachKho = new ArrayList<>();
    }

    // ===== CRUD Operations =====

    public void themHangVaoKho(ThucTheKho hangHoa) {
        danhSachKho.add(hangHoa);
        ghiLog("THÊM", hangHoa.getMaThucThe(), hangHoa.getSoLuong());
        System.out.println("✓ Đã thêm vào kho: " + hangHoa.getMaThucThe());
    }

    public ThucTheKho timTheoMa(String maKho) {
        for (ThucTheKho item : danhSachKho) {
            if (item.getMaThucThe().equalsIgnoreCase(maKho)) {
                return item;
            }
        }
        return null;
    }

    public List<ThucTheKho> timTheoSerial(String serial) {
        List<ThucTheKho> ketQua = new ArrayList<>();
        for (ThucTheKho item : danhSachKho) {
            if (item instanceof SanPhamTrongKho) {
                SanPhamTrongKho sp = (SanPhamTrongKho) item;
                if (sp.getSerial().contains(serial)) {
                    ketQua.add(sp);
                }
            } else if (item instanceof HangLoiCanXuLy) {
                HangLoiCanXuLy hl = (HangLoiCanXuLy) item;
                if (hl.getSerial().contains(serial)) {
                    ketQua.add(hl);
                }
            }
        }
        return ketQua;
    }

    public List<ThucTheKho> timTheoViTri(String viTri) {
        List<ThucTheKho> ketQua = new ArrayList<>();
        for (ThucTheKho item : danhSachKho) {
            if (item.getViTriKe().contains(viTri)) {
                ketQua.add(item);
            }
        }
        return ketQua;
    }

    public List<ThucTheKho> timTheoTrangThai(String trangThai) {
        List<ThucTheKho> ketQua = new ArrayList<>();
        for (ThucTheKho item : danhSachKho) {
            if (item.getTrangThai().equalsIgnoreCase(trangThai)) {
                ketQua.add(item);
            }
        }
        return ketQua;
    }

    public boolean capNhatSoLuong(String maKho, int soLuongMoi) {
        ThucTheKho item = timTheoMa(maKho);
        if (item != null) {
            int cu = item.getSoLuong();
            item.setSoLuong(soLuongMoi);
            ghiLog("CẬP NHẬT", maKho, soLuongMoi - cu);
            System.out.println("✓ Đã cập nhật số lượng: " + maKho);
            return true;
        }
        return false;
    }

    public boolean xoaKhoiKho(String maKho) {
        ThucTheKho item = timTheoMa(maKho);
        if (item != null) {
            danhSachKho.remove(item);
            ghiLog("XÓA", maKho, item.getSoLuong());
            System.out.println("✓ Đã xóa khỏi kho: " + maKho);
            return true;
        }
        return false;
    }

    // ===== Implement IQuanLyKho =====

    @Override
    public void nhapKho(ThucTheKho hangHoa, NhaCungCap ncc) {
        ThucTheKho existing = timTheoMa(hangHoa.getMaThucThe());
        if (existing != null) {
            // Cập nhật số lượng nếu đã tồn tại
            existing.setSoLuong(existing.getSoLuong() + hangHoa.getSoLuong());
            System.out.println("✓ Đã nhập thêm: " + hangHoa.getMaThucThe() +
                    " (SL: +" + hangHoa.getSoLuong() + ")");
        } else {
            danhSachKho.add(hangHoa);
            System.out.println("✓ Đã nhập kho mới: " + hangHoa.getMaThucThe());
        }
        ghiLog("NHẬP KHO", hangHoa.getMaThucThe(), hangHoa.getSoLuong());

        // Cập nhật đánh giá NCC (giả sử không có lỗi khi nhập)
        if (ncc != null) {
            ncc.capNhatDanhGia(false);
        }
    }

    @Override
    public void xuatKho(String maKho, int soLuong) {
        ThucTheKho item = timTheoMa(maKho);
        if (item == null) {
            System.out.println("✗ Không tìm thấy mã kho: " + maKho);
            return;
        }

        if (item.getSoLuong() < soLuong) {
            System.out.println("✗ Không đủ hàng! (Có: " + item.getSoLuong() +
                    ", Cần: " + soLuong + ")");
            return;
        }

        item.setSoLuong(item.getSoLuong() - soLuong);
        ghiLog("XUẤT KHO", maKho, -soLuong);
        System.out.println("✓ Đã xuất kho: " + maKho + " (SL: -" + soLuong + ")");

        // Tự động xóa nếu hết hàng
        if (item.getSoLuong() == 0) {
            danhSachKho.remove(item);
            System.out.println("ℹ️ Đã tự động xóa khỏi kho (hết hàng)");
        }
    }

    @Override
    public List<ThucTheKho> canhBaoHetHang(int nguongToiThieu) {
        List<ThucTheKho> ketQua = new ArrayList<>();
        for (ThucTheKho item : danhSachKho) {
            if (item.getSoLuong() <= nguongToiThieu &&
                    !item.getTrangThai().equals("Lỗi")) {
                ketQua.add(item);
            }
        }
        return ketQua;
    }

    @Override
    public List<ThucTheKho> canhBaoTonKhoLau(int soNgay) {
        List<ThucTheKho> ketQua = new ArrayList<>();
        for (ThucTheKho item : danhSachKho) {
            if (KhoHelper.tinhTuoiTonKho(item.getNgayNhapKho()) >= soNgay) {
                ketQua.add(item);
            }
        }
        return ketQua;
    }

    @Override
    public double tinhGiaTriTonKho() {
        double tongGiaTri = 0;
        for (ThucTheKho item : danhSachKho) {
            tongGiaTri += item.tinhGiaTriTonKho();
        }
        return tongGiaTri;
    }

    @Override
    public void kiemKho() {
        System.out.println("\n    KIỂM KHO ");
        System.out.println("Tổng số mặt hàng: " + danhSachKho.size());
        System.out.println("Giá trị tồn kho: " + KhoHelper.formatTien(tinhGiaTriTonKho()));

        // Thống kê theo loại
        int sanPham = 0, linhKien = 0, hangLoi = 0;
        for (ThucTheKho item : danhSachKho) {
            if (item instanceof SanPhamTrongKho) sanPham++;
            else if (item instanceof LinhKienTonKho) linhKien++;
            else if (item instanceof HangLoiCanXuLy) hangLoi++;
        }

        System.out.println(" Sản phẩm: " + sanPham);
        System.out.println(" Linh kiện: " + linhKien);
        System.out.println(" Hàng lỗi: " + hangLoi);

        // Cảnh báo
        List<ThucTheKho> sapHet = canhBaoHetHang(5);
        List<ThucTheKho> tonLau = canhBaoTonKhoLau(90);

        if (!sapHet.isEmpty()) {
            System.out.println("\n CẢNH BÁO: " + sapHet.size() + " mặt hàng sắp hết!");
        }
        if (!tonLau.isEmpty()) {
            System.out.println(" CẢNH BÁO: " + tonLau.size() + " mặt hàng tồn lâu (>90 ngày)!");
        }
        System.out.println("                                \n");
    }

    // ===== File I/O =====

    public void ghiFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tenFile))) {
            for (ThucTheKho item : danhSachKho) {
                if (item instanceof SanPhamTrongKho) {
                    writer.println("SP|" + ((SanPhamTrongKho) item).toFileFormat());
                } else if (item instanceof LinhKienTonKho) {
                    writer.println("LK|" + ((LinhKienTonKho) item).toFileFormat());
                } else if (item instanceof HangLoiCanXuLy) {
                    writer.println("HL|" + ((HangLoiCanXuLy) item).toFileFormat());
                }
            }
            System.out.println(" Đã ghi file: " + tenFile);
        } catch (IOException e) {
            System.out.println(" Lỗi ghi file: " + e.getMessage());
        }
    }

    public void docFile() {
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println(" File chưa tồn tại: " + tenFile);
            return;
        }

        danhSachKho.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length < 2) continue;

                String loai = parts[0];
                String[] data = parts[1].split("\\|");

                try {
                    if (loai.equals("SP")) {
                        SanPhamTrongKho sp = new SanPhamTrongKho();
                        sp.setMaThucThe(data[0]);
                        sp.setTenSanPham(data[1]);
                        sp.setSerial(data[2]);
                        sp.setLoaiSanPham(data[3]);
                        sp.setViTriKe(data[4]);
                        sp.setSoLuong(Integer.parseInt(data[5]));
                        sp.setTrangThai(data[6]);
                        sp.setGiaNhap(Double.parseDouble(data[7]));
                        sp.setGiaBan(Double.parseDouble(data[8]));
                        sp.setNhaCungCap(data[9]);
                        sp.setNgayNhapKho(new Date(Long.parseLong(data[10])));
                        danhSachKho.add(sp);
                    } else if (loai.equals("LK")) {
                        LinhKienTonKho lk = new LinhKienTonKho();
                        lk.setMaThucThe(data[0]);
                        lk.setTenLinhKien(data[1]);
                        lk.setLoaiLinhKien(data[2]);
                        lk.setViTriKe(data[3]);
                        lk.setSoLuong(Integer.parseInt(data[4]));
                        lk.setTrangThai(data[5]);
                        lk.setNgayNhapKho(new Date(Long.parseLong(data[6])));
                        lk.setHanSuDung(new Date(Long.parseLong(data[7])));
                        lk.setGiaNhap(Double.parseDouble(data[8]));
                        danhSachKho.add(lk);
                    } else if (loai.equals("HL")) {
                        HangLoiCanXuLy hl = new HangLoiCanXuLy();
                        hl.setMaThucThe(data[0]);
                        hl.setTenSanPham(data[1]);
                        hl.setSerial(data[2]);
                        hl.setViTriKe(data[3]);
                        hl.setSoLuong(Integer.parseInt(data[4]));
                        hl.setTrangThai(data[5]);
                        hl.setLoiPhatHien(data[6]);
                        hl.setPhuongAnXuLy(data[8]);
                        hl.setNgayNhapKho(new Date(Long.parseLong(data[11])));
                        danhSachKho.add(hl);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Bỏ qua dòng lỗi: " + line);
                }
            }
            System.out.println("✓ Đã đọc file: " + tenFile + " (" + danhSachKho.size() + " mục)");
        } catch (IOException e) {
            System.out.println("✗ Lỗi đọc file: " + e.getMessage());
        }
    }

    private void ghiLog(String hanhDong, String maKho, int soLuong) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            String log = String.format("%s|%s|%s|%d|%s",
                    KhoHelper.formatDateTime(new Date()),
                    hanhDong, maKho, soLuong,
                    System.getProperty("user.name"));
            writer.println(log);
        } catch (IOException e) {
            // Silent fail for logging
        }
    }

    // ===== Display =====

    public void hienThiTatCa() {
        if (danhSachKho.isEmpty()) {
            System.out.println("Kho trống!");
            return;
        }

        System.out.println("\n       DANH SÁCH KHO ");
        for (int i = 0; i < danhSachKho.size(); i++) {
            System.out.println("\n[" + (i + 1) + "] " + danhSachKho.get(i).layThongTinChiTiet());
        }
        System.out.println("\n                                ");
    }

    public List<ThucTheKho> getDanhSachKho() {
        return danhSachKho;
    }

    public int getSoLuongMatHang() {
        return danhSachKho.size();
    }
}