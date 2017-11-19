import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Empyreans on 29.10.2017.
 */
public class CSVParser {

    ArrayList<Day> availableDays = new ArrayList<>();

    public CSVParser(String fileName) {
        try (FileReader fileReader = new FileReader(fileName)) {
            parseCSVFile(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseCSVFile(FileReader fileReader) {

        CSVReader reader = new CSVReaderBuilder(fileReader).build();
        Day tempDay;
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) { // holt sich die nächste Zeile der .csv

                // Tag bereits vorhanden
                if ((tempDay = dayAvailabe(nextLine[0])) != null){
                    tempDay.addWeatherData(nextLine[1], nextLine[2]);
                }

                // Tag nicht vorhanden
                if (dayAvailabe(nextLine[0]) == null){
                    tempDay = new Day(nextLine[0], nextLine[1], nextLine[2]);
                    availableDays.add(tempDay);
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String printDayWeatherData(String dayString) {
        Day day = dayAvailabe(dayString);
        if (day != null){
            return day.getDayWeatherData();
        }
        return null;
    }

    public int countAvailableWeatherDataForDay(String dayString){
        Day day = dayAvailabe(dayString);
        if (day != null){
            return day.getWeatherDataList().size();
        } else {
            return 0;
        }
    }

    // durchsucht die verfuegbaren Tage nach dem gefragten Tag und gibt diesen bei Vorhandenheit zurück
    public Day dayAvailabe(String day){
        for (Day d:availableDays){
            if (d.getDate().equals(day)){
                return d;
            }
        }
        return null;
    }

}
