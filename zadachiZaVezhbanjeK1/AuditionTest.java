package NP.zadachiZaVezhbanjeK1;

import java.util.*;
import java.util.stream.Collectors;

class Participant{
    private String name;
    private String code;
    private int age;

    public Participant(String name, String code, int age) {
        this.name = name;
        this.code = code;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getAge() {
        return age;
    }
}

class Audition{
    private Map<String, List<Participant>> hash;

    public Audition() {
        this.hash = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age){
        hash.putIfAbsent(city, new ArrayList<>());
        List<Participant> list = hash.get(city);
        boolean exist = list.stream().anyMatch(t->t.getCode().equals(code));
        if(!exist){
            Participant p = new Participant(name, code, age);
            list.add(p);
        }
    }

    public void listByCity(String city){
        List<Participant> list = hash.get(city);
        List<Participant> newList = list
                .stream().sorted(Comparator
                        .comparing(Participant::getName)
                        .thenComparing(Participant::getAge)
                        .thenComparing(Participant::getCode))
                .collect(Collectors.toList());
        for(Participant p : newList){
            System.out.printf("%s %s %d\n",
                    p.getCode(), p.getName(), p.getAge());
        }
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}