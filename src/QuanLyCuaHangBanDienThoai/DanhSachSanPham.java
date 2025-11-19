package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DanhSachSanPham implements IQuanLySanPham {
    private ArrayList<SanPham> danhSach;

    public DanhSachSanPham() {
        this.danhSach = new ArrayList<>();
    }

    @Override
    public void themSanPham(SanPham sp) {
        if (timTheoMa(sp.getMaSP()) == null) {
            danhSach.add(sp);
        }
    }

    @Override
    public boolean xoaSanPham(String maSP) {
        SanPham sp = timTheoMa(maSP);
        if (sp != null) {
            return danhSach.remove(sp);
        }
        return false;
    }

    @Override
    public boolean suaSanPham(SanPham sp) {
        int index = danhSach.indexOf(timTheoMa(sp.getMaSP()));
        if (index != -1) {
            danhSach.set(index, sp);
            return true;
        }
        return false;
    }

    @Override
    public SanPham timTheoMa(String maSP) {
        return danhSach.stream()
                .filter(sp -> sp.getMaSP().equals(maSP))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<SanPham> timTheoTen(String tenSP) {
        return danhSach.stream()
                .filter(sp -> sp.getTenSP().toLowerCase().contains(tenSP.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public SanPham timTheoIMEI(String imei) {
        return danhSach.stream()
                .filter(sp -> sp instanceof DienThoai)
                .map(sp -> (DienThoai) sp)
                .filter(dt -> dt.getImei().equals(imei))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<SanPham> timTheoHang(String hangSX) {
        return danhSach.stream()
                .filter(sp -> sp.getHangSX().equalsIgnoreCase(hangSX))
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPham> locTheoGia(double min, double max) {
        return danhSach.stream()
                .filter(sp -> sp.getGiaBan() >= min && sp.getGiaBan() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPham> timGanDung(String tuKhoa) {
        return danhSach.stream()
                .filter(sp -> tinhKhoangCachLevenshtein(sp.getTenSP().toLowerCase(), tuKhoa.toLowerCase()) <= 3)
                .collect(Collectors.toList());
    }

    private int tinhKhoangCachLevenshtein(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(
                        dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1),
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1
                    );
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    @Override
    public Map<String, Long> thongKeTheoHang() {
        return danhSach.stream()
                .collect(Collectors.groupingBy(SanPham::getHangSX, Collectors.counting()));
    }

    @Override
    public List<SanPham> getTopBanChay(int top) {
        // Giả lập số lượng bán = soLuongTon ban đầu - soLuongTon hiện tại
        return danhSach.stream()
                .sorted((sp1, sp2) -> Integer.compare(sp2.getSoLuongTon(), sp1.getSoLuongTon()))
                .limit(top)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPham> getSanPhamTonKhoCao() {
        double trungBinhTonKho = danhSach.stream()
                .mapToInt(SanPham::getSoLuongTon)
                .average()
                .orElse(0.0);

        return danhSach.stream()
                .filter(sp -> sp.getSoLuongTon() > trungBinhTonKho)
                .collect(Collectors.toList());
    }

    @Override
    public void sapXepTheoGia(boolean tangDan) {
        danhSach.sort((sp1, sp2) -> 
            tangDan ? Double.compare(sp1.getGiaBan(), sp2.getGiaBan())
                   : Double.compare(sp2.getGiaBan(), sp1.getGiaBan()));
    }

    @Override
    public void sapXepTheoTen() {
        danhSach.sort((sp1, sp2) -> sp1.getTenSP().compareToIgnoreCase(sp2.getTenSP()));
    }

    @Override
    public void sapXepTheoNgayNhap() {
        danhSach.sort((sp1, sp2) -> sp1.getNgayNhap().compareTo(sp2.getNgayNhap()));
    }

    @Override
    public void ghiFile(String tenFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tenFile))) {
            oos.writeObject(danhSach);
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void docFile(String tenFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tenFile))) {
            danhSach = (ArrayList<SanPham>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    @Override
    public List<SanPham> goiYThayThe(SanPham sp) {
        return danhSach.stream()
                .filter(s -> s.getLoaiSP().equals(sp.getLoaiSP()) &&
                           s.getHangSX().equals(sp.getHangSX()) &&
                           Math.abs(s.getGiaBan() - sp.getGiaBan()) <= sp.getGiaBan() * 0.2 &&
                           s.getSoLuongTon() > 0 &&
                           !s.getMaSP().equals(sp.getMaSP()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhuKien> goiYPhuKienTuongThich(DienThoai dt) {
        return danhSach.stream()
                .filter(sp -> sp instanceof PhuKien)
                .map(sp -> (PhuKien) sp)
                .filter(pk -> pk.getTuongThich().toLowerCase().contains(dt.getHangSX().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<SanPham> getDanhSach() {
        return new ArrayList<>(danhSach);
    }
}