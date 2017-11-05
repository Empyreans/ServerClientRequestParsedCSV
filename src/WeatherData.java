/**
 * Created by Empyreans on 29.10.2017.
 */
public class WeatherData {
    private String time;
    private String celsius;

    public WeatherData(String time, String celsius){
        this.time = time;
        this.celsius = celsius;
    }

    public String getTime() {
        return time;
    }

    public String getCelsius() {
        return celsius;
    }
}
