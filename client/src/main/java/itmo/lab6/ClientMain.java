package itmo.lab6;

import itmo.lab6.core.ClientCore;

public class ClientMain {
    public static void main(String[] args) {
        ClientCore core = new ClientCore(5050);
        core.run();
    }
}
