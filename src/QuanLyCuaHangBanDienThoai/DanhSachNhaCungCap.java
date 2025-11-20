package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.util.*;

// Lớp quản lý danh sách nhà cung cấp
public class DanhSachNhaCungCap {
    private List<NhaCungCap> danhSach;

    // Hàm thiết lập
    public DanhSachNhaCungCap() {
        this.danhSach = new ArrayList<>();
    }

    // Thêm nhà cung cấp
    public void themNhaCungCap(NhaCungCap ncc) {
        danhSach.add(ncc);
    }

    // Xóa nhà cung cấp
    public boolean xoaNhaCungCap(String maNCC) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaNhaCungCap().equals(maNCC)) {
                danhSach.remove(i);
                return true;
            }
        }
        return false;
    }

    // Sửa thông tin nhà cung cấp
    public boolean suaNhaCungCap(String maNCC, String tenMoi, String spMoi, double doTinCayMoi) {
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getMaNhaCungCap().equals(maNCC)) {
                ncc.setTenNhaCungCap(tenMoi);
                ncc.setSanPhamCungCap(spMoi);
                ncc.setDoTinCay(doTinCayMoi);
                return true;
            }
        }
        return false;
    }

    // Tìm kiếm nhà cung cấp theo mã
    public NhaCungCap timTheoMa(String maNCC) {
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getMaNhaCungCap().equals(maNCC)) {
                return ncc;
            }
        }
        return null;
    }

    // Tìm kiếm theo tên
    public List<NhaCungCap> timTheoTen(String ten) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        String tenLower = ten.toLowerCase();
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getTenNhaCungCap().toLowerCase().contains(tenLower)) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    // Tìm kiếm theo sản phẩm cung cấp
    public List<NhaCungCap> timTheoSanPham(String sanPham) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        String spLower = sanPham.toLowerCase();
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getSanPhamCungCap().toLowerCase().contains(spLower)) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    // Tìm kiếm theo số điện thoại
    public List<NhaCungCap> timTheoSDT(String sdt) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getSoDienThoai().contains(sdt)) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    // Xem tất cả nhà cung cấp
    public void xemTatCa() {
        System.out.println("========== DANH SÁCH NHÀ CUNG CẤP ==========");
        if (danhSach.isEmpty()) {
            System.out.println("Chưa có nhà cung cấp nào!");
        } else {
            for (NhaCungCap ncc : danhSach) {
                System.out.println("Mã: " + ncc.getMaNhaCungCap() +
                        " | Tên: " + ncc.getTenNhaCungCap() +
                        " | Sản phẩm: " + ncc.getSanPhamCungCap() +
                        " | Độ tin cậy: " + ncc.getDoTinCay() + "%" +
                        " | SĐT: " + ncc.getSoDienThoai() +
                        " | Địa chỉ: " + ncc.getDiaChi());
            }
            System.out.println("Tổng số nhà cung cấp: " + danhSach.size());
        }
    }

    // ========== CÁC PHƯƠNG THỨC ĐÃ COMMENT (KHÔNG CẦN THIẾT) ==========
    
    /*
    // Đánh giá chất lượng - tính tỷ lệ hàng lỗi - ĐÃ COMMENT
    public void danhGiaChatLuong(String maNCC, int tongHangNhap, int soHangLoi) {
        NhaCungCap ncc = timTheoMa(maNCC);
        if (ncc != null) {
            double tyLeHangLoi = (double) soHangLoi / tongHangNhap * 100;
            double doTinCayMoi = 100 - tyLeHangLoi;
            ncc.setDoTinCay(doTinCayMoi);
            System.out.println("Đã cập nhật độ tin cậy cho " + ncc.getTenNhaCungCap() + ": " + doTinCayMoi + "%");
        }
    }

    // Lọc nhà cung cấp theo độ tin cậy - ĐÃ COMMENT
    public List<NhaCungCap> locTheoDoTinCay(double nguongToiThieu) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSach) {
            if (ncc.getDoTinCay() >= nguongToiThieu) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }
    */

    // Ghi dữ liệu ra file
    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (NhaCungCap ncc : danhSach) {
                bw.write(ncc.toString());
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
                if (data.length >= 6) {
                    NhaCungCap ncc = new NhaCungCap(
                            data[0],
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            data[4],
                            data[5]
                    );
                    danhSach.add(ncc);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    // Getter
    public List<NhaCungCap> getDanhSach() {
        return danhSach;
    }
}