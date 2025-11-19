package QuanLyCuaHangBanDienThoai;// Thành viên 3 - Quản lý Giao Dịch
// File: DanhSachGiaoDich.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Lớp quản lý danh sách các giao dịch, bao gồm cả việc đọc/ghi file.
 */
public class DanhSachGiaoDich {
    private List<GiaoDich> danhSach;

    public DanhSachGiaoDich() {
        this.danhSach = new ArrayList<>();
    }

    /**
     * Thêm một giao dịch mới vào danh sách.
     * @param gd Giao dịch cần thêm.
     */
    public void themGiaoDich(GiaoDich gd) {
        danhSach.add(gd);
    }

    /**
     * In thông tin của tất cả giao dịch trong danh sách ra console.
     */
    public void xuatTatCa() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách giao dịch trống.");
            return;
        }
        for (GiaoDich gd : danhSach) {
            gd.xuatThongTin();
            System.out.println(); // Thêm dòng trống cho dễ nhìn
        }
    }

    /**
     * Ghi danh sách giao dịch hiện tại vào file text.
     * @param tenFile Tên file để lưu dữ liệu.
     */
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
                }
                // Có thể thêm logic cho PhieuNhapHang, PhieuBaoHanh nếu cần
            }
            System.out.println("Ghi file '" + tenFile + "' thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    /**
     * Đọc dữ liệu giao dịch từ file text vào danh sách.
     * @param tenFile Tên file để đọc dữ liệu.
     */
    public void docFile(String tenFile) {
        this.danhSach.clear(); // Xóa dữ liệu cũ trước khi đọc
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String line;
            HoaDonBan hienTai = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String loai = parts[0];

                if (loai.equals("HDB")) {
                    String maGD = parts[1];
                    Date ngayGD = sdf.parse(parts[2]);
                    String nguoiThucHien = parts[3];
                    String maKH = parts[4];
                    hienTai = new HoaDonBan(maGD, ngayGD, nguoiThucHien, maKH);
                    this.danhSach.add(hienTai);
                } else if (loai.equals("CT") && hienTai != null) {
                    String maSP = parts[1];
                    int soLuong = Integer.parseInt(parts[2]);
                    double donGia = Double.parseDouble(parts[3]);
                    ChiTietGiaoDich ct = new ChiTietGiaoDich(maSP, soLuong, donGia);
                    hienTai.themChiTiet(ct);
                }
            }
             System.out.println("Đọc file '" + tenFile + "' thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}