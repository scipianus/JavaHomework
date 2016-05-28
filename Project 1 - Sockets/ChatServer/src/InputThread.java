import java.io.ObjectInputStream;

class InputThread extends Thread {

    private ClientHandler client;
    private ObjectInputStream streamFromClient;

    public InputThread(ClientHandler client) {
        this.client = client;
        streamFromClient = client.getStreamFromClient();
    }

    private InputThread() {

    }

    public void run() {
        boolean stillConnected = true;
        try {
            while (stillConnected) {
                Message msg = (Message) streamFromClient.readObject();
                ServerResponse response = new ServerResponse();
                response.setInitialMessage(msg);
                switch (msg.getType()) {
                    case MSG_DISCONNECT: {
                        System.out.println(client.getIPAddress() + " '" + client.getNickname() + "' got disconnected..");
                        stillConnected = false;
                        response.setResponse("disconnected");
                        Server.sendMessageToUser(client.getNickname(), response);
                        Server.removeClient(client);
                    }
                    break;
                    case MSG_NICKNAME: {
                        client.setNickname(msg.getMessage());
                        response.setResponse("The nickname was set");
                        Server.sendMessageToUser(client.getNickname(), response);
                    }
                    break;
                    case MSG_LIST: {
                        response.setResponse(Server.listClients());
                        Server.sendMessageToUser(client.getNickname(), response);
                    }
                    break;
                    case MSG_INVALID: {
                        response.setResponse("Incorrect message format, try again");
                        Server.sendMessageToUser(client.getNickname(), response);
                    }
                    break;
                    case MSG_TALK: {
                        String receiver = msg.getReceiver();
                        if (Server.userExists(receiver)) {
                            response.setResponse(client.getNickname() + ": " + msg.getMessage());
                            Server.sendMessageToUser(receiver, response);
                        } else {
                            response.setResponse("User " + receiver + " is not connected");
                            Server.sendMessageToUser(client.getNickname(), response);
                        }
                    }
                    break;
                    case MSG_BROADCAST: {
                        response.setResponse(client.getNickname() + ": " + msg.getMessage());
                        Server.sendMessageToAll(response);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
