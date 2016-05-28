import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler {
    private Socket socket;

    private ObjectOutputStream streamToClient;
    private ObjectInputStream streamFromClient;

    private InputThread inputThread;
    private OutputThread outputThread;

    private String IPAddress;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        IPAddress = socket.getInetAddress().getHostAddress();
        try {
            streamFromClient = new ObjectInputStream(socket.getInputStream());
            streamToClient = new ObjectOutputStream(socket.getOutputStream());
            streamToClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        inputThread = new InputThread(this);
        inputThread.start();
        outputThread = new OutputThread(this);
        outputThread.start();
    }

    public synchronized Socket getSocket() {
        return socket;
    }

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    public synchronized String getNickname() {
        return nickname;
    }

    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
        Server.registerNickname(this);
    }

    public synchronized String getIPAddress() {
        return IPAddress;
    }

    public synchronized void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public synchronized ObjectInputStream getStreamFromClient() {
        return streamFromClient;
    }

    public synchronized void setStreamFromClient(ObjectInputStream streamFromClient) {
        this.streamFromClient = streamFromClient;
    }

    public synchronized ObjectOutputStream getStreamToClient() {
        return streamToClient;
    }

    public synchronized void setStreamToClient(ObjectOutputStream stramToClient) {
        this.streamToClient = stramToClient;
    }

    public synchronized void sendMessage(ServerResponse response) {
        outputThread.setResponse(response);
    }
}
