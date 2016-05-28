package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Ciprian on 23-Apr-16.
 */
public class ATMClient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        final String IP = "localhost";
        final int port = 63000;
        Registry registry = LocateRegistry.getRegistry(IP, port);
        service.Generator srv = (service.Generator) registry.lookup("ATMManager");
        service.ATMManager atmManager = srv.getMyServer();

        System.out.println("Commands:");
        System.out.println("\tdeposit amount");
        System.out.println("\twithdraw amount");
        System.out.println("\tinquiry");
        System.out.println("\tterminate");

        boolean terminated = false;
        while (!terminated) {
            String request = sc.next();
            request = request.toLowerCase().trim();
            switch (request) {
                case "terminate": {
                    try {
                        atmManager.terminate();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    terminated = true;
                    break;
                }
                case "deposit": {
                    int amount = sc.nextInt();
                    try {
                        atmManager.deposit(amount);
                        System.out.println("Deposited " + amount + "$ into account");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "withdraw": {
                    int amount = sc.nextInt();
                    try {
                        boolean success = atmManager.withdraw(amount);
                        if (success)
                            System.out.println("Withdrawn " + amount + "$ from account");
                        else
                            System.out.println("Couldn't withdraw " + amount + "$ from account");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "inquiry": {
                    try {
                        int result = atmManager.inquiry();
                        System.out.println("Account contains " + result + "$");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        }
    }
}
