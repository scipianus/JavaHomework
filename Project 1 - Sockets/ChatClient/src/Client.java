import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

class Client {
    private static Socket socket;
    private static InputThread inputThread;
    private static OutputThread outputThread;
    private static boolean connected;

    public static void main(String[] args) {
        try {
            String serverAddress = JOptionPane.showInputDialog("Enter IP (running on port 9090)");
            socket = new Socket(serverAddress, 9090);
            connected = true;
            inputThread = new InputThread(socket);
            inputThread.start();
            outputThread = new OutputThread(socket);
            outputThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public synchronized static Message parseMessage(String msgText) {
        final String[] tokens = msgText.split(" ");
        Message msg = new Message();

        if (tokens[0].compareToIgnoreCase("disconnect") == 0) {
            msg.setType(Message.MsgType.MSG_DISCONNECT);
        } else if (tokens[0].compareToIgnoreCase("list") == 0) {
            msg.setType(Message.MsgType.MSG_LIST);
        } else if (tokens[0].compareToIgnoreCase("talk") == 0 && tokens.length >= 2) {
            msg.setType(Message.MsgType.MSG_TALK);
            msg.setReceiver(tokens[1]);
            int position = msgText.indexOf(tokens[1]) + tokens[1].length();
            String content = msgText.substring(position);
            msg.setMessage(content.trim());
        } else if (tokens[0].compareToIgnoreCase("broadcast") == 0) {
            msg.setType(Message.MsgType.MSG_BROADCAST);
            int position = msgText.indexOf(tokens[0]) + tokens[0].length();
            String content = msgText.substring(position);
            msg.setMessage(content.trim());
        }

        return msg;
    }

    public synchronized static void printResponse(ServerResponse response) {
        switch (response.getInitialMessage().getType()) {
            case MSG_LIST: {
                List<String> list = (List<String>) response.getResponse();
                System.out.println("These clients are connected");
                for (String nickname : list) {
                    System.out.println(nickname);
                }
            }
            break;
            default: {
                System.out.println(response.getResponse());
            }
            break;
        }
    }

    public synchronized static void disconnect() {
        connected = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            System.exit(0);
        }
    }

    public synchronized static boolean isConnected() {
        return connected;
    }
}