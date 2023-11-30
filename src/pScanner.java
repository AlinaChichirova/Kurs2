import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class pScanner implements Runnable{
    private String ipAddress;
    private int port;
    private List<Integer> openPorts;

    public pScanner(String ipAddress, int port, List<Integer> openPorts) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.openPorts = openPorts;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            Socket socket = new Socket(inetAddress, port);
            socket.close();
            openPorts.add(port);
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + ipAddress);
        } catch (IOException e) {
            // Порт закрыт
        }
    }
}

