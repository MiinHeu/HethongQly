package QuanLyCuaHangBanDienThoai;

class KhachHangHelper {

    private static int stt = 1;

    public static String sinhMaKhachHang() {
        return String.format("KH%03d", stt++);
    }

    public static boolean validateSDT(String sdt) {
        return sdt.matches("0\\d{9}");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");
    }
}
