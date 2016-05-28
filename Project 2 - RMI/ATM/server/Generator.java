package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ciprian on 24-Apr-16.
 */

public class Generator extends UnicastRemoteObject implements service.Generator {

    private int serverCount = 0;

    Generator() throws RemoteException {
        super();
    }

    @Override
    public ATMManager getMyServer() throws RemoteException {
        return new ATMManager(serverCount++);
    }

}