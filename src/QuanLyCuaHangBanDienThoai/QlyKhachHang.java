package QuanLyCuaHangBanDienThoai;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class QlyKhachHang {
    private DanhSachKhachHang dskh;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "src/QuanLyCuaHangBanDienThoai/danhsachKH.txt";

    public QlyKhachHang() {
        qltt = QuanLyTrungTam.getInstance();
        dskh = qltt.getDanhSachKhachHang();
    }

// meunu chính

public void menu() {
    int choice;
    do {
        System.out.println("\n--- QUAN LY KHACH HANG ---");
        System.out.println("1. Them khach hang");
        System.out.println("2. Sua thong tin khach hang");
        System.out.println("3. Xoa khach hang");
        System.out.println("4. Tim kiem khach hang");
        System.out.println("5. Xem danh sach khach hang");
        System.out.println("6. Tai du lieu tu file");
        System.out.println("7. Xuat du lieu ra file");
        System.out.println("8. Quay lai");
        System.out.print("Chon: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: themKhachHang(); break;
            case 2: suaKhachHang(); break;
            case 3: xoaKhachHang(); break;
            case 4: timKiemKhachHang(); break;
            case 5: xemDanhSach(); break;
            case 6: taiDuLieu(); break;
            case 7: xuatDuLieu(); break;
            case 8: break;
            default: System.out.println("Lua chon khong hop le!");
        }
    } while (choice != 8);
}

// ===== 1. THEM KHACH HANG =====

private void themKhachHang() {
    try {
        System.out.println("\n--- THEM KHACH HANG ---");
        
        String ma = KhachHangHelper.sinhMaKhachHang();
        
        System.out.print("Ten khach hang: ");
        String ten = scanner.nextLine().trim();
        if (ten.isEmpty()) {
            System.out.println("Loi: Ten khong duoc de trong!");
            return;
        }
        
        String sdt;
        do {
            System.out.print("So dien thoai: ");
            sdt = scanner.nextLine().trim();
            if (!KhachHangHelper.validateSDT(sdt)) {
                System.out.println("SDT khong hop le!");
            }
        } while (!KhachHangHelper.validateSDT(sdt));
        
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (!KhachHangHelper.validateEmail(email)) {
                System.out.println("Email khong hop le!");
            }
        } while (!KhachHangHelper.validateEmail(email));
        
        LocalDate ngaySinh = nhapNgaySinh();
        
        System.out.print("Diem tich luy: ");
        int diem = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Hang thanh vien (Thuong/Vang/VIP): ");
        String hang = scanner.nextLine().trim();
        
        KhachHang kh;
        if (hang.equalsIgnoreCase("VIP")) {
            kh = new KhachHangVIP(ma, ten, sdt, email, ngaySinh, diem, hang, new ArrayList<>(), 0.1);
        } else {
            kh = new KhachHang(ma, ten, sdt, email, ngaySinh, diem, hang, new ArrayList<>());
        }
        
        dskh.themKhachHang(kh);
        System.out.println("\nThem thanh cong! Ma KH: " + ma);
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        scanner.nextLine();
    }
}

private LocalDate nhapNgaySinh() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    while (true) {
        try {
            System.out.print("Ngay sinh (dd/MM/yyyy): ");
            return LocalDate.parse(scanner.nextLine(), df);
        } catch (Exception e) {
            System.out.println("Ngay sinh khong hop le!");
        }
    }
}

// ===== 2. SUA KHACH HANG =====

