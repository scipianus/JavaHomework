package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Ciprian on 24-Apr-16.
 */

public interface Generator extends Remote {
    public ATMManager getMyServer() throws RemoteException;
    
}