package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Ciprian on 23-Apr-16.
 */
public interface ATMManager extends Remote {
    void deposit(int amount) throws RemoteException;

    boolean withdraw(int amount) throws RemoteException;

    int inquiry() throws RemoteException;

    void terminate() throws RemoteException;
}
