package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Ciprian on 23-Apr-16.
 */
public class ATMServer {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");

        final int port = 63000;
        Generator generator = new Generator();
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind("ATMManager", generator);

        System.out.println("Server is bound.");
    }
}
