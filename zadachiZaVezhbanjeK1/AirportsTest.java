package NP.zadachiZaVezhbanjeK1;

import java.util.*;
import java.util.stream.Collectors;

class Flight{
    private String from;
    private String to;
    private int time;
    private int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }
    public String convertTime(){
        int startHours = time/60;
        int startMinutes = time%60;

        int endMinutesTotal = time + duration;
        int endHours = (endMinutesTotal / 60) % 24;
        int endMinutes = endMinutesTotal % 60;
        int durationHours = duration/60;
        int durationMinutes = duration%60;

        String label = ((time + duration) >= 24 * 60) ? " +1d" : "";

        return String.format("%02d:%02d-%02d:%02d%s %dh%02dm",startHours,startMinutes, endHours,endMinutes,label,durationHours,durationMinutes);
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s", from, to, convertTime());
    }
}

class Airport{
    private String name;
    private String country;
    private String code;
    private int passengers;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getPassengers() {
        return passengers;
    }
}

class Airports{
    private List<Airport> airports = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();

    public void addAirport(String name, String country, String code, int passengers){
        Airport airport = new Airport(name, country, code, passengers);
        airports.add(airport);
    }
    public void addFlights(String from, String to, int time, int duration){
        Flight flight = new Flight(from, to, time, duration);
        flights.add(flight);
    }
    public void showFlightsFromAirport(String code){
        List<Flight> theFlights = new ArrayList<>();
        Airport a = airports.stream()
                .filter(r->r.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
        theFlights = flights.stream()
                .filter(r->r.getFrom()
                        .equals(code))
                .collect(Collectors.toList());
        theFlights = theFlights.stream()
                .sorted(Comparator.comparing(Flight::getTo)
                        .thenComparing(Flight::getTime))
                .collect(Collectors.toList());
        String s = String.format("%s (%s)\n%s\n%d",a.getName(),a.getCode(),a.getCountry(),a.getPassengers());
        System.out.println(s);
        for(int i=0; i<theFlights.size(); i++){
            System.out.println(i+1 +". " + theFlights.get(i).toString());
        }
    }
    public void showDirectFlightsFromTo(String from, String to){
        List<Flight> theFlights = new ArrayList<>();
        theFlights = flights.stream()
                .filter(r->r.getFrom().equals(from) && r.getTo().equals(to))
                .collect(Collectors.toList());
        if(theFlights.isEmpty()){
            System.out.println("No flights from " + from + " to " + to);
            return;
        }
        theFlights = theFlights.stream()
                .sorted(Comparator.comparing(Flight::getTime))
                .collect(Collectors.toList());
        for(Flight f : theFlights){
            System.out.println(f.toString());
        }
    }
    public void showDirectFlightsTo(String to){
        List<Flight> theFlights = new ArrayList<>();
        theFlights = flights.stream().filter(r->r.getTo().equals(to)).collect(Collectors.toList());
        if(theFlights.isEmpty()){
            System.out.println("No flights to " + to);
            return;
        }
        theFlights = theFlights.stream().sorted(Comparator.comparing(Flight::getTime).thenComparing(Flight::getDuration)).collect(Collectors.toList());
        for(Flight f : theFlights){
            System.out.println(f.toString());
        }

    }
}
public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde

