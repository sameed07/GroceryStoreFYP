package Models;

public class UserModel {

    private String user_name;
    private String phone_number;
    private String user_password;
    private String user_email;

    public UserModel() {
    }

    public UserModel(String user_name, String phone_number, String user_password, String user_email) {
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.user_password = user_password;
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
