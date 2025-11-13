package NP.zadachiZaVezhbanjeK1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super("InvalidTimeException: " + message);
    }
}
class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String message) {
        super("UnsupportedFormatException: " + message);
    }
}

class TimeTable {
    private final List<String> list;

    public TimeTable() {
        this.list = new ArrayList<>();
    }

    public static int getTime(String str) {
        String[] parts = str.split(":");
        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        return hour * 60 + min;
    }

    public void readTimes(InputStream inputStream) throws IOException, UnsupportedFormatException, InvalidTimeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            for (String token : tokens) {
                if (!isValidFormat(token))
                    throw new UnsupportedFormatException(token);

                token = token.replace(".", ":");
                if (!isValidTime(token))
                    throw new InvalidTimeException(token);

                list.add(token);
            }
        }
    }

    public void writeTimes(OutputStream outputStream, TimeFormat tf) {
        PrintWriter writer = new PrintWriter(outputStream);
        list.stream()
                .sorted(Comparator.comparing(TimeTable::getTime))
                .forEach(time -> {
                    if (tf == TimeFormat.FORMAT_24)
                        writer.printf("%5s%n", time);
                    else
                        writer.printf("%s%n", toAMPM(time));
                });
        writer.flush();
    }

    private boolean isValidFormat(String str) {
        return str.matches("\\d+[:.]\\d+");
    }

    private boolean isValidTime(String str) {
        String[] parts = str.split("[:.]");
        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        return hour >= 0 && hour <= 23 && min >= 0 && min <= 59;
    }

    public static String toAMPM(String str) {
        String[] parts = str.split(":");
        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        String ampm;

        if (hour == 0) {
            hour = 12;
            ampm = "AM";
        } else if (hour >= 1 && hour <= 11) {
            ampm = "AM";
        } else if (hour == 12) {
            ampm = "PM";
        } else {
            hour -= 12;
            ampm = "PM";
        }

        return String.format("%2d:%02d %s", hour, min, ampm);
    }
}


public class TimesTest {

    public static void main(String[] args) throws IOException, UnsupportedFormatException, InvalidTimeException {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (IOException | UnsupportedFormatException | InvalidTimeException e){
            System.out.println(e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
