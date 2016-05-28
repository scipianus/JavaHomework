import javax.swing.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

class OutputThread extends Thread {
    private Socket socket;

    public OutputThread(Socket socket) {
        this.socket = socket;
    }

    private OutputThread() {

    }

    public void run() {
        try {
            ObjectOutputStream streamToServer = new ObjectOutputStream(socket.getOutputStream());
            Message message;
            Scanner scan = new Scanner(System.in);

            String nickname = JOptionPane.showInputDialog("Enter nickname");
            message = new Message();
            message.setType(Message.MsgType.MSG_NICKNAME);
            message.setMessage(nickname);
            streamToServer.writeObject(message);

            while (Client.isConnected()) {
                String msgText = scan.nextLine();
                message = Client.parseMessage(msgText);

                if (message.getType() == Message.MsgType.MSG_INVALID) {
                    System.out.println("Incorrect message format, try again");
                } else {
                    streamToServer.writeObject(message);
                    streamToServer.flush();
                }
            }
            streamToServer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
