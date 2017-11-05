import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Empyreans on 29.10.2017.
 */
public class CSVParser {

    ArrayList<Day> availableDays = new ArrayList<>(); // HashMap<Day, ArrayList<WeatherData>> ?

    public CSVParser(String fileName) {
        try (FileReader fileReader = new FileReader(fileName)) {
            parseCSVFile(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseCSVFile(FileReader fileReader) {

        CSVReader reader = new CSVReaderBuilder(fileReader).build();
        Day tempDay = null; // nur am Anfang null
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) { // holt sich die nächste Zeile der .csv

                // Fall 1: Day bereits vorhanden
                if (tempDay != null && tempDay.getDate().equals(nextLine[0])) {
                    tempDay.addWeatherData(nextLine[1], nextLine[2]);
                }

                // Fall 2: leere Liste
                if (tempDay == null) {
                    tempDay = new Day(nextLine[0], nextLine[1], nextLine[2]);
                    availableDays.add(tempDay);
                }

                // Fall 3: Day noch nicht vorhanden
                if (!(tempDay.getDate().equals(nextLine[0]))) {
                    tempDay = new Day(nextLine[0], nextLine[1], nextLine[2]);
                    availableDays.add(tempDay);
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println(printDayWeatherData("28.10.2017"));
    }

    public String printDayWeatherData(String dayString) {
        Day day = dayAvailabe(dayString);
        if (day != null){
            return day.getDayWeatherData();
        }
        return null;
    }

    public Day dayAvailabe(String day){
        for (Day d:availableDays){
            if (d.getDate().equals(day)){
                return d;
            }
        }
        return null;
    }

}
