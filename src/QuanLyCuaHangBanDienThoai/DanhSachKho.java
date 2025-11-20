package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.util.*;

// Lớp quản lý danh sách kho - cài đặt interface IQuanLyKho
public class DanhSachKho implements IQuanLyKho {
    private List<SanPhamTrongKho> danhSachSanPham;
    private List<LinhKienTonKho> danhSachLinhKien;
    private List<HangLoiCanXuLy> danhSachHangLoi;

    // Hàm thiết lập
    public DanhSachKho() {
        this.danhSachSanPham = new ArrayList<>();
        this.danhSachLinhKien = new ArrayList<>();
        this.danhSachHangLoi = new ArrayList<>();
    }

    // Cài đặt phương thức từ interface
    @Override
    public void nhapKho(SanPham sp, int soLuong, NhaCungCap ncc) {
        String serial = KhoHelper.sinhSerialNgauNhien();
        String viTriKe = KhoHelper.ganViTriKe(sp.getLoaiSP());
        SanPhamTrongKho spk = new SanPhamTrongKho(sp, serial, new Date(), viTriKe, viTriKe, soLuong, "Mới nhập");
        danhSachSanPham.add(spk);
        ghiLogNhapXuat("NHẬP KHO: " + sp.getTenSP() + " - SL: " + soLuong + " - NCC: " + ncc.getTenNhaCungCap());
    }

    @Override
    public void xuatKho(String maSP, int soLuong) {
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getSanPham().getMaSP().equals(maSP)) {
                if (sp.getSoLuong() >= soLuong) {
                    sp.setSoLuong(sp.getSoLuong() - soLuong);
                    ghiLogNhapXuat("XUẤT KHO: " + sp.getSanPham().getTenSP() + " - SL: " + soLuong);
                }
                break;
            }
        }
    }

    @Override
    public List<SanPham> canhBaoHetHang(int nguongToiThieu) {
        List<SanPham> dsSapHet = new ArrayList<>();
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getSoLuong() < nguongToiThieu) {
                dsSapHet.add(sp.getSanPham());
            }
        }
        return dsSapHet;
    }

    @Override
    public double tinhGiaTriTonKho() {
        double tongGiaTri = 0;
        for (SanPhamTrongKho sp : danhSachSanPham) {
            tongGiaTri += sp.tinhGiaTri();
        }
        for (LinhKienTonKho lk : danhSachLinhKien) {
            tongGiaTri += lk.tinhGiaTri();
        }
        return tongGiaTri;
    }

    // Thêm sản phẩm vào kho
    public void themSanPham(SanPhamTrongKho sp) {
        danhSachSanPham.add(sp);
    }

    // Xóa sản phẩm khỏi kho
    public boolean xoaSanPham(String serial) {
        for (int i = 0; i < danhSachSanPham.size(); i++) {
            if (danhSachSanPham.get(i).getSerialIMEI().equals(serial)) {
                danhSachSanPham.remove(i);
                return true;
            }
        }
        return false;
    }

    // Sửa thông tin sản phẩm
    public boolean suaSanPham(String serial, int soLuongMoi, String trangThaiMoi) {
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getSerialIMEI().equals(serial)) {
                sp.setSoLuong(soLuongMoi);
                sp.setTrangThai(trangThaiMoi);
                return true;
            }
        }
        return false;
    }

    // Tìm kiếm theo serial/IMEI
    public SanPhamTrongKho timTheoSerial(String serial) {
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getSerialIMEI().equals(serial)) {
                return sp;
            }
        }
        return null;
    }

    // Tìm kiếm theo vị trí
    public List<SanPhamTrongKho> timTheoViTri(String viTri) {
        List<SanPhamTrongKho> ketQua = new ArrayList<>();
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getViTriKe().equals(viTri)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    // Tìm kiếm theo trạng thái
    public List<SanPhamTrongKho> timTheoTrangThai(String trangThai) {
        List<SanPhamTrongKho> ketQua = new ArrayList<>();
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (sp.getTrangThai().equals(trangThai)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    // Thống kê hàng tồn kho lâu
    public List<SanPhamTrongKho> thongKeHangTonLau() {
        List<SanPhamTrongKho> ketQua = new ArrayList<>();
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (KhoHelper.laTonKhoLau(sp.getNgayNhap())) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    // Thống kê hàng sắp hết
    public List<SanPhamTrongKho> thongKeHangSapHet() {
        List<SanPhamTrongKho> ketQua = new ArrayList<>();
        for (SanPhamTrongKho sp : danhSachSanPham) {
            if (KhoHelper.laSapHetHang(sp.getSoLuong())) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    // Kiểm kho - so sánh thực tế với hệ thống
    public void kiemKho(String serial, int soLuongThucTe) {
        SanPhamTrongKho sp = timTheoSerial(serial);
        if (sp != null) {
            if (sp.getSoLuong() != soLuongThucTe) {
                System.out.println("Chênh lệch: " + sp.getSanPham().getTenSP());
                System.out.println("Hệ thống: " + sp.getSoLuong() + " - Thực tế: " + soLuongThucTe);
                sp.setSoLuong(soLuongThucTe);
            }
        }
    }

    // Thêm linh kiện
    public void themLinhKien(LinhKienTonKho lk) {
        danhSachLinhKien.add(lk);
    }

    // Thêm hàng lỗi
    public void themHangLoi(HangLoiCanXuLy hl) {
        danhSachHangLoi.add(hl);
    }

    // Xem tất cả sản phẩm
    public void xemTatCaSanPham() {
        System.out.println("=== DANH SÁCH SẢN PHẨM TRONG KHO ===");
        for (SanPhamTrongKho sp : danhSachSanPham) {
            System.out.println(sp.layThongTinChiTiet());
        }
    }

    // Xem tất cả linh kiện
    public void xemTatCaLinhKien() {
        System.out.println("=== DANH SÁCH LINH KIỆN TỒN KHO ===");
        for (LinhKienTonKho lk : danhSachLinhKien) {
            System.out.println(lk.layThongTinChiTiet());
        }
    }

    // Xem tất cả hàng lỗi
    public void xemTatCaHangLoi() {
        System.out.println("=== DANH SÁCH HÀNG LỖI CẦN XỬ LÝ ===");
        for (HangLoiCanXuLy hl : danhSachHangLoi) {
            System.out.println(hl.layThongTinChiTiet());
        }
    }

    // Ghi log nhập xuất
    private void ghiLogNhapXuat(String noiDung) {
        try (FileWriter fw = new FileWriter("src/QuanLyCuaHangBanDienThoai/lichsu_nhapxuat.txt", true)) {
            fw.write(new Date() + ": " + noiDung + "\n");
        } catch (IOException e) {
            System.out.println("Lỗi ghi log: " + e.getMessage());
        }
    }

    // Ghi dữ liệu ra file
    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (SanPhamTrongKho sp : danhSachSanPham) {
                bw.write(sp.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    // Đọc dữ liệu từ file
    public void docFile(String tenFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            String dong;
            while ((dong = br.readLine()) != null) {
                String[] data = dong.split("\\|");
                if (data.length >= 7) {
                    // Đọc và tái tạo đối tượng từ file
                    System.out.println("Đã đọc: " + dong);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    // Getter
    public List<SanPhamTrongKho> getDanhSachSanPham() {
        return danhSachSanPham;
    }

    public List<LinhKienTonKho> getDanhSachLinhKien() {
        return danhSachLinhKien;
    }

    public List<HangLoiCanXuLy> getDanhSachHangLoi() {
        return danhSachHangLoi;
    }
}