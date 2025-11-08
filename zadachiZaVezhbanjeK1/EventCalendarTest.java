package NP.zadachiZaVezhbanjeK1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class WrongDateException extends Exception{
    public WrongDateException(String message) {
        super(message);
    }
}

class Event{
    private String name;
    private String location;
    private Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public long getTime(){
        return date.getTime();
    }

    public String getName() {
        return name;
    }
    public int getYear(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        return String.format("%s at %s, %s", df.format(date), location, name);
    }
}

class EventCalendar{
    private List<Event> events;
    private int year;

    public EventCalendar(int year) {
        this.year = year;
        events = new ArrayList<>();
    }
    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Event event = new Event(name,location, date);
        if(event.getYear()==year) events.add(new Event(name,location,date));
        else throw new WrongDateException("Wrong date: " + date);
    }
    public void listEvents(Date date){
        Calendar target = Calendar.getInstance();
        target.setTime(date);

        List<Event> eventsDate = events.stream()
                .filter(e->{
                    Calendar c = Calendar.getInstance();
                    c.setTime(e.getDate());
                    return c.get(Calendar.YEAR) == target.get(Calendar.YEAR)
                            && c.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR);
                }).sorted(Comparator.comparing(Event::getTime).thenComparing(Event::getName)).collect(Collectors.toList());
        if(eventsDate.isEmpty()) System.out.println("No events on this day!");
        for(Event e : eventsDate){
            System.out.println(e);
        }
    }

    public void listByMonth(){
        for(int i=0; i<12; i++){
            final int m  = i;
            long count = events.stream().filter(e->{
                Calendar cal = Calendar.getInstance();
                cal.setTime(e.getDate());
                return cal.get(Calendar.MONTH) == m;
            }).count();
            System.out.println((i+1) + " : " + count);
        }
    }

}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
