import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    private Socket socket = new Socket();
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    public boolean connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void send(String cmd) {
        outputWriter.println(cmd);
        outputWriter.flush();
    }

    public String read() {
        return inputScanner.nextLine();
    }

    public boolean close() {
        try {
            inputScanner.close();
            outputWriter.close();
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
