import java.io.ObjectInputStream;
import java.net.Socket;

class InputThread extends Thread {

    private Socket socket;

    public InputThread(Socket socket) {
        this.socket = socket;
    }

    private InputThread() {

    }

    public void run() {
        try {
            ObjectInputStream streamFromServer = new ObjectInputStream(socket.getInputStream());
            ServerResponse response;
            while (Client.isConnected() && (response = (ServerResponse) streamFromServer.readObject()) != null) {
                Client.printResponse(response);
                if (response.getInitialMessage().getType() == Message.MsgType.MSG_DISCONNECT)
                    Client.disconnect();
            }
            streamFromServer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
