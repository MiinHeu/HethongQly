package QuanLyCuaHangBanDienThoai;

// Lớp trừu tượng đại diện cho thực thể trong kho
public abstract class ThucTheKho {
    protected String viTri;
    protected int soLuong;
    protected String trangThai;

    // Hàm thiết lập
    public  ThucTheKho(){
        this.soLuong = 0;
        this.trangThai = "Trạng thái";
        this.viTri = "A0";
    }
    public ThucTheKho(String viTri, int soLuong, String trangThai) {
        this.viTri = viTri;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }
    // Hàm trừu tượng - bắt buộc lớp con phải cài đặt
    public abstract String layThongTinChiTiet();
    public abstract double tinhGiaTri();

    // Getter và Setter
    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}