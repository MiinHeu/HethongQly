package QuanLyCuaHangBanDienThoai;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuSanPham {
    private Scanner scanner;
    private DanhSachSanPham danhSachSP;

    public MenuSanPham() {
        try {
            scanner = new Scanner(System.in);
            danhSachSP = new DanhSachSanPham();
        } catch (Exception e) {
            System.out.println("Lỗi khởi tạo: " + e.getMessage());
        }
    }

    public void hienThiMenu() {
        while (true) {
            System.out.println("\n===== QUAN LY SAN PHAM =====");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Sua san pham");
            System.out.println("4. Tim kiem san pham");
            System.out.println("5. Hien thi danh sach san pham");
            System.out.println("6. Thong ke san pham");
            System.out.println("7. Sap xep san pham");
            System.out.println("8. Doc/Ghi file");
            System.out.println("9. Goi y san pham");
            System.out.println("0. Quay lai menu chinh");
            
            System.out.print("Chon chuc nang: ");
            int luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng new line

            switch (luaChon) {
                case 1:
                    themSanPham();
                    break;
                case 2:
                    xoaSanPham();
                    break;
                case 3:
                    suaSanPham();
                    break;
                case 4:
                    menuTimKiem();
                    break;
                case 5:
                    hienThiDanhSach();
                    break;
                case 6:
                    menuThongKe();
                    break;
                case 7:
                    menuSapXep();
                    break;
                case 8:
                    menuFile();
                    break;
                case 9:
                    menuGoiY();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void themSanPham() {
        System.out.println("\n----- THEM SAN PHAM -----");
        System.out.println("1. Them Dien thoai");
        System.out.println("2. Them Phu kien");
        System.out.println("3. Them Linh kien");
        System.out.print("Chon loai san pham: ");
        
        int loai = scanner.nextInt();
        scanner.nextLine();

        // Nhap thong tin chung
        String maSP = SanPhamHelper.sinhMaSanPham();
        System.out.print("Ten san pham: ");
        String tenSP = scanner.nextLine();
        System.out.print("Gia ban: ");
        double giaBan = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Hang san xuat: ");
        String hangSX = scanner.nextLine();
        System.out.print("So luong ton: ");
        int soLuongTon = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ngay nhap (dd/MM/yyyy): ");
        String ngayNhap = scanner.nextLine();

        SanPham sp = null;
        switch (loai) {
            case 1:
                System.out.print("IMEI: ");
                String imei = scanner.nextLine();
                System.out.print("Cau hinh: ");
                String cauHinh = scanner.nextLine();
                sp = new DienThoai(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, imei, cauHinh);
                break;
            case 2:
                System.out.print("Loai phu kien: ");
                String loaiPK = scanner.nextLine();
                System.out.print("Tuong thich voi: ");
                String tuongThich = scanner.nextLine();
                sp = new PhuKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, loaiPK, tuongThich);
                break;
            case 3:
                System.out.print("Danh cho sua chua (true/false): ");
                boolean danhChoSuaChua = scanner.nextBoolean();
                System.out.print("Thuộc bảo hành (true/false): ");
                boolean thuocBaoHanh = scanner.nextBoolean();
                sp = new LinhKien(maSP, tenSP, giaBan, hangSX, soLuongTon, ngayNhap, danhChoSuaChua, thuocBaoHanh);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        danhSachSP.themSanPham(sp);
        System.out.println("Thêm sản phẩm thành công!");
    }

    private void xoaSanPham() {
        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String maSP = scanner.nextLine();
        if (danhSachSP.xoaSanPham(maSP)) {
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm!");
        }
    }

    private void suaSanPham() {
        System.out.print("Nhập mã sản phẩm cần sửa: ");
        String maSP = scanner.nextLine();
        SanPham sp = danhSachSP.timTheoMa(maSP);
        if (sp == null) {
            System.out.println("Không tìm thấy sản phẩm!");
            return;
        }

        // Hiển thị thông tin hiện tại
        System.out.println("Thông tin hiện tại: " + sp);

        // Nhập thông tin mới
        System.out.print("Tên mới (Enter để giữ nguyên): ");
        String tenMoi = scanner.nextLine();
        if (!tenMoi.isEmpty()) {
            sp.setTenSP(tenMoi);
        }

        System.out.print("Giá mới (0 để giữ nguyên): ");
        double giaMoi = scanner.nextDouble();
        if (giaMoi > 0) {
            sp.setGiaBan(giaMoi);
        }

        scanner.nextLine();
        System.out.print("Số lượng tồn mới (-1 để giữ nguyên): ");
        int slMoi = scanner.nextInt();
        if (slMoi >= 0) {
            sp.setSoLuongTon(slMoi);
        }

        danhSachSP.suaSanPham(sp);
        System.out.println("Cập nhật thành công!");
    }

    private void menuTimKiem() {
        System.out.println("\n----- TIM KIEM SAN PHAM -----");
        System.out.println("1. Tim theo ma");
        System.out.println("2. Tim theo ten");
        System.out.println("3. Tim theo IMEI");
        System.out.println("4. Tim theo hang");
        System.out.println("5. Loc theo gia");
        System.out.println("6. Tim gan dung");
        System.out.print("Chon cach tim: ");
        
        int luaChon = scanner.nextInt();
        scanner.nextLine();
        
        List<SanPham> ketQua = null;
        switch (luaChon) {
            case 1:
                System.out.print("Nhập mã sản phẩm: ");
                String ma = scanner.nextLine();
                SanPham sp = danhSachSP.timTheoMa(ma);
                if (sp != null) {
                    System.out.println(sp);
                } else {
                    System.out.println("Không tìm thấy sản phẩm!");
                }
                break;
            case 2:
                System.out.print("Nhập tên sản phẩm: ");
                String ten = scanner.nextLine();
                ketQua = danhSachSP.timTheoTen(ten);
                break;
            case 3:
                System.out.print("Nhập IMEI: ");
                String imei = scanner.nextLine();
                SanPham dienThoai = danhSachSP.timTheoIMEI(imei);
                if (dienThoai != null) {
                    System.out.println(dienThoai);
                } else {
                    System.out.println("Không tìm thấy điện thoại!");
                }
                break;
            case 4:
                System.out.print("Nhập hãng sản xuất: ");
                String hang = scanner.nextLine();
                ketQua = danhSachSP.timTheoHang(hang);
                break;
            case 5:
                System.out.print("Nhập giá tối thiểu: ");
                double min = scanner.nextDouble();
                System.out.print("Nhập giá tối đa: ");
                double max = scanner.nextDouble();
                ketQua = danhSachSP.locTheoGia(min, max);
                break;
            case 6:
                System.out.print("Nhập từ khóa: ");
                String tuKhoa = scanner.nextLine();
                ketQua = danhSachSP.timGanDung(tuKhoa);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        if (ketQua != null) {
            if (ketQua.isEmpty()) {
                System.out.println("Không tìm thấy sản phẩm nào!");
            } else {
                ketQua.forEach(System.out::println);
            }
        }
    }

    private void hienThiDanhSach() {
        List<SanPham> danhSach = danhSachSP.getDanhSach();
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        danhSach.forEach(System.out::println);
    }

    private void menuThongKe() {
        System.out.println("\n----- THONG KE SAN PHAM -----");
        System.out.println("1. Thong ke theo hang");
        System.out.println("2. Top san pham ban chay");
        System.out.println("3. San pham ton kho cao");
        System.out.print("Chon loai thong ke: ");
        
        int luaChon = scanner.nextInt();
        switch (luaChon) {
            case 1:
                Map<String, Long> thongKeHang = danhSachSP.thongKeTheoHang();
                thongKeHang.forEach((hang, soLuong) -> 
                    System.out.printf("%s: %d sản phẩm%n", hang, soLuong));
                break;
            case 2:
                System.out.print("Nhập số lượng top: ");
                int top = scanner.nextInt();
                List<SanPham> topBanChay = danhSachSP.getTopBanChay(top);
                System.out.println("Top " + top + " sản phẩm bán chạy:");
                topBanChay.forEach(System.out::println);
                break;
            case 3:
                List<SanPham> tonKhoCao = danhSachSP.getSanPhamTonKhoCao();
                System.out.println("Sản phẩm tồn kho cao:");
                tonKhoCao.forEach(System.out::println);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void menuSapXep() {
        System.out.println("\n----- SAP XEP SAN PHAM -----");
        System.out.println("1. Sap xep theo gia (tang dan)");
        System.out.println("2. Sap xep theo gia (giam dan)");
        System.out.println("3. Sap xep theo ten");
        System.out.println("4. Sap xep theo ngay nhap");
        System.out.print("Chon cach sap xep: ");
        
        int luaChon = scanner.nextInt();
        switch (luaChon) {
            case 1:
                danhSachSP.sapXepTheoGia(true);
                break;
            case 2:
                danhSachSP.sapXepTheoGia(false);
                break;
            case 3:
                danhSachSP.sapXepTheoTen();
                break;
            case 4:
                danhSachSP.sapXepTheoNgayNhap();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }
        System.out.println("Đã sắp xếp xong! Danh sách sau khi sắp xếp:");
        hienThiDanhSach();
    }

    private void menuFile() {
        System.out.println("\n----- QUẢN LÝ FILE -----");
        System.out.println("1. Đọc từ file");
        System.out.println("2. Ghi vào file");
        System.out.print("Chọn chức năng: ");
        
        int luaChon = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nhập tên file: ");
        String tenFile = scanner.nextLine();
        
        switch (luaChon) {
            case 1:
                danhSachSP.docFile(tenFile);
                System.out.println("Đọc file thành công!");
                break;
            case 2:
                danhSachSP.ghiFile(tenFile);
                System.out.println("Ghi file thành công!");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void menuGoiY() {
        System.out.println("\n----- GOI Y SAN PHAM -----");
        System.out.println("1. Goi y san pham thay the");
        System.out.println("2. Goi y phu kien tuong thich");
        System.out.print("Chon chuc nang: ");
        
        int luaChon = scanner.nextInt();
        scanner.nextLine();
        
        switch (luaChon) {
            case 1:
                System.out.print("Nhập mã sản phẩm: ");
                String maSP = scanner.nextLine();
                SanPham sp = danhSachSP.timTheoMa(maSP);
                if (sp != null) {
                    List<SanPham> goiY = danhSachSP.goiYThayThe(sp);
                    if (goiY.isEmpty()) {
                        System.out.println("Không có sản phẩm thay thế phù hợp!");
                    } else {
                        System.out.println("Các sản phẩm thay thế gợi ý:");
                        goiY.forEach(System.out::println);
                    }
                } else {
                    System.out.println("Không tìm thấy sản phẩm!");
                }
                break;
            case 2:
                System.out.print("Nhập mã điện thoại: ");
                String maDT = scanner.nextLine();
                SanPham dt = danhSachSP.timTheoMa(maDT);
                if (dt instanceof DienThoai) {
                    List<PhuKien> phuKien = danhSachSP.goiYPhuKienTuongThich((DienThoai) dt);
                    if (phuKien.isEmpty()) {
                        System.out.println("Không có phụ kiện tương thích!");
                    } else {
                        System.out.println("Các phụ kiện tương thích:");
                        phuKien.forEach(System.out::println);
                    }
                } else {
                    System.out.println("Sản phẩm không phải là điện thoại!");
                }
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
}