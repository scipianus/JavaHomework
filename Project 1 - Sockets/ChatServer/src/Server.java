import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Server {
    private static ServerSocket myServerSocket;
    private static List<ClientHandler> clientList;
    private static Map<String, ClientHandler> nicknames;

    public static void main(String[] args) {
        try {
            myServerSocket = new ServerSocket(9090);
            clientList = new ArrayList<>();
            nicknames = new HashMap<>();

            while (true) {
                Socket socket = myServerSocket.accept();
                ClientHandler ch = new ClientHandler(socket);
                addClient(ch);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public synchronized static void removeClient(ClientHandler ch) {
        clientList.remove(ch);
        if (userExists(ch.getNickname()))
            nicknames.remove(ch.getNickname());
    }

    public synchronized static void addClient(ClientHandler ch) {
        clientList.add(ch);
    }

    public synchronized static List<String> listClients() {
        List<String> result = new ArrayList<>();
        for (ClientHandler clientHandler : clientList) result.add(clientHandler.getNickname());
        return result;
    }

    public synchronized static boolean userExists(String nickname) {
        return (nicknames.containsKey(nickname));
    }

    public synchronized static void sendMessageToUser(String nickname, ServerResponse response) {
        ClientHandler clientHandler = nicknames.get(nickname);
        clientHandler.sendMessage(response);
    }

    public synchronized static void sendMessageToAll(ServerResponse response) {
        for (ClientHandler clientHandler : clientList) {
            clientHandler.sendMessage(response);
        }
    }

    public synchronized static void registerNickname(ClientHandler client) {
        nicknames.put(client.getNickname(), client);
    }
}
