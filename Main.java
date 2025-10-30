import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.*;

// =======================
// 1. LOP TRUU TUONG CORE
// =======================
abstract class ConNguoi {
    protected String ma;
    protected String ten;
    protected String sdt;
    protected String email;
    protected LocalDate ngaySinh;

    public ConNguoi() {}

    public ConNguoi(String ma, String ten, String sdt, String email, LocalDate ngaySinh) {
        this.ma = ma;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
    }

    public abstract void nhap(Scanner sc);
    public abstract void xuat();

    public String layMa() { return ma; }
    public String layTen() { return ten; }
    public String laySDT() { return sdt; }
    public String layEmail() { return email; }
    public LocalDate layNgaySinh() { return ngaySinh; }
}

// ==============================
// 2. CAC LOP KE THUA CON NGUOI
// ==============================
class KhachHang extends ConNguoi {
    protected int diemTichLuy;
    protected String hangThanhVien;
    protected double lichSuMuaHang;

    public KhachHang() {}

    public KhachHang(String ma, String ten, String sdt, String email, LocalDate ngaySinh, int diem, String hang, double lichSu) {
        super(ma, ten, sdt, email, ngaySinh);
        this.diemTichLuy = diem;
        this.hangThanhVien = hang;
        this.lichSuMuaHang = lichSu;
    }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("Nhap ten: "); ten = sc.nextLine();
        System.out.print("Nhap SDT: "); sdt = sc.nextLine();
        System.out.print("Nhap email: "); email = sc.nextLine();
        System.out.print("Nhap ngay sinh (yyyy-mm-dd): "); ngaySinh = LocalDate.parse(sc.nextLine());
        System.out.print("Nhap diem tich luy: "); diemTichLuy = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap hang thanh vien: "); hangThanhVien = sc.nextLine();
        System.out.print("Nhap tong tien da mua: "); lichSuMuaHang = Double.parseDouble(sc.nextLine());
    }

    @Override
    public void xuat() {
        System.out.printf("%-8s %-20s %-12s %-22s %-12s %-8d %-10s %-10.2f%n",
                ma, ten, sdt, email, ngaySinh, diemTichLuy, hangThanhVien, lichSuMuaHang);
    }

    public String toCsv() {
        return String.join(",", ma, ten, sdt, email, ngaySinh.toString(),
                String.valueOf(diemTichLuy), hangThanhVien, String.valueOf(lichSuMuaHang));
    }

    public static KhachHang parseFromCsv(String line) {
        String[] p = line.split(",");
        return new KhachHang(p[0], p[1], p[2], p[3], LocalDate.parse(p[4]),
                Integer.parseInt(p[5]), p[6], Double.parseDouble(p[7]));
    }
}

class KhachHangVIP extends KhachHang {
    private double uuDai;

    public KhachHangVIP(String ma, String ten, String sdt, String email, LocalDate ngaySinh, int diem, String hang, double lichSu, double uuDai) {
        super(ma, ten, sdt, email, ngaySinh, diem, hang, lichSu);
        this.uuDai = uuDai;
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf("   => Khach hang VIP (Uu dai: %.0f%%)%n", uuDai * 100);
    }

    public double layUuDai() { return uuDai; }
}

class KhachHangTiemNang extends KhachHang {
    public KhachHangTiemNang(String ma, String ten, String sdt, String email, LocalDate ngaySinh) {
        super(ma, ten, sdt, email, ngaySinh, 0, "Tiem nang", 0);
    }

    @Override
    public void xuat() {
        System.out.printf("%-8s %-20s %-12s %-22s %-12s %-10s%n",
                ma, ten, sdt, email, ngaySinh, "Chua mua hang");
    }
}

// ===================================
// 3. INTERFACE TU THIET KE
// ===================================
interface IQuanLyKhachHang {
    void capNhatDiemTichLuy(String maKH, int diem);
    String xepHang(KhachHang kh);
    double tinhChietKhau(KhachHang kh, double tongTien);
    List<KhachHang> locKhachSapSinhNhat(int soNgay);
}

// ===================================
// 4. LOP TIEN ICH STATIC HELPER
// ===================================
class KhachHangHelper {
    private static int dem = 1;

    public static String sinhMaKhachHang() {
        return String.format("KH%03d", dem++);
    }

    public static boolean validateSDT(String sdt) {
        return sdt != null && sdt.matches("\\d{10,11}");
    }

    public static boolean validateEmail(String email) {
        return email != null && Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }
}

// ===================================
// 5. DANH SACH KHACH HANG
// ===================================
class DanhSachKhachHang implements IQuanLyKhachHang {
    private List<KhachHang> ds = new ArrayList<>();

    public void them(KhachHang kh) { ds.add(kh); }

    public void xuat() {
        System.out.printf("%-8s %-20s %-12s %-22s %-12s %-8s %-10s %-10s%n",
                "Ma", "Ten", "SDT", "Email", "Ngay sinh", "Diem", "Hang", "TongMua");
        for (KhachHang kh : ds) kh.xuat();
    }

    public KhachHang timTheoSDT(String sdt) {
        for (KhachHang k : ds)
            if (k.laySDT().equals(sdt)) return k;
        return null;
    }

