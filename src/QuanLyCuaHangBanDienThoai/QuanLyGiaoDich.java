package quanlygiaodich;
import java.util.Date;
import java.util.Scanner;

public class QuanLyGiaoDich {
    private DanhSachGiaoDich dsgd;
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "giaodich.txt";

    public QuanLyGiaoDich() {
        dsgd = new DanhSachGiaoDich();
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
                    dsgd.docFile(fileName);
                    break;
                case 7:
                    dsgd.ghiFile(fileName);
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
                HoaDonBan hdb = new HoaDonBan(maGD, ngayGD, nguoiThucHien, maKH);
                
                while (true) {
                    System.out.print("Thêm sản phẩm vào hóa đơn? (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("n")) break;

                    System.out.print("  - Mã SP: ");
                    String maSP = scanner.nextLine();
                    System.out.print("  - Số lượng: ");
                    int sl = scanner.nextInt();
                    System.out.print("  - Đơn giá: ");
                    double gia = scanner.nextDouble();
                    scanner.nextLine();

                    hdb.themChiTiet(new ChiTietGiaoDich(maSP, sl, gia));
                }
                gd = hdb;
                break;

            case 2: // Phiếu nhập
                System.out.print("Nhập nhà cung cấp: ");
                String ncc = scanner.nextLine();
                System.out.print("Nhập số lượng tổng: ");
                int slNhap = scanner.nextInt();
                System.out.print("Nhập giá nhập (đơn giá): ");
                double giaNhap = scanner.nextDouble();
                scanner.nextLine();
                gd = new PhieuNhapHang(maGD, ngayGD, nguoiThucHien, ncc, slNhap, giaNhap);
                break;

            case 3: // Phiếu bảo hành
                System.out.print("Nhập mã sản phẩm bảo hành: ");
                String maSPBH = scanner.nextLine();
                System.out.print("Mô tả lỗi: ");
                String loi = scanner.nextLine();
                System.out.print("Phí sửa chữa: ");
                double phi = scanner.nextDouble();
                scanner.nextLine();
                gd = new PhieuBaoHanh(maGD, ngayGD, nguoiThucHien, maSPBH, loi, phi);
                break;

            default:
                System.out.println("Loại giao dịch không hợp lệ!");
                return;
        }

        if (gd != null) {
            dsgd.themGiaoDich(gd);
            System.out.println("Thêm giao dịch thành công! Mã GD: " + maGD);
        }
    }

    private void xoaGiaoDich() {
        System.out.print("Nhập mã giao dịch cần xóa: ");
        String maXoa = scanner.nextLine();
        System.out.println("Chức năng đang phát triển (Cần cập nhật DanhSachGiaoDich để hỗ trợ xóa).");
    }

    private void timKiemGiaoDich() {
        System.out.print("Nhập mã giao dịch cần tìm: ");
        String maTim = scanner.nextLine();
        System.out.println("Chức năng đang phát triển.");
    }

    private void suaGiaoDich() {
        System.out.print("Nhập mã giao dịch cần sửa: ");
        String maSua = scanner.nextLine();
        System.out.println("Chức năng đang phát triển.");
    }
}