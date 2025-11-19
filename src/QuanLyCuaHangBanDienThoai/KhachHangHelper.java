package QuanLyCuaHangBanDienThoai;

class KhachHangHelper {
    private static int dem = 0;

    public static String sinhMaKhachHang() {
        dem++;
        return String.format("KH%03d", dem);
    }

    public static boolean validateSDT(String sdt) {
        return sdt.matches("\\d{10,11}");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$");
    }
}