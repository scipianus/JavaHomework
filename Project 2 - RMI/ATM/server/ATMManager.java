package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Ciprian on 23-Apr-16.
 */
public class ATMManager extends UnicastRemoteObject implements service.ATMManager {
    private int id;
    private int accountAmount = 0;

    ATMManager(int id) throws RemoteException {
        super();

        this.id = id;
    }

    @Override
    public void deposit(int amount) throws RemoteException {
        accountAmount += amount;
    }

    @Override
    public boolean withdraw(int amount) throws RemoteException {
        if (accountAmount >= amount) {
            accountAmount -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int inquiry() throws RemoteException {
        return accountAmount;
    }

    @Override
    public void terminate() {
        System.out.println("The client " + id + " will disconnect");
    }
}
