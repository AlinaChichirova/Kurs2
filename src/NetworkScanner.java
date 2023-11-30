import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkScanner {

    private static final int size = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите IP-адрес: ");
        String ipAddress = scanner.nextLine();

        List<Integer> openPorts = scanPorts(ipAddress);
        if (openPorts.isEmpty()) {
            System.out.println("На данном IP-адресе нет активных устройств.");
        } else {
            System.out.println("Активные устройства на IP-адресе " + ipAddress + ":");
            for (int port : openPorts) {
                System.out.println("  - Порт " + port + " открыт");
            }
        }

        System.out.print("Хотите сохранить результаты в файл? (txt/csv): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("txt")) {
            saveToFile(ipAddress, openPorts);
        }
        if (choice.equalsIgnoreCase("csv")) {
            saveToFile(ipAddress, openPorts);
        }

        scanner.close();
    }

    private static List<Integer> scanPorts(String ipAddress) {
        List<Integer> openPorts = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(size);
        for (int port = 1; port <= 65535; port++) {
            executor.execute(new PortScanner(ipAddress, port, openPorts));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        return openPorts;
    }

    private static void saveToFile(String ipAddress, List<Integer> openPorts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу сохранения: ");
        String filePath = scanner.nextLine();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Активные устройства на IP-адресе " + ipAddress + ":\n");
            for (int port : openPorts) {
                writer.write("  - Порт " + port + " открыт\n");
            }
            System.out.println("Результаты сохранены в файл " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла.");
        }
    }
}
