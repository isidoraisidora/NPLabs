package NP.zadachiZaVezhbanjeK1;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Racer{
    private final String name;
    private List<LocalTime> times = new ArrayList<>();

    public Racer(String name, List<LocalTime> times) {
        this.name = name;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public LocalTime returnShortest(){
        return times.stream().min(LocalTime::compareTo).orElse(null);
    }
}

class F1Race{
    private final List<Racer> racers;

    public F1Race() {
        this.racers = new ArrayList<>();
    }

    public LocalTime time(String s) {
        String[] parts = s.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int millis = Integer.parseInt(parts[2]);
        return LocalTime.of(0,minutes,seconds,millis*1_000_000);
    }

    public void readResults(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line=reader.readLine())!=null){
            String[] parts = line.split("\\s+");
            String name = parts[0];
            List<LocalTime> times = new ArrayList<>();
            for(int i=1; i<parts.length; i++){
                LocalTime lt = time(parts[i]);
                times.add(lt);
            }
            Racer r = new Racer(name, times);
            racers.add(r);
        }
    }

    public void printSorted(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
        DateTimeFormatter out = DateTimeFormatter.ofPattern("m:ss:SSS");
        List<Racer> newList = racers.stream().sorted(Comparator.comparing(Racer::returnShortest)).collect(Collectors.toList());
        for(int i=0; i<newList.size(); i++){
            pw.printf("%d. %-10s%10s\n",
                    i+1, newList.get(i).getName(), newList.get(i).returnShortest().format(out));
        }
        pw.flush();
    }
}

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

