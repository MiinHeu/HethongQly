import java.util.*;

public class DuBaoNhapHang {
    private DanhSachKho danhSachKho;
    private Map<String, List<Integer>> lichSuBanHang; // maSP -> [số lượng bán theo ngày]

    public DuBaoNhapHang(DanhSachKho danhSachKho) {
        this.danhSachKho = danhSachKho;
        this.lichSuBanHang = new HashMap<>();
    }

    //  Phân tích tốc độ bán

    public void capNhatLichSuBan(String maSP, int soLuongBan) {
        lichSuBanHang.putIfAbsent(maSP, new ArrayList<>());
        lichSuBanHang.get(maSP).add(soLuongBan);
    }

    public double tinhTocDoBanTrungBinh(String maSP) {
        List<Integer> lichSu = lichSuBanHang.get(maSP);
        if (lichSu == null || lichSu.isEmpty()) {
            return 0;
        }

        int tong = lichSu.stream().mapToInt(Integer::intValue).sum();
        return tong * 1.0 / lichSu.size();
    }

    public int duDoanSoNgayHetHang(String maSP) {
        ThucTheKho item = danhSachKho.timTheoMa(maSP);
        if (item == null) return -1;

        double tocDoBan = tinhTocDoBanTrungBinh(maSP);
        if (tocDoBan <= 0) return -1;

        return (int) Math.ceil(item.getSoLuong() / tocDoBan);
    }

    // ===== Gợi ý đặt hàng tự động =====

    public List<GoiYNhapHang> taoGoiYNhapHang() {
        List<GoiYNhapHang> danhSachGoiY = new ArrayList<>();

        for (ThucTheKho item : danhSachKho.getDanhSachKho()) {
            if (item instanceof SanPhamTrongKho) {
                SanPhamTrongKho sp = (SanPhamTrongKho) item;

                // Kiểm tra nếu sắp hết hàng
                int soNgayHetHang = duDoanSoNgayHetHang(sp.getMaThucThe());

                if (soNgayHetHang > 0 && soNgayHetHang <= 10) {
                    double tocDoBan = tinhTocDoBanTrungBinh(sp.getMaThucThe());
                    int soLuongNen = (int) Math.ceil(tocDoBan * 30); // Đủ dùng 30 ngày

                    GoiYNhapHang goiY = new GoiYNhapHang(
                            sp.getMaThucThe(),
                            sp.getTenSanPham(),
                            sp.getSoLuong(),
                            soLuongNen,
                            soNgayHetHang,
                            sp.getNhaCungCap(),
                            "Sắp hết hàng - Ưu tiên CAO"
                    );
                    danhSachGoiY.add(goiY);
                }
            }
        }

        return danhSachGoiY;
    }

    // ===== Phát hiện bán chạy đột biến =====

    public List<SanPhamBanChay> phatHienBanChayDotBien() {
        List<SanPhamBanChay> ketQua = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : lichSuBanHang.entrySet()) {
            String maSP = entry.getKey();
            List<Integer> lichSu = entry.getValue();

            if (lichSu.size() < 5) continue; // Cần ít nhất 5 ngày dữ liệu

            // So sánh 3 ngày gần nhất với TB của 30 ngày trước
            int recentDays = 3;
            double tbGanDay = 0;
            for (int i = lichSu.size() - recentDays; i < lichSu.size(); i++) {
                tbGanDay += lichSu.get(i);
            }
            tbGanDay /= recentDays;

            double tbTruoc = 0;
            int count = 0;
            for (int i = Math.max(0, lichSu.size() - 30); i < lichSu.size() - recentDays; i++) {
                tbTruoc += lichSu.get(i);
                count++;
            }
            tbTruoc = count > 0 ? tbTruoc / count : 0;

            if (tbTruoc > 0 && tbGanDay > tbTruoc * 2) { // Tăng > 200%
                ThucTheKho item = danhSachKho.timTheoMa(maSP);
                if (item instanceof SanPhamTrongKho) {
                    SanPhamTrongKho sp = (SanPhamTrongKho) item;
                    double tyLeTang = ((tbGanDay - tbTruoc) / tbTruoc) * 100;

                    ketQua.add(new SanPhamBanChay(
                            maSP, sp.getTenSanPham(), tbTruoc, tbGanDay, tyLeTang
                    ));
                }
            }
        }

