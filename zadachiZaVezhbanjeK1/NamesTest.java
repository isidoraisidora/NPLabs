package NP.zadachiZaVezhbanjeK1;

import java.util.*;
import java.util.stream.Collectors;

class Names{
    private final Map<String,Integer> nameCounts;

    public Names() {
        nameCounts = new HashMap<>();
    }

    public void addName(String name){
        nameCounts.put(name, nameCounts.getOrDefault(name, 0)+1);
    }

    public void printN(int n){
        List<Map.Entry<String,Integer>> filtered = nameCounts.entrySet().stream()
                .filter(e->e.getValue() >= n)
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        for(Map.Entry<String,Integer> entry : filtered){
            String name = entry.getKey();
            int count = entry.getValue();
            long uniqueLetters = name.toLowerCase().chars().filter(Character::isLetter)
                    .distinct().count();
            System.out.println(name + " (" + count + ") " + uniqueLetters);
        }
    }
    public String findName(int len, int x){
        List<String> filtered = nameCounts.keySet()
                .stream()
                .filter(name -> name.length() < len)
                .sorted()
                .collect(Collectors.toList());

        if(filtered.isEmpty()) return null;

        int index = x% filtered.size();
        return filtered.get(index);
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde