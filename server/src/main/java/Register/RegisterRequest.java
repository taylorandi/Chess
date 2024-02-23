package Register;

public class RegisterRequest {
    private static String userName;
    private static String password;
    private static String email;

    public RegisterRequest(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }
}