    public List<KhachHang> timTheoTenGanDung(String tuKhoa, double nguong) {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang k : ds) {
            double doTuongDong = tinhDoTuongDong(k.layTen().toLowerCase(), tuKhoa.toLowerCase());
            if (doTuongDong >= nguong) kq.add(k);
        }
        return kq;
    }

    private double tinhDoTuongDong(String a, String b) {
        int kc = tinhKhoangCachLevenshtein(a, b);
        int max = Math.max(a.length(), b.length());
        return 1 - (double) kc / max;
    }

    private int tinhKhoangCachLevenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
    }

    public List<KhachHang> locKhachVIP() {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang k : ds)
            if (k instanceof KhachHangVIP) kq.add(k);
        return kq;
    }

    public KhachHang khachMuaNhieuNhat() {
        return ds.stream().max(Comparator.comparingDouble(k -> k.lichSuMuaHang)).orElse(null);
    }

    @Override
    public void capNhatDiemTichLuy(String maKH, int diem) {
        for (KhachHang k : ds)
            if (k.layMa().equals(maKH)) k.diemTichLuy += diem;
    }

    @Override
    public String xepHang(KhachHang kh) {
        if (kh.diemTichLuy >= 1000) return "VIP";
        if (kh.diemTichLuy >= 500) return "VANG";
        return "THUONG";
    }

    @Override
    public double tinhChietKhau(KhachHang kh, double tongTien) {
        switch (kh.hangThanhVien.toUpperCase()) {
            case "VIP": return tongTien * 0.1;
            case "VANG": return tongTien * 0.05;
            default: return 0;
        }
    }

    @Override
    public List<KhachHang> locKhachSapSinhNhat(int soNgay) {
        List<KhachHang> kq = new ArrayList<>();
        LocalDate homNay = LocalDate.now();
        for (KhachHang k : ds) {
            LocalDate ns = k.layNgaySinh();
            try {
                LocalDate sinhNhatNamNay = LocalDate.of(homNay.getYear(), ns.getMonth(), ns.getDayOfMonth());
                long chenh = ChronoUnit.DAYS.between(homNay, sinhNhatNamNay);
                if (chenh >= 0 && chenh <= soNgay) kq.add(k);
            } catch (Exception ex) {
                // skip lỗi ngày 29/2
            }
        }
        return kq;
    }

    public void docFile(String duongDan) throws IOException {
        ds.clear();
        List<String> dong = Files.readAllLines(Paths.get(duongDan));
        for (String s : dong)
            if (!s.trim().isEmpty()) ds.add(KhachHang.parseFromCsv(s));
    }

    public void ghiFile(String duongDan) throws IOException {
        List<String> dong = new ArrayList<>();
        for (KhachHang k : ds) dong.add(k.toCsv());
        Files.write(Paths.get(duongDan), dong);
    }

    // Tinh nang sang tao: goi y khuyen mai
    public Map<KhachHang, String> goiYKhuyenMai() {
        Map<KhachHang, String> goiY = new HashMap<>();
        for (KhachHang k : ds) {
            if (k instanceof KhachHangVIP) goiY.put(k, "Tang voucher 200k");
            else if (k.lichSuMuaHang > 5000000) goiY.put(k, "Giam 10% cho lan mua ke tiep");
            else if (k.diemTichLuy > 300) goiY.put(k, "Tang 50 diem tich luy");
        }
        return goiY;
    }
}

// ===================================
// 6. MAIN DEMO CHUONG TRINH
// ===================================
public class Main {
    public static void main(String[] args) {
        DanhSachKhachHang ds = new DanhSachKhachHang();

        KhachHangVIP vip1 = new KhachHangVIP(KhachHangHelper.sinhMaKhachHang(),
                "Nguyen Van A", "0901234567", "a@gmail.com",
                LocalDate.of(1995, 10, 15), 1200, "VIP", 12000000, 0.15);

        KhachHang kh2 = new KhachHang(KhachHangHelper.sinhMaKhachHang(),
                "Tran Thi B", "0912345678", "b@yahoo.com",
                LocalDate.of(1998, 10, 28), 600, "VANG", 7000000);

        KhachHangTiemNang kh3 = new KhachHangTiemNang(KhachHangHelper.sinhMaKhachHang(),
                "Le Van C", "0987654321", "c@gmail.com", LocalDate.of(2000, 3, 10));

        ds.them(vip1);
        ds.them(kh2);
        ds.them(kh3);

        System.out.println("=== DANH SACH KHACH HANG ===");
        ds.xuat();

        System.out.println("\n=== KHACH SAP SINH NHAT (15 ngay toi) ===");
        for (KhachHang k : ds.locKhachSapSinhNhat(15)) k.xuat();

        System.out.println("\n=== TIM TEN GAN DUNG voi 'Nguyen Van' ===");
        List<KhachHang> ganDung = ds.timTheoTenGanDung("Nguyen Van", 0.3);
        if (ganDung.isEmpty()) System.out.println("Khong tim thay ket qua gan dung");
        else for (KhachHang k : ganDung) k.xuat();

        System.out.println("\n=== GOI Y KHUYEN MAI ===");
        Map<KhachHang, String> goiY = ds.goiYKhuyenMai();
        for (var e : goiY.entrySet()) {
            System.out.println(e.getKey().layTen() + " -> " + e.getValue());
        }

        try {
            ds.ghiFile("khachhang.csv");
            System.out.println("\nDa ghi file khachhang.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
