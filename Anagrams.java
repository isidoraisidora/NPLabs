package NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) throws IOException {
        // Vasiod kod ovde
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Map<String, List<String>> map = new LinkedHashMap<>();
        String line;
        while((line=reader.readLine())!=null){
            char[] chars = line.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k->new ArrayList<>()).add(line);
        }

        reader.close();

        for(List<String> l: map.values()) {
            if (l.size() >= 5) {
                Collections.sort(l);
                System.out.println(String.join(" ", l));
            }
        }
    }
}
