package itmo.lab6.server;

import java.util.Scanner;

/**
 * Поток для считывания команды exit
 */
public final class ExitThread extends Thread {
    public ExitThread() {
        super(() -> {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().contains("exit")) {
                System.exit(0);
            }
        });
    }
}
