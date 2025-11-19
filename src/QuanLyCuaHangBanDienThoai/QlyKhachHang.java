package QuanLyCuaHangBanDienThoai;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class QlyKhachHang {
    private DanhSachKhachHang dskh;
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "KhachHang.txt";

    public QlyKhachHang() {
        dskh = new DanhSachKhachHang();
    }

    private LocalDate nhapNgaySinh() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
                return LocalDate.parse(scanner.nextLine(), df);
            } catch (Exception e) {
                System.out.println("Ngày sinh không hợp lệ!");
            }
        }
    }

    private KhachHang nhapThongTinKhachHang() {
        String ma = KhachHangHelper.sinhMaKhachHang();

        System.out.print("Tên khách: ");
        String ten = scanner.nextLine();

        String sdt;
        do {
            System.out.print("Số điện thoại: ");
            sdt = scanner.nextLine();
        } while (!KhachHangHelper.validateSDT(sdt));

        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
        } while (!KhachHangHelper.validateEmail(email));

        LocalDate ngaySinh = nhapNgaySinh();

        System.out.print("Điểm tích lũy: ");
        int diem = Integer.parseInt(scanner.nextLine());

        System.out.print("Hạng thành viên (Thuong/Vang/VIP): ");
        String hang = scanner.nextLine();

        return new KhachHang(ma, ten, sdt, email, ngaySinh, diem, hang, new ArrayList<>());
    }

    public void menu() {
        int choice;

        do {
            System.out.println("=============== QUẢN LÝ KHÁCH HÀNG ===============");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Sửa thông tin khách hàng");
            System.out.println("3. Xóa khách hàng");
            System.out.println("4. Tìm kiếm khách hàng");
            System.out.println("5. Xem danh sách khách hàng");
            System.out.println("6. Tải danh sách từ file");
            System.out.println("7. Xuất danh sách ra file");
            System.out.println("8. Quay về menu chính");
            System.out.print("Chọn: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    KhachHang kh = nhapThongTinKhachHang();
                    dskh.them(kh);
                    System.out.println("Thêm khách hàng thành công!");
                    break;

                case 2:
                    System.out.print("Nhập mã KH cần sửa: ");
                    String maSua = scanner.nextLine();
                    KhachHang kSua = dskh.timTheoMa(maSua);
                    if (kSua == null) {
                        System.out.println("Không tìm thấy khách hàng!");
                    } else {
                        System.out.println("Nhập thông tin mới:");
                        KhachHang khMoi = nhapThongTinKhachHang();
                        dskh.xoa(maSua);
                        dskh.them(khMoi);
                        System.out.println("Sửa thành công!");
                    }
                    break;

                case 3:
                    System.out.print("Nhập mã KH cần xóa: ");
                    String maXoa = scanner.nextLine();
                    dskh.xoa(maXoa);
                    System.out.println("Đã xóa nếu tồn tại!");
                    break;

                case 4:
                    System.out.println("Nhập tên khách cần tìm: ");
                    String tenTK = scanner.nextLine();
                    List<KhachHang> kq = dskh.timTheoTen(tenTK);
                    if (kq.isEmpty()) System.out.println("Không tìm thấy!");
                    else kq.forEach(KhachHang::xuat);
                    break;

                case 5:
                    dskh.xuat();
                    break;

                case 6:
                    try {
                        dskh.docFile(fileName);
                        System.out.println("Tải file thành công!");
                    } catch (Exception e) {
                        System.out.println("Lỗi đọc file!");
                    }
                    break;

                case 7:
                    try {
                        dskh.ghiFile(fileName);
                        System.out.println("Xuất file thành công!");
                    } catch (Exception e) {
                        System.out.println("Lỗi ghi file!");
                    }
                    break;

                case 8:
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 8);
    }
}