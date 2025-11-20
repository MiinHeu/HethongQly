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
    // Tim theo loai san pham
    public List<SanPham> timTheoLoai(String loai) {
        List<SanPham> kq = new ArrayList<>();
        for (SanPham sp : danhSach) {
            if (sp.getLoaiSP().toLowerCase().contains(loai.toLowerCase())) {
                kq.add(sp);
            }
        }
        return kq;
    }

    // Lay danh sach (alias)
    public List<SanPham> layDanhSach() {
        return getDanhSach();
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile))) {
            writer.write("# File danh sách sản phẩm - Format: LOAI|maSP|tenSP|giaBan|hangSX|soLuongTon|ngayNhap|...");
            writer.newLine();
            writer.write("# DT = Điện thoại, PK = Phụ kiện, LK = Linh kiện");
            writer.newLine();
            
            for (SanPham sp : danhSach) {
                if (sp instanceof DienThoai) {
                    DienThoai dt = (DienThoai) sp;
                    writer.write(String.format("DT|%s|%s|%.2f|%s|%d|%s|%s|%s",
                        dt.getMaSP(), dt.getTenSP(), dt.getGiaBan(), dt.getHangSX(),
                        dt.getSoLuongTon(), dt.getNgayNhap(), dt.getImei(), dt.getCauHinh()));
                    writer.newLine();
                } else if (sp instanceof PhuKien) {
                    PhuKien pk = (PhuKien) sp;
                    writer.write(String.format("PK|%s|%s|%.2f|%s|%d|%s|%s|%s",
                        pk.getMaSP(), pk.getTenSP(), pk.getGiaBan(), pk.getHangSX(),
                        pk.getSoLuongTon(), pk.getNgayNhap(), pk.getLoaiPhuKien(), pk.getTuongThich()));
                    writer.newLine();
                } else if (sp instanceof LinhKien) {
                    LinhKien lk = (LinhKien) sp;
                    writer.write(String.format("LK|%s|%s|%.2f|%s|%d|%s|%b|%b",
                        lk.getMaSP(), lk.getTenSP(), lk.getGiaBan(), lk.getHangSX(),
                        lk.getSoLuongTon(), lk.getNgayNhap(), lk.isDanhChoSuaChua(), lk.isThuocBaoHanh()));
                    writer.newLine();
                }
            }
            System.out.println("Ghi file '" + tenFile + "' thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void docFile(String tenFile) {
        danhSach.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Bỏ qua dòng trống và comment
                
                String[] parts = line.split("\\|", -1);
                if (parts.length < 7) continue; // Dòng không hợp lệ
                
                String loai = parts[0];
                String maSP = parts[1];
                String tenSP = parts[2];
                double giaBan = Double.parseDouble(parts[3]);
                String hangSX = parts[4];
                int soLuongTon = Integer.parseInt(parts[5]);
                String ngayNhap = parts[6];
                
                SanPham sp = null;
                
                if (loai.equals("DT") && parts.length >= 9) {
                    // Điện thoại
                    String imei = parts[7];
                    String cauHinh = parts[8];
                    sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
                } else if (loai.equals("PK") && parts.length >= 9) {
                    // Phụ kiện
                    String loaiPhuKien = parts[7];
                    String tuongThich = parts[8];
                    sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPhuKien, tuongThich);
                } else if (loai.equals("LK") && parts.length >= 9) {
                    // Linh kiện
                    boolean danhChoSuaChua = Boolean.parseBoolean(parts[7]);
                    boolean thuocBaoHanh = Boolean.parseBoolean(parts[8]);
                    sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSuaChua, thuocBaoHanh);
                }
                
                if (sp != null) {
                    danhSach.add(sp);
                }
            }
            System.out.println("Đọc file '" + tenFile + "' thành công! Đã tải " + danhSach.size() + " sản phẩm.");
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số trong file: " + e.getMessage());
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