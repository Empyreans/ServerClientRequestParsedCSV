import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Empyreans on 31.10.2017.
 */
public class Server {

    private final int port = 4444;
    private CSVParser csvParser;

    public static void main(String[] args) {
        new Server("tempdata.CSV");
    }

    public Server(String fileName){
        initializeParser(fileName);
        initializeServer(port);
    }

    public void initializeParser(String filename){
        csvParser = new CSVParser(filename);
    }

    public void initializeServer(int port){
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Der Server ist bereit Clientanfragen anzunehmen . . .");
            out.println("Bitte geben sie ein Datum in folgendem Format ein: tt.mm.jjjj");

            String clientMessage;

            while ((clientMessage = in.readLine()) != null){
                out.println(serverHandle(clientMessage));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serverHandle(String clientMessage) {
        if (csvParser.dayAvailabe(clientMessage) != null){
            if (csvParser.countAvailableWeatherDataForDay(clientMessage) != 24) {
                return "keine 24 Wetterdaten fuer den Tag vorhanden!";
            } else {
                return csvParser.printDayWeatherData(clientMessage);
            }
        } else {
            return "Tag nicht vorhanden";
        }
    }

}
