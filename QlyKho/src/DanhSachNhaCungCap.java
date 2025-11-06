import java.util.*;
import java.io.*;

public class DanhSachNhaCungCap {
    private List<NhaCungCap> danhSachNCC;
    private String tenFile = "nhacungcap.txt";

    public DanhSachNhaCungCap() {
        this.danhSachNCC = new ArrayList<>();
    }

    //  CRUD Operations

    public void themNCC(NhaCungCap ncc) {
        if (timTheoMa(ncc.getMaNCC()) != null) {
            System.out.println("✗ Mã NCC đã tồn tại: " + ncc.getMaNCC());
            return;
        }
        danhSachNCC.add(ncc);
        System.out.println("✓ Đã thêm NCC: " + ncc.getTenNCC());
    }

    public NhaCungCap timTheoMa(String maNCC) {
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getMaNCC().equalsIgnoreCase(maNCC)) {
                return ncc;
            }
        }
        return null;
    }

    public List<NhaCungCap> timTheoTen(String ten) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getTenNCC().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    public List<NhaCungCap> timTheoSanPham(String sanPham) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getSanPhamCungCap().toLowerCase().contains(sanPham.toLowerCase())) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    public boolean capNhatNCC(String maNCC, NhaCungCap nccMoi) {
        NhaCungCap ncc = timTheoMa(maNCC);
        if (ncc != null) {
            ncc.setTenNCC(nccMoi.getTenNCC());
            ncc.setDiaChi(nccMoi.getDiaChi());
            ncc.setSoDienThoai(nccMoi.getSoDienThoai());
            ncc.setEmail(nccMoi.getEmail());
            ncc.setSanPhamCungCap(nccMoi.getSanPhamCungCap());
            System.out.println("✓ Đã cập nhật NCC: " + maNCC);
            return true;
        }
        return false;
    }

    public boolean xoaNCC(String maNCC) {
        NhaCungCap ncc = timTheoMa(maNCC);
        if (ncc != null) {
            danhSachNCC.remove(ncc);
            System.out.println("✓ Đã xóa NCC: " + maNCC);
            return true;
        }
        return false;
    }

    //  Thống kê & Đánh giá

    public List<NhaCungCap> xepHangTheoDoTinCay() {
        List<NhaCungCap> sorted = new ArrayList<>(danhSachNCC);
        sorted.sort((a, b) -> Double.compare(b.getDoTinCay(), a.getDoTinCay()));
        return sorted;
    }

    public List<NhaCungCap> danhSachNCCKem() {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getDoTinCay() < 5.0 || ncc.getTyLeHangLoi() > 10) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    public void hienThiThongKeNCC() {
        System.out.println("\n THỐNG KÊ NHÀ CUNG CẤP ");
        System.out.println("Tổng số NCC: " + danhSachNCC.size());

        double avgDoTinCay = danhSachNCC.stream()
                .mapToDouble(NhaCungCap::getDoTinCay)
                .average()
                .orElse(0);
        System.out.println("Độ tin cậy TB: " + String.format("%.1f/10", avgDoTinCay));

        List<NhaCungCap> top = xepHangTheoDoTinCay();
        if (!top.isEmpty()) {
            System.out.println("\n TOP NCC UY TÍN:");
            for (int i = 0; i < Math.min(3, top.size()); i++) {
                NhaCungCap ncc = top.get(i);
                System.out.println((i + 1) + ". " + ncc.getTenNCC() +
                        " - " + ncc.getDoTinCay() + " Tốt");
            }
        }

        List<NhaCungCap> kem = danhSachNCCKem();
        if (!kem.isEmpty()) {
            System.out.println("\n NCC CẦN XEM XÉT:");
            for (NhaCungCap ncc : kem) {
                System.out.println("- " + ncc.getTenNCC() +
                        " (Lỗi: " + String.format("%.1f%%", ncc.getTyLeHangLoi()) + ")");
            }
        }
        System.out.println("                             \n");
    }

    // ===== File I/O =====

    public void ghiFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tenFile))) {
            for (NhaCungCap ncc : danhSachNCC) {
                writer.println(ncc.toFileFormat());
            }
            System.out.println("✓ Đã ghi file: " + tenFile);
        } catch (IOException e) {
            System.out.println("✗ Lỗi ghi file: " + e.getMessage());
        }
    }

    public void docFile() {
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println(" File chưa tồn tại: " + tenFile);
            return;
        }

        danhSachNCC.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 10) {
                    NhaCungCap ncc = new NhaCungCap(
                            data[0], data[1], data[2], data[3], data[4], data[5]
                    );
                    // Phục hồi dữ liệu thống kê
                    ncc.capNhatDanhGia(false); // Reset
                    danhSachNCC.add(ncc);
                }
            }
            System.out.println("Đã đọc file: " + tenFile + " (" + danhSachNCC.size() + " NCC)");
        } catch (IOException e) {
            System.out.println("✗ Lỗi đọc file: " + e.getMessage());
        }
    }

    //  Display

    public void hienThiTatCa() {
        if (danhSachNCC.isEmpty()) {
            System.out.println("ℹ️ Chưa có nhà cung cấp!");
            return;
        }

        System.out.println("\n        DANH SÁCH NHÀ CUNG CẤP ");
        for (int i = 0; i < danhSachNCC.size(); i++) {
            System.out.println("\n[" + (i + 1) + "] " + danhSachNCC.get(i).layThongTinChiTiet());
        }
        System.out.println("                                                         ");
    }

    public List<NhaCungCap> getDanhSachNCC() {
        return danhSachNCC;
    }

    public int getSoLuongNCC() {
        return danhSachNCC.size();
    }
}