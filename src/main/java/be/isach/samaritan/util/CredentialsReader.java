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
            for (int i = 0; i < 2; i++)
                if (scanner.hasNextLine())
                    switch (i) {
                        case 0:
                            email = scanner.nextLine();
                            break;
                        case 1:
                            password = scanner.nextLine();
                            break;
                    }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Credentials(email, password);
    }

}
