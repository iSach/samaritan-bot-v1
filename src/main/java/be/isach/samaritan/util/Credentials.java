package be.isach.samaritan.util;

/**
 * Created by Sacha on 6/01/16.
 */
public class Credentials {

    private String emailAddress, password;

    public Credentials(String emailAddress, String password) {
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
