package NP.zadachiZaVezhbanjeK1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Measurement{
    private float temp;
    private float humidity;
    private float wind;
    private float visibility;
    private Date date;

    public Measurement(float temp, float humidity, float wind, float visibility, Date date) {
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.visibility = visibility;
        this.date = date;
    }

    public float getTemp() {
        return temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWind() {
        return wind;
    }

    public float getVisibility() {
        return visibility;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",
                temp,humidity,wind, visibility, sdf.format(date));
    }
}

class WeatherStation{
    private int days;
    private List<Measurement> measurements;


    public WeatherStation(int days){
        this.days = days;
        this.measurements = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        long newTime = date.getTime();
        for (Measurement t : measurements) {
            long diff = Math.abs(t.getDate().getTime() - newTime);
            if (diff <= 150000) {
                return;
            }
        }
        Measurement m = new Measurement(temperature, wind, humidity, visibility, date);
        measurements.add(m);
        long limit = newTime - (long) days * 24 * 60 * 60 * 1000;
        measurements.removeIf(meas -> meas.getDate().getTime() < limit);
    }

    public int total(){
        return measurements.size();
    }

    public void status(Date from, Date to){
        List<Measurement> mes = measurements.stream().filter(t->!t.getDate().before(from) && !t.getDate().after(to)).collect(Collectors.toList());
        if(mes.isEmpty())
            throw new RuntimeException();
        mes = mes.stream().sorted(Comparator.comparing(Measurement::getDate)).collect(Collectors.toList());
        double avg = mes.stream().mapToDouble(Measurement::getTemp).average().orElse(0);
        mes.forEach(m->System.out.println(m.toString()));
        System.out.printf("Average temperature: %.2f%n", avg);
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
