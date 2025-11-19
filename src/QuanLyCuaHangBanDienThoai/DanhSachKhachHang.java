package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


class DanhSachKhachHang implements IQuanLyKhachHang {
    private List<KhachHang> ds = new ArrayList<>();

    // CRUD
    public void them(KhachHang kh) { ds.add(kh); }
    public void xuat() { ds.forEach(KhachHang::xuat); }
    public boolean xoa(String ma) { return ds.removeIf(k -> k.getMa().equalsIgnoreCase(ma)); }

    public KhachHang timTheoMa(String ma) {
        return ds.stream().filter(k -> k.getMa().equalsIgnoreCase(ma)).findFirst().orElse(null);
    }

    public List<KhachHang> timTheoTen(String ten) {
        return ds.stream()
                .filter(k -> k.getTen().toLowerCase().contains(ten.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<KhachHang> timTheoEmail(String email) {
        return ds.stream()
                .filter(k -> k.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    public List<KhachHang> timTheoSDT(String sdt) {
        return ds.stream()
                .filter(k -> k.getSdt().equalsIgnoreCase(sdt))
                .collect(Collectors.toList());
    }

    // Gần đúng tên (cho phép sai chính tả đơn giản)
    public List<KhachHang> timGanDungTen(String ten) {
        return ds.stream()
                .filter(k -> tinhKhoangCachChuoi(k.getTen().toLowerCase(), ten.toLowerCase()) <= 2)
                .collect(Collectors.toList());
    }

    // Thống kê khách VIP
    public List<KhachHang> thongKeVIP() {
        return ds.stream()
                .filter(k -> k instanceof KhachHangVIP)
                .collect(Collectors.toList());
    }

    // Tìm khách mua nhiều nhất
    public KhachHang khachMuaNhieuNhat() {
        return ds.stream()
                .max(Comparator.comparingInt(k -> k.getLichSuMuaHang().size()))
                .orElse(null);
    }

    // File I/O
    public void ghiFile(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (KhachHang k : ds) {
                bw.write(k.getMa() + "," + k.getTen() + "," + k.getSdt() + "," + k.getEmail() + "," +
                        k.getNgaySinh() + "," + k.getDiemTichLuy() + "," + k.getHangThanhVien());
                bw.newLine();
            }
        }
    }

    public void docFile(String path) throws IOException {
        ds.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 7) {
                    ds.add(new KhachHang(p[0], p[1], p[2], p[3],
                            LocalDate.parse(p[4]), Integer.parseInt(p[5]), p[6], new ArrayList<>()));
                }
            }
        }
    }

    // Hàm tính khoảng cách chuỗi đơn giản (Levenshtein-like)
    private int tinhKhoangCachChuoi(String s1, String s2) {
        int[][] dp = new int[s1.length()+1][s2.length()+1];
        for (int i=0; i<=s1.length(); i++)
            for (int j=0; j<=s2.length(); j++) {
                if (i==0) dp[i][j]=j;
                else if (j==0) dp[i][j]=i;
                else dp[i][j] = (s1.charAt(i-1)==s2.charAt(j-1)) ? dp[i-1][j-1] :
                            1+Math.min(dp[i-1][j-1], Math.min(dp[i][j-1], dp[i-1][j]));
            }
        return dp[s1.length()][s2.length()];
    }

    /* ====== IMPLEMENT INTERFACE ====== */
    public void capNhatDiemTichLuy(String maKH, int diem) {
        KhachHang kh = timTheoMa(maKH);
        if (kh != null) kh.diemTichLuy += diem;
    }

    public String xepHang(KhachHang kh) {
        if (kh.getDiemTichLuy() >= 1000) return "VIP";
        else if (kh.getDiemTichLuy() >= 500) return "Vàng";
        else return "Thường";
    }

    public double tinhChietKhau(KhachHang kh, double tongTien) {
        String hang = xepHang(kh);
        switch (hang) {
            case "VIP": return tongTien * 0.1;
            case "Vàng": return tongTien * 0.05;
            default: return 0;
        }
    }

    public List<KhachHang> locKhachSapSinhNhat(int soNgay) {
        LocalDate now = LocalDate.now();
        return ds.stream()
                .filter(k -> {
                    LocalDate next = k.getNgaySinh().withYear(now.getYear());
                    long diff = ChronoUnit.DAYS.between(now, next);
                    return diff >= 0 && diff <= soNgay;
                })
                .collect(Collectors.toList());
    }
}
