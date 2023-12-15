package ui;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\uD83D\uDC51 Welcome to 240 chess. Type Help to get started. \uD83D\uDC51");
        String curr = "[LOGGED_OUT] >>> ";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(curr);
            String response = scanner.next();
            if (Objects.equals(response.toLowerCase(), "help")) {
                PreLoginUI preLoginUI = new PreLoginUI();
                preLoginUI.help();
                break;
            } else {
                System.out.println("invalid command\n");
            }
        }
    }
}
