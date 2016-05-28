import java.io.ObjectOutputStream;

class OutputThread extends Thread {
    private ClientHandler client;

    private ObjectOutputStream streamToClient;

    private ServerResponse response;

    public OutputThread(ClientHandler client) {
        this.client = client;
        streamToClient = client.getStreamToClient();
    }

    private OutputThread() {

    }

    public void run() {
        while (true) {
            sendResponse();
        }
    }

    private synchronized void sendResponse() {
        try {
            if (response == null)
                wait();
            streamToClient.writeObject(response);
            streamToClient.flush();
            response = null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ServerResponse getResponse() {
        return response;
    }

    public synchronized void setResponse(ServerResponse response) {
        this.response = response;
        if (response != null)
            notify();
    }
}
