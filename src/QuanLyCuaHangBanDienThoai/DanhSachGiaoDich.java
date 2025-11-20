package QuanLyCuaHangBanDienThoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DanhSachGiaoDich {
    private List<GiaoDich> danhSach;
    public DanhSachGiaoDich() {
        this.danhSach = new ArrayList<>();
    }
    public void themGiaoDich(GiaoDich gd) {
        danhSach.add(gd);
    }
    
    // Xóa giao dịch theo mã
    public boolean xoaGiaoDich(String maGD) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaGD().equals(maGD)) {
                danhSach.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // Sửa giao dịch (thay thế giao dịch cũ bằng giao dịch mới)
    public boolean suaGiaoDich(String maGD, GiaoDich gdMoi) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaGD().equals(maGD)) {
                danhSach.set(i, gdMoi);
                return true;
            }
        }
        return false;
    }
    
    // Tìm kiếm giao dịch theo mã
    public GiaoDich timTheoMa(String maGD) {
        for (GiaoDich gd : danhSach) {
            if (gd.getMaGD().equals(maGD)) {
                return gd;
            }
        }
        return null;
    }
    
    // Tìm kiếm giao dịch theo ngày
    public List<GiaoDich> timTheoNgay(Date ngay) {
        List<GiaoDich> ketQua = new ArrayList<>();
        for (GiaoDich gd : danhSach) {
            if (gd.getNgayGD().equals(ngay)) {
                ketQua.add(gd);
            }
        }
        return ketQua;
    }
    
    // Lấy danh sách tất cả giao dịch
    public List<GiaoDich> getDanhSach() {
        return new ArrayList<>(danhSach);
    }
    
    public void xuatTatCa() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách giao dịch trống.");
            return;
        }
        for (GiaoDich gd : danhSach) {
            gd.xuatThongTin();
            System.out.println();
        }
    }
    public void ghiFile(String tenFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (GiaoDich gd : danhSach) {
                if (gd instanceof HoaDonBan) {
                    HoaDonBan hdb = (HoaDonBan) gd;
                    writer.write("HDB," + hdb.getMaGD() + "," + sdf.format(hdb.getNgayGD()) + "," + hdb.getNguoiThucHien() + "," + hdb.getMaKhachHang());
                    writer.newLine();
                    for (ChiTietGiaoDich ct : hdb.getDanhSachChiTiet()) {
                        writer.write("CT," + ct.getMaSP() + "," + ct.getSoLuong() + "," + ct.getDonGia());
                        writer.newLine();
                    }
                } else if (gd instanceof PhieuNhapHang) {
                    PhieuNhapHang pnh = (PhieuNhapHang) gd;
                    writer.write("PNH," + pnh.getMaGD() + "," + sdf.format(pnh.getNgayGD()) + "," + pnh.getNguoiThucHien() + "," + pnh.getNhaCungCap() + "," + pnh.getSoLuong() + "," + pnh.getGiaNhap());
                    writer.newLine();
                } else if (gd instanceof PhieuBaoHanh) {
                    PhieuBaoHanh pbh = (PhieuBaoHanh) gd;
                    // loiHong may contain commas; replace with semi-colon when writing
                    String loi = pbh.getLoiHong() == null ? "" : pbh.getLoiHong().replace(',', ';');
                    writer.write("PBH," + pbh.getMaGD() + "," + sdf.format(pbh.getNgayGD()) + "," + pbh.getNguoiThucHien() + "," + pbh.getMaSanPham() + "," + loi + "," + pbh.getPhiSuaChua());
                    writer.newLine();
                }
            }
            System.out.println("Ghi file '" + tenFile + "' thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    public void docFile(String tenFile) {
        this.danhSach.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String line;
            HoaDonBan hienTai = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // skip empty lines and comments
                String[] parts = line.split(",", -1);
                String loai = parts[0];
                if (loai.equals("HDB")) {
                    if (parts.length < 5) continue; // malformed
                    String maGD = parts[1];
                    Date ngayGD = sdf.parse(parts[2]);
                    String nguoiThucHien = parts[3];
                    String maKH = parts[4];
                    hienTai = new HoaDonBan(maGD, ngayGD, nguoiThucHien, maKH);
                    this.danhSach.add(hienTai);
                } else if (loai.equals("CT") && hienTai != null) {
                    if (parts.length < 4) continue; // malformed
                    String maSP = parts[1];
                    int soLuong = Integer.parseInt(parts[2]);
                    double donGia = Double.parseDouble(parts[3]);
                    ChiTietGiaoDich ct = new ChiTietGiaoDich(maSP, soLuong, donGia);
                    hienTai.themChiTiet(ct);
                } else if (loai.equals("PNH")) {
                    if (parts.length < 7) continue;
                    String maGD = parts[1];
                    Date ngayGD = sdf.parse(parts[2]);
                    String nguoiThucHien = parts[3];
                    String ncc = parts[4];
                    int soLuong = Integer.parseInt(parts[5]);
                    double giaNhap = Double.parseDouble(parts[6]);
                    PhieuNhapHang pnh = new PhieuNhapHang(maGD, ngayGD, nguoiThucHien, ncc, soLuong, giaNhap);
                    this.danhSach.add(pnh);
                } else if (loai.equals("PBH")) {
                    if (parts.length < 7) continue;
                    String maGD = parts[1];
                    Date ngayGD = sdf.parse(parts[2]);
                    String nguoiThucHien = parts[3];
                    String maSP = parts[4];
                    String loi = parts[5];
                    double phi = Double.parseDouble(parts[6]);
                    PhieuBaoHanh pbh = new PhieuBaoHanh(maGD, ngayGD, nguoiThucHien, maSP, loi, phi);
                    this.danhSach.add(pbh);
                }
            }
             System.out.println("Đọc file '" + tenFile + "' thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}