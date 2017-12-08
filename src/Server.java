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

    private CSVParser csvParser;

    public Server(String fileName, int port){
        csvParser = new CSVParser(fileName);
        initializeServer(port);
    }

    private void initializeServer(int port){
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
            System.out.println("Client hat Verbindung abgebrochen, warte auf neue Verbindung");
        }
    }

    private String serverHandle(String clientMessage) {
        String parserResponse;
        if (csvParser.dayAvailabe(clientMessage) != null){
            if (csvParser.countAvailableWeatherDataForDay(clientMessage) != 24) {
                parserResponse = "keine 24 Wetterdaten für den Tag vorhanden";
            } else {
                parserResponse = csvParser.requestDayWeatherData(clientMessage);
            }
        } else {
            parserResponse = "Tag nicht vorhanden";
        }
        return parserResponse;
    }

}