        return ketQua;
    }

    // ===== Cảnh báo hàng tồn đọng =====

    public List<CanhBaoTonDong> canhBaoHangTonDong() {
        List<CanhBaoTonDong> ketQua = new ArrayList<>();

        for (ThucTheKho item : danhSachKho.getDanhSachKho()) {
            int tuoiTonKho = KhoHelper.tinhTuoiTonKho(item.getNgayNhapKho());

            if (tuoiTonKho > 180) { // Tồn > 6 tháng
                double giaTriTon = item.tinhGiaTriTonKho();
                int phanTramGiamGia = tuoiTonKho > 270 ? 30 :
                        tuoiTonKho > 180 ? 20 : 10;

                String lyDo = "";
                if (tuoiTonKho > 270) {
                    lyDo = "Tồn kho CỰC LÂU (>9 tháng) - CẦN thanh lý GẤP!";
                } else if (tuoiTonKho > 180) {
                    lyDo = "Tồn kho lâu (>6 tháng) - Nên giảm giá";
                }

                CanhBaoTonDong canhBao = new CanhBaoTonDong(
                        item.getMaThucThe(),
                        item instanceof SanPhamTrongKho ?
                                ((SanPhamTrongKho) item).getTenSanPham() : "Hàng hóa",
                        tuoiTonKho,
                        item.getSoLuong(),
                        giaTriTon,
                        phanTramGiamGia,
                        lyDo
                );
                ketQua.add(canhBao);
            }
        }

        return ketQua;
    }

    // ===== Dự đoán doanh thu =====

    public double duDoanDoanhThuThangSau() {
        double tongDoanhThu = 0;

        for (ThucTheKho item : danhSachKho.getDanhSachKho()) {
            if (item instanceof SanPhamTrongKho) {
                SanPhamTrongKho sp = (SanPhamTrongKho) item;
                double tocDoBan = tinhTocDoBanTrungBinh(sp.getMaThucThe());

                if (tocDoBan > 0) {
                    double soLuongDuKien = tocDoBan * 30; // 30 ngày
                    soLuongDuKien = Math.min(soLuongDuKien, sp.getSoLuong());
                    tongDoanhThu += soLuongDuKien * sp.getGiaBan();
                }
            }
        }

        return tongDoanhThu;
    }

    // ===== Hiển thị báo cáo =====

    public void hienThiBaoCaoThongMinh() {
        System.out.println("\n DỰ BÁO THÔNG MINH ");

        // 1. Gợi ý nhập hàng
        List<GoiYNhapHang> goiYList = taoGoiYNhapHang();
        if (!goiYList.isEmpty()) {
            System.out.println("\n📦 GỢI Ý NHẬP HÀNG (" + goiYList.size() + " sản phẩm):");
            for (GoiYNhapHang gy : goiYList) {
                System.out.println(gy);
            }
        }

        // 2. Sản phẩm bán chạy
        List<SanPhamBanChay> banChayList = phatHienBanChayDotBien();
        if (!banChayList.isEmpty()) {
            System.out.println("\n🔥 SẢN PHẨM BÁN CHẠY ĐỘT BIẾN:");
            for (SanPhamBanChay bc : banChayList) {
                System.out.println(bc);
            }
        }

        // 3. Hàng tồn đọng
        List<CanhBaoTonDong> tonDongList = canhBaoHangTonDong();
        if (!tonDongList.isEmpty()) {
            System.out.println("\n⚠️ CẢNH BÁO HÀNG TỒN ĐỌNG (" + tonDongList.size() + " mặt hàng):");
            for (CanhBaoTonDong cb : tonDongList) {
                System.out.println(cb);
            }
        }

        // 4. Dự đoán doanh thu
        double duDoanDT = duDoanDoanhThuThangSau();
        System.out.println("\n💰 DỰ ĐOÁN DOANH THU THÁNG SAU: " +
                KhoHelper.formatTien(duDoanDT));

        System.out.println("╚═══════════════════════════════════════════════╝\n");
    }

    // Inner classes cho kết quả phân tích

    public static class GoiYNhapHang {
        String maSP, tenSP, ncc, lyDo;
        int tonHienTai, soLuongNen, soNgayHetHang;

        public GoiYNhapHang(String maSP, String tenSP, int tonHienTai,
                            int soLuongNen, int soNgayHetHang, String ncc, String lyDo) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.tonHienTai = tonHienTai;
            this.soLuongNen = soLuongNen;
            this.soNgayHetHang = soNgayHetHang;
            this.ncc = ncc;
            this.lyDo = lyDo;
        }

        @Override
        public String toString() {
            return String.format("├─ %s: %s\n" +
                            "│  Tồn: %d | Nên nhập: %d | Hết sau: %d ngày\n" +
                            "│  NCC: %s | %s",
                    maSP, tenSP, tonHienTai, soLuongNen,
                    soNgayHetHang, ncc, lyDo);
        }
    }

    public static class SanPhamBanChay {
        String maSP, tenSP;
        double tbTruoc, tbHienTai, tyLeTang;

        public SanPhamBanChay(String maSP, String tenSP, double tbTruoc,
                              double tbHienTai, double tyLeTang) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.tbTruoc = tbTruoc;
            this.tbHienTai = tbHienTai;
            this.tyLeTang = tyLeTang;
        }

        @Override
        public String toString() {
            return String.format("├─ %s: %s\n" +
                            "│  Bán TB trước: %.1f/ngày → Hiện tại: %.1f/ngày\n" +
                            "│  Tăng trưởng: +%.0f%%",
                    maSP, tenSP, tbTruoc, tbHienTai, tyLeTang);
        }
    }

    public static class CanhBaoTonDong {
        String maSP, tenSP, lyDo;
        int tuoiTonKho, soLuong, phanTramGiam;
        double giaTriTon;

        public CanhBaoTonDong(String maSP, String tenSP, int tuoiTonKho,
                              int soLuong, double giaTriTon, int phanTramGiam, String lyDo) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.tuoiTonKho = tuoiTonKho;
            this.soLuong = soLuong;
            this.giaTriTon = giaTriTon;
            this.phanTramGiam = phanTramGiam;
            this.lyDo = lyDo;
        }

        @Override
        public String toString() {
            return String.format("├─ %s: %s\n" +
                            "│  Tồn: %d ngày | SL: %d | Giá trị: %s\n" +
                            "│  Đề xuất: Giảm %d%% | %s",
                    maSP, tenSP, tuoiTonKho, soLuong,
                    KhoHelper.formatTien(giaTriTon), phanTramGiam, lyDo);
        }
    }
}