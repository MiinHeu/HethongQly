package QuanLyCuaHangBanDienThoai;
import java.util.Date;
import java.util.Scanner;

public class QlyGiaoDich {
    private DanhSachGiaoDich dsgd;
    private QuanLyTrungTam qltt; // Liên kết với quản lý trung tâm
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "src/QuanLyCuaHangBanDienThoai/giaodich.txt";

    public QlyGiaoDich() {
        qltt = QuanLyTrungTam.getInstance();
        dsgd = qltt.getDanhSachGiaoDich();
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ GIAO DỊCH =====");
            System.out.println("1. Thêm Giao dịch mới");
            System.out.println("2. Sửa thông tin Giao dịch");
            System.out.println("3. Xóa Giao dịch");
            System.out.println("4. Tìm kiếm Giao dịch");
            System.out.println("5. Xem danh sách Giao dịch");
            System.out.println("6. Tải danh sách từ file");
            System.out.println("7. Xuất danh sách ra file");
            System.out.println("8. Quay trở về giao diện chính");
            System.out.print("Chọn chức năng: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Vui lòng nhập một số nguyên!");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc dòng trống sau khi đọc số

            switch (choice) {
                case 1:
                    themGiaoDich();
                    break;
                case 2:
                    suaGiaoDich();
                    break;
                case 3:
                    xoaGiaoDich();
                    break;
                case 4:
                    timKiemGiaoDich();
                    break;
                case 5:
                    System.out.println("\n--- DANH SÁCH GIAO DỊCH ---");
                    dsgd.xuatTatCa();
                    break;
                case 6:
                    qltt.taiDuLieuTuFile();
                    break;
                case 7:
                    qltt.luuDuLieuRaFile();
                    break;
                case 8:
                    System.out.println("Đã quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 8);
    }

    private void themGiaoDich() {
        System.out.println("\n--- CHỌN LOẠI GIAO DỊCH ---");
        System.out.println("1. Hóa đơn bán hàng");
        System.out.println("2. Phiếu nhập hàng");
        System.out.println("3. Phiếu bảo hành");
        System.out.print("Chọn loại: ");
        int loai = scanner.nextInt();
        scanner.nextLine();

        String maGD = GiaoDichHelper.sinhMaHoaDon();
        Date ngayGD = new Date();
        
        System.out.print("Nhập tên người thực hiện: ");
        String nguoiThucHien = scanner.nextLine();

        GiaoDich gd = null;

        switch (loai) {
            case 1: // Hóa đơn bán
                System.out.print("Nhập mã khách hàng: ");
                String maKH = scanner.nextLine();
                
                // Kiểm tra khách hàng có tồn tại không
                KhachHang kh = qltt.getDanhSachKhachHang().timTheoMa(maKH);
                if (kh == null) {
                    System.out.println("Lỗi: Không tìm thấy khách hàng với mã: " + maKH);
                    System.out.println("Vui lòng thêm khách hàng trước khi tạo hóa đơn.");
                    return;
                }
                
                System.out.println("Khách hàng: " + kh.getTen() + " - Hạng: " + kh.getHangThanhVien());
                
                HoaDonBan hdb = new HoaDonBan(maGD, ngayGD, nguoiThucHien, maKH);
                
                while (true) {
                    System.out.print("Thêm sản phẩm vào hóa đơn? (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("n")) break;

                    System.out.print("  - Mã SP: ");
                    String maSP = scanner.nextLine();
                    
                    // Kiểm tra sản phẩm có tồn tại không
                    SanPham sp = qltt.getDanhSachSanPham().timTheoMa(maSP);
                    if (sp == null) {
                        System.out.println("  Lỗi: Không tìm thấy sản phẩm với mã: " + maSP);
                        continue;
                    }
                    
                    System.out.println("  Sản phẩm: " + sp.getTenSP() + " - Giá: " + 
                        GiaoDichHelper.formatTien(sp.getGiaBan()) + " - Tồn kho: " + sp.getSoLuongTon());
                    
                    System.out.print("  - Số lượng: ");
                    int sl = scanner.nextInt();
                    scanner.nextLine();
                    
                    // Kiểm tra số lượng tồn kho
                    if (sl > sp.getSoLuongTon()) {
                        System.out.println("  Lỗi: Số lượng tồn kho không đủ! Chỉ còn " + sp.getSoLuongTon());
                        continue;
                    }
                    
                    if (sl <= 0) {
                        System.out.println("  Lỗi: Số lượng phải lớn hơn 0!");
                        continue;
                    }

                    // Lấy đơn giá từ sản phẩm (chiết khấu sẽ tính ở tổng tiền)
                    double donGia = sp.getGiaBan();
                    
                    System.out.println("  Đơn giá: " + GiaoDichHelper.formatTien(donGia));

                    hdb.themChiTiet(new ChiTietGiaoDich(maSP, sl, donGia));
                }
                
                // Kiểm tra hóa đơn có sản phẩm không
                if (hdb.getDanhSachChiTiet().isEmpty()) {
                    System.out.println("Hóa đơn không có sản phẩm nào. Hủy tạo hóa đơn.");
                    return;
                }
                
                // Xử lý bán hàng (liên kết các module)
                if (qltt.xuLyBanHang(hdb)) {
                    gd = hdb;
                    System.out.println("Tạo hóa đơn thành công! Mã GD: " + maGD);
                } else {
                    System.out.println("Lỗi khi xử lý bán hàng. Hủy tạo hóa đơn.");
                    return;
                }
                break;

            case 2: // Phiếu nhập
                System.out.print("Nhập mã nhà cung cấp: ");
                String maNCC = scanner.nextLine();
                
                // Kiểm tra nhà cung cấp có tồn tại không
                NhaCungCap ncc = qltt.getDanhSachNhaCungCap().timTheoMa(maNCC);
                if (ncc == null) {
                    var dsNCC = qltt.getDanhSachNhaCungCap().timTheoTen(maNCC);
                    if (dsNCC.isEmpty()) {
                        System.out.println("Lỗi: Không tìm thấy nhà cung cấp: " + maNCC);
                        System.out.println("Vui lòng thêm nhà cung cấp trước khi nhập hàng.");
                        return;
                    }
                    ncc = dsNCC.get(0);
                    maNCC = ncc.getMaNhaCungCap();
                }
                
                System.out.println("Nhà cung cấp: " + ncc.getTenNhaCungCap() + 
                    " - Độ tin cậy: " + ncc.getDoTinCay() + "%");
                
                System.out.print("Nhập mã sản phẩm: ");
                String maSPNhap = scanner.nextLine();
                System.out.print("Nhập tên sản phẩm: ");
                String tenSPNhap = scanner.nextLine();
                System.out.print("Nhập hãng sản xuất: ");
                String hangSXNhap = scanner.nextLine();
                System.out.print("Nhập giá bán: ");
                double giaBanNhap = scanner.nextDouble();
                System.out.print("Nhập số lượng: ");
                int slNhap = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nhập giá nhập (đơn giá): ");
                double giaNhap = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Nhập ngày nhập (dd/MM/yyyy): ");
                String ngayNhapStr = scanner.nextLine();
                System.out.print("Nhập loại sản phẩm (Điện thoại/Phụ kiện/Linh kiện): ");
                String loaiSPNhap = scanner.nextLine();
                
                if (slNhap <= 0 || giaNhap <= 0 || giaBanNhap <= 0) {
                    System.out.println("Lỗi: Số lượng và giá phải lớn hơn 0!");
                    return;
                }
                
                PhieuNhapHang pnh = new PhieuNhapHang(maGD, ngayGD, nguoiThucHien, maNCC, slNhap, giaNhap);
                
                // Xử lý nhập hàng (liên kết các module)
                if (qltt.xuLyNhapHang(pnh, maSPNhap, tenSPNhap, giaBanNhap, hangSXNhap, ngayNhapStr, loaiSPNhap)) {
                    gd = pnh;
                    System.out.println("Tạo phiếu nhập hàng thành công! Mã GD: " + maGD);
                } else {
                    System.out.println("Lỗi khi xử lý nhập hàng. Hủy tạo phiếu nhập.");
                    return;
                }
                break;

            case 3: // Phiếu bảo hành
                System.out.print("Nhập mã sản phẩm bảo hành: ");
                String maSPBH = scanner.nextLine();
                
                // Kiểm tra sản phẩm có tồn tại không
                SanPham spBH = qltt.getDanhSachSanPham().timTheoMa(maSPBH);
                if (spBH == null) {
                    System.out.println("Lỗi: Không tìm thấy sản phẩm với mã: " + maSPBH);
                    return;
                }
                
                System.out.println("Sản phẩm: " + spBH.getTenSP() + " - Hãng: " + spBH.getHangSX());
                System.out.print("Mô tả lỗi: ");
                String loi = scanner.nextLine();
                System.out.print("Phí sửa chữa: ");
                double phi = scanner.nextDouble();
                scanner.nextLine();
                
                if (phi < 0) {
                    System.out.println("Lỗi: Phí sửa chữa không được âm!");
                    return;
                }
                
                gd = new PhieuBaoHanh(maGD, ngayGD, nguoiThucHien, maSPBH, loi, phi);
                dsgd.themGiaoDich(gd);
                System.out.println("Tạo phiếu bảo hành thành công! Mã GD: " + maGD);
                return;

            default:
                System.out.println("Loại giao dịch không hợp lệ!");
                return;
        }

        // Giao dịch đã được thêm trong các phương thức xử lý riêng
    }

    private void xoaGiaoDich() {
        System.out.print("Nhập mã giao dịch cần xóa: ");
        String maXoa = scanner.nextLine();
        if (dsgd.xoaGiaoDich(maXoa)) {
            System.out.println("Xóa giao dịch thành công!");
        } else {
            System.out.println("Không tìm thấy giao dịch với mã: " + maXoa);
        }
    }

    private void timKiemGiaoDich() {
        System.out.println("\n--- TÌM KIẾM GIAO DỊCH ---");
        System.out.println("1. Tìm theo mã giao dịch");
        System.out.println("2. Tìm theo ngày");
        System.out.print("Chọn: ");
        int chon = scanner.nextInt();
        scanner.nextLine();
        
        switch (chon) {
            case 1:
                System.out.print("Nhập mã giao dịch: ");
                String maTim = scanner.nextLine();
                GiaoDich gd = dsgd.timTheoMa(maTim);
                if (gd != null) {
                    gd.xuatThongTin();
                } else {
                    System.out.println("Không tìm thấy giao dịch!");
                }
                break;
            case 2:
                System.out.print("Nhập ngày (dd/MM/yyyy): ");
                String ngayStr = scanner.nextLine();
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date ngay = sdf.parse(ngayStr);
                    java.util.List<GiaoDich> ketQua = dsgd.timTheoNgay(ngay);
                    if (ketQua.isEmpty()) {
                        System.out.println("Không có giao dịch nào trong ngày này!");
                    } else {
                        System.out.println("Tìm thấy " + ketQua.size() + " giao dịch:");
                        for (GiaoDich g : ketQua) {
                            g.xuatThongTin();
                            System.out.println();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Ngày không hợp lệ!");
                }
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void suaGiaoDich() {
        System.out.print("Nhập mã giao dịch cần sửa: ");
        String maSua = scanner.nextLine();
        GiaoDich gdCu = dsgd.timTheoMa(maSua);
        if (gdCu == null) {
            System.out.println("Không tìm thấy giao dịch!");
            return;
        }
        
        System.out.println("Thông tin giao dịch hiện tại:");
        gdCu.xuatThongTin();
        System.out.println("\nVui lòng tạo giao dịch mới để thay thế:");
        themGiaoDich();
        System.out.println("Lưu ý: Giao dịch mới sẽ có mã khác. Để sửa đúng, vui lòng xóa giao dịch cũ và tạo mới.");
    }
}