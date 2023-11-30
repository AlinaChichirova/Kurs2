import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class PortScanner implements Runnable {
    private String ipAddress;
    private int port;
    private List<Integer> openPorts;

    public PortScanner(String ipAddress, int port, List<Integer> openPorts) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.openPorts = openPorts;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            Socket socket = new Socket(inetAddress, port);
            openPorts.add(port);
            socket.close();

        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + ipAddress);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}