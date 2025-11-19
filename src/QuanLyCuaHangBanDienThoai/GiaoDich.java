package QuanLyCuaHangBanDienThoai;

import java.util.Date;
public abstract class GiaoDich {
    protected String maGD;
    protected Date ngayGD;
    protected String nguoiThucHien;
    protected double tongTien;
    public GiaoDich() {}
    public GiaoDich(String maGD, Date ngayGD, String nguoiThucHien, double tongTien) {
        this.maGD = maGD;
        this.ngayGD = ngayGD;
        this.nguoiThucHien = nguoiThucHien;
        this.tongTien = tongTien;
    }
    public String getMaGD() { return maGD; }
    public void setMaGD(String maGD) { this.maGD = maGD; }
    public Date getNgayGD() { return ngayGD; }
    public void setNgayGD(Date ngayGD) { this.ngayGD = ngayGD; }
    public String getNguoiThucHien() { return nguoiThucHien; }
    public void setNguoiThucHien(String nguoiThucHien) { this.nguoiThucHien = nguoiThucHien; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public abstract void xuatThongTin();
}