private void suaKhachHang() {
    try {
        System.out.println("\n--- SUA KHACH HANG ---");
        
        System.out.print("Nhap ma khach hang: ");
        String ma = scanner.nextLine().trim();
        
        KhachHang kh = dskh.timTheoMa(ma);
        if (kh == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ma: " + kh.getMa());
        System.out.println("Ten: " + kh.getTen());
        System.out.println("SDT: " + kh.getSdt());
        System.out.println("Email: " + kh.getEmail());
        System.out.println("Diem: " + kh.getDiemTichLuy());
        System.out.println("Hang: " + kh.getHangThanhVien());
        
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        System.out.print("Ten [" + kh.getTen() + "]: ");
        String ten = scanner.nextLine().trim();
        if (!ten.isEmpty()) kh.setTen(ten);
        
        System.out.print("SDT [" + kh.getSdt() + "]: ");
        String sdt = scanner.nextLine().trim();
        if (!sdt.isEmpty() && KhachHangHelper.validateSDT(sdt)) {
            kh.setSdt(sdt);
        }
        
        System.out.print("Email [" + kh.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty() && KhachHangHelper.validateEmail(email)) {
            kh.setEmail(email);
        }
        
        System.out.print("Diem [" + kh.getDiemTichLuy() + "]: ");
        String diemStr = scanner.nextLine().trim();
        if (!diemStr.isEmpty()) {
            try {
                int diem = Integer.parseInt(diemStr);
                kh.setDiemTichLuy(diem);
            } catch (NumberFormatException e) {}
        }
        
        System.out.print("Hang [" + kh.getHangThanhVien() + "]: ");
        String hang = scanner.nextLine().trim();
        if (!hang.isEmpty()) kh.setHangThanhVien(hang);
        
        System.out.println("\nCap nhat thanh cong!");
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}

// ===== 3. XOA KHACH HANG =====

private void xoaKhachHang() {
    try {
        System.out.println("\n--- XOA KHACH HANG ---");
        
        System.out.print("Nhap ma khach hang: ");
        String ma = scanner.nextLine().trim();
        
        KhachHang kh = dskh.timTheoMa(ma);
        if (kh == null) {
            System.out.println("Khong tim thay!");
            return;
        }
        
        System.out.println("\nThong tin khach hang:");
        System.out.println("Ma: " + kh.getMa());
        System.out.println("Ten: " + kh.getTen());
        System.out.println("SDT: " + kh.getSdt());
        
        System.out.print("\nXac nhan xoa? (y/n): ");
        String xacNhan = scanner.nextLine().trim();
        
        if (xacNhan.equalsIgnoreCase("y")) {
            if (dskh.xoaKhachHang(ma)) {
                System.out.println("Xoa thanh cong!");
            } else {
                System.out.println("Loi khi xoa!");
            }
        } else {
            System.out.println("Da huy!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
    }
}

// ===== 4. TIM KIEM KHACH HANG =====

private void timKiemKhachHang() {
    try {
        System.out.println("\n--- TIM KIEM KHACH HANG ---");
        System.out.println("1. Tim theo Ma");
        System.out.println("2. Tim theo Ten");
        System.out.println("3. Tim theo SDT");
        System.out.println("4. Tim theo Hang");
        System.out.print("Chon: ");
        int chon = scanner.nextInt();
        scanner.nextLine();

        if (chon == 1) {
            System.out.print("Nhap ma: ");
            String ma = scanner.nextLine().trim();
            KhachHang kh = dskh.timTheoMa(ma);
            if (kh != null) {
                System.out.println("\nTim thay:");
                hienThiKH(kh);
            } else {
                System.out.println("Khong tim thay!");
            }
        } else if (chon == 2) {
            System.out.print("Nhap ten: ");
            String ten = scanner.nextLine().trim();
            List<KhachHang> ds = dskh.timTheoTen(ten);
            hienThiDanhSachKH(ds);
        } else if (chon == 3) {
            System.out.print("Nhap SDT: ");
            String sdt = scanner.nextLine().trim();
            List<KhachHang> ds = dskh.timTheoSDT(sdt);
            hienThiDanhSachKH(ds);
        } else if (chon == 4) {
            System.out.print("Nhap hang: ");
            String hang = scanner.nextLine().trim();
            List<KhachHang> ds = dskh.timTheoHang(hang);
            hienThiDanhSachKH(ds);
        } else {
            System.out.println("Lua chon khong hop le!");
        }
        
    } catch (Exception e) {
        System.out.println("Loi: " + e.getMessage());
        scanner.nextLine();
    }
}

private void hienThiKH(KhachHang kh) {
    System.out.println("Ma: " + kh.getMa());
    System.out.println("Ten: " + kh.getTen());
    System.out.println("SDT: " + kh.getSdt());
    System.out.println("Email: " + kh.getEmail());
    System.out.println("Diem: " + kh.getDiemTichLuy());
    System.out.println("Hang: " + kh.getHangThanhVien());
}

private void hienThiDanhSachKH(List<KhachHang> ds) {
    if (ds.isEmpty()) {
        System.out.println("Khong tim thay!");
    } else {
        System.out.println("\nTim thay " + ds.size() + " khach hang:");
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- Khach hang " + (i + 1) + " ---");
            hienThiKH(ds.get(i));
        }
    }
}

// ===== 5. XEM DANH SACH =====

private void xemDanhSach() {
    System.out.println("\n--- DANH SACH KHACH HANG ---");
    List<KhachHang> ds = dskh.layDanhSach();
    if (ds.isEmpty()) {
        System.out.println("Danh sach trong!");
    } else {
        for (int i = 0; i < ds.size(); i++) {
            System.out.println("\n--- Khach hang " + (i + 1) + " ---");
            hienThiKH(ds.get(i));
        }
        System.out.println("\nTong so: " + ds.size());
    }
}

// ===== 6. TAI/XUAT FILE =====

private void taiDuLieu() {
    try {
        dskh.docFile(fileName);
        System.out.println("Da tai du lieu tu file!");
    } catch (Exception e) {
        System.out.println("Loi khi tai file: " + e.getMessage());
    }
}

private void xuatDuLieu() {
    try {
        dskh.ghiFile(fileName);
        System.out.println("Da xuat du lieu ra file!");
    } catch (Exception e) {
        System.out.println("Loi khi xuat file: " + e.getMessage());
    }
}

}
