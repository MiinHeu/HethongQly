package QuanLyCuaHangBanDienThoai;

import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


class DanhSachKhachHang implements IQuanLyKhachHang {

    private List<KhachHang> ds = new ArrayList<>();

    public void them(KhachHang kh) {
        ds.add(kh);
    }

    public void xoa(String ma) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMa().equalsIgnoreCase(ma)) {
                ds.remove(i);
                break;
            }
        }
    }

    public void xuat() {
        if (ds.isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }
        for (KhachHang kh : ds) {
            kh.xuat();
        }
    }

    // Tìm kiếm
    public KhachHang timTheoMa(String ma) {
        for (KhachHang kh : ds) {
            if (kh.getMa().equalsIgnoreCase(ma)) {
                return kh;
            }
        }
        return null;
    }

    public List<KhachHang> timTheoTen(String ten) {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang kh : ds) {
            if (kh.getTen().toLowerCase().contains(ten.toLowerCase())) {
                kq.add(kh);
            }
        }
        return kq;
    }

    public List<KhachHang> timTheoSDT(String sdt) {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang kh : ds) {
            if (kh.getSdt().contains(sdt)) {
                kq.add(kh);
            }
        }
        return kq;
    }

    public List<KhachHang> timTheoEmail(String mail) {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang kh : ds) {
            if (kh.getEmail().equalsIgnoreCase(mail)) {
                kq.add(kh);
            }
        }
        return kq;
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
            case "VIP":
                return tongTien * 0.10;
            case "Vàng":
                return tongTien * 0.05;
            default:
                return 0;
        }
    }

    // Loc khach sap sinh nhat - DA COMMENT (khong can thiet)

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


public List<KhachHang> timTheoHang(String hang) {
    List<KhachHang> kq = new ArrayList<>();
    for (KhachHang kh : ds) {
        if (kh.getHangThanhVien().toLowerCase().contains(hang.toLowerCase())) {
            kq.add(kh);
        }
    }
    return kq;
}

    // Lay danh sach
    public List<KhachHang> layDanhSach() {
        return ds;
    }

    // Thêm khach hang (alias)
    public void themKhachHang(KhachHang kh) {
        them(kh);
    }

    // Xoa khach hang (alias tra ve boolean)
    public boolean xoaKhachHang(String ma) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMa().equalsIgnoreCase(ma)) {
                ds.remove(i);
                return true;
            }
        }
        return false;
    }
}
