package be.isach.samaritan.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Sacha on 6/01/16.
 */
public class CredentialsReader {

    public Credentials getCredentials() {
        String email = "", password = "";
        File file = new File("credentials.txt");

        try (Scanner scanner = new Scanner(file)) {
            email = scanner.hasNextLine() ? scanner.nextLine() : "unknown";
            password = scanner.hasNextLine() ? scanner.nextLine() : "unknown";
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Credentials(email, password);
    }

}
