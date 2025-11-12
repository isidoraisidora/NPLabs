package NP.zadachiZaVezhbanjeK1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class MinMax<T extends Comparable<T>> {
    private T min;
    private T max;
    private List<T> occurances;


    public MinMax() {
        this.occurances = new ArrayList<>();
    }

    public void update(T element) {
        occurances.add(element);
        if(min==null && max==null){
            min = element;
            max = element;
            return;
        }

        if(min!=null && element.compareTo(min) < 0){
            min = element;
        }

        else if(max!=null && element.compareTo(max) > 0){
            max = element;
        }
    }

    public T max() {
        return max;
    }

    public T min() {
        return min;
    }

    public int countOccurences(){
        if(min==null || max==null){
            return 0;
        }
        int count = 0;
        for(T e : occurances){
            if(e.compareTo(min)!=0 && e.compareTo(max)!=0) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return min + " " + max + " " + countOccurences() + "\n";
    }
}


public class MinAndMax{


    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}