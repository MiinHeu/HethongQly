package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


class DanhSachKhachHang implements IQuanLyKhachHang {

    private List<KhachHang> ds = new ArrayList<>();

    public void them(KhachHang kh) { ds.add(kh); }

    public void xoa(String ma) {
        ds.removeIf(kh -> kh.getMa().equalsIgnoreCase(ma));
    }

    public void xuat() {
        if (ds.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }
        ds.forEach(KhachHang::xuat);
    }

    // Tìm kiếm
    public KhachHang timTheoMa(String ma) {
        return ds.stream()
                .filter(k -> k.getMa().equalsIgnoreCase(ma))
                .findFirst()
                .orElse(null);
    }

    public List<KhachHang> timTheoTen(String ten) {
        return ds.stream()
                .filter(k -> k.getTen().toLowerCase().contains(ten.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<KhachHang> timTheoSDT(String sdt) {
        return ds.stream().filter(k -> k.getSdt().equals(sdt)).collect(Collectors.toList());
    }

    public List<KhachHang> timTheoEmail(String mail) {
        return ds.stream().filter(k -> k.getEmail().equalsIgnoreCase(mail)).collect(Collectors.toList());
    }

    // Tìm gần đúng theo tên 
    public List<KhachHang> timGanDungTen(String ten) {
        return ds.stream()
                .filter(k -> tinhKhoangCachChuoi(k.getTen().toLowerCase(), ten.toLowerCase()) <= 2)
                .collect(Collectors.toList());
    }

    // Thống kê VIP
    public List<KhachHang> thongKeVIP() {
        return ds.stream()
                .filter(k -> k instanceof KhachHangVIP)
                .collect(Collectors.toList());
    }

    // Khách mua nhiều nhất (nhiều đơn nhất)
    public KhachHang khachMuaNhieuNhat() {
        return ds.stream()
                .max(Comparator.comparingInt(k -> k.getLichSuMuaHang().size()))
                .orElse(null);
    }

    // FILE I/O 
    public void ghiFile(String fileName) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (KhachHang k : ds) {
            bw.write(k.toData());
            bw.newLine();
        }
        bw.close();
    }

    public void docFile(String fileName) throws Exception {
        ds.clear();
        File file = new File(fileName);
        if (!file.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            ds.add(KhachHang.fromData(line));
        }
        br.close();
    }

    // Hàm so sánh chuỗi gần đúng
    private int tinhKhoangCachChuoi(String s1, String s2) {
        int[][] dp = new int[s1.length()+1][s2.length()+1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else dp[i][j] = (s1.charAt(i-1) == s2.charAt(j-1))
                        ? dp[i-1][j-1]
                        : 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // 7. INTERFACE 

    @Override
    public void capNhatDiemTichLuy(String maKH, int diem) {
        KhachHang kh = timTheoMa(maKH);
        if (kh != null) kh.diemTichLuy += diem;
    }

    @Override
    public String xepHang(KhachHang kh) {
        if (kh.getDiemTichLuy() >= 1000) return "VIP";
        if (kh.getDiemTichLuy() >= 500) return "Vàng";
        return "Thường";
    }

    @Override
    public double tinhChietKhau(KhachHang kh, double tongTien) {
        String hang = xepHang(kh);
        switch (hang) {
            case "VIP": return tongTien * 0.10;
            case "Vàng": return tongTien * 0.05;
            default: return 0;
        }
    }

    @Override
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
