import java.util.Date;

public abstract class ThucTheKho {
    protected String maThucThe;
    protected String viTriKe;
    protected int soLuong;
    protected String trangThai;
    protected Date ngayNhapKho;
    protected String ghiChu;



public ThucTheKho(){
    this.trangThai = trangThai;
    this.ngayNhapKho = new Date();
    this.soLuong = 0;
}

public ThucTheKho(String maThucThe, String viTriKe, int soLuong, String trangThai){
    this.maThucThe = maThucThe;
    this.viTriKe = viTriKe;
    this.soLuong = soLuong;
    this.trangThai = trangThai;
    this.ngayNhapKho = new Date();
}

public abstract String layThongTinChiTiet();
public abstract double tinhGiaTriTonKho();
public abstract boolean kiemTraCanCanhBao();
//public abstract String layLoaiThucThe();


    // Getters and Setters
    public String getMaThucThe() { return maThucThe; }
    public void setMaThucThe(String maThucThe) { this.maThucThe = maThucThe; }

    public String getViTriKe() { return viTriKe; }
    public void setViTriKe(String viTriKe) { this.viTriKe = viTriKe; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            System.out.println("Số lượng không thể âm");
        }
        this.soLuong = soLuong;
    }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public Date getNgayNhapKho() { return ngayNhapKho; }
    public void setNgayNhapKho(Date ngayNhapKho) { this.ngayNhapKho = ngayNhapKho; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    // Business methods
    public void themSoLuong(int soLuong) {
        if (soLuong <= 0) {
            System.out.println("Số lượng thêm phải > 0");
        }
        this.soLuong += soLuong;
    }

    public void giamSoLuong(int soLuong) {
        if (soLuong <= 0) {
            System.out.println("Số lượng giảm phải > 0");
        }
        if (this.soLuong < soLuong) {
            System.out.println("Không đủ số lượng trong kho");
        }
        this.soLuong -= soLuong;
    }

    public int tinhSoNgayTonKho() {
        long diff = new Date().getTime() - ngayNhapKho.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    @Override
    public String toString() {
        return String.format("Mã: %s | Vị trí: %s | SL: %d | Trạng thái: %s | Ngày nhập: %s",
                maThucThe, viTriKe, soLuong, trangThai,
                KhoHelper.formatDate(ngayNhapKho));
    }

    public void setTenSanPham(String datum) {
    }

    public void setSerial(String datum) {
    }
}