import java.util.List;

public interface IQuanLyKho {
    void nhapKho(ThucTheKho hangHoa, NhaCungCap ncc);
    void xuatKho(String maKho, int soLuong);
    List<ThucTheKho> canhBaoHetHang(int nguongToiThieu);
    List<ThucTheKho> canhBaoTonKhoLau(int soNgay);
    double tinhGiaTriTonKho();
    void kiemKho();
